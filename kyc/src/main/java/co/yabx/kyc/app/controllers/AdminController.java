package co.yabx.kyc.app.controllers;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.security.SecurityUtils;
import co.yabx.kyc.app.service.AdminService;
import co.yabx.kyc.app.service.AppConfigService;

/**
 * 
 * @author Asad.ali
 *
 */
@Controller
@CrossOrigin
@RequestMapping(value = "/v1")
public class AdminController {

	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private AdminService adminService;

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

	@RequestMapping(value = "/appConfig/refresh")
	@ResponseBody
	public ResponseEntity<?> reloadAppConfiguration(
			@RequestParam(value = "secret_key", required = true) String secret_key) {
		if (secret_key.equals(appConfigService.getProperty("APP_CONFIG_RECONCILE_API_PASSWORD", "magic@yabx"))) {
			appConfigService.refresh();
			LOGGER.info("AppConfiguration has been refreshed");
			return new ResponseEntity<>(HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "/admin/generateAuthToken")
	public ResponseEntity<?> generateAuthTokens(@RequestParam("yabxId") Long yabxId) {
		Map<String, String> jsonResponse = adminService.getAuthToken(yabxId);
		if (jsonResponse == null)
			return new ResponseEntity<>("user not found or yabxId is null for this user", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}
}
