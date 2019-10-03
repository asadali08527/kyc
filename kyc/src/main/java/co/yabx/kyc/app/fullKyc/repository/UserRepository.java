package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.User;

@Repository("userRepository")
public interface UserRepository extends BaseUserRepository<User>, CrudRepository<User, Long> {

	User findByYabxToken(String yabxToken);

	User findBymsisdn(String msisdn);

}
