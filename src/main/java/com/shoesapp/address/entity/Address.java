package com.shoesapp.address.entity;

import com.shoesapp.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long addressId;

    @NotBlank
    @Size(min = 4, message = "City name must contain atleast 4 characters")
    private String city;

    @NotBlank
    @Size(min = 5, message = "Building name must contain atleast 5 characters")
    private String buildingName;

    @NotBlank
    @Size(min = 6, message = "Pincode must contain atleast 6 characters")
    private String zip;

    @NotBlank
    @Size(min = 5, message = "Street name must contain atleast 5 characters")
    private String street;

    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();


    public Address() {

    }

    public Address(String city, String buildingName, String zip, String street){
        this.city = city;
        this.buildingName = buildingName;
        this.zip = zip;
        this.street = street;
    }


}
