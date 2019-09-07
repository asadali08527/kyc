package co.yabx.kyc.app.service;

import java.util.List;
import java.util.Map;

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

	public AccountStatuses updateAccountStatus(String msisdn, String status, String reason, String updatedBy);

	public Map<String, String> updateAllAccountStatus();

	public Map<String, String> reActivate();

	public AccountStatuses createAccountStatus(String msisdn, String createdBy);

}
