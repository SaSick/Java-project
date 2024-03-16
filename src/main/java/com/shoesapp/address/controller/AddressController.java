package com.shoesapp.address.controller;

import com.shoesapp.address.dto.AddressDTO;
import com.shoesapp.address.dto.AddressResponse;
import com.shoesapp.address.entity.Address;
import com.shoesapp.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/address")
    public ResponseEntity<AddressDTO> createAddress(
            @RequestBody AddressDTO addressDTO
    ){
        AddressDTO createdAddress = addressService.createAddress(addressDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    @GetMapping("/addresses")
    public AddressResponse<Address> getAllAddresses(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        Page<Address> pagedResponse = addressService.getAllAddresses(PageRequest.of(page, size));

        AddressResponse<Address> addressResponse = new AddressResponse<>();

        addressResponse.setData(pagedResponse.getContent());
        addressResponse.setTotal(pagedResponse.getTotalElements());

        return addressResponse;
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<AddressDTO> getAddress(
            @PathVariable("addressId") Long addressId
    ){
        AddressDTO addressDTO = addressService.getAddressById(addressId);
        return ResponseEntity.status(HttpStatus.OK).body(addressDTO);
    }

    @PutMapping("/address/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(
            @PathVariable("addressId") Long addressId,
            @RequestBody AddressDTO addressRequest
    ){
        AddressDTO addressDTO = addressService.updateAddress(addressId, addressRequest);
        return ResponseEntity.status(HttpStatus.OK).body(addressDTO);
    }

    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<AddressDTO> deleteAddress(
            @PathVariable("addressId") Long addressId
    ){
        addressService.deleteAddressById(addressId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
