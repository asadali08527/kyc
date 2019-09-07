package co.yabx.kyc.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import co.yabx.kyc.app.service.AccountStatusService;
import co.yabx.kyc.app.service.AppConfigService;

/**
 * 
 * @author Asad.ali
 *
 */
@Controller
@RequestMapping(value = "/v1")
public class AccountStatusScheduler {
	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private AccountStatusService accountStatusService;

	@RequestMapping(value = "/update/status", method = RequestMethod.POST)
	public ResponseEntity<?> update(@RequestParam("magicKey") String magicKey) {
		if (appConfigService.getProperty("SECRET_KEY_FOR_UPDATING_ACCOUNT_STATUSES", "yabx@kyc")
				.equalsIgnoreCase(magicKey)) {
			return new ResponseEntity<>(accountStatusService.updateAllAccountStatus(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/status/reactivate", method = RequestMethod.POST)
	public ResponseEntity<?> reactivate(@RequestParam("magicKey") String magicKey) {
		if (appConfigService.getProperty("SECRET_KEY_FOR_UPDATING_ACCOUNT_STATUSES", "yabx@kyc")
				.equalsIgnoreCase(magicKey)) {
			return new ResponseEntity<>(accountStatusService.reActivate(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

}
