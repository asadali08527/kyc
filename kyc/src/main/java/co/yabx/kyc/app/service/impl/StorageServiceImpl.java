package co.yabx.kyc.app.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.yabx.kyc.app.enums.AttachmentType;
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
	@Transactional
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
				return null;
			}
			LOGGER.info("File={} saved for retailer={}", file.getOriginalFilename(), retailerId);
			Set<Attachments> attachmentList = new HashSet<Attachments>();
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			String[] fileName = file.getOriginalFilename().replaceAll("." + extension, "").split("-");
			DocumentType documentType = null;
			DocumentSide documentSide = null;
			AttachmentDetails attachmentDetails = null;
			Attachments attachments = null;
			AttachmentType attachmentType = null;
			if (fileName.length > 1) {
				for (String name : fileName) {
					if (name.equalsIgnoreCase("DRIVING_LICENSE")) {
						documentType = DocumentType.DRIVING_LICENSE;
					} else if (name.equalsIgnoreCase("PASSPORT")) {
						documentType = DocumentType.PASSPORT;
					} else if (name.equalsIgnoreCase("front")) {
						documentSide = DocumentSide.FRONT;
					} else if (name.equalsIgnoreCase("back")) {
						documentSide = DocumentSide.BACK;
					} else if (name.equalsIgnoreCase("idProof")) {
						attachmentType = AttachmentType.IdentityProof;
					} else if (name.equalsIgnoreCase("addressProof")) {
						attachmentType = AttachmentType.AddressProof;
					}
				}
			} else {
				if (fileName[0].equalsIgnoreCase("tinCertificates")) {
					documentType = DocumentType.TIN_CERTIFICATE;
				} else if (fileName[0].equalsIgnoreCase("tradeLicense")) {
					documentType = DocumentType.TRADE_LICENSE;
				} else if (fileName[0].equalsIgnoreCase("nomineePhoto")) {
					documentType = DocumentType.NOMINEE_PHOTO;
				} else if (fileName[0].equalsIgnoreCase("signature")) {
					documentType = DocumentType.SIGNATURE;
				}
			}
			if (documentType != null) {
				attachmentDetails = attachmentDetailsRepository.findByUserAndDocumentType(user, documentType);
				if (attachmentDetails == null) {
					attachmentDetails = new AttachmentDetails();
					attachmentDetails.setAttachmentType(attachmentType);
				} else if (documentSide != null) {
					if (documentSide == DocumentSide.FRONT) {
						Optional<Attachments> frontDoc = attachmentDetails.getAttachments().stream()
								.filter(f -> f != null && f.getDocumentSide() != null
										&& f.getDocumentSide().equals(DocumentSide.FRONT))
								.findFirst();
						if (frontDoc.isPresent())
							attachments = frontDoc.get();
					} else {
						Optional<Attachments> BackDoc = attachmentDetails.getAttachments().stream()
								.filter(f -> f != null && f.getDocumentSide() != null
										&& f.getDocumentSide().equals(DocumentSide.BACK))
								.findFirst();
						if (BackDoc.isPresent())
							attachments = BackDoc.get();
					}
				} else {
					Optional<Attachments> frontDoc = attachmentDetails.getAttachments().stream().findFirst();
					if (frontDoc.isPresent())
						attachments = frontDoc.get();
				}
				if (attachments == null) {
					attachments = new Attachments();
					if (documentSide != null) {
						attachments.setDocumentSide(documentSide);
					}
					attachments.setDocumentUrl(path);
					attachmentList.add(attachments);
					attachmentDetails.setAttachments(attachmentList);
					attachmentDetails.setDocumentType(documentType);
					attachmentDetails.setUser(user);
					attachmentDetails = attachmentDetailsRepository.save(attachmentDetails);
					return attachmentDetails;
				} else {
					attachments.setDocumentUrl(path);
					attachmentsRepository.save(attachments);
				}

			}
		}
		return null;
	}

}
