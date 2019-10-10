package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.OtherServicesInformations;

@Repository("otherServicesInformationsRepository")
public interface OtherServicesInformationsRepository extends CrudRepository<OtherServicesInformations, Long> {

}
