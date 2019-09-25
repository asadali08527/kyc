package co.yabx.kyc.app.entity.entityListener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import co.yabx.kyc.app.miniKyc.entity.AccountStatuses;

/**
 * 
 * @author Asad.ali
 *
 */
public class AccountStatusesTrackerListener extends Listener {
	@PreUpdate
	@PrePersist
	public void persist(AccountStatuses accountStatuses) {
		if (checkAccountStatusTrackerEnableDisable()) {
			if (accountStatuses != null) {
				publishAccountStatusesTrackers(accountStatuses);
			}
		}
	}

}
