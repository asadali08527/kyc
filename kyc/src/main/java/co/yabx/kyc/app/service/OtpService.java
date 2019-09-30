package co.yabx.kyc.app.service;

import java.util.Date;

import co.yabx.kyc.app.entities.OTP;
import co.yabx.kyc.app.enums.OtpType;

/**
 * 
 * @author Asad.ali
 *
 */
public interface OtpService {

	OTP generateAndPersistOTP(Long user, OtpType otpType, Date expiryTime, String otpGroup);

}
