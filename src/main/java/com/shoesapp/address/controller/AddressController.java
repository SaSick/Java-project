package com.shoesapp.address.controller;

import com.shoesapp.address.dto.AddressDTO;
import com.shoesapp.address.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/address")
    public ResponseEntity<AddressDTO> createAddress(
           @Valid @RequestBody AddressDTO addressDTO
    ){
        AddressDTO createdAddress = addressService.createAddress(addressDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAllAddresses(
    ){

        List<AddressDTO> addressesDTO = addressService.getAllAddresses();
        return ResponseEntity.status(HttpStatus.OK).body(addressesDTO);
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddress(
            @PathVariable("addressId") Long addressId
    ){
        AddressDTO addressDTO = addressService.getAddressById(addressId);
        return ResponseEntity.status(HttpStatus.OK).body(addressDTO);
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(
            @PathVariable("addressId") Long addressId,
            @RequestBody AddressDTO addressRequest
    ){
        AddressDTO addressDTO = addressService.updateAddress(addressId, addressRequest);
        return ResponseEntity.status(HttpStatus.OK).body(addressDTO);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(
            @PathVariable("addressId") Long addressId
    ){
        String status = addressService.deleteAddressById(addressId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
