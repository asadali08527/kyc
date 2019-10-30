package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.enums.DocumentType;
import co.yabx.kyc.app.fullKyc.entity.AttachmentDetails;
import co.yabx.kyc.app.fullKyc.entity.BankAccountDetails;

@Repository("attachmentDetailsRepository")
public interface AttachmentDetailsRepository extends CrudRepository<AttachmentDetails, Long> {

	AttachmentDetails findByDocumentType(DocumentType documentType);

}
