package co.yabx.kyc.app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entities.Pages;

@Repository("pagesRepository")
public interface PagesRepository extends CrudRepository<Pages, Long> {

	List<Pages> findByPageName(String user_type);

	List<Pages> findByPageType(String type);

}
