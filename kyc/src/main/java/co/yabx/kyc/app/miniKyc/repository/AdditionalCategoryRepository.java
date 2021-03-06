package co.yabx.kyc.app.miniKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.miniKyc.entity.AdditionalCategory;

@Repository("additionalCategoryRepository")
public interface AdditionalCategoryRepository extends CrudRepository<AdditionalCategory, Long> {

}
