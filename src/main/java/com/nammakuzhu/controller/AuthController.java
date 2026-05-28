package com.nammakuzhu.controller;

import com.nammakuzhu.dto.LoginResponse;
import com.nammakuzhu.dto.RegisterRequest;
import com.nammakuzhu.dto.LoginRequest;
import com.nammakuzhu.service.AuthService;
import com.nammakuzhu.service.OtpService;
import com.nammakuzhu.dto.OtpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nammakuzhu.dto.VerifyOtpRequest;
import com.nammakuzhu.dto.ResetPasswordRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private OtpService otpService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        if (response == null) {
            return ResponseEntity
                    .status(401)
                    .body("Invalid phone number or password");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/send-otp")
    public String sendOtp(@RequestBody OtpRequest request) {
        return otpService.generateOtp(
                request.getPhoneNumber(),
                request.getPurpose()
        );
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody VerifyOtpRequest request) {
        return otpService.verifyOtp(request);
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {
        return authService.resetPassword(request);
    }

}
