package co.yabx.kyc.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import co.yabx.kyc.app.dto.AccountStatusDTO;
import co.yabx.kyc.app.entity.AccountStatuses;
import co.yabx.kyc.app.service.AccountStatusService;
import co.yabx.kyc.app.service.AccountStatusTrackerService;

/**
 * 
 * @author Asad.ali
 *
 */
@Controller
@CrossOrigin
@RequestMapping(value = "/v1")
public class AccoutStatusController {
	@Autowired
	private AccountStatusTrackerService accountStatusTrackerService;

	@Autowired
	private AccountStatusService accountStatusService;

	@RequestMapping(value = "/status/account/update", method = RequestMethod.POST)
	public ResponseEntity<?> updateAccountStatuses(@RequestBody List<AccountStatusDTO> accountStatusDTO) {
		if (accountStatusDTO != null && !accountStatusDTO.isEmpty()) {
			accountStatusService.updateAccountStatus(accountStatusDTO);
		}
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@RequestMapping(value = "/update/account/status", method = RequestMethod.POST)
	public ResponseEntity<AccountStatuses> updateAccountStatus(@RequestParam("msisdn") String msisdn,
			@RequestParam("status") String status, @RequestParam("reason") String reason,
			@RequestParam("updatedBy") String updatedBy) {
		if (msisdn != null && !msisdn.isEmpty() && !updatedBy.isEmpty() && !reason.isEmpty() && !status.isEmpty()) {
			AccountStatuses accountStatuses = accountStatusService.updateAccountStatus(msisdn, status, reason,
					updatedBy);
			return new ResponseEntity<>(accountStatuses, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/status/account", method = RequestMethod.GET)
	public ResponseEntity<?> getAccountStatus(@RequestParam("msisdn") String msisdn) {
		AccountStatusDTO accountStatusDTO = accountStatusService.fetchAccountStatus(msisdn);
		return new ResponseEntity<>(accountStatusDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/status/account/create", method = RequestMethod.POST)
	public ResponseEntity<?> craeteAccountStatus(@RequestParam("msisdn") String msisdn,
			@RequestParam("createdBy") String createdBy) {
		if (msisdn != null && !msisdn.isEmpty() && createdBy != null && !createdBy.isEmpty()) {
			AccountStatuses accountStatuses = accountStatusService.createAccountStatus(msisdn, createdBy);
			if (accountStatuses != null) {
				accountStatusTrackerService.createAccountTracker(accountStatuses);
				return new ResponseEntity<>(accountStatusService.fetchAccountStatus(msisdn), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

}
