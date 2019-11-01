package co.yabx.kyc.app.service;

import co.yabx.kyc.app.entities.DeviceInformations;

public interface UserDeviceService {

	DeviceInformations saveDeviceTokenInMysql(String token, String deviceType, String deviceId, String instanceId, String voipToken,
			String deviceModel, String deviceBrand, String deviceManufacturer);

	String updateExpiredDeviceTokenInMysql(String token, String deviceType, long expiredDate);

}
