package com.shoesapp.address.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private Long addressId;
    private String city;
    private String buildingName;
    private String zip;
    private String street;
}
