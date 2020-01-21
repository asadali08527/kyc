package co.yabx.kyc.app.service;

import co.yabx.kyc.app.enums.KycStatus;

public interface RedisService {

	Integer getProfileCount(String key, KycStatus kycStatus);

	Integer cacheProfileCount(KycStatus kycStatus);
}
