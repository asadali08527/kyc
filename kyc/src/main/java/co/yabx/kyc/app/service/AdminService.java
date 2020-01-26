package co.yabx.kyc.app.service;

import java.util.Map;

import co.yabx.kyc.app.dto.DsrRetailerRegistrationDto;
import co.yabx.kyc.app.dto.UserDto;
import co.yabx.kyc.app.entities.AuthInfo;
import co.yabx.kyc.app.fullKyc.entity.DSRUser;

public interface AdminService {

	Map<String, String> getAuthToken(String token);

	Map<String, String> prepareTokenAndKey(DSRUser dsrUser, String msisdn);

	void resetYabxToken(AuthInfo authInfo);

	void registerDSR(DsrRetailerRegistrationDto dsrRetailerRegistrationDto);

	Map<String, String> registerRM(UserDto userDto);
}
