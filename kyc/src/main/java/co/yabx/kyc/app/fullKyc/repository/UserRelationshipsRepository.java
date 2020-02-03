package co.yabx.kyc.app.fullKyc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.enums.Relationship;
import co.yabx.kyc.app.fullKyc.entity.Retailers;
import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.fullKyc.entity.UserRelationships;

@Repository("userRelationshipsRepository")
public interface UserRelationshipsRepository extends CrudRepository<UserRelationships, Long> {

	List<UserRelationships> findByMsisdn(String msisdn);

	UserRelationships findByRelative(User user);

	UserRelationships findByMsisdnAndRelative(String dsrMsisdn, Retailers retailers);

	List<UserRelationships> findByMsisdnAndRelationship(String msisdn, Relationship relationship);

	UserRelationships findByMsisdnAndRelationshipAndRelative(String dsrMSISDN, Relationship retailer,
			User retailerUser);

	List<UserRelationships> findByRelationship(Relationship retailer);

	List<UserRelationships> findByRelationshipAndRelative(Relationship relationship, User retailer);
}
