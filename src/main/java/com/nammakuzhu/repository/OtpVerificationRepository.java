package com.nammakuzhu.repository;

import com.nammakuzhu.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nammakuzhu.enums.OtpPurpose;
import java.util.Optional;

public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {

    Optional<OtpVerification> findTopByPhoneNumberOrderByIdDesc(String phoneNumber);

    Optional<OtpVerification> findTopByPhoneNumberAndPurposeAndVerifiedTrueOrderByIdDesc(
            String phoneNumber,
            OtpPurpose purpose
    );

    Optional<OtpVerification>
    findTopByPhoneNumberAndPurposeOrderByIdDesc(
            String phoneNumber,
            OtpPurpose purpose
    );

}