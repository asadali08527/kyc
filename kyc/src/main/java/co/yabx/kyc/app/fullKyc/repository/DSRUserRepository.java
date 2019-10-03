package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.Customers;
import co.yabx.kyc.app.fullKyc.entity.DSRUser;
import co.yabx.kyc.app.fullKyc.entity.Nominees;
import co.yabx.kyc.app.fullKyc.entity.Retailers;
import co.yabx.kyc.app.fullKyc.entity.User;

@Repository("dsrUserRepository")
public interface DSRUserRepository extends BaseUserRepository<DSRUser>, CrudRepository<DSRUser, Long> {

	User findByYabxToken(String yabxToken);

	User findBymsisdn(String msisdn);

}
