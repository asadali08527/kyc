package co.yabx.kyc.app.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.dto.DsrProfileDTO;
import co.yabx.kyc.app.dto.ResponseDTO;
import co.yabx.kyc.app.dto.VerifyOtpDTO;
import co.yabx.kyc.app.dto.dtoHelper.DsrDtoHelper;
import co.yabx.kyc.app.dto.dtoHelper.RetailersDtoHelper;
import co.yabx.kyc.app.entities.AuthInfo;
import co.yabx.kyc.app.entities.OTP;
import co.yabx.kyc.app.enums.OtpGroup;
import co.yabx.kyc.app.enums.OtpType;
import co.yabx.kyc.app.enums.UserStatus;
import co.yabx.kyc.app.enums.UserType;
import co.yabx.kyc.app.fullKyc.dto.WorkEducationDetailsDTO;
import co.yabx.kyc.app.fullKyc.entity.AddressDetails;
import co.yabx.kyc.app.fullKyc.entity.DSRUser;
import co.yabx.kyc.app.fullKyc.entity.WorkEducationDetails;
import co.yabx.kyc.app.fullKyc.repository.DSRUserRepository;
import co.yabx.kyc.app.service.AdminService;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.DSRService;
import co.yabx.kyc.app.service.EmailService;
import co.yabx.kyc.app.service.OtpService;
import co.yabx.kyc.app.service.UserService;
import co.yabx.kyc.app.wrappers.UserWrapper;

/**
 * 
 * @author Asad.ali
 *
 */
@Service
public class DSRServiceImpl implements DSRService {

	@Autowired
	private AdminService adminService;

	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private DSRUserRepository dsrUserRepository;

	@Autowired
	private OtpService otpService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;

	private static final Logger LOGGER = LoggerFactory.getLogger(DSRServiceImpl.class);

	@Override
	public ResponseDTO login(String msisdn) {
		DSRUser dsrUser = dsrUserRepository.findByMsisdn(msisdn);
		if (dsrUser != null) {
			Calendar otpExpiryTime = Calendar.getInstance();
			otpExpiryTime.add(Calendar.SECOND, appConfigService.getIntProperty("OTP_EXPIRY_TIME_IN_SECONDS", 300));
			OTP otp = otpService.generateAndPersistOTP(dsrUser.getId(), OtpType.SMS, otpExpiryTime.getTime(),
					OtpGroup.REGISTRATION);
			// send otp code
			return DsrDtoHelper.getLoginDTO(msisdn, "SUCCESS", "200", null);
		} else {
			return DsrDtoHelper.getLoginDTO(msisdn, "DSR Not Found", "404", null);

		}
	}

	@Override
	public ResponseDTO verifyPhoneOTP(VerifyOtpDTO verifyOtpDTO) {
		if (verifyOtpDTO != null) {
			if (verifyOtpDTO.getOtp() != null && !verifyOtpDTO.getOtp().isEmpty()) {
				DSRUser dsrUser = dsrUserRepository.findByMsisdn(verifyOtpDTO.getDsrMSISDN());
				dsrUser = otpService.verifyOtp(dsrUser, verifyOtpDTO.getOtp(), OtpType.SMS);
				if (dsrUser != null) {
					ResponseDTO responseDTO = DsrDtoHelper.getLoginDTO("", "SUCCESS", "200", dsrUser.getUserStatus());
					responseDTO.setAuthInfo(adminService.prepareTokenAndKey(dsrUser, verifyOtpDTO.getDsrMSISDN()));
					responseDTO.setName(dsrUser.getFirstName());
					responseDTO.setEmail(dsrUser.getEmail());
					return responseDTO;
				} else {
					return DsrDtoHelper.getLoginDTO("", "Incorrect OTP or OTP Expired", "403", null);
				}
			} else {
				return DsrDtoHelper.getLoginDTO("", "Incorrect OTP or OTP Expired", "403", null);
			}

		}
		return DsrDtoHelper.getLoginDTO("", "Incorrect OTP or OTP Expired", "403", null);

	}

	@Override
	public ResponseDTO submitDsrProfile(DsrProfileDTO dsrProfileDTO) {
		if (dsrProfileDTO != null && dsrProfileDTO.getDsrMSISDN() != null) {
			try {
				DSRUser dsrUser = dsrUserRepository.findByMsisdn(dsrProfileDTO.getDsrMSISDN());
				if (dsrUser != null) {
					userService.persistOrUpdateUserInfo(dsrProfileDTO.getPageResponse(), dsrUser, null);
					return DsrDtoHelper.getLoginDTO(null, "SUCCESS", "200", UserStatus.ACTIVE);
				} else {
					return DsrDtoHelper.getLoginDTO(null, "NO DSR Found", "404", null);

				}
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("Exception raised while persisting DSR profile={}, error={} ",
						dsrProfileDTO.getDsrMSISDN(), e.getMessage());
				return DsrDtoHelper.getLoginDTO(null, "Internal Server Error", "500", null);

			}
		}
		return DsrDtoHelper.getLoginDTO(null, "Invalid Feilds", "403", null);
	}

	@Transactional
	private DSRUser persistDSRUser(DsrProfileDTO dsrProfileDTO, UserStatus dsrStatus) {
		DSRUser dsrUser = new DSRUser();
		dsrUser.setUserStatus(dsrStatus);
		dsrUser.setMsisdn(dsrProfileDTO.getDsrMSISDN());
		dsrUser.setWorkEducationDetails(prepareWorkEducationdetails(dsrProfileDTO));
		dsrUser.setAddressDetails(prepareAddress(dsrProfileDTO));
		dsrUser = dsrUserRepository.save(dsrUser);
		return dsrUser;
	}

	private Set<AddressDetails> prepareAddress(DsrProfileDTO dsrProfileDTO) {
		if (dsrProfileDTO != null && dsrProfileDTO.getAddressDetailsDTO() != null
				&& !dsrProfileDTO.getAddressDetailsDTO().isEmpty()) {
			Set<AddressDetails> addressDetails = UserWrapper.getAddressDetails(dsrProfileDTO.getAddressDetailsDTO());
			return addressDetails;
		}
		return null;
	}

	private Set<WorkEducationDetails> prepareWorkEducationdetails(DsrProfileDTO dsrProfileDTO) {
		if (dsrProfileDTO != null && dsrProfileDTO.getWorkEducationDetailsDTO() != null) {
			Set<WorkEducationDetails> workEducationDetails = UserWrapper
					.getWorkEducationDetails(new ArrayList<WorkEducationDetailsDTO>() {
						{
							add(dsrProfileDTO.getWorkEducationDetailsDTO());
						}
					});
			return workEducationDetails;
		}
		return null;
	}

	@Override
	@Transactional
	public void updateAuthInfo(DSRUser dsrUser, AuthInfo authInfo) {
		if (dsrUser != null && authInfo != null) {
			dsrUser.setAuthInfo(authInfo);
			dsrUserRepository.save(dsrUser);
		}
	}

	@Override
	public ResponseDTO logout(String msisdn) {
		DSRUser dsrUser = dsrUserRepository.findByMsisdn(msisdn);
		if (dsrUser != null) {
			AuthInfo authInfo = dsrUser.getAuthInfo();
			if (authInfo != null) {
				adminService.resetYabxToken(authInfo);
				return DsrDtoHelper.getLoginDTO(msisdn, "SUCCESS", "200", null);
			}
		}
		return DsrDtoHelper.getLoginDTO(msisdn, "DSR Not Found", "404", null);
	}

	@Override
	public ResponseDTO generateMailOTP(String mail) {
		DSRUser dsrUser = dsrUserRepository.findByEmail(mail);
		if (dsrUser != null) {
			Calendar otpExpiryTime = Calendar.getInstance();
			otpExpiryTime.add(Calendar.SECOND, appConfigService.getIntProperty("OTP_EXPIRY_TIME_IN_SECONDS", 300));
			OTP otp = otpService.generateAndPersistOTP(dsrUser.getId(), OtpType.MAIL, otpExpiryTime.getTime(),
					OtpGroup.VERIFICATION);
			emailService.sendOTP(otp, mail);
			return DsrDtoHelper.getLoginDTO(mail, "SUCCESS", "200", null);

		}
		return DsrDtoHelper.getLoginDTO(mail, "DSR Not Found", "404", null);
	}

	@Override
	public ResponseDTO verifyMail(VerifyOtpDTO verifyOtpDTO) {
		if (verifyOtpDTO != null) {
			if (verifyOtpDTO.getOtp() != null && !verifyOtpDTO.getOtp().isEmpty()) {
				DSRUser dsrUser = dsrUserRepository.findByMsisdn(verifyOtpDTO.getDsrMSISDN());
				dsrUser = otpService.verifyOtp(dsrUser, verifyOtpDTO.getOtp(), OtpType.MAIL);
				if (dsrUser != null) {
					ResponseDTO responseDTO = DsrDtoHelper.getLoginDTO(verifyOtpDTO.getDsrMSISDN(), "SUCCESS", "200",
							null);
					return responseDTO;
				} else {
					return DsrDtoHelper.getLoginDTO("", "Incorrect OTP or OTP Expired", "403", null);
				}
			} else {
				return DsrDtoHelper.getLoginDTO("", "Incorrect OTP or OTP Expired", "403", null);
			}

		}
		return DsrDtoHelper.getLoginDTO("", "Incorrect OTP or OTP Expired", "403", null);

	}

	@Override
	public ResponseDTO getDsrProfile(String msisdn) {
		DSRUser dsrUser = dsrUserRepository.findByMsisdn(msisdn);
		if (dsrUser != null) {
			ResponseDTO responseDTO = RetailersDtoHelper.getResponseDTO(null, "SUCCESS", "200",
					dsrUser.getUserStatus());
			responseDTO.setDsrInfo(userService.getUserDetails(dsrUser, UserType.DISTRIBUTORS.name()));
			return responseDTO;
		} else {
			ResponseDTO responseDTO = RetailersDtoHelper.getResponseDTO(null, "No DSR Found", "404", null);
			// responseDTO.setDsrInfo(userService.getUserDetails(dsrUser,
			// UserType.DISTRIBUTORS.name()));
			return responseDTO;
		}
	}

}
