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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import co.yabx.kyc.app.dto.QuestionAnswerDTO;
import co.yabx.kyc.app.dto.ResponseDTO;
import co.yabx.kyc.app.dto.RetailerRequestDTO;
import co.yabx.kyc.app.dto.RetailersDTO;
import co.yabx.kyc.app.fullKyc.dto.BusinessDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.LiabilitiesDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.UserDTO;
import co.yabx.kyc.app.fullKyc.entity.AttachmentDetails;
import co.yabx.kyc.app.service.AuthInfoService;
import co.yabx.kyc.app.service.RetailerService;
import co.yabx.kyc.app.service.StorageService;

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
	private AuthInfoService authInfoService;

	@Autowired
	private RetailerService retailerService;

	@Autowired
	private StorageService storageService;

	private static final Logger LOGGER = LoggerFactory.getLogger(RetailerController.class);

	@RequestMapping(value = "/retailer/summary", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> fetchRetailersSummary(@RequestParam("dsrMSISDN") String dsrMSISDN,
			@RequestParam("startIndex") Integer startIndex, @RequestParam("endIndex") Integer endIndex,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		if (authInfoService.isAuthorized(dsrMSISDN, httpServletRequest, httpServletResponse)) {
			ResponseDTO loginDTO = retailerService.getSummaries(dsrMSISDN, startIndex, endIndex);
			return new ResponseEntity<>(loginDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}

	}

	@RequestMapping(value = "/retailer/{dsrMsisdn}/{retailerId}", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> fetchRetailerDetails(@PathVariable String dsrMsisdn,
			@PathVariable Long retailerId, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		if (authInfoService.isAuthorized(dsrMsisdn, httpServletRequest, httpServletResponse)) {
			ResponseDTO loginDTO = retailerService.retailerDetails(dsrMsisdn, retailerId);
			return new ResponseEntity<>(loginDTO, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "/retailer/personal-information", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> submitRetailerProfile(@RequestBody RetailerRequestDTO retailerRequestDTO,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		if (authInfoService.isAuthorized(retailerRequestDTO != null ? retailerRequestDTO.getDsrMSISDN() : null,
				httpServletRequest, httpServletResponse)) {
			LOGGER.info("/retailer/personal-information request received with retailerRequestDTO={}",
					retailerRequestDTO);
			ResponseDTO loginDTO = retailerService.submitRetailerProfile(retailerRequestDTO);
			return new ResponseEntity<>(loginDTO, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

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
	public ResponseEntity<?> submitKyc(@RequestBody RetailersDTO retailersDTO, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		if (authInfoService.isAuthorized(retailersDTO.getDsrMsisdn() != null && !retailersDTO.getDsrMsisdn().isEmpty()
				? retailersDTO.getDsrMsisdn()
				: null, httpServletRequest, httpServletResponse)) {
			ResponseDTO loginDTO = retailerService.submitKyc(retailersDTO);
			return new ResponseEntity<>(loginDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}

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
		ResponseDTO loginDTO = retailerService.searchRetailerByDsr(dsrMsisdn, retailerId);
		return new ResponseEntity<>(loginDTO, HttpStatus.OK);

	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/upload/image", method = RequestMethod.POST)
	public ResponseEntity<?> uploadImage(@RequestParam("dsrMSISDN") String msisdn,
			@RequestParam("retailerId") Long retailerId, @RequestParam MultipartFile files,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		if (authInfoService.isAuthorized(msisdn, httpServletRequest, httpServletResponse)) {
			LOGGER.info("/upload/image request recieved for retailer={}, dsr={}", retailerId, msisdn);
			try {
				AttachmentDetails attachmentDetails = storageService.uplaod(msisdn, retailerId, files);
				if (attachmentDetails != null)
					return new ResponseEntity<>(files, HttpStatus.OK);
				else {
					return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("exception raised while uploading image={},retailer={},error={}",
						files.getOriginalFilename(), retailerId, e.getMessage());
				return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

}
