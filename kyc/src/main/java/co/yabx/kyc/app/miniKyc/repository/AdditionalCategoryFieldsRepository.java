package co.yabx.kyc.app.miniKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.miniKyc.entity.AdditionalCategoryFields;

@Repository("additionalCategoryFieldsRepository")
public interface AdditionalCategoryFieldsRepository extends CrudRepository<AdditionalCategoryFields, Long> {

}
