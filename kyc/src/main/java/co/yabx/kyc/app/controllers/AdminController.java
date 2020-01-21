package co.yabx.kyc.app.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import co.yabx.kyc.app.dto.DsrRetailerRegistrationDto;
import co.yabx.kyc.app.dto.PagesDTO;
import co.yabx.kyc.app.dto.ProfileDTO;
import co.yabx.kyc.app.enums.KycStatus;
import co.yabx.kyc.app.enums.PageType;
import co.yabx.kyc.app.enums.UserType;
import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.fullKyc.repository.UserRepository;
import co.yabx.kyc.app.miniKyc.entity.AccountStatuses;
import co.yabx.kyc.app.miniKyc.repository.AccountStatusesRepository;
import co.yabx.kyc.app.service.AccountStatusService;
import co.yabx.kyc.app.service.AdminService;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.OtpService;
import co.yabx.kyc.app.service.RedisService;
import co.yabx.kyc.app.service.UserService;

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

	@Autowired
	private OtpService otpService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private AccountStatusesRepository accountStatusesRepository;

	@Autowired
	private RedisService redisService;

	@Autowired
	private AccountStatusService accountStatusService;

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

	@RequestMapping(value = "/appConfig/refresh", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> reloadAppConfiguration(
			@RequestParam(value = "secret_key", required = true) String secret_key) {
		if (secret_key.equals(appConfigService.getProperty("APP_CONFIG_RECONCILE_API_PASSWORD", "magic@yabx"))) {
			appConfigService.refresh();
			LOGGER.info("AppConfiguration has been refreshed");
			return new ResponseEntity<>(HttpStatus.OK);

		}
		return new ResponseEntity<>("Invalid secret key", HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "/admin/generateAuthToken", method = RequestMethod.GET)
	public ResponseEntity<?> generateAuthTokens(@RequestParam("msisdn") String msisdn,
			@RequestParam(value = "secret_key", required = true) String secret_key) {
		if (secret_key.equals(appConfigService.getProperty("GET_AUTH_TOKEN_API_PASSWORD", "magic@yabx"))) {
			Map<String, String> jsonResponse = adminService.getAuthToken(msisdn);
			if (jsonResponse == null)
				return new ResponseEntity<>("user not found or msisdn is null for this user", HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		}
		return new ResponseEntity<>("Invalid secret key", HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "/admin/otp", method = RequestMethod.GET)
	public ResponseEntity<?> findOTP(@RequestParam("msisdn") String msisdn,
			@RequestParam(value = "secret_key", required = true) String secret_key) {
		if (secret_key.equals(appConfigService.getProperty("GET_AUTH_TOKEN_API_PASSWORD", "magic@yabx"))) {
			Map<String, String> jsonResponse = new HashMap<String, String>();
			jsonResponse.put("OTP", otpService.findOtpByMsisdn(msisdn));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		}
		return new ResponseEntity<>("Invalid secret key", HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "/create/dsr", method = RequestMethod.POST)
	public ResponseEntity<?> createDSR(@RequestBody DsrRetailerRegistrationDto dsrRetailerRegistrationDto,
			@RequestParam(value = "secret_key", required = true) String secret_key) {
		if (secret_key.equals(appConfigService.getProperty("REGISTER_DSR_RETAILER_API_PASSWORD", "magic@yabx"))) {
			adminService.registerDSR(dsrRetailerRegistrationDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>("Invalid secret key", HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "/retailers/profiles", method = RequestMethod.GET)
	public ResponseEntity<?> getRetailer(@RequestParam(value = "status", required = true) String status,
			@RequestParam(value = "secret_key", required = true) String secret_key,
			@RequestParam(value = "page_no", required = false) Integer pageNo,
			@RequestParam(value = "page_size", required = false) Integer pageSize) {
		if (secret_key.equals(appConfigService.getProperty("RETAILER_PROFILE_API_PASSWORD", "magic@yabx"))) {
			LOGGER.info("/retailers/profiles request received for status={}", status);
			KycStatus kycStatus = KycStatus.valueOf(status);
			if (kycStatus != null) {
				Pageable pageable = PageRequest.of(pageNo != null ? pageNo : 0,
						pageSize != null ? pageSize : appConfigService.getIntProperty("DEFAULT_PAGE_RECORD_SIZE", 5));
				List<AccountStatuses> accountStatuses = accountStatusesRepository
						.findByKycVerifiedAndUpdateAt(kycStatus, pageable);
				Integer profileCount = redisService.getProfileCount(kycStatus + "PROFILE_COUNT_FOR",
						String.valueOf(kycStatus.ordinal()));
				LOGGER.info("Total received profile for status={} is ={}", status, accountStatuses.size());
				List<PagesDTO> appPagesDTOList = new ArrayList<PagesDTO>();
				for (AccountStatuses accountStatus : accountStatuses) {
					User user = userRepository.findBymsisdnAndUserType(accountStatus.getMsisdn(),
							UserType.RETAILERS.name());
					if (user != null) {
						LOGGER.info("Pages being prepared for user ={}", user.getId());
						appPagesDTOList.addAll(userService.getUserDetails(user, PageType.RETAILERS));
					}
				}
				ProfileDTO profileDTO = new ProfileDTO();
				profileDTO.setPagesDTOs(appPagesDTOList);
				profileDTO.setProfileCount(profileCount);
				return new ResponseEntity<>(profileDTO, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Invalid secret key", HttpStatus.UNAUTHORIZED);
	}

	/**
	 * 
	 * @param msisdn
	 * @param updateBy
	 * @param secret_key
	 * @param status
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	@RequestMapping(value = "/update/kyc/status", method = RequestMethod.POST)
	public ResponseEntity<?> updateStatus(@RequestParam(value = "msisdn", required = true) String msisdn,
			@RequestParam(value = "username", required = true) String updateBy,
			@RequestParam(value = "secret_key", required = true) String secret_key,
			@RequestParam(value = "status", required = true) String status, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		if (secret_key.equals(appConfigService.getProperty("REGISTER_DSR_RETAILER_API_PASSWORD", "magic@yabx"))) {
			KycStatus kycStatus = KycStatus.valueOf(status);
			if (kycStatus != null) {
				return new ResponseEntity<>(accountStatusService.createAccountStatus(msisdn, updateBy, true, kycStatus),
						HttpStatus.OK);
			}
			return new ResponseEntity<>("Invalid status", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>("Invalid secret key", HttpStatus.UNAUTHORIZED);

	}

}
