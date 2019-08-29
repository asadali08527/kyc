package co.yabx.kyc.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entity.AccountStatusesTrackers;

@Repository("accountStatusesTrackersRepository")
public interface AccountStatusesTrackersRepository extends CrudRepository<AccountStatusesTrackers, Long> {

}
