package co.yabx.kyc.app.service;

import co.yabx.kyc.app.dto.DsrProfileDTO;
import co.yabx.kyc.app.dto.ResponseDTO;
import co.yabx.kyc.app.dto.VerifyOtpDTO;
import co.yabx.kyc.app.entities.AuthInfo;
import co.yabx.kyc.app.fullKyc.entity.DSRUser;

public interface DSRService {

	ResponseDTO login(String msisdn);

	ResponseDTO submitDsrProfile(DsrProfileDTO dsrProfileDTO);

	void updateAuthInfo(DSRUser dsrUser, AuthInfo authInfo);

	ResponseDTO logout(String msisdn);

	ResponseDTO generateMailOTP(String mail);

	ResponseDTO verifyPhoneOTP(VerifyOtpDTO verifyOtpDTO);

	ResponseDTO verifyMail(VerifyOtpDTO verifyOtpDTO);

	ResponseDTO getDsrProfile(String msisdn, String locale);

}
