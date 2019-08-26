package co.yabx.kyc.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entity.AdditionalFieldsValue;

@Repository("additionalFieldsValueRepository")
public interface AdditionalFieldsValueRepository extends CrudRepository<AdditionalFieldsValue, Long> {

}
