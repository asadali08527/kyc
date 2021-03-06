package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.BusinessDetails;

@Repository("businessDetailsRepository")
public interface BusinessDetailsRepository extends CrudRepository<BusinessDetails, Long> {

}
