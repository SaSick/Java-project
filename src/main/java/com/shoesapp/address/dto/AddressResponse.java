package com.shoesapp.address.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddressResponse<T> {
    private List<T> data;
    private long total;
}
