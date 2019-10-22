package co.yabx.kyc.app.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.ResponseBody;

import co.yabx.kyc.app.dto.DsrProfileDTO;
import co.yabx.kyc.app.dto.ResponseDTO;
import co.yabx.kyc.app.dto.VerifyOtpDTO;
import co.yabx.kyc.app.service.AuthInfoService;
import co.yabx.kyc.app.service.DSRService;

/**
 * 
 * @author Asad.ali
 *
 */
@Controller
@CrossOrigin
@RequestMapping(value = "/version/v1")
public class DSRController {

	@Autowired
	private AuthInfoService authInfoService;

	@Autowired
	private DSRService dsrService;

	private static final Logger LOGGER = LoggerFactory.getLogger(DSRController.class);

	@RequestMapping(value = "/dsr/profile", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> submitDSRProfile(@RequestBody DsrProfileDTO dsrProfileDTO,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		if (authInfoService.isAuthorized(dsrProfileDTO != null ? dsrProfileDTO.getDsrMSISDN() : null,
				httpServletRequest, httpServletResponse)) {
			ResponseDTO loginDTO = dsrService.submitDsrProfile(dsrProfileDTO);
			return new ResponseEntity<>(loginDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@RequestMapping(value = "/dsr/profile/{dsrMsisdn}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> fetchDSRProfile(@PathVariable String dsrMsisdn, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		if (authInfoService.isAuthorized(dsrMsisdn, httpServletRequest, httpServletResponse)) {
			ResponseDTO loginDTO = dsrService.getDsrProfile(dsrMsisdn);
			return new ResponseEntity<>(loginDTO, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "/dsr/logout/{msisdn}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> logout(@PathVariable String msisdn, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		if (authInfoService.isAuthorized(msisdn, httpServletRequest, httpServletResponse)) {
			ResponseDTO loginDTO = dsrService.logout(msisdn);
			return new ResponseEntity<>(loginDTO, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "/dsr/verify/otp", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> verifyMail(@RequestBody VerifyOtpDTO verifyOtpDTO, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		if (authInfoService.isAuthorized(verifyOtpDTO != null ? verifyOtpDTO.getDsrMSISDN() : null, httpServletRequest,
				httpServletResponse)) {
			ResponseDTO loginDTO = dsrService.verifyMail(verifyOtpDTO);
			return new ResponseEntity<>(loginDTO, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/dsr/verify/mail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> generateOTP(@RequestBody VerifyOtpDTO verifyOtpDTO, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		if (authInfoService.isAuthorized(verifyOtpDTO != null ? verifyOtpDTO.getDsrMSISDN() : null, httpServletRequest,
				httpServletResponse)) {
			ResponseDTO loginDTO = dsrService.generateMailOTP(verifyOtpDTO.getEmail());
			return new ResponseEntity<>(loginDTO, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
