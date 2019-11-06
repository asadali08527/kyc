package co.yabx.kyc.app.service;

import co.yabx.kyc.app.entities.OTP;
import co.yabx.kyc.app.enums.ActionType;

public interface EmailService {

	void sendOTP(OTP otp, String mail, ActionType actionType);

}
