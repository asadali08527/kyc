package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.enums.AddressType;
import co.yabx.kyc.app.fullKyc.entity.AddressDetails;
import co.yabx.kyc.app.fullKyc.entity.BusinessDetails;
import co.yabx.kyc.app.fullKyc.entity.User;

@Repository("addressDetailsRepository")
public interface AddressDetailsRepository extends CrudRepository<AddressDetails, Long> {

	AddressDetails findByAddressType(AddressType addressType);

	AddressDetails findByUserAndAddressType(User retailerOrDsrUser, AddressType addressType);

	AddressDetails findByBusinessDetailsAndAddressType(BusinessDetails businessDetails, AddressType addressType);

}
