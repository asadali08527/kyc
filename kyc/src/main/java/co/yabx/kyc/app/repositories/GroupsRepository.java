package co.yabx.kyc.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entities.Groups;

@Repository("groupsRepository")
public interface GroupsRepository extends CrudRepository<Groups, Long> {

}
