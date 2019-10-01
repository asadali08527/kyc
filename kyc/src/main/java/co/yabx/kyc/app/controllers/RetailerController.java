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
import co.yabx.kyc.app.dto.RetailerRequestDTO;
import co.yabx.kyc.app.dto.VerifyOtpDTO;
import co.yabx.kyc.app.fullKyc.dto.UserDTO;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.DSRService;
import co.yabx.kyc.app.service.RetailerService;

/**
 * 
 * @author Asad.ali
 *
 */
@Controller
@CrossOrigin
@RequestMapping(value = "/v1")
public class RetailerController {

	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private RetailerService retailerService;

	private static final Logger LOGGER = LoggerFactory.getLogger(RetailerController.class);

	@RequestMapping(value = "/retailer/summary", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> fetchRetailersSummary(@RequestBody RetailerRequestDTO retailerRequestDTO) {
		ResponseDTO loginDTO = retailerService.getSummaries(retailerRequestDTO);
		return new ResponseEntity<>(loginDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/retailer/details/{dsrMsisdn}/{merchantId}", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> fetchRetailerDetails(@PathVariable String dsrMsisdn,
			@PathVariable String merchantId) {
		ResponseDTO loginDTO = retailerService.retailerDetails(dsrMsisdn, merchantId);
		return new ResponseEntity<>(loginDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/retailer/personal-information", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> submitDSRProfile(@RequestBody UserDTO UserDTO) {
		ResponseDTO loginDTO = retailerService.submitDsrProfile(UserDTO);
		return new ResponseEntity<>(loginDTO, HttpStatus.OK);

	}

}
