package co.yabx.kyc.app.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
	public AttachmentDetails uplaod(String msisdn, Long retailerId, MultipartFile file) throws Exception {
		User user = userService.getRetailerById(retailerId);
		if (user != null) {
			String fileName = file.getOriginalFilename().replaceAll(" ", "_");
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			String newFileName = UUID.randomUUID().toString() + System.currentTimeMillis() + "." + extension;
			File convFile = new File(fileName);
			String path = appConfigService.getProperty("DOCUMENT_STORAGE_BASE_PATH", "/tmp/") + newFileName;
			convFile.createNewFile();
			try (FileOutputStream fos = new FileOutputStream(convFile)) {
				fos.write(file.getBytes());
			}
			BufferedImage image = null;
			image = ImageIO.read(convFile);
			ImageIO.write(image, extension, new File(path));
			LOGGER.info("File={} saved for retailer={}", file.getOriginalFilename(), retailerId);
			Set<Attachments> attachmentList = new HashSet<Attachments>();
			String[] fileNames = fileName.replaceAll("." + extension, "").split("-");
			DocumentType documentType = null;
			DocumentSide documentSide = null;
			AttachmentDetails attachmentDetails = null;
			Attachments attachments = null;
			AttachmentType attachmentType = null;
			boolean isNew = false;
			if (fileNames.length > 1) {
				for (String name : fileNames) {
					if (name.equalsIgnoreCase("DRIVING_LICENSE") || name.equalsIgnoreCase("DRIVING LICENSE")) {
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
				if (fileNames[0].equalsIgnoreCase("tinCertificates")) {
					documentType = DocumentType.TIN_CERTIFICATE;
				} else if (fileNames[0].equalsIgnoreCase("tradeLicense")) {
					documentType = DocumentType.TRADE_LICENSE;
				} else if (fileNames[0].equalsIgnoreCase("nomineePhoto")) {
					documentType = DocumentType.NOMINEE_PHOTO;
				} else if (fileNames[0].equalsIgnoreCase("signature")) {
					documentType = DocumentType.SIGNATURE;
				}
			}
			if (documentType != null) {
				attachmentDetails = attachmentDetailsRepository.findByUserAndDocumentType(user, documentType);
				if (attachmentDetails == null) {
					attachmentDetails = new AttachmentDetails();
					attachmentDetails.setAttachmentType(attachmentType);
					isNew = true;
				}
				if (documentSide != null) {
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
					Optional<Attachments> optional = attachmentDetails.getAttachments().stream().findFirst();
					if (optional.isPresent())
						attachments = optional.get();
				}
				if (attachments == null && isNew) {
					attachments = new Attachments();
					if (documentSide != null) {
						attachments.setDocumentSide(documentSide);
					}
					attachments.setDocumentUrl(newFileName);
					attachmentList.add(attachments);
					attachmentDetails.setAttachments(attachmentList);
					attachmentDetails.setDocumentType(documentType);
					attachmentDetails.setUser(user);
					attachmentDetails = attachmentDetailsRepository.save(attachmentDetails);
					return attachmentDetails;
				} else if (!isNew && attachments == null) {
					attachments = new Attachments();
					if (documentSide != null) {
						attachments.setDocumentSide(documentSide);
					}
					attachments.setDocumentUrl(path);
					attachments.setAttachmentDetails(attachmentDetails);
					attachmentsRepository.save(attachments);
				} else {
					attachments.setDocumentUrl(path);
					attachmentsRepository.save(attachments);
				}

			}
		}
		return null;
	}

}
