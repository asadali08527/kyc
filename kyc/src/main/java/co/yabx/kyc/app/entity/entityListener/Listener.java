package co.yabx.kyc.app.entity.entityListener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.yabx.kyc.app.entity.AccountStatuses;
import co.yabx.kyc.app.entity.AccountStatusesTrackers;
import co.yabx.kyc.app.service.AccountStatusTrackerService;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.impl.AccountStatusTrackerServiceImpl;
import co.yabx.kyc.app.service.impl.AppConfigServiceImpl;
import co.yabx.kyc.app.util.SpringUtil;

public abstract class Listener {
	private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);

	protected boolean checkEnableDisable() {
		AppConfigService dbConfigService = SpringUtil.bean(AppConfigServiceImpl.class);
		return dbConfigService.getBooleanProperty("ENTITY_LISTENER_ENABLE", false);
	}

	protected boolean checkAccountStatusTrackerEnableDisable() {
		AppConfigService dbConfigService = SpringUtil.bean(AppConfigServiceImpl.class);
		return dbConfigService.getBooleanProperty("ENTITY_ACCOUNT_STATUS_TRACKER_LISTENER_ENABLE", true);
	}

	protected void publishKycId(String id) {
		// TODO Auto-generated method stub

	}

	protected void publishAccountStatusesTrackers(AccountStatuses accountStatuses) {
		String msisdn = accountStatuses.getMsisdn();
		AccountStatusTrackerService accountStatusTrackerService = SpringUtil
				.bean(AccountStatusTrackerServiceImpl.class);
		try {
			List<AccountStatusesTrackers> accountStatusesTrackers = accountStatusTrackerService.findByMsisdn(msisdn);
			if (accountStatusesTrackers != null && !accountStatusesTrackers.isEmpty()) {
				accountStatusesTrackers.sort((o1, o2) -> o1.getChangedAt().compareTo(o2.getChangedAt()));
				accountStatusTrackerService.updateAccountTracker(accountStatuses,
						accountStatusesTrackers.get(0).getTo());
			} else {
				accountStatusTrackerService.createAccountTracker(accountStatuses);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception raised while updating account status tracker for msisdn={}, error={}",
					accountStatuses.getMsisdn(), e.getMessage());
		}
	}
}
