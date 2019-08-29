package co.yabx.kyc.app.service;

import co.yabx.kyc.app.dto.KycDetailsDTO;
import co.yabx.kyc.app.entity.KycDetails;

/**
 * 
 * @author Asad.ali
 *
 */
public interface KYCService {

	public KycDetails persistKYC(KycDetailsDTO kycdto);

	public KycDetailsDTO getKycDetails(String msisdn);

}
