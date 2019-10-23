package co.yabx.kyc.app.dto.dtoHelper;

import java.io.Serializable;

import co.yabx.kyc.app.dto.ResponseDTO;
import co.yabx.kyc.app.enums.UserStatus;

public class DsrDtoHelper implements Serializable {

	public static ResponseDTO getLoginDTO(String msisdn, String status, String statusCode,
			UserStatus dsrProfileStatus) {
		ResponseDTO loginDTO = new ResponseDTO();
		loginDTO.setMessage(status);
		loginDTO.setStatusCode(statusCode);
		loginDTO.setDsrProfileStatus(dsrProfileStatus);
		return loginDTO;
	}
}
