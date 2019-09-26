package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.Attachments;
import co.yabx.kyc.app.fullKyc.entity.BusinessDetails;

@Repository("attachmentsRepository")
public interface AttachmentsRepository extends CrudRepository<Attachments, Long> {

}
