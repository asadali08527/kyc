package co.yabx.kyc.app.service;

import org.springframework.web.multipart.MultipartFile;

import co.yabx.kyc.app.dto.KYCDTO;

/**
 * 
 * @author Asad.ali
 *
 */
public interface KYCService {

	public void persistKYC(KYCDTO kycdto);

	public void persistPhoto(MultipartFile multipartFile);

}
