package com.nammakuzhu.entity;

import com.nammakuzhu.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true )
    private String email;
    @Column(unique = true, nullable = false)
    private String phoneNumber;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
