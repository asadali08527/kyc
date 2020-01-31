package co.yabx.kyc.app.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.entities.DeviceInformations;
import co.yabx.kyc.app.repositories.DeviceInformationsRepository;
import co.yabx.kyc.app.service.UserDeviceService;

/**
 * 
 * @author Asad.ali
 *
 */
@Service
public class UserDeviceServiceImpl implements UserDeviceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDeviceServiceImpl.class);

	@Autowired
	private DeviceInformationsRepository deviceInformationsRepository;

	@Override
	@Transactional
	public DeviceInformations saveDeviceTokenInMysql(String token, String deviceType, String deviceId,
			String instanceId, String voipToken, String deviceModel, String deviceBrand, String deviceManufacturer) {
		DeviceInformations deviceInformations = deviceInformationsRepository.findByDeviceId(deviceId);
		if (deviceInformations != null && deviceInformations.getDeviceToken() != null
				&& deviceInformations.getDeviceToken().equals(token) && deviceInformations.isActive()) {
			LOGGER.debug("deviceToken already updated {}", deviceId);
			return deviceInformations;
		}

		if (deviceInformations == null) {
			LOGGER.debug("request insert deviceToken {}", deviceId);
			deviceInformations = new DeviceInformations();
			deviceInformations.setDeviceId(deviceId);
		}

		if (instanceId == null) {
			instanceId = "UNKNOWN";
		}

		deviceInformations.setVoipToken(voipToken);
		deviceInformations.setInstanceId(instanceId);
		deviceInformations.setActive(true);
		deviceInformations.setDeviceType(DeviceInformations.DeviceType.valueOf(deviceType));
		deviceInformations.setDeviceToken(token);
		if (deviceModel != null) {
			deviceInformations.setDeviceModel(deviceModel);
		}
		if (deviceBrand != null) {
			deviceInformations.setDeviceBrand(deviceBrand);
		}
		if (deviceManufacturer != null) {
			deviceInformations.setDeviceManufacturer(deviceManufacturer);
		}

		return deviceInformationsRepository.saveAndFlush(deviceInformations);
	}

	@Override
	public String updateExpiredDeviceTokenInMysql(String token, String deviceType, long expiredDate) {
		List<DeviceInformations> deviceTokenList = deviceInformationsRepository.findByDeviceToken(token);
		String deviceId = "";
		for (DeviceInformations deviceToken : deviceTokenList) {
			if (deviceToken != null && deviceToken.getDeviceToken() != null && deviceToken.isActive()
					&& deviceToken.getUpdatedAt().getTime() < expiredDate) {
				deviceToken.setActive(false);
				deviceInformationsRepository.save(deviceToken);
				return deviceToken.getDeviceId();
			} else {
				LOGGER.warn("can not set expired  deviceToken {}| expiredDate {}", token, expiredDate);
			}
			deviceId = deviceToken.getDeviceId();
		}
		return deviceId;
	}

}