package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.BusinessDetails;
import co.yabx.kyc.app.fullKyc.entity.DocumentDetails;

@Repository("documentDetailsRepository")
public interface DocumentDetailsRepository extends CrudRepository<DocumentDetails, Long> {

}
