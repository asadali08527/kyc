package co.yabx.kyc.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entities.AppPagesSections;

@Repository("appPagesSectionsRepository")
public interface AppPagesSectionsRepository extends CrudRepository<AppPagesSections, Long> {

}
