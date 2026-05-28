package com.nammakuzhu.dto;

import com.nammakuzhu.enums.OtpPurpose;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpRequest {

    private String phoneNumber;
    private OtpPurpose purpose;
}