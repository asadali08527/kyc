package co.yabx.kyc.app.service;

import java.util.List;

import co.yabx.kyc.app.dto.KycDetailsDTO;
import co.yabx.kyc.app.miniKyc.entity.KycDetails;

/**
 * 
 * @author Asad.ali
 *
 */
public interface KYCService {

	public List<KycDetails> persistKYC(List<KycDetailsDTO> kycDetailsDTO);

	public KycDetailsDTO getKycDetails(String msisdn, boolean masked, boolean scrumbled);

}
