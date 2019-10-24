package co.yabx.kyc.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entities.Sections;

@Repository("sectionsRepository")
public interface SectionsRepository extends CrudRepository<Sections, Long> {

}
