package co.yabx.kyc.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import co.yabx.kyc.app.dto.AccountStatusDTO;
import co.yabx.kyc.app.service.AccountStatusService;
import co.yabx.kyc.app.service.AppConfigService;

/**
 * 
 * @author Asad.ali
 *
 */
@Controller
@RequestMapping(value = "/v1")
public class AccoutStatusController {
	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private AccountStatusService accountStatusService;

	@RequestMapping(value = "/status/account/update", method = RequestMethod.POST)
	public ResponseEntity<?> updateAccountStatus(@RequestBody List<AccountStatusDTO> accountStatusDTO) {
		if (accountStatusDTO != null && !accountStatusDTO.isEmpty()) {
			accountStatusService.updateAccountStatus(accountStatusDTO);
		}
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@RequestMapping(value = "/status/account", method = RequestMethod.GET)
	public ResponseEntity<?> getAccountStatus(@RequestParam("msisdn") String msisdn) {
		AccountStatusDTO accountStatusDTO = accountStatusService.fetchAccountStatus(msisdn);
		return new ResponseEntity<>(accountStatusDTO, HttpStatus.OK);

	}

}
