package com.nammakuzhu.entity;

import com.nammakuzhu.enums.OtpPurpose;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp_verifications")
@Getter
@Setter
public class OtpVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    private String otp;

    @Enumerated(EnumType.STRING)
    private OtpPurpose purpose;

    private LocalDateTime expiryTime;

    private boolean verified;
}