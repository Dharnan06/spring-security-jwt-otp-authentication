package com.nammakuzhu.service;

import com.nammakuzhu.dto.LoginRequest;
import com.nammakuzhu.dto.LoginResponse;
import com.nammakuzhu.dto.RegisterRequest;
import com.nammakuzhu.entity.User;
import com.nammakuzhu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.nammakuzhu.enums.OtpPurpose;
import com.nammakuzhu.repository.OtpVerificationRepository;
import com.nammakuzhu.dto.ResetPasswordRequest;
import com.nammakuzhu.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OtpVerificationRepository otpVerificationRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(RegisterRequest request) {

        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            return "Mobile number already registered";
        }

        boolean isOtpVerified = otpVerificationRepository
                .findTopByPhoneNumberAndPurposeAndVerifiedTrueOrderByIdDesc(
                        request.getPhoneNumber(),
                        OtpPurpose.REGISTRATION
                )
                .isPresent();

        if (!isOtpVerified) {
            return "Please verify OTP before registration";
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);

        return "User Registered Successfully";
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByPhoneNumber(request.getPhoneNumber())
                .orElse(null);

        if (user == null) {
            return null;
        }

        boolean isPasswordMatched = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!isPasswordMatched) {
            return null;
        }
        String token = jwtUtil.generateToken(user.getPhoneNumber());

        return new LoginResponse(
                user.getId(),
                user.getFullName(),
                user.getPhoneNumber(),
                user.getRole(),
                token
        );

    }

    public String resetPassword(ResetPasswordRequest request) {

        User user = userRepository
                .findByPhoneNumber(request.getPhoneNumber())
                .orElse(null);

        if (user == null) {
            return "User not found";
        }

        boolean isOtpVerified = otpVerificationRepository
                .findTopByPhoneNumberAndPurposeAndVerifiedTrueOrderByIdDesc(
                        request.getPhoneNumber(),
                        OtpPurpose.FORGOT_PASSWORD
                )
                .isPresent();

        if (!isOtpVerified) {
            return "Please verify OTP before resetting password";
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        return "Password reset successfully";
    }

}