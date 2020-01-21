package co.yabx.kyc.app.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.cache.RedisRepository;
import co.yabx.kyc.app.enums.KycStatus;
import co.yabx.kyc.app.fullKyc.repository.DSRUserRepository;
import co.yabx.kyc.app.miniKyc.entity.AccountStatuses;
import co.yabx.kyc.app.miniKyc.repository.AccountStatusesRepository;
import co.yabx.kyc.app.repositories.OtpRepository;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.RedisService;
import co.yabx.kyc.app.util.MailSource;

/**
 * 
 * @author Asad.ali
 *
 */
@Service
public class RedisServiceImpl implements RedisService {

	@Autowired
	private RedisRepository redisRepository;

	@Autowired
	private AccountStatusesRepository accountStatusesRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisServiceImpl.class);

	@Override
	@Async
	public void cacheProfileCount(String msisdn, KycStatus kycStatus) {
		Integer count = accountStatusesRepository.findByCountByKycStatus(kycStatus);
		redisRepository.saveStatus(kycStatus + "PROFILE_COUNT_FOR", String.valueOf(kycStatus.ordinal()),
				count == null ? 1 : count + 1);
	}

	@Override
	public Integer getProfileCount(String key, String hKey) {
		return redisRepository.findCountByStatus(key, hKey);
	}

}
