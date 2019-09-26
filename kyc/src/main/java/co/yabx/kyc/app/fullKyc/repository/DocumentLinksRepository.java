package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.BusinessDetails;
import co.yabx.kyc.app.fullKyc.entity.DocumentDetails;
import co.yabx.kyc.app.fullKyc.entity.DocumentLinks;

@Repository("documentLinksRepository")
public interface DocumentLinksRepository extends CrudRepository<DocumentLinks, Long> {

}
