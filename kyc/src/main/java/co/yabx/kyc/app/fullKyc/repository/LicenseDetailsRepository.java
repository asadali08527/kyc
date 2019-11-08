package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.enums.LicenseType;
import co.yabx.kyc.app.fullKyc.entity.BusinessDetails;
import co.yabx.kyc.app.fullKyc.entity.LicenseDetails;

@Repository("licenseDetailsRepository")
public interface LicenseDetailsRepository extends CrudRepository<LicenseDetails, Long> {

	LicenseDetails findByBusinessDetailsAndLicenseType(BusinessDetails businessDetails, LicenseType licenseType);

}
