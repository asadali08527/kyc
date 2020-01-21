package co.yabx.kyc.app.service.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.cache.RedisRepository;
import co.yabx.kyc.app.dto.DsrRetailerRegistrationDto;
import co.yabx.kyc.app.dto.UserDto;
import co.yabx.kyc.app.entities.AuthInfo;
import co.yabx.kyc.app.enums.Relationship;
import co.yabx.kyc.app.enums.UserType;
import co.yabx.kyc.app.fullKyc.entity.DSRUser;
import co.yabx.kyc.app.fullKyc.entity.Retailers;
import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.fullKyc.entity.UserRelationships;
import co.yabx.kyc.app.fullKyc.repository.DSRUserRepository;
import co.yabx.kyc.app.fullKyc.repository.UserRelationshipsRepository;
import co.yabx.kyc.app.fullKyc.repository.UserRepository;
import co.yabx.kyc.app.repositories.AuthInfoRepository;
import co.yabx.kyc.app.security.SecurityUtils;
import co.yabx.kyc.app.service.AdminService;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.AuthInfoService;
import co.yabx.kyc.app.service.DSRService;
import co.yabx.kyc.app.util.UtilHelper;

/**
 * 
 * @author Asad.ali
 *
 */
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AuthInfoService authInfoService;

	@Autowired
	private AuthInfoRepository authInfoRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRelationshipsRepository userRelationshipsRepository;

	@Autowired
	private DSRService dsrService;

	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private RedisRepository redisRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Override
	public Map<String, String> getAuthToken(String msisdn) {
		AuthInfo authInfo = authInfoRepository.findByMsisdn(msisdn);
		if (authInfo == null || authInfo.getYabxToken() == null) {
			return null;
		}
		String yabxToken = authInfo.getYabxToken();
		Map<String, String> jsonResponse = new HashMap<String, String>();
		try {
			jsonResponse.put("YABX_ACCESS_TOKEN", SecurityUtils.encript(yabxToken));
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			LOGGER.info("exception while encripting token for msisdn={}", msisdn);
			e.printStackTrace();
		}
		return jsonResponse;
	}

	@Override
	public Map<String, String> prepareTokenAndKey(DSRUser dsrUser, String userName) {
		String yabxToken = null;
		Map<String, String> jsonResponse = new HashMap<String, String>();
		try {
			String uuid = UUID.randomUUID().toString();
			/*
			 * final String salt = UtilHelper
			 * .getNumericString(appConfigService.getIntProperty("AES_ENCRYPTION_LENGTH",
			 * 16));
			 */
			yabxToken = SecurityUtils.encript(uuid);
			if (yabxToken != null) {
				AuthInfo authInfo = persistTokenAndKey(uuid, userName, dsrUser.getMsisdn());
				if (authInfo != null) {
					if (authInfo.getYabxToken() != null && redisRepository != null) {
						if (appConfigService.getBooleanProperty("IS_CACHING_ENABLED", true))
							redisRepository.update("YABX_ACCESS_TOKEN", uuid, authInfo);
					}
					dsrService.updateAuthInfo(dsrUser, authInfo);
					jsonResponse.put("YABX_ACCESS_TOKEN", yabxToken);
				}
			}
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
			LOGGER.error("Something went wrong while encripting appLozicToken for yabxId={},msisdn={} error={}",
					userName, dsrUser.getMsisdn(), e.getMessage());
		}
		return jsonResponse;
	}

	private AuthInfo persistTokenAndKey(String uuid, String userName, String msisdn) {
		AuthInfo authInfo = null;
		if (msisdn != null) {
			authInfo = authInfoRepository.findByMsisdn(msisdn);
			if (authInfo == null) {
				authInfo = new AuthInfo();
			}
			authInfoService.persistYabxTokenAndSecretKey(authInfo, uuid, userName, msisdn);
		} else {
			Optional<AuthInfo> userOptional = authInfoRepository.findByUsername(userName);
			if (userOptional.isPresent()) {
				authInfo = userOptional.get();
				authInfoService.persistYabxTokenAndSecretKey(authInfo, uuid, userName, msisdn);
			}

		}
		return authInfo;

	}

	@Override
	public void resetYabxToken(AuthInfo authInfo) {
		if (authInfo != null) {
			authInfo.setYabxToken(null);
			authInfo.setCredentialsNonExpired(false);
			authInfo.setCredentialsNonExpired(false);
			authInfo.setAccountNonLocked(false);
			authInfo.setAccountNonExpired(false);
			authInfo.setEnabled(false);
			authInfoRepository.save(authInfo);
		}
	}

	@Override
	public void registerDSR(DsrRetailerRegistrationDto dsrRetailerRegistrationDto) {
		if (dsrRetailerRegistrationDto != null) {
			List<UserDto> dsrList = dsrRetailerRegistrationDto.getDsr();
			for (UserDto userDto : dsrList) {
				String dsrMSISDN = userDto.getMsisdn();
				String dsrName = userDto.getName();
				List<UserDto> retailersList = userDto.getRetailer();
				User dsrUser = userRepository.findBymsisdnAndUserType(dsrMSISDN, UserType.DISTRIBUTORS.name());
				if (dsrUser != null) {
					createOrUpdateRetailers(dsrUser, retailersList);
				} else {
					dsrUser = new DSRUser();
					dsrUser.setFirstName(dsrName);
					dsrUser.setMsisdn(dsrMSISDN);
					dsrUser.setNumberOfDependents(0);
					dsrUser = userRepository.save(dsrUser);
					createOrUpdateRetailers(dsrUser, retailersList);
				}

			}
		}
	}

	private void createOrUpdateRetailers(User dsrUser, List<UserDto> retailersList) {
		if (retailersList != null) {
			for (UserDto retailers : retailersList) {
				String retailerMSISDN = retailers.getMsisdn();
				String retailersName = retailers.getName();
				User retailerUser = userRepository.findBymsisdnAndUserType(retailerMSISDN, UserType.RETAILERS.name());
				if (retailerUser != null) {

				} else {
					retailerUser = new Retailers();
					retailerUser.setFirstName(retailersName);
					retailerUser.setMsisdn(retailerMSISDN);
					retailerUser.setNumberOfDependents(0);
					retailerUser = userRepository.save(retailerUser);
				}
				UserRelationships userRelationships = userRelationshipsRepository
						.findByMsisdnAndRelationshipAndRelative(dsrUser.getMsisdn(), Relationship.RETAILER,
								retailerUser);
				if (userRelationships != null) {

				} else {
					userRelationships = new UserRelationships();
					userRelationships.setMsisdn(dsrUser.getMsisdn());
					userRelationships.setRelationship(Relationship.RETAILER);
					userRelationships.setRelative(retailerUser);
					userRelationshipsRepository.save(userRelationships);
				}
			}
		}
	}

}
