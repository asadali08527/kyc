package co.yabx.kyc.app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entities.Device;

@Repository("DeviceRepository")
public interface DeviceRepository extends CrudRepository<Device, Long> {

	List<Device> getDevicesByUser(Long user);

}
