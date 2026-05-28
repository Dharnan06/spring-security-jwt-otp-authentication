package com.nammakuzhu.dto;

import com.nammakuzhu.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// data transfer object

public class RegisterRequest {

    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private Role role;

}
