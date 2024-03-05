package com.shoesapp.role.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    private Long roleId;
    private String roleName;

}
