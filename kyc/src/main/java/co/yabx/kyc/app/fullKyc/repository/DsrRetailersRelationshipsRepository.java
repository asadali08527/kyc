package co.yabx.kyc.app.fullKyc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.DsrRetailersRelationships;

@Repository("dsrRetailersRelationshipsRepository")
public interface DsrRetailersRelationshipsRepository extends CrudRepository<DsrRetailersRelationships, Long> {

	List<DsrRetailersRelationships> findByDsrMsisdn(String dsrMSISDN);

}
