package co.yabx.kyc.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entity.AccountStatuses;

@Repository("accountStatusesRepository")
public interface AccountStatusesRepository extends CrudRepository<AccountStatuses, String> {

}
