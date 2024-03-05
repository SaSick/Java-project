package com.shoesapp.address.entity;

import com.shoesapp.user.entity.User;
import jakarta.persistence.*;
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
    private String city;
    private String buildingName;
    private String zip;
    private String street;

    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();
}
