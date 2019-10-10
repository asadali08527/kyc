package co.yabx.kyc.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entities.AppPagesSectionGroups;

@Repository("appPagesSectionGroupsRepository")
public interface AppPagesSectionGroupsRepository extends CrudRepository<AppPagesSectionGroups, Long> {

}
