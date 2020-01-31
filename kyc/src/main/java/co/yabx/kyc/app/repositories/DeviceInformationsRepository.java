package co.yabx.kyc.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entities.DeviceInformations;

/**
 * 
 * @author Asad.ali
 *
 */
@Repository("deviceInformationsRepository")
public interface DeviceInformationsRepository extends JpaRepository<DeviceInformations, String> {

	DeviceInformations findByDeviceId(String deviceId);

	List<DeviceInformations> findByDeviceToken(String deviceToken);
}