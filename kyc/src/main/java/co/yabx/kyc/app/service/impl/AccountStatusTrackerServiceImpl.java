package co.yabx.kyc.app.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.entity.AccountStatus;
import co.yabx.kyc.app.entity.AccountStatuses;
import co.yabx.kyc.app.entity.AccountStatusesTrackers;
import co.yabx.kyc.app.repository.AccountStatusesRepository;
import co.yabx.kyc.app.repository.AccountStatusesTrackersRepository;
import co.yabx.kyc.app.repository.KycDetailsRepository;
import co.yabx.kyc.app.repository.KycDocumentsRepository;
import co.yabx.kyc.app.service.AccountStatusTrackerService;
import co.yabx.kyc.app.service.AppConfigService;

@Service
public class AccountStatusTrackerServiceImpl implements AccountStatusTrackerService {

	@Autowired
	private AccountStatusesTrackersRepository accountStatusesTrackersRepository;

	@Autowired
	private AppConfigService appConfigService;

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountStatusTrackerServiceImpl.class);

	@Override
	@Transactional
	public AccountStatusesTrackers createAccountTracker(AccountStatuses accountStatuses) {
		if (accountStatuses != null) {
			try {
				List<AccountStatusesTrackers> accountStatusesTrackersList = accountStatusesTrackersRepository
						.findByMsisdn(accountStatuses.getMsisdn());
				if (accountStatusesTrackersList == null || accountStatusesTrackersList.isEmpty()) {
					AccountStatusesTrackers accountStatusesTrackers = new AccountStatusesTrackers();
					accountStatusesTrackers.setCreatedBy(accountStatuses.getCreatedBy());
					accountStatusesTrackers.setTo(AccountStatus.NEW);
					accountStatusesTrackers.setMsisdn(accountStatuses.getMsisdn());
					accountStatusesTrackers.setReason(appConfigService.getProperty("NEW_KYC_ACCOUNT_TRACKER_REASON",
							"NEW CREATE REQUEST RECIEVED"));
					accountStatusesTrackers = accountStatusesTrackersRepository.save(accountStatusesTrackers);
					return accountStatusesTrackers;
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("Exception raised while updating account status for msisdn={}, error={}",
						accountStatuses.getMsisdn(), e.getMessage());
			}
		}
		return null;

	}

	@Override
	@Transactional
	public AccountStatusesTrackers updateAccountTracker(AccountStatuses accountStatuses, AccountStatus oldStatus) {
		if (accountStatuses != null) {
			try {
				AccountStatusesTrackers accountStatusesTrackers = new AccountStatusesTrackers();
				accountStatusesTrackers.setUpdatedBy(accountStatuses.getUpdatedBy());
				accountStatusesTrackers.setFrom(oldStatus);
				accountStatusesTrackers.setTo(accountStatuses.getAccountStatus());
				accountStatusesTrackers.setMsisdn(accountStatuses.getMsisdn());
				accountStatusesTrackers.setReason(accountStatuses.getUpdateReason());
				accountStatusesTrackers.setCreatedBy(accountStatuses.getCreatedBy());
				accountStatusesTrackers = accountStatusesTrackersRepository.save(accountStatusesTrackers);
				return accountStatusesTrackers;
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("Exception raised while updating account status for msisdn={}, error={}",
						accountStatuses.getMsisdn(), e.getMessage());
			}
		}
		return null;

	}

	@Override
	public List<AccountStatusesTrackers> findByMsisdn(String msisdn) {
		return accountStatusesTrackersRepository.findByMsisdn(msisdn);
	}

}
