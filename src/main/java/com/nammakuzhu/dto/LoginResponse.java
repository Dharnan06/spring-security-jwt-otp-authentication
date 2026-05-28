package com.nammakuzhu.dto;

import com.nammakuzhu.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    private Long id;
    private String fullName;
    private String phoneNumber;
    private Role role;
    private String token;

}