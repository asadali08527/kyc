package co.yabx.kyc.app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entities.SectionGroupRelationship;

@Repository("sectionGroupRelationshipRepository")
public interface SectionGroupRelationshipRepository extends CrudRepository<SectionGroupRelationship, Long> {

	List<SectionGroupRelationship> findBySectionId(Long sectionId);

}
