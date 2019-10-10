package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.CustomerInformations;

@Repository("customerInformationsRepository")
public interface CustomerInformationsRepository extends CrudRepository<CustomerInformations, Long> {

}
