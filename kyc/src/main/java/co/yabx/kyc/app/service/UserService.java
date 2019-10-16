package co.yabx.kyc.app.service;

import java.util.List;

import co.yabx.kyc.app.dto.AppPagesDTO;
import co.yabx.kyc.app.fullKyc.entity.DSRUser;
import co.yabx.kyc.app.fullKyc.entity.Retailers;
import co.yabx.kyc.app.fullKyc.entity.User;

public interface UserService {

	void persistYabxTokenAndSecretKey(User user, String yabxToken, String aPI_SECRET_KEY);

	List<AppPagesDTO> getUserDetails(User dsrUser, String type);

	DSRUser getDSRByMsisdn(String dsrMsisdn);

	Retailers getRetailerById(Long retailerId);

	void persistOrUpdateUserInfo(AppPagesDTO appPagesDTO, User dsrUser, User retailer);

}
