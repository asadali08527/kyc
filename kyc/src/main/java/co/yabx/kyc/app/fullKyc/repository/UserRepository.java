package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.BusinessDetails;
import co.yabx.kyc.app.fullKyc.entity.DocumentDetails;
import co.yabx.kyc.app.fullKyc.entity.DocumentLinks;
import co.yabx.kyc.app.fullKyc.entity.IncomeDetails;
import co.yabx.kyc.app.fullKyc.entity.IntroducerDetails;
import co.yabx.kyc.app.fullKyc.entity.LiabilitiesDetails;
import co.yabx.kyc.app.fullKyc.entity.LicenseDetails;
import co.yabx.kyc.app.fullKyc.entity.User;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {

}
