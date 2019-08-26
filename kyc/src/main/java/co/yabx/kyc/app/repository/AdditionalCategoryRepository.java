package co.yabx.kyc.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entity.AdditionalCategory;

@Repository("additionalCategoryRepository")
public interface AdditionalCategoryRepository extends CrudRepository<AdditionalCategory, Long> {

}
