package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.IntroducerDetails;

@Repository("introducerDetailsRepository")
public interface IntroducerDetailsRepository extends CrudRepository<IntroducerDetails, Long> {

}
