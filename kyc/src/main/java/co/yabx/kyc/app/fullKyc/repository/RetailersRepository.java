package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.Nominees;
import co.yabx.kyc.app.fullKyc.entity.Retailers;
import co.yabx.kyc.app.fullKyc.entity.User;

@Repository("retailersRepository")
public interface RetailersRepository extends BaseUserRepository<Retailers>, CrudRepository<Retailers, Long> {

	User findByYabxToken(String yabxToken);

	User findBymsisdn(String msisdn);

	Retailers findByRetailerId(String retailerId);

}
