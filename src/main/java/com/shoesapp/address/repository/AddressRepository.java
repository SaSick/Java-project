package com.shoesapp.address.repository;

import com.shoesapp.address.dto.AddressDTO;
import com.shoesapp.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository <Address, Long> {
    Address findByAddressIdAndCityAndBuildingNameAndZipAndStreet(Long addressId, String city, String buildingName, String zip, String Street);
}
