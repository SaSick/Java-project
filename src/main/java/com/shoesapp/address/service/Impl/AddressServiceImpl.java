package com.shoesapp.address.service.impl;

import com.shoesapp.address.dto.AddressDTO;
import com.shoesapp.address.entity.Address;
import com.shoesapp.address.repository.AddressRepository;
import com.shoesapp.address.service.AddressService;
import com.shoesapp.exception.APIException;
import com.shoesapp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper mapper;

    public AddressDTO createAddress(AddressDTO addressDTO) {

        Address address = addressRepository.findByAddressIdAndCityAndBuildingNameAndZipAndStreet(
                addressDTO.getAddressId(),
                addressDTO.getCity(),
                addressDTO.getBuildingName(),
                addressDTO.getZip(),
                addressDTO.getStreet()
        );

        if(address != null){
            throw new APIException("Current address with Id already exists: " + addressDTO.getAddressId());
        }

        address = mapper.map(addressDTO, Address.class);
        addressRepository.save(address);
        return mapper.map(address, AddressDTO.class);
    }


    public List<AddressDTO> getAllAddresses() {
        List<Address> addressesFromDB = addressRepository.findAll();
        return addressesFromDB.stream().map(address -> mapper.map(address, AddressDTO.class)).toList();
    }


    public AddressDTO getAddressById(Long addressId) {
        Address addressFromDB = findById(addressId);
        return mapper.map(addressFromDB, AddressDTO.class);
    }


    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        Address addressFromDB = findById(addressId);
        addressFromDB.setAddressId(addressId);
        addressFromDB.setCity(addressDTO.getCity());
        addressFromDB.setZip(addressDTO.getZip());
        addressFromDB.setStreet(addressDTO.getStreet());
        addressFromDB.setBuildingName(addressDTO.getBuildingName());

        addressRepository.save(addressFromDB);
        return mapper.map(addressFromDB, AddressDTO.class);
    }


    public String deleteAddressById(Long addressId) {
        Address addressFromDB = findById(addressId);
        addressRepository.delete(addressFromDB);
        return "Address with ID: " + addressId + " deleted successfully!";
    }

    private Address findById(Long addressId){
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address with id: " + addressId + " not found!"));
    }

}
