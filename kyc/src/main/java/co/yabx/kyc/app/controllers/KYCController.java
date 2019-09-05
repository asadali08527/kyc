package co.yabx.kyc.app.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import co.yabx.kyc.app.dto.KycDetailsDTO;
import co.yabx.kyc.app.dto.KycDocumentsDTO;
import co.yabx.kyc.app.entity.AccountStatuses;
import co.yabx.kyc.app.entity.AccountStatusesTrackers;
import co.yabx.kyc.app.entity.KycDetails;
import co.yabx.kyc.app.service.AccountStatusService;
import co.yabx.kyc.app.service.AccountStatusTrackerService;
import co.yabx.kyc.app.service.KYCService;

/**
 * 
 * @author Asad.ali
 *
 */
@Controller
@RequestMapping(value = "/v1")
public class KYCController {
	@Autowired
	private KYCService kycService;

	@Autowired
	private AccountStatusService accountStatusService;

	@Autowired
	private AccountStatusTrackerService accountStatusTrackerService;

	private static final Logger LOGGER = LoggerFactory.getLogger(KYCController.class);

	@RequestMapping(value = "/kyc/create", method = RequestMethod.POST)
	public ResponseEntity<?> createAccount(@RequestBody List<KycDetailsDTO> kycDetailsDTO) {
		if (kycDetailsDTO != null) {
			List<KycDetails> kycDetailsList = kycService.persistKYC(kycDetailsDTO);
			if (kycDetailsList != null && !kycDetailsList.isEmpty()) {
				for (KycDetails kycDetails : kycDetailsList) {
					AccountStatuses accountStatuses = accountStatusService.createAccountStatus(kycDetails);
					if (accountStatuses != null) {
						accountStatusTrackerService.createAccountTracker(accountStatuses);
					}
				}
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/kyc/details", method = RequestMethod.GET)
	public ResponseEntity<?> getKycdetails(@RequestParam(name = "msisdn") String msisdn) {
		if (msisdn != null) {
			KycDetailsDTO kycDetailsDTO = kycService.getKycDetails(msisdn);
			if (kycDetailsDTO != null)
				return new ResponseEntity<>(kycDetailsDTO, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/upload/photo", method = RequestMethod.POST)
	public ResponseEntity<?> createAccount(@RequestParam(name = "file") MultipartFile file) {
		File convFile = new File(file.getOriginalFilename());
		try {
			convFile.createNewFile();
			try (FileOutputStream fos = new FileOutputStream(convFile)) {
				fos.write(file.getBytes());
			}
			BufferedImage image = null;
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			image = ImageIO.read(convFile);
			ImageIO.write(image, extension, new File("D://" + file.getOriginalFilename()));
		} catch (Exception e) {

		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private List<KycDocumentsDTO> prepareDocuments() {
		List<KycDocumentsDTO> kycDocumentList = new ArrayList<KycDocumentsDTO>();
		KycDocumentsDTO kycDocuments = new KycDocumentsDTO();
		kycDocuments.setDocumentExpiryDate(1567065991297L);
		kycDocuments.setDocumentIssueDate(1567065991297L);
		kycDocuments.setDocumentNumber("APPPA1234M");
		kycDocuments.setDocumentSide("back");
		kycDocuments.setDocumentType("Driving Licence");
		kycDocuments.setDocumentUrl("http://s3.sa-east-1.amazonaws.com/bucket/kyc/abc.png");
		kycDocuments.setSelfie(false);
		kycDocuments.setSnapTime(null);
		kycDocumentList.add(kycDocuments);
		KycDocumentsDTO kycDocumentsBack = new KycDocumentsDTO();
		kycDocumentsBack.setDocumentExpiryDate(1567065991297L);
		kycDocumentsBack.setDocumentIssueDate(1567065991297L);
		kycDocumentsBack.setDocumentNumber("APPPA1234M");
		kycDocumentsBack.setDocumentSide("front");
		kycDocumentsBack.setDocumentType("Driving Licence");
		kycDocumentsBack.setDocumentUrl("http://s3.sa-east-1.amazonaws.com/bucket/kyc/pqr.png");
		kycDocumentsBack.setSelfie(false);
		kycDocumentsBack.setSnapTime(new Date());
		kycDocumentList.add(kycDocumentsBack);

		return kycDocumentList;
	}

}
