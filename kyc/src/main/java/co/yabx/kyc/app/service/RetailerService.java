package co.yabx.kyc.app.service;

import co.yabx.kyc.app.dto.ResponseDTO;
import co.yabx.kyc.app.dto.RetailerRequestDTO;
import co.yabx.kyc.app.fullKyc.dto.UserDTO;

public interface RetailerService {

	ResponseDTO getSummaries(RetailerRequestDTO retailerRequestDTO);

	ResponseDTO retailerDetails(String dsrMsisdn, String merchantId);

	ResponseDTO submitDsrProfile(UserDTO userDTO);

}
