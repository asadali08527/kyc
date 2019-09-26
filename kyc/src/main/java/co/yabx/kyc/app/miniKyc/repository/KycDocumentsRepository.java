package co.yabx.kyc.app.miniKyc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.yabx.kyc.app.miniKyc.entity.KycDocuments;

@Repository("kycDocumentsRepository")
public interface KycDocumentsRepository extends CrudRepository<KycDocuments, Long> {

	List<KycDocuments> findByMsisdn(String msisdn);

	List<KycDocuments> findByMsisdnAndDocumentType(String encrytedMsisdn, String documentType);

	@Transactional
	@Modifying
	@Query("delete from KycDocuments kd where kd.msisdn=?1")
	void deleteByMsisdn(String encrytedMsisdn);

}
