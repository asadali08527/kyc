package co.yabx.kyc.app.entity.entityListener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import co.yabx.kyc.app.entity.AccountStatuses;

public class AccountStatusesTrackerListener extends Listener {
	@PrePersist
	@PreUpdate
	public void persist(AccountStatuses accountStatuses) {
		if (checkAccountStatusTrackerEnableDisable()) {
			if (accountStatuses != null) {
				publishAccountStatusesTrackers(accountStatuses);
			}
		}
	}

}
