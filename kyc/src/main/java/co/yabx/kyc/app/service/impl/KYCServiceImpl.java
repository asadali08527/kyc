package co.yabx.kyc.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.controllers.KYCController;
import co.yabx.kyc.app.dto.KycDetailsDTO;
import co.yabx.kyc.app.dto.KycDocumentsDTO;
import co.yabx.kyc.app.dto.dtoHelper.KycDtoHelper;
import co.yabx.kyc.app.entity.KycDetails;
import co.yabx.kyc.app.entity.KycDocuments;
import co.yabx.kyc.app.repository.KycDetailsRepository;
import co.yabx.kyc.app.repository.KycDocumentsRepository;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.KYCService;
import co.yabx.kyc.app.util.EncoderDecoderUtil;

@Service
public class KYCServiceImpl implements KYCService {
	@Autowired
	private KycDetailsRepository kycDetailsRepository;

	@Autowired
	private KycDocumentsRepository kycDocumentsRepository;

	@Autowired
	private AppConfigService appConfigService;

	private static final Logger LOGGER = LoggerFactory.getLogger(KYCServiceImpl.class);

	@Override
	@Transactional
	public List<KycDetails> persistKYC(List<KycDetailsDTO> kycDetailsDTOs) {
		List<KycDetails> KycDetailsList = new ArrayList<KycDetails>();
		for (KycDetailsDTO kycDetailsDTO : kycDetailsDTOs) {
			if (kycDetailsDTO != null) {
				if (appConfigService.getBooleanProperty("IS_TO_DISPLAY_LOGS", true))
					LOGGER.info("persisting/updating kyc={}", kycDetailsDTO);
				String msisdn = kycDetailsDTO.getMsisdn();
				String encrytedMsisdn = EncoderDecoderUtil.base64Encode(msisdn);
				KycDetails kycDetails = kycDetailsRepository.findByMsisdn(encrytedMsisdn);
				if (kycDetails == null) {
					kycDetails = new KycDetails();
					kycDetails.setMsisdn(encrytedMsisdn);
					kycDetails.setCreatedBy(kycDetailsDTO.getUserId());
				}
				kycDetails.setArea(EncoderDecoderUtil.base64Encode(kycDetailsDTO.getArea()));
				kycDetails.setCity(EncoderDecoderUtil.base64Encode(kycDetailsDTO.getCity()));
				kycDetails.setRegion(EncoderDecoderUtil.base64Encode(kycDetailsDTO.getRegion()));
				kycDetails.setUpdatedBy(kycDetailsDTO.getUserId());
				kycDetails.setDob(kycDetailsDTO.getDob());
				kycDetails.setPob(EncoderDecoderUtil.base64Encode(kycDetailsDTO.getPob()));
				kycDetails.setFirstName(EncoderDecoderUtil.base64Encode(kycDetailsDTO.getFirstName()));
				kycDetails.setGender(kycDetailsDTO.getGender());
				kycDetails.setHouseNumberOrStreetName(
						EncoderDecoderUtil.base64Encode(kycDetailsDTO.getHouseNumberOrStreetName()));
				kycDetails.setLastName(EncoderDecoderUtil.base64Encode(kycDetailsDTO.getLastName()));
				kycDetails.setMiddleName(EncoderDecoderUtil.base64Encode(kycDetailsDTO.getMiddleName()));
				kycDetails.setZipCode(kycDetailsDTO.getZipCode());
				kycDetails.setEmail(EncoderDecoderUtil.base64Encode(kycDetailsDTO.getEmail()));
				kycDetails = kycDetailsRepository.save(kycDetails);
				if (appConfigService.getBooleanProperty("IS_TO_DISPLAY_LOGS", true))
					LOGGER.info("persisted/updated kycDetails={}", kycDetails);
				List<KycDocumentsDTO> newDocumentsList = kycDetailsDTO.getKycDocuments();
				List<KycDocuments> oldDocumentsLists = kycDocumentsRepository.findByMsisdn(encrytedMsisdn);
				if (oldDocumentsLists == null || oldDocumentsLists.isEmpty()) {
					// New KYC Documents , received at very first time
					persistNewDocuments(newDocumentsList, encrytedMsisdn);
				} else {
					/**
					 * Update KYC documents or add some more new one
					 */
					for (KycDocumentsDTO kycDocumentsDTO : newDocumentsList) {
						oldDocumentsLists = kycDocumentsRepository.findByMsisdnAndDocumentType(encrytedMsisdn,
								kycDocumentsDTO.getDocumentType());
						if (oldDocumentsLists == null || oldDocumentsLists.isEmpty()) {
							persistKycDocuments(null, kycDocumentsDTO, encrytedMsisdn);
						} else {
							/*
							 * update old documents
							 */
							for (KycDocuments oldDocuments : oldDocumentsLists) {
								if (oldDocuments.getDocumentSide() != null && oldDocuments.getDocumentSide()
										.equalsIgnoreCase(kycDocumentsDTO.getDocumentSide())) {
									persistKycDocuments(oldDocuments, kycDocumentsDTO, encrytedMsisdn);

								}
							}
						}
					}
				}
				KycDetailsList.add(kycDetails);
			}
		}
		return KycDetailsList;
	}

	private void persistNewDocuments(List<KycDocumentsDTO> newDocumentsList, String encrytedMsisdn) {
		for (KycDocumentsDTO kycDocumentsDTO : newDocumentsList) {
			persistKycDocuments(null, kycDocumentsDTO, encrytedMsisdn);

		}
	}

	@Transactional
	private void persistKycDocuments(KycDocuments kycDocuments, KycDocumentsDTO kycDocumentsDTO,
			String encrytedMsisdn) {
		if (kycDocuments == null)
			kycDocuments = new KycDocuments();
		kycDocuments.setDocumentExpiryDate(kycDocumentsDTO.getDocumentExpiryDate());
		kycDocuments.setDocumentIssueDate(kycDocumentsDTO.getDocumentIssueDate());
		kycDocuments.setDocumentNumber(EncoderDecoderUtil.base64Encode(kycDocumentsDTO.getDocumentNumber()));
		kycDocuments.setDocumentSide(kycDocumentsDTO.getDocumentSide());
		kycDocuments.setDocumentType(kycDocumentsDTO.getDocumentType());
		kycDocuments.setPlaceOfIssue(EncoderDecoderUtil.base64Encode(kycDocumentsDTO.getPlaceOfIssue()));
		kycDocuments.setDocumentUrl(EncoderDecoderUtil.base64Encode(kycDocumentsDTO.getDocumentUrl()));
		kycDocuments.setMsisdn(encrytedMsisdn);
		kycDocuments.setSelfie(kycDocumentsDTO.isSelfie());
		kycDocuments.setSnapTime(kycDocumentsDTO.getSnapTime());
		kycDocuments = kycDocumentsRepository.save(kycDocuments);
		if (appConfigService.getBooleanProperty("IS_TO_DISPLAY_LOGS", true))
			LOGGER.info("persisted/updated kycDocuments={}", kycDocuments);

	}

	@Override
	public KycDetailsDTO getKycDetails(String msisdn, boolean masked, boolean scrumbled) {
		String encrytedMsisdn = EncoderDecoderUtil.base64Encode(msisdn);
		KycDetails kycDetails = kycDetailsRepository.findByMsisdn(encrytedMsisdn);
		if (kycDetails != null) {
			List<KycDocuments> oldDocumentsLists = kycDocumentsRepository.findByMsisdn(encrytedMsisdn);
			return KycDtoHelper.prepareDto(kycDetails, oldDocumentsLists, masked, scrumbled);
		}
		return null;
	}

}
