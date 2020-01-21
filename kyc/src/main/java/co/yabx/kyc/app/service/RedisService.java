package co.yabx.kyc.app.service;

import co.yabx.kyc.app.enums.KycStatus;

public interface RedisService {

	void cacheProfileCount(String msisdn, KycStatus kycStatus);

	Integer getProfileCount(String string, String valueOf);
}
