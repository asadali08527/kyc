package co.yabx.kyc.app.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.entities.OTP;
import co.yabx.kyc.app.enums.OtpGroup;
import co.yabx.kyc.app.enums.OtpType;
import co.yabx.kyc.app.fullKyc.entity.DSRUser;
import co.yabx.kyc.app.fullKyc.repository.DSRUserRepository;
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

	@Autowired
	private DSRUserRepository dsrUserRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(OtpServiceImpl.class);

	@Transactional
	@Override
	public OTP generateAndPersistOTP(Long user, OtpType otpType, Date expiryTime, OtpGroup otpGroup) {
		OTP otp = null;
		List<OTP> otps = otpRepository.findByUserOtpType(user, otpType);
		if (otps == null || otps.isEmpty())
			otp = new OTP();
		else
			otp = otps.get(0);
		otp.setCreatedAt(new Date());
		otp.setOtpType(otpType);
		otp.setUser(user);
		otp.setOtp(UtilHelper.getNumericString(appConfigService.getIntProperty("OTP_LENGTH_FOR_" + otpType, 6)));
		otp.setExpiryTime(expiryTime);
		otp.setOtpGroup(otpGroup);
		otp = otpRepository.save(otp);
		return otp;
	}

	@Override
	public DSRUser verifyOtp(DSRUser dsrUser, String otp, OtpType otpType) {
		if (dsrUser != null) {
			OTP otpFound = otpRepository.findByUserAndOtp(dsrUser.getId(), otp);
			if (otpFound != null) {
				if (otpFound.getExpiryTime().after(new Date()) && otpType.equals(otpFound.getOtpType())) {
					return dsrUser;
				} else {
					LOGGER.info("OTP={} is either expired or of different type, for msisdn={}, and type={}", otp,
							dsrUser.getMsisdn(), otpType);
					return null;
				}
			} else {
				LOGGER.info("OTP={} doesn't exist for msisdn={} and type={}", otp, dsrUser.getMsisdn(), otpType);
				return null;
			}
		}
		return null;
	}

	@Override
	public String findOtpByMsisdn(String msisdn) {
		DSRUser dsrUser = dsrUserRepository.findBymsisdn(msisdn);
		if (dsrUser != null) {
			List<OTP> otps = otpRepository.findByUserOtpType(dsrUser.getId(), OtpType.SMS);
			OTP otpFound = otps.get(0);
			if (otpFound != null) {
				if (otpFound.getExpiryTime().after(new Date()))
					return otpFound.getOtp();
				else
					return null;
			}
		}
		return null;
	}

}
