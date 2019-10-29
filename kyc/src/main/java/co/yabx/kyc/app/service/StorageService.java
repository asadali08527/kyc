package co.yabx.kyc.app.service;

import org.springframework.web.multipart.MultipartFile;

import co.yabx.kyc.app.fullKyc.entity.AttachmentDetails;

public interface StorageService {

	AttachmentDetails uplaod(String msisdn, Long retailerId, MultipartFile files);

}
