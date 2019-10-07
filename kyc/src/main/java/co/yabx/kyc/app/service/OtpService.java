package co.yabx.kyc.app.service;

import java.util.Date;

import co.yabx.kyc.app.entities.OTP;
import co.yabx.kyc.app.enums.OtpGroup;
import co.yabx.kyc.app.enums.OtpType;
import co.yabx.kyc.app.fullKyc.entity.DSRUser;

/**
 * 
 * @author Asad.ali
 *
 */
public interface OtpService {

	OTP generateAndPersistOTP(Long user, OtpType otpType, Date expiryTime, OtpGroup otpGroup);

	DSRUser verifyOtp(String dsrMSISDN, String otp);

	String findOtpByMsisdn(String msisdn);

}
