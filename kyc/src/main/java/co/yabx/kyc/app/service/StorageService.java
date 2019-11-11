package co.yabx.kyc.app.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	String uploadImage(MultipartFile file, Long userId) throws Exception;

	byte[] getImage(String filename, Long userId) throws Exception;

}
