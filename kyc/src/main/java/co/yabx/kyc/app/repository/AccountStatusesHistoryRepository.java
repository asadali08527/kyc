package co.yabx.kyc.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entity.AccountStatusesHistory;

@Repository("accountStatusesHistoryRepository")
public interface AccountStatusesHistoryRepository extends CrudRepository<AccountStatusesHistory, Long> {

}
