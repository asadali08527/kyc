package co.yabx.kyc.app.service.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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

import co.yabx.kyc.app.entities.AuthInfo;
import co.yabx.kyc.app.fullKyc.entity.DSRUser;
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
	private AppConfigService appConfigService;

	@Autowired
	private DSRService dsrService;

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

}
