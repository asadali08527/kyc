package co.yabx.kyc.app.dto.dtoHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.yabx.kyc.app.dto.KycDetailsDTO;
import co.yabx.kyc.app.dto.KycDocumentsDTO;
import co.yabx.kyc.app.dto.ResponseDTO;
import co.yabx.kyc.app.enums.DsrProfileStatus;
import co.yabx.kyc.app.miniKyc.entity.KycDetails;
import co.yabx.kyc.app.miniKyc.entity.KycDocuments;
import co.yabx.kyc.app.util.EncoderDecoderUtil;
import co.yabx.kyc.app.util.MaskUtil;

public class DsrDtoHelper implements Serializable {

	public static ResponseDTO getLoginDTO(String msisdn, String status, String statusCode, DsrProfileStatus dsrProfileStatus) {
		ResponseDTO loginDTO = new ResponseDTO();
		loginDTO.setMessage(status);
		loginDTO.setStatusCode(statusCode);
		loginDTO.setDsrProfileStatus(dsrProfileStatus);
		return loginDTO;
	}
}
