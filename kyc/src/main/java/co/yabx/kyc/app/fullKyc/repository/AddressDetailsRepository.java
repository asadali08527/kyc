package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.AddressDetails;

@Repository("addressDetailsRepository")
public interface AddressDetailsRepository extends CrudRepository<AddressDetails, Long> {

}
