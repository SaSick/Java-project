package com.shoesapp.user.service.impl;

import com.shoesapp.address.dto.AddressDTO;
import com.shoesapp.address.entity.Address;
import com.shoesapp.address.repository.AddressRepository;
import com.shoesapp.cart.dto.CartDTO;
import com.shoesapp.cart.service.CartService;
import com.shoesapp.cartItem.entity.CartItem;
import com.shoesapp.exception.ResourceNotFoundException;
import com.shoesapp.product.dto.ProductDTO;
import com.shoesapp.user.dto.UserDTO;
import com.shoesapp.user.entity.User;
import com.shoesapp.user.repository.UserRepository;
import com.shoesapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CartService cartService;
    @Override
    public Page<User> getAllUsers(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = findById(userId);
        UserDTO userDTO = mapper.map(user, UserDTO.class);
        userDTO.setAddressDTO(mapper.map(user.getAddresses().stream().findFirst().get(), AddressDTO.class));
        CartDTO cartDTO = mapper.map(user.getCart(), CartDTO.class);
        List<ProductDTO> products = user.getCart().getCartItems()
                .stream().map(p -> mapper.map(p.getProduct(), ProductDTO.class)).toList();

        userDTO.setCartDTO(cartDTO);

        userDTO.getCartDTO().setProductDTOS(products);

        return userDTO;
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User userFromDB = findById(userId);

        userFromDB.setFirstName(userDTO.getFirstname());
        userFromDB.setLastName(userDTO.getLastname());
        userFromDB.setEmail(userDTO.getEmail());
        userFromDB.setPassword(userDTO.getPassword());
        userFromDB.setPhoneNumber(userDTO.getPhoneNumber());

        if(userDTO.getAddressDTO() != null){
            String city = userDTO.getAddressDTO().getCity();
            String buildingName = userDTO.getAddressDTO().getBuildingName();
            String zip = userDTO.getAddressDTO().getZip();
            String street = userDTO.getAddressDTO().getStreet();

            Address address = addressRepository.findByCityAndBuildingNameAndZipAndStreet(city, buildingName, zip, street);

            if(address == null){
                address = new Address(city, buildingName, zip, street);

                addressRepository.save(address);

                userFromDB.setAddresses(List.of(address));
            }
        }
        userDTO = mapper.map(userFromDB, UserDTO.class);

        userDTO.setAddressDTO(mapper.map(userFromDB.getAddresses().stream().findFirst().get(), AddressDTO.class));

        CartDTO cart = mapper.map(userFromDB.getCart(), CartDTO.class);

        List<ProductDTO> productDTOS = userFromDB.getCart().getCartItems()
                .stream().map(p -> mapper.map(p.getProduct(), ProductDTO.class)).toList();

        userDTO.setCartDTO(cart);
        userDTO.getCartDTO().setProductDTOS(productDTOS);

        return userDTO;

    }

    @Override
    public String deleteUserById(Long userId) {
        User user = findById(userId);

        List<CartItem> cartItems = user.getCart().getCartItems();
        Long cartId = user.getCart().getCartId();

        cartItems.forEach(item -> {

            Long productId = item.getProduct().getProductId();

            cartService.deleteProductFromCart(cartId, productId);
        });

        userRepository.delete(user);

        return "User with Id: " + userId + " deleted successfully!";
    }


    private User findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User with Id: " + userId + " not found!"));
    }
}
