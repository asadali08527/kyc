package co.yabx.kyc.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entities.OTP;

@Repository("otpRepository")
public interface OtpRepository extends CrudRepository<OTP, Long> {

}
