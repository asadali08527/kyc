package co.yabx.kyc.app.service;

import co.yabx.kyc.app.entity.AccountStatus;
import co.yabx.kyc.app.entity.AccountStatuses;
import co.yabx.kyc.app.entity.AccountStatusesTrackers;

/**
 * 
 * @author Asad.ali
 *
 */
public interface AccountStatusTrackerService {

	AccountStatusesTrackers createAccountTracker(AccountStatuses accountStatuses);

	AccountStatusesTrackers updateAccountTracker(AccountStatuses accountStatuses, AccountStatus oldStatus);

}
