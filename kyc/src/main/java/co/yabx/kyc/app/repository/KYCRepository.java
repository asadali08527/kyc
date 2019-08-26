package co.yabx.kyc.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entity.KYC;

@Repository("kycRepository")
public interface KYCRepository extends CrudRepository<KYC, Long> {

}
