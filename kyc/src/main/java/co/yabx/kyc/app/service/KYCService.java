package co.yabx.kyc.app.service;

import org.springframework.web.multipart.MultipartFile;

import co.yabx.kyc.app.dto.KycDetailsDTO;

/**
 * 
 * @author Asad.ali
 *
 */
public interface KYCService {

	public void persistKYC(KycDetailsDTO kycdto);

	public void persistPhoto(MultipartFile multipartFile);

}
