package co.yabx.kyc.app.service;

import co.yabx.kyc.app.dto.DsrProfileDTO;
import co.yabx.kyc.app.dto.ResponseDTO;
import co.yabx.kyc.app.dto.VerifyOtpDTO;

public interface DSRService {

	ResponseDTO login(String msisdn);

	ResponseDTO verifyOTP(VerifyOtpDTO verifyOtpDTO);

	ResponseDTO submitDsrProfile(DsrProfileDTO dsrProfileDTO);

}
