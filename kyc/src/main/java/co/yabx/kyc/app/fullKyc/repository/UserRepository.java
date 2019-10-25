package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entities.AuthInfo;
import co.yabx.kyc.app.enums.UserType;
import co.yabx.kyc.app.fullKyc.entity.User;

@Repository("userRepository")
public interface UserRepository extends BaseUserRepository<User>, CrudRepository<User, Long> {

	User findBymsisdn(String msisdn);

	User findByAuthInfo(AuthInfo authInfo);

	User findBymsisdnAndUserType(String dsrMSISDN, String userType);

}
