package co.yabx.kyc.app.service;

import org.springframework.web.multipart.MultipartFile;

import co.yabx.kyc.app.fullKyc.entity.AttachmentDetails;
import co.yabx.kyc.app.fullKyc.entity.User;

/**
 * 
 * @author Asad.ali
 *
 */
public interface AttachmentService {

	AttachmentDetails persistInDb(User user, MultipartFile file, String filename) throws Exception;

	AttachmentDetails persistDsrProfilePicInDb(User user, MultipartFile files, String saveFileName);

}
