package co.yabx.kyc.app.service;

import java.util.List;
import java.util.Map;

import co.yabx.kyc.app.dto.AccountStatusDTO;
import co.yabx.kyc.app.miniKyc.entity.AccountStatuses;
import co.yabx.kyc.app.miniKyc.entity.KycDetails;

/**
 * 
 * @author Asad.ali
 *
 */
public interface AccountStatusService {

	void updateAccountStatus(List<AccountStatusDTO> accountStatusDTO, boolean force);

	public AccountStatuses createAccountStatus(KycDetails kycDetails);

	public AccountStatusDTO fetchAccountStatus(String msisdn);

	public AccountStatuses updateAccountStatus(String msisdn, String status, String reason, String updatedBy);

	public Map<String, String> updateAllAccountStatus();

	public Map<String, String> reActivate();

	public AccountStatuses createAccountStatus(String msisdn, String createdBy);

	public AccountStatuses createAccountStatus(String msisdn, String createdBy, boolean isKycAvailable);

}
