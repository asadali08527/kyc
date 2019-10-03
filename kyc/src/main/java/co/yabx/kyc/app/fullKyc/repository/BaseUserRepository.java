package co.yabx.kyc.app.fullKyc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import co.yabx.kyc.app.fullKyc.entity.User;

@NoRepositoryBean
public interface BaseUserRepository<EntityType extends User> extends CrudRepository<EntityType, Long> {

}
