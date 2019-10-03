package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.Customers;
import co.yabx.kyc.app.fullKyc.entity.Nominees;
import co.yabx.kyc.app.fullKyc.entity.Retailers;
import co.yabx.kyc.app.fullKyc.entity.User;

@Repository("customersRepository")
public interface CustomersRepository extends BaseUserRepository<Customers>, CrudRepository<Customers, Long> {

	User findByYabxToken(String yabxToken);

	User findBymsisdn(String msisdn);

}
