package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.DSRUser;

@Repository("dsrUserRepository")
public interface DSRUserRepository extends BaseUserRepository<DSRUser>, CrudRepository<DSRUser, Long> {


	DSRUser findByEmail(String mail);

	DSRUser findByMsisdn(String dsrMsisdn);

}
