package co.yabx.kyc.app.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import co.yabx.kyc.app.fullKyc.dto.AttachmentDetailsDTO;
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

	String fetchDsrProfilePic(User user);

	List<AttachmentDetailsDTO> getRetailerDocuments(User user);

}
