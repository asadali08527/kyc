package co.yabx.kyc.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.miniKyc.entity.AppConfigurations;

@Repository("appConfigurationRepository")
public interface AppConfigurationRepository extends CrudRepository<AppConfigurations, Long> {

	// public void saveOrUpdate(AppConfigurations config);

	/* public AppConfigurations findByProperty(String propertyName); */

}
