package co.yabx.kyc.app.service;

import co.yabx.kyc.app.entities.OTP;

public interface EmailService {

	void sendOTP(OTP otp, String mail);

}
