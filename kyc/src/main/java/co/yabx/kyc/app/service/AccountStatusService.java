package co.yabx.kyc.app.service;

import java.util.List;

import co.yabx.kyc.app.dto.AccountStatusDTO;
import co.yabx.kyc.app.entity.AccountStatuses;
import co.yabx.kyc.app.entity.KycDetails;

/**
 * 
 * @author Asad.ali
 *
 */
public interface AccountStatusService {

	void updateAccountStatus(List<AccountStatusDTO> accountStatusDTO);

	public AccountStatuses createAccountStatus(KycDetails kycDetails);

	public AccountStatusDTO fetchAccountStatus(String msisdn);

}
