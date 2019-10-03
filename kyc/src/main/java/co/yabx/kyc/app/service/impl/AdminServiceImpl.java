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

import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.fullKyc.repository.UserRepository;
import co.yabx.kyc.app.security.SecurityUtils;
import co.yabx.kyc.app.service.AdminService;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.UserService;
import co.yabx.kyc.app.util.UtilHelper;

/**
 * 
 * @author Asad.ali
 *
 */
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private UserService userService;

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Override
	public Map<String, String> getAuthToken(Long yabxId) {
		Optional<User> user = userRepository.findById(yabxId);
		if (!user.isPresent() || user.get().getYabxToken() == null) {
			return null;
		}
		String yabxToken = user.get().getYabxToken();
		String API_SECRET_KEY = user.get().getSecretKey();
		Map<String, String> jsonResponse = new HashMap<String, String>();
		try {
			jsonResponse.put("YABX_ACCESS_TOKEN", SecurityUtils.encript(yabxToken, API_SECRET_KEY));
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			LOGGER.info("exception while encripting from admin for yabxId {}", yabxId);
			e.printStackTrace();
		}
		return jsonResponse;
	}

	@Override
	public Map<String, String> prepareTokenAndKey(Long yabxId, String msisdn) {
		String yabxToken = null;
		Map<String, String> jsonResponse = new HashMap<String, String>();
		try {
			String uuid = UUID.randomUUID().toString();
			final String API_SECRET_KEY = UtilHelper
					.getNumericString(appConfigService.getIntProperty("AES_ENCRYPTION_LENGTH", 16));
			yabxToken = SecurityUtils.encript(uuid, API_SECRET_KEY);
			if (yabxToken != null) {
				persistTokenAndKey(uuid, API_SECRET_KEY, yabxId, msisdn);
				jsonResponse.put("YABX_ACCESS_TOKEN", yabxToken);
			}
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
			LOGGER.error("Something went wrong while encripting appLozicToken for yabxId={}, error={}", yabxId,
					e.getMessage());
		}
		return jsonResponse;
	}

	private void persistTokenAndKey(String uuid, String aPI_SECRET_KEY, Long yabxId, String msisdn) {
		User user = null;
		if (yabxId != null) {
			Optional<User> userOptional = userRepository.findById(yabxId);
			if (userOptional.isPresent()) {
				user = userOptional.get();
				userService.persistYabxTokenAndSecretKey(user, uuid, aPI_SECRET_KEY);
			}
		} else {
			user = userRepository.findBymsisdn(msisdn);
			userService.persistYabxTokenAndSecretKey(user, uuid, aPI_SECRET_KEY);
		}

	}

}
