package co.yabx.kyc.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entity.AccountStatus;
import co.yabx.kyc.app.entity.AccountStatuses;

@Repository("accountStatusesRepository")
public interface AccountStatusesRepository extends CrudRepository<AccountStatuses, String> {

	List<AccountStatuses> findByAccountStatus(AccountStatus accountStatus);

}
