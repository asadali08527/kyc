package co.yabx.kyc.app.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.fullKyc.repository.UserRepository;
import co.yabx.kyc.app.repositories.OtpRepository;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.UserService;

/**
 * 
 * @author Asad.ali
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AppConfigService appConfigService;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	@Transactional
	public void persistYabxTokenAndSecretKey(User user, String yabxToken, String aPI_SECRET_KEY) {
		/*
		 * if (user != null) { user.setYabxToken(yabxToken);
		 * user.setSecretKey(aPI_SECRET_KEY); userRepository.save(user); }
		 */}

}
