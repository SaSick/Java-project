package com.shoesapp.role.entity;

import com.shoesapp.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    private Long roleId;
    private String roleName;

}
