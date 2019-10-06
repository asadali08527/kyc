package co.yabx.kyc.app.service;

import java.util.Optional;

import co.yabx.kyc.app.entities.AuthInfo;
import co.yabx.kyc.app.fullKyc.entity.User;

public interface AuthInfoService {

	String login(String username, String password);

	Optional<User> findByToken(String token);

	AuthInfo persistYabxTokenAndSecretKey(AuthInfo authInfo, String uuid, String userName, String msisdn);

}
