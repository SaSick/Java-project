package com.shoesapp.address.service;

import com.shoesapp.address.dto.AddressDTO;
import com.shoesapp.address.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface AddressService {

    AddressDTO createAddress(AddressDTO addressDTO);

    Page<Address> getAllAddresses(PageRequest of);

    AddressDTO getAddressById(Long addressId);

    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);

    String deleteAddressById(Long addressId);
}
