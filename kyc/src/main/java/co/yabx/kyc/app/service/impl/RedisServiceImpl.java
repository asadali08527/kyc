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
	public Integer cacheProfileCount(KycStatus kycStatus) {
		Integer count = accountStatusesRepository.findByCountByKycStatus(kycStatus);
		redisRepository.saveStatus(kycStatus + "_PROFILE_COUNT_FOR", String.valueOf(kycStatus.ordinal()),
				count == null ? 0 : count);
		return count;
	}

	@Override
	public Integer getProfileCount(String key, KycStatus kycStatus) {
		Integer profiles = redisRepository.findCountByStatus(key, String.valueOf(kycStatus.ordinal()));
		if (profiles == null || profiles == 0)
			return cacheProfileCount(kycStatus);
		return profiles;
	}

}
