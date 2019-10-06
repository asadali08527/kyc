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

import co.yabx.kyc.app.dto.QuestionAnswerDTO;
import co.yabx.kyc.app.dto.ResponseDTO;
import co.yabx.kyc.app.dto.RetailerRequestDTO;
import co.yabx.kyc.app.dto.RetailersDTO;
import co.yabx.kyc.app.fullKyc.dto.BusinessDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.LiabilitiesDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.UserDTO;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.RetailerService;

/**
 * 
 * @author Asad.ali
 *
 */
@Controller
@CrossOrigin
@RequestMapping(value = "/version/v1")
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

	@RequestMapping(value = "/retailer/{dsrMsisdn}/{merchantId}", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> fetchRetailerDetails(@PathVariable String dsrMsisdn,
			@PathVariable String merchantId) {
		ResponseDTO loginDTO = retailerService.retailerDetails(dsrMsisdn, merchantId);
		return new ResponseEntity<>(loginDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/retailer/personal-information", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> submitRetailerProfile(@RequestBody UserDTO UserDTO) {
		ResponseDTO loginDTO = retailerService.submitRetailerProfile(UserDTO);
		return new ResponseEntity<>(loginDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/retailer/nominee-information", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> submitRetailerNomineeInfo(@RequestBody UserDTO UserDTO) {
		ResponseDTO loginDTO = retailerService.submitRetailerNomineeProfile(UserDTO);
		return new ResponseEntity<>(loginDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/retailer/business-information", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> submitRetailerBusinessInfo(@RequestBody BusinessDetailsDTO businessDetailsDTO) {
		ResponseDTO loginDTO = retailerService.submitRetailerBusinessInfo(businessDetailsDTO);
		return new ResponseEntity<>(loginDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/retailer/liabilities-information", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> submitLiabilitiesInfo(@RequestBody LiabilitiesDetailsDTO liabilitiesDetailsDTO) {
		ResponseDTO loginDTO = retailerService.submitLiabilitiesInfo(liabilitiesDetailsDTO);
		return new ResponseEntity<>(loginDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/retailer/kyc-submit", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> submitKyc(@RequestBody RetailersDTO RetailersDTO) {
		ResponseDTO loginDTO = retailerService.submitKyc(RetailersDTO);
		return new ResponseEntity<>(loginDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/retailer/question/{questionId}", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> fetchRetailerQuestion(@PathVariable Integer questionId) {
		ResponseDTO loginDTO = retailerService.getRetailerQuestion(questionId);
		return new ResponseEntity<>(loginDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/retailer/all/question", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> fetchRetailerAllQuestion(
			@RequestParam(name = "retailerId", required = false) String retailerId) {
		ResponseDTO loginDTO = retailerService.getRetailerAllQuestions(retailerId);
		return new ResponseEntity<>(loginDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/retailer/answer", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> postRetailerAnswer(@RequestBody QuestionAnswerDTO questionAnswerDTO) {
		ResponseDTO loginDTO = retailerService.persistRetailerAnswer(questionAnswerDTO);
		return new ResponseEntity<>(loginDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/retailer/{dsrMsisdn}/search/{retailerId}", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> searchRetailer(@PathVariable String dsrMsisdn, @PathVariable String retailerId) {
		ResponseDTO loginDTO = retailerService.searchRetailerByDsr(dsrMsisdn,retailerId);
		return new ResponseEntity<>(loginDTO, HttpStatus.OK);

	}

}
