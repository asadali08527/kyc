package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.IncomeDetails;

@Repository("incomeDetailsRepository")
public interface IncomeDetailsRepository extends CrudRepository<IncomeDetails, Long> {

}
