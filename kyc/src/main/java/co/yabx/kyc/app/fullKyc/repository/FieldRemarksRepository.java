package co.yabx.kyc.app.fullKyc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.FieldRemarks;

@Repository("fieldRemarksRepository")
public interface FieldRemarksRepository extends CrudRepository<FieldRemarks, Long> {

	List<FieldRemarks> findByUserIdAndFieldId(Long userId, String fieldId);

	List<FieldRemarks> findByUserId(Long id);

}