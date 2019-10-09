package co.yabx.kyc.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entities.AppDynamicFields;

@Repository("appDynamicFieldsRepository")
public interface AppDynamicFieldsRepository extends CrudRepository<AppDynamicFields, Long> {

}
