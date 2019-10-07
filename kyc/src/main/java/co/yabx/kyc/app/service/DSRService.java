package co.yabx.kyc.app.service;

import co.yabx.kyc.app.dto.DsrProfileDTO;
import co.yabx.kyc.app.dto.ResponseDTO;
import co.yabx.kyc.app.dto.VerifyOtpDTO;
import co.yabx.kyc.app.entities.AuthInfo;
import co.yabx.kyc.app.fullKyc.entity.DSRUser;

public interface DSRService {

	ResponseDTO login(String msisdn);

	ResponseDTO verifyOTP(VerifyOtpDTO verifyOtpDTO);

	ResponseDTO submitDsrProfile(DsrProfileDTO dsrProfileDTO);

	void updateAuthInfo(DSRUser dsrUser, AuthInfo authInfo);

	ResponseDTO logout(String msisdn);

}
