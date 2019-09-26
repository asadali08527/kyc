package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.fullKyc.entity.WorkEducationDetails;

@Repository("workEducationDetailsRepository")
public interface WorkEducationDetailsRepository extends CrudRepository<WorkEducationDetails, Long> {

}
