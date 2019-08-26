package co.yabx.kyc.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entity.Photos;

@Repository("photosRepository")
public interface PhotosRepository extends CrudRepository<Photos, Long> {

}
