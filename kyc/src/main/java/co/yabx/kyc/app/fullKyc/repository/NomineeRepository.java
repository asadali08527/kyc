package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.Nominees;
import co.yabx.kyc.app.fullKyc.entity.User;

@Repository("nomineesRepository")
public interface NomineeRepository extends BaseUserRepository<Nominees>, CrudRepository<Nominees, Long> {


	User findBymsisdn(String msisdn);

}
