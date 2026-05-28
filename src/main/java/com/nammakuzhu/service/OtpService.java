package com.nammakuzhu.service;

import com.nammakuzhu.entity.OtpVerification;
import com.nammakuzhu.enums.OtpPurpose;
import com.nammakuzhu.repository.OtpVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nammakuzhu.dto.VerifyOtpRequest;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpVerificationRepository otpVerificationRepository;

    public String generateOtp(String phoneNumber, OtpPurpose purpose) {

        OtpVerification latestOtp =
                otpVerificationRepository
                        .findTopByPhoneNumberAndPurposeOrderByIdDesc(
                                phoneNumber,
                                purpose
                        )
                        .orElse(null);

        if (latestOtp != null) {

            LocalDateTime nextAllowedTime =
                    latestOtp.getExpiryTime().minusMinutes(4);

            if (LocalDateTime.now().isBefore(nextAllowedTime)) {

                return "Please wait before requesting another OTP";
            }
        }

        Random random = new Random();

        int otpNumber = 100000 + random.nextInt(900000);

        String otp = String.valueOf(otpNumber);

        OtpVerification otpVerification = new OtpVerification();

        otpVerification.setPhoneNumber(phoneNumber);
        otpVerification.setOtp(otp);
        otpVerification.setPurpose(purpose);

        otpVerification.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        otpVerification.setVerified(false);

        otpVerificationRepository.save(otpVerification);

        System.out.println("OTP : " + otp);

        return "OTP Sent Successfully";
    }

    public String verifyOtp(VerifyOtpRequest request) {

        OtpVerification otpVerification =
                otpVerificationRepository
                        .findTopByPhoneNumberOrderByIdDesc(
                                request.getPhoneNumber()
                        )
                        .orElse(null);

        if (otpVerification == null) {
            return "OTP not found";
        }

        if (!otpVerification.getOtp().equals(request.getOtp())) {
            return "Invalid OTP";
        }

        if (otpVerification.isVerified()) {
            return "OTP already used";
        }

        if (otpVerification.getExpiryTime()
                .isBefore(LocalDateTime.now())) {

            return "OTP expired";
        }

        otpVerification.setVerified(true);

        otpVerificationRepository.save(otpVerification);

        return "OTP verified successfully";
    }

}