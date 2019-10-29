package co.yabx.kyc.app.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.yabx.kyc.app.enums.DocumentSide;
import co.yabx.kyc.app.enums.DocumentType;
import co.yabx.kyc.app.fullKyc.entity.AttachmentDetails;
import co.yabx.kyc.app.fullKyc.entity.Attachments;
import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.fullKyc.repository.AttachmentDetailsRepository;
import co.yabx.kyc.app.fullKyc.repository.AttachmentsRepository;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.StorageService;
import co.yabx.kyc.app.service.UserService;

/**
 * 
 * @author Asad.ali
 *
 */
@Service
public class StorageServiceImpl implements StorageService {

	@Autowired
	private UserService userService;

	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private AttachmentsRepository attachmentsRepository;

	@Autowired
	private AttachmentDetailsRepository attachmentDetailsRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(StorageServiceImpl.class);

	@Override
	public AttachmentDetails uplaod(String msisdn, Long retailerId, MultipartFile file) {
		User user = userService.getRetailerById(retailerId);
		if (user != null) {
			File convFile = new File(file.getOriginalFilename());
			String path = appConfigService.getProperty("DOCUMENT_STORAGE_BASE_PATH", "/tmp/")
					+ file.getOriginalFilename();
			try {
				convFile.createNewFile();
				try (FileOutputStream fos = new FileOutputStream(convFile)) {
					fos.write(file.getBytes());
				}
				BufferedImage image = null;
				String extension = FilenameUtils.getExtension(file.getOriginalFilename());
				image = ImageIO.read(convFile);

				ImageIO.write(image, extension, new File(path));
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("exception raised while copying image={},user={},error={}", file.getOriginalFilename(),
						retailerId, e.getMessage());
			}
			Set<Attachments> attachmentList = new HashSet<Attachments>();
			AttachmentDetails attachmentDetails = new AttachmentDetails();
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			String[] fileName = file.getOriginalFilename().replaceAll("." + extension, "").split("-");
			Attachments attachments = new Attachments();
			if (fileName.length > 1) {
				for (String name : fileName) {
					if (name.equalsIgnoreCase("DRIVING_LICENSE")) {
						attachmentDetails.setDocumentType(DocumentType.DRIVING_LICENSE);
					} else if (name.equalsIgnoreCase("PASSPORT")) {
						attachmentDetails.setDocumentType(DocumentType.PASSPORT);
					} else if (name.equalsIgnoreCase("front")) {
						attachments.setDocumentSide(DocumentSide.FRONT);
					} else if (name.equalsIgnoreCase("back")) {
						attachments.setDocumentSide(DocumentSide.BACK);
					}
				}
			} else {
				if (fileName[0].equalsIgnoreCase("tinCertificates")) {
					attachmentDetails.setDocumentType(DocumentType.TIN_CERTIFICATE);
				} else if (fileName[0].equalsIgnoreCase("tradeLicense")) {
					attachmentDetails.setDocumentType(DocumentType.TRADE_LICENSE);
				} else if (fileName[0].equalsIgnoreCase("nomineePhoto")) {
					attachmentDetails.setDocumentType(DocumentType.NOMINEE_PHOTO);
				} else if (fileName[0].equalsIgnoreCase("signature")) {
					attachmentDetails.setDocumentType(DocumentType.SIGNATURE);
				}
			}

			attachmentList.add(attachments);
			attachments.setDocumentUrl(path);
			attachmentDetails.setAttachments(attachmentList);
			attachmentDetails.setUser(user);
			attachmentDetails = attachmentDetailsRepository.save(attachmentDetails);
			return attachmentDetails;
		}
		return null;
	}

}
