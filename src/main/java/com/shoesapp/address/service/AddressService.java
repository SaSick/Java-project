package com.shoesapp.address.service;

import com.shoesapp.address.dto.AddressDTO;
import com.shoesapp.address.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface AddressService {

    AddressDTO createAddress(AddressDTO addressDTO);

    List<AddressDTO> getAllAddresses();

    AddressDTO getAddressById(Long addressId);

    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);

    String deleteAddressById(Long addressId);
}
