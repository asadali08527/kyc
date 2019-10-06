package co.yabx.kyc.app.miniKyc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.enums.AccountStatus;
import co.yabx.kyc.app.miniKyc.entity.AccountStatuses;

@Repository("accountStatusesRepository")
public interface AccountStatusesRepository extends CrudRepository<AccountStatuses, String> {

	List<AccountStatuses> findByAccountStatus(AccountStatus accountStatus);

	@Query("select a from AccountStatuses a where a.msisdn=?1")
	AccountStatuses findByMsisdn(String msisdn);

}