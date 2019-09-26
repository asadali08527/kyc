package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.BusinessDetails;
import co.yabx.kyc.app.fullKyc.entity.DocumentDetails;
import co.yabx.kyc.app.fullKyc.entity.DocumentLinks;
import co.yabx.kyc.app.fullKyc.entity.IncomeDetails;

@Repository("incomeDetailsRepository")
public interface IncomeDetailsRepository extends CrudRepository<IncomeDetails, Long> {

}
