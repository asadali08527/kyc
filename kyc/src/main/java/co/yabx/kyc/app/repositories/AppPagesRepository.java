package co.yabx.kyc.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entities.AppPages;

@Repository("appPagesRepository")
public interface AppPagesRepository extends CrudRepository<AppPages, Long> {

	AppPages findByPageName(String user_type);

}
