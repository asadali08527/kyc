package co.yabx.kyc.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entities.OTP;
import co.yabx.kyc.app.enums.OtpType;

@Repository("otpRepository")
public interface OtpRepository extends CrudRepository<OTP, Long> {

	OTP findByUserAndOtp(Long user, String otp);

	@Query("select o from OTP o where o.user=?1 and o.otpType=?2")
	List<OTP> findByUserOtpType(Long user, OtpType otpType);


}
