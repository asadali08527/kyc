package co.yabx.kyc.app.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.dto.AccountStatusDTO;
import co.yabx.kyc.app.dto.dtoHelper.AccountStatusesDtoHelper;
import co.yabx.kyc.app.entity.AccountStatus;
import co.yabx.kyc.app.entity.AccountStatuses;
import co.yabx.kyc.app.entity.AccountStatusesTrackers;
import co.yabx.kyc.app.entity.AmlCftStatus;
import co.yabx.kyc.app.entity.KycDetails;
import co.yabx.kyc.app.entity.KycVerified;
import co.yabx.kyc.app.repository.AccountStatusesRepository;
import co.yabx.kyc.app.repository.AccountStatusesTrackersRepository;
import co.yabx.kyc.app.service.AccountStatusService;
import co.yabx.kyc.app.service.AccountStatusTrackerService;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.util.EncoderDecoderUtil;

@Service
public class AccountStatusServiceImpl implements AccountStatusService {

	@Autowired
	private AccountStatusesRepository accountStatusesRepository;

	@Autowired
	private AccountStatusesTrackersRepository accountStatusesTrackersRepository;

	@Autowired
	private AccountStatusTrackerService accountStatusTrackerService;

	@Autowired
	private AppConfigService appConfigService;

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountStatusServiceImpl.class);

	@Override
	@Transactional
	public void updateAccountStatus(List<AccountStatusDTO> accountStatusDTOList) {
		if (accountStatusDTOList != null && !accountStatusDTOList.isEmpty()) {
			for (AccountStatusDTO accountStatusDTO : accountStatusDTOList) {
				try {
					AccountStatuses accountStatuses = accountStatusesRepository.findOne(accountStatusDTO.getMsisdn());
					if (accountStatuses != null) {
						AccountStatus oldStatus = accountStatuses.getAccountStatus();
						accountStatuses.setAmlCftStatus(
								accountStatusDTO.getAmlCftStatus() != null ? accountStatusDTO.getAmlCftStatus()
										: AmlCftStatus.NO);
						accountStatuses.setKycVerified(accountStatusDTO.getKycVerified() == null ? KycVerified.NO
								: accountStatusDTO.getKycVerified());
						if (AmlCftStatus.NO.equals(accountStatuses.getAmlCftStatus())) {
							if (accountStatuses.isKycAvailable()
									&& (KycVerified.YES.equals(accountStatuses.getKycVerified())
											|| KycVerified.NO.equals(accountStatuses.getKycVerified()))) {
								accountStatuses.setAccountStatus(AccountStatus.ACTIVE);
							} else if (accountStatuses.isKycAvailable()
									&& KycVerified.REJECTED.equals(accountStatuses.getKycVerified())) {
								accountStatuses.setAccountStatus(AccountStatus.BLOCKED);
							}
						} else if (AmlCftStatus.YES.equals(accountStatuses.getAmlCftStatus())) {
							accountStatuses.setAccountStatus(AccountStatus.BLOCKED);
						} else {
							try {
								if (accountStatuses.isKycAvailable()
										&& (KycVerified.YES.equals(accountStatuses.getKycVerified())
												|| KycVerified.NO.equals(accountStatuses.getKycVerified()))) {
									accountStatuses.setAccountStatus(AccountStatus.ACTIVE);
								} else if (accountStatuses.isKycAvailable()
										&& KycVerified.REJECTED.equals(accountStatuses.getKycVerified())) {
									accountStatuses.setAccountStatus(AccountStatus.BLOCKED);
								} else if (accountStatusDTO.getAccountStatus() != null)
									accountStatuses.setAccountStatus(
											AccountStatus.valueOf(accountStatusDTO.getAccountStatus().name()));
							} catch (Exception e) {
								e.printStackTrace();
								LOGGER.error("Exception raised while setting status={},error={}",
										accountStatusDTO.getAccountStatus(), e.getMessage());
							}
						}
						accountStatuses.setUpdatedBy(accountStatusDTO.getUpdatedBy());
						accountStatuses.setUpdateReason(accountStatusDTO.getUpdateReason());
						accountStatuses = accountStatusesRepository.save(accountStatuses);
						accountStatusTrackerService.updateAccountTracker(accountStatuses, oldStatus);
					}
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error("Exception raised while updating account status for msisdn={}, error={}",
							accountStatusDTO.getMsisdn(), e.getMessage());
				}
			}
		}
	}

	@Override
	@Transactional
	public AccountStatuses createAccountStatus(KycDetails kycDetails) {
		if (kycDetails != null) {
			try {
				AccountStatuses accountStatuses = accountStatusesRepository
						.findOne(EncoderDecoderUtil.base64Decode(kycDetails.getMsisdn()));
				return createAccount(accountStatuses, EncoderDecoderUtil.base64Decode(kycDetails.getMsisdn()),
						kycDetails.getCreatedBy(), true);
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("Exception raised while updating account status for msisdn={}, error={}",
						kycDetails.getMsisdn(), e.getMessage());
			}
		}
		return null;
	}

	private AccountStatuses createAccount(AccountStatuses accountStatuses, String msisdn, String createdBy,
			boolean isKycAvailable) {
		if (accountStatuses == null) {
			accountStatuses = new AccountStatuses();
			accountStatuses.setKycAvailable(isKycAvailable);
			accountStatuses.setMsisdn(msisdn);
			accountStatuses
					.setUpdateReason(appConfigService.getProperty("NEW_ACCOUNT_CREATED_REASON", "NEW ACCOUNT CREATED"));
		} else if (isKycAvailable) {
			accountStatuses
					.setUpdateReason(appConfigService.getProperty("NEW_KYC_ACCOUNT_STATUS_REASON", "KYC RECEIVED"));
			accountStatuses.setKycAvailable(isKycAvailable);
		}
		accountStatuses.setAccountStatus(AccountStatus.NEW);
		accountStatuses.setAmlCftStatus(AmlCftStatus.NO);
		accountStatuses.setKycVerified(KycVerified.NO);
		accountStatuses.setCreatedBy(createdBy);
		accountStatuses = accountStatusesRepository.save(accountStatuses);
		return accountStatuses;
	}

	@Override
	public AccountStatusDTO fetchAccountStatus(String msisdn) {
		AccountStatuses accountStatuses = accountStatusesRepository.findOne(msisdn);
		if (accountStatuses != null) {
			try {
				if (appConfigService.getBooleanProperty("IS_TO_PREPRE_ACCOUNT_STATUS_TRACKER_DTO_AS_WELL", true)) {
					List<AccountStatusesTrackers> accountStatusesTrackers = accountStatusesTrackersRepository
							.findByMsisdn(msisdn);
					return AccountStatusesDtoHelper.prepareDto(accountStatuses, accountStatusesTrackers);
				} else {
					return AccountStatusesDtoHelper.prepareDto(accountStatuses, null);
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("Exception raised while updating account status for msisdn={}, error={}", msisdn,
						e.getMessage());
			}
		}
		return null;
	}

	@Override
	public AccountStatuses updateAccountStatus(String msisdn, String status, String reason, String updatedBy) {
		AccountStatuses accountStatuses = accountStatusesRepository.findOne(msisdn);
		return updateAccountStatus(accountStatuses, status, reason, updatedBy);
	}

	@Transactional
	private AccountStatuses updateAccountStatus(AccountStatuses accountStatuses, String status, String reason,
			String updatedBy) {
		if (accountStatuses != null && status != null && !status.isEmpty() && reason != null && !reason.isEmpty()) {
			AccountStatus oldStatus = accountStatuses.getAccountStatus();
			AccountStatus accountStatus = null;
			try {
				accountStatus = AccountStatus.valueOf(status.toUpperCase());
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("exception raised while fetch status={}, error={}", status, e.getMessage());
				return null;
			}
			accountStatuses.setAccountStatus(accountStatus);
			accountStatuses.setUpdateReason(reason);
			accountStatuses.setUpdatedBy(updatedBy);
			accountStatuses = accountStatusesRepository.save(accountStatuses);
			accountStatusTrackerService.updateAccountTracker(accountStatuses, oldStatus);
		}
		return accountStatuses;
	}

	@Override
	public Map<String, String> updateAllAccountStatus() {
		Map<String, String> statusTrackers = new HashMap<String, String>();
		List<AccountStatuses> accountStatusesList = accountStatusesRepository.findByAccountStatus(AccountStatus.NEW);
		LOGGER.info("Total={} new account found whose status to be updated", accountStatusesList.size());
		for (AccountStatuses accountStatuses : accountStatusesList) {
			updateAccount(accountStatuses, statusTrackers);
		}
		LOGGER.info("Total={} new account status updated to either blocked or active", statusTrackers.size());
		return statusTrackers;
	}

	private void updateAccount(AccountStatuses accountStatuses, Map<String, String> statusTrackers) {
		if (AmlCftStatus.YES.equals(accountStatuses.getAmlCftStatus())) {
			updateAccountStatus(accountStatuses, AccountStatus.BLOCKED.toString(),
					appConfigService.getProperty("REASON_IF_AML_CFT_STATUS_IS_YES", "DUE TO AML/CFT"),
					"SYSTEM CRON JOB");
			if (statusTrackers != null)
				statusTrackers.put(accountStatuses.getMsisdn(),
						AccountStatus.NEW + "_TO_" + AccountStatus.BLOCKED + ",REASON:-"
								+ appConfigService.getProperty("REASON_IF_AML_CFT_STATUS_IS_YES", "DUE TO AML/CFT")
								+ ",Date:-" + new Date());
			LOGGER.info("Account={} status has been updated from new to blocked", accountStatuses.getMsisdn());
		} else if (accountStatuses.isKycAvailable() && KycVerified.REJECTED.equals(accountStatuses.getKycVerified())) {
			updateAccountStatus(accountStatuses, AccountStatus.BLOCKED.toString(),
					appConfigService.getProperty("REASON_IF_KYC_IS_REJECTED", "KYC REJECTED"), "SYSTEM CRON JOB");
			if (statusTrackers != null)
				statusTrackers.put(accountStatuses.getMsisdn(),
						AccountStatus.NEW + "_TO_" + AccountStatus.BLOCKED + ",REASON:-"
								+ appConfigService.getProperty("REASON_IF_KYC_IS_REJECTED", "KYC REJECTED") + ",Date:-"
								+ new Date());
			LOGGER.info("Account={} status has been updated from new to blocked", accountStatuses.getMsisdn());
		} else if (accountStatuses.isKycAvailable() && !KycVerified.REJECTED.equals(accountStatuses.getKycVerified())) {
			updateAccountStatus(accountStatuses, AccountStatus.ACTIVE.toString(),
					appConfigService.getProperty("REASON_IF_KYC_IS_AVAILABLE_BUT_NOT_VERIFIED",
							"KYC IS AVAILABLE, BUT NEITHER VERIFIED NOR REJECTED"),
					"SYSTEM CRON JOB");
			if (statusTrackers != null)
				statusTrackers.put(accountStatuses.getMsisdn(),
						AccountStatus.NEW + "_TO_" + AccountStatus.ACTIVE + ",REASON:-"
								+ appConfigService.getProperty("REASON_IF_KYC_IS_AVAILABLE_BUT_NOT_VERIFIED",
										"KYC IS AVAILABLE, BUT NEITHER VERIFIED NOR REJECTED")
								+ ",Date:-" + new Date());
			LOGGER.info("Account={} status has been updated from new to active", accountStatuses.getMsisdn());
		}
	}

	@Override
	public Map<String, String> reActivate() {
		Map<String, String> statusTrackers = new HashMap<String, String>();
		List<AccountStatuses> suspendedAccounts = accountStatusesRepository
				.findByAccountStatus(AccountStatus.SUSPENDED);
		LOGGER.info("Total={} suspende account found whose status to be updated", suspendedAccounts.size());
		for (AccountStatuses accountStatuses : suspendedAccounts) {
			Date now = new Date();
			Date updatedAt = accountStatuses.getUpdatedAt();
			long hours = (now.getTime() - updatedAt.getTime()) / (60 * 60 * 1000);
			if (hours >= appConfigService.getLongProperty("THRESHOLD_TIME_TO_REACTIVATE_SUSPENDED_ACCOUNT", 24l)) {
				updateAccountStatus(accountStatuses, AccountStatus.ACTIVE.toString(), appConfigService
						.getProperty("REASON_SUSPENDED_THRESHOLD_TIME_CROSSED", "SUSPENDED THRESHOLD TIME CROSSED"),
						"SYSTEM CRON JOB");
				statusTrackers.put(accountStatuses.getMsisdn(),
						AccountStatus.SUSPENDED + "_TO_" + AccountStatus.ACTIVE + ",REASON:-"
								+ appConfigService.getProperty("REASON_SUSPENDED_THRESHOLD_TIME_CROSSED",
										"SUSPENDED THRESHOLD TIME CROSSED")
								+ ",Date:-" + new Date());
				LOGGER.info("Account={} status has been updated from suspended to active", accountStatuses.getMsisdn());
			}
		}
		LOGGER.info("Total={} suspended account status updated to active", statusTrackers.size());
		return statusTrackers;
	}

	@Override
	public AccountStatuses createAccountStatus(String msisdn, String createdBy) {
		AccountStatuses accountStatuses = accountStatusesRepository.findOne(msisdn);
		return createAccount(accountStatuses, msisdn, createdBy, false);
	}

}
