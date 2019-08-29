package co.yabx.kyc.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entity.KycDocuments;

@Repository("kycDocumentsRepository")
public interface KycDocumentsRepository extends CrudRepository<KycDocuments, Long> {

	List<KycDocuments> findByMsisdn(String msisdn);

}
