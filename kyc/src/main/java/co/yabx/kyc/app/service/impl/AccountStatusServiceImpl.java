package co.yabx.kyc.app.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.KYCApplication;
import co.yabx.kyc.app.dto.AccountStatusDTO;
import co.yabx.kyc.app.dto.dtoHelper.AccountStatusesDtoHelper;
import co.yabx.kyc.app.entity.AccountStatus;
import co.yabx.kyc.app.entity.AccountStatuses;
import co.yabx.kyc.app.entity.AccountStatusesTrackers;
import co.yabx.kyc.app.entity.KycDetails;
import co.yabx.kyc.app.entity.KycVerified;
import co.yabx.kyc.app.repository.AccountStatusesRepository;
import co.yabx.kyc.app.repository.AccountStatusesTrackersRepository;
import co.yabx.kyc.app.repository.KycDetailsRepository;
import co.yabx.kyc.app.repository.KycDocumentsRepository;
import co.yabx.kyc.app.service.AccountStatusService;
import co.yabx.kyc.app.service.AccountStatusTrackerService;
import co.yabx.kyc.app.service.AppConfigService;

@Service
public class AccountStatusServiceImpl implements AccountStatusService {
	@Autowired
	private KycDetailsRepository kycDetailsRepository;

	@Autowired
	private KycDocumentsRepository kycDocumentsRepository;

	@Autowired
	private AccountStatusesRepository accountStatusesRepository;

	@Autowired
	private AccountStatusesTrackersRepository accountStatusesTrackersRepository;

	@Autowired
	private AccountStatusTrackerService accountStatusTrackerService;

	@Autowired
	private AppConfigService appConfigService;

	@Override
	@Transactional
	public void updateAccountStatus(List<AccountStatusDTO> accountStatusDTOList) {
		if (accountStatusDTOList != null && accountStatusDTOList.isEmpty()) {
			for (AccountStatusDTO accountStatusDTO : accountStatusDTOList) {
				AccountStatuses accountStatuses = accountStatusesRepository.findOne(accountStatusDTO.getMsisdn());
				AccountStatus oldStatus = accountStatuses.getAccountStatus();
				accountStatuses.setAccountStatus(AccountStatus.valueOf(accountStatusDTO.getAccountStatus().name()));
				accountStatuses.setAmlCftStatus(accountStatusDTO.getAmlCftStatus());
				accountStatuses.setKycVerified(accountStatusDTO.getKycVerified());
				accountStatuses.setUpdatedBy(accountStatusDTO.getUpdatedBy());
				accountStatuses.setUpdateReason(accountStatusDTO.getUpdateReason());
				accountStatuses = accountStatusesRepository.save(accountStatuses);
				accountStatusTrackerService.updateAccountTracker(accountStatuses, oldStatus);
			}
		}
	}

	@Override
	@Transactional
	public AccountStatuses createAccountStatus(KycDetails kycDetails) {
		if (kycDetails != null) {
			AccountStatuses accountStatuses = new AccountStatuses();
			accountStatuses.setAccountStatus(AccountStatus.NEW);
			accountStatuses.setAmlCftStatus(null);
			accountStatuses.setCreatedBy(kycDetails.getCreatedBy());
			accountStatuses.setKycAvailable(true);
			accountStatuses.setKycVerified(KycVerified.NO);
			accountStatuses.setMsisdn(kycDetails.getMsisdn());
			accountStatuses.setUpdateReason(
					appConfigService.getProperty("NEW_KYC_ACCOUNT_STATUS_REASON", "NEW KYC ACCOUNT CREATED"));
			accountStatuses = accountStatusesRepository.save(accountStatuses);
			return accountStatuses;
		}
		return null;
	}

	@Override
	public AccountStatusDTO fetchAccountStatus(String msisdn) {
		AccountStatuses accountStatuses = accountStatusesRepository.findOne(msisdn);
		if (accountStatuses != null) {
			if (appConfigService.getBooleanProperty("IS_TO_PREPRE_ACCOUNT_STATUS_TRACKER_DTO_AS_WELL", true)) {
				List<AccountStatusesTrackers> accountStatusesTrackers = accountStatusesTrackersRepository
						.findByMsisdn(msisdn);
				AccountStatusesDtoHelper.prepareDto(accountStatuses, accountStatusesTrackers);
			} else {
				AccountStatusesDtoHelper.prepareDto(accountStatuses, null);
			}
		}
		return null;
	}

}
