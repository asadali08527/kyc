package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.AttachmentDetails;

@Repository("attachmenetDetailsRepository")
public interface AttachmenetDetailsRepository extends CrudRepository<AttachmentDetails, Long> {

}
