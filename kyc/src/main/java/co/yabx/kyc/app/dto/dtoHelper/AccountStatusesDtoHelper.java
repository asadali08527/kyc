package co.yabx.kyc.app.dto.dtoHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.yabx.kyc.app.dto.AccountStatusDTO;
import co.yabx.kyc.app.dto.AccountStatusTrackerDTO;
import co.yabx.kyc.app.entity.AccountStatuses;
import co.yabx.kyc.app.entity.AccountStatusesTrackers;

public class AccountStatusesDtoHelper implements Serializable {

	public static AccountStatusDTO prepareDto(AccountStatuses accountStatuses,
			List<AccountStatusesTrackers> accountStatusesTrackers) {
		if (accountStatuses != null) {
			AccountStatusDTO accountStatusDTO = new AccountStatusDTO();
			accountStatusDTO.setMsisdn(accountStatuses.getMsisdn());
			accountStatusDTO.setCreatedBy(accountStatuses.getCreatedBy());
			accountStatusDTO.setUpdatedBy(accountStatuses.getUpdatedBy());
			accountStatusDTO.setCreatedAt(accountStatuses.getCreatedAt());
			accountStatusDTO.setUpdatedAt(accountStatuses.getUpdatedAt());
			accountStatusDTO.setAccountStatus(accountStatuses.getAccountStatus());
			accountStatusDTO.setAmlCftStatus(accountStatuses.getAmlCftStatus());
			accountStatusDTO.setKycAvailable(accountStatuses.isKycAvailable());
			accountStatusDTO.setKycVerified(accountStatuses.getKycVerified());
			accountStatusDTO.setTransactionStatus(accountStatuses.getTransactionStatus());
			accountStatusDTO.setUpdateReason(accountStatuses.getUpdateReason());
			if (accountStatusesTrackers != null && !accountStatusesTrackers.isEmpty())
				accountStatusDTO.setAccountStatusTrackerDTO(prepareAccountStatusTrackerDto(accountStatusesTrackers));
			return accountStatusDTO;
		}
		return null;
	}

	private static List<AccountStatusTrackerDTO> prepareAccountStatusTrackerDto(
			List<AccountStatusesTrackers> accountStatusesTrackers) {
		if (accountStatusesTrackers != null) {
			List<AccountStatusTrackerDTO> accountStatusTrackerDTOList = new ArrayList<AccountStatusTrackerDTO>();
			for (AccountStatusesTrackers accountStatusesTracker : accountStatusesTrackers) {
				AccountStatusTrackerDTO accountStatusTrackerDTO = new AccountStatusTrackerDTO();
				accountStatusTrackerDTO.setChangedAt(accountStatusesTracker.getChangedAt());
				accountStatusTrackerDTO.setCreatedBy(accountStatusesTracker.getCreatedBy());
				accountStatusTrackerDTO.setFromStatus(accountStatusesTracker.getFrom());
				accountStatusTrackerDTO.setMsisdn(accountStatusesTracker.getMsisdn());
				accountStatusTrackerDTO.setReason(accountStatusesTracker.getReason());
				accountStatusTrackerDTO.setToStatus(accountStatusesTracker.getTo());
				accountStatusTrackerDTO.setUpdatedBy(accountStatusesTracker.getUpdatedBy());
				accountStatusTrackerDTOList.add(accountStatusTrackerDTO);

			}
			return accountStatusTrackerDTOList;

		}
		return null;
	}

}
