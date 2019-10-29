package co.yabx.kyc.app.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void uplaod(String msisdn, Long retailerId, MultipartFile files);

}
