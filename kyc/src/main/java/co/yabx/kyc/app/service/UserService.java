package co.yabx.kyc.app.service;

import co.yabx.kyc.app.fullKyc.entity.User;

public interface UserService {

	void persistYabxTokenAndSecretKey(User user, String yabxToken, String aPI_SECRET_KEY);

}
