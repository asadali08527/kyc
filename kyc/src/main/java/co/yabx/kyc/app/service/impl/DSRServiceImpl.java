package co.yabx.kyc.app.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.dto.DsrProfileDTO;
import co.yabx.kyc.app.dto.ResponseDTO;
import co.yabx.kyc.app.dto.VerifyOtpDTO;
import co.yabx.kyc.app.dto.dtoHelper.DsrDtoHelper;
import co.yabx.kyc.app.entities.OTP;
import co.yabx.kyc.app.enums.DsrProfileStatus;
import co.yabx.kyc.app.enums.OtpType;
import co.yabx.kyc.app.fullKyc.repository.UserRepository;
import co.yabx.kyc.app.repositories.OtpRepository;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.DSRService;
import co.yabx.kyc.app.service.OtpService;
import co.yabx.kyc.app.util.UtilHelper;

/**
 * 
 * @author Asad.ali
 *
 */
@Service
public class DSRServiceImpl implements DSRService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AppConfigService appConfigService;

	private static final Logger LOGGER = LoggerFactory.getLogger(DSRServiceImpl.class);

	@Override
	public ResponseDTO login(String msisdn) {
		// userRepository.findByMsisdn
		return DsrDtoHelper.getLoginDTO(msisdn, "SUCCESS", "200", null);
	}

	@Override
	public ResponseDTO verifyOTP(VerifyOtpDTO verifyOtpDTO) {
		// TODO Auto-generated method stub
		return DsrDtoHelper.getLoginDTO("", "SUCCESS", "200", DsrProfileStatus.NEW);
	}

	@Override
	public ResponseDTO submitDsrProfile(DsrProfileDTO dsrProfileDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
