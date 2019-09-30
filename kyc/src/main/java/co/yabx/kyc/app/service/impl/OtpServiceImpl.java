package co.yabx.kyc.app.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.entities.OTP;
import co.yabx.kyc.app.enums.OtpType;
import co.yabx.kyc.app.repositories.OtpRepository;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.OtpService;
import co.yabx.kyc.app.util.UtilHelper;

/**
 * 
 * @author Asad.ali
 *
 */
@Service
public class OtpServiceImpl implements OtpService {

	@Autowired
	private OtpRepository otpRepository;

	@Autowired
	private AppConfigService appConfigService;

	private static final Logger LOGGER = LoggerFactory.getLogger(OtpServiceImpl.class);

	@Transactional
	@Override
	public OTP generateAndPersistOTP(Long user, OtpType otpType, Date expiryTime, String otpGroup) {
		OTP otp = new OTP();
		otp.setCreatedAt(new Date());
		otp.setOtpType(otpType);
		otp.setUser(user);
		otp.setOtp(UtilHelper.getNumericString(appConfigService.getIntProperty("OTP_LENGTH_FOR_" + otpType, 6)));
		otp.setExpiryTime(expiryTime);
		otp.setOtpGroup(otpGroup);
		otp = otpRepository.save(otp);
		return otp;
	}

}
