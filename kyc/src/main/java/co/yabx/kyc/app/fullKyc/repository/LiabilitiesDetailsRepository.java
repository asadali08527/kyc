package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.LiabilitiesDetails;

@Repository("liabilitiesDetailsRepository")
public interface LiabilitiesDetailsRepository extends CrudRepository<LiabilitiesDetails, Long> {

}
