package co.yabx.kyc.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import co.yabx.kyc.app.dto.DsrProfileDTO;
import co.yabx.kyc.app.dto.ResponseDTO;
import co.yabx.kyc.app.dto.VerifyOtpDTO;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.DSRService;

/**
 * 
 * @author Asad.ali
 *
 */
@Controller
@CrossOrigin
@RequestMapping(value = "/v1")
public class DSRController {

	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private DSRService dsrService;

	private static final Logger LOGGER = LoggerFactory.getLogger(DSRController.class);

	@RequestMapping(value = "/dsr/login/{{msisdn}}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> login(@PathVariable(value = "msisdn", required = true) String msisdn) {
		ResponseDTO loginDTO = dsrService.login(msisdn);
		return new ResponseEntity<>(loginDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/dsr/otp/verify", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> verifyOTP(@RequestBody VerifyOtpDTO verifyOtpDTO) {
		ResponseDTO loginDTO = dsrService.verifyOTP(verifyOtpDTO);
		return new ResponseEntity<>(loginDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/dsr/otp/verify", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> submitDSRProfile(@RequestBody DsrProfileDTO dsrProfileDTO) {
		ResponseDTO loginDTO = dsrService.submitDsrProfile(dsrProfileDTO);
		return new ResponseEntity<>(loginDTO, HttpStatus.OK);

	}

}
