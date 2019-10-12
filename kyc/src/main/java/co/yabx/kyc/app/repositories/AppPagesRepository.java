package co.yabx.kyc.app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entities.AppPages;

@Repository("appPagesRepository")
public interface AppPagesRepository extends CrudRepository<AppPages, Long> {

	List<AppPages> findByPageName(String user_type);

	List<AppPages> findByPageType(String type);

}
