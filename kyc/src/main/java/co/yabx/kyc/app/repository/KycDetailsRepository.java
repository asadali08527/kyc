package co.yabx.kyc.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.miniKyc.entity.KycDetails;

@Repository("kycDetailsRepository")
public interface KycDetailsRepository extends CrudRepository<KycDetails, String> {

	KycDetails findByMsisdn(String encrytedMsisdn);

}
