package co.yabx.kyc.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entity.AccountStatusesTrackers;

@Repository("accountStatusesTrackersRepository")
public interface AccountStatusesTrackersRepository extends CrudRepository<AccountStatusesTrackers, Long> {

	@Query("select a from AccountStatusesTrackers a where a.msisdn=?1")
	List<AccountStatusesTrackers> findByMsisdn(String msisdn);

}
