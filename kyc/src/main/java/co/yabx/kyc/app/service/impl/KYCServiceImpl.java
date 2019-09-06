package co.yabx.kyc.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.dto.KycDetailsDTO;
import co.yabx.kyc.app.dto.KycDocumentsDTO;
import co.yabx.kyc.app.dto.dtoHelper.KycDtoHelper;
import co.yabx.kyc.app.entity.KycDetails;
import co.yabx.kyc.app.entity.KycDocuments;
import co.yabx.kyc.app.repository.KycDetailsRepository;
import co.yabx.kyc.app.repository.KycDocumentsRepository;
import co.yabx.kyc.app.service.KYCService;
import co.yabx.kyc.app.util.EncoderDecoderUtil;

@Service
public class KYCServiceImpl implements KYCService {
	@Autowired
	private KycDetailsRepository kycDetailsRepository;

	@Autowired
	private KycDocumentsRepository kycDocumentsRepository;

	@Override
	@Transactional
	public List<KycDetails> persistKYC(List<KycDetailsDTO> kycDetailsDTOs) {
		List<KycDetails> KycDetailsList = new ArrayList<KycDetails>();
		for (KycDetailsDTO kycDetailsDTO : kycDetailsDTOs) {
			if (kycDetailsDTO != null) {
				boolean isNewKyc = false;
				String msisdn = kycDetailsDTO.getMsisdn();
				String encrytedMsisdn = EncoderDecoderUtil.base64Encode(msisdn);
				KycDetails kycDetails = kycDetailsRepository.findByMsisdn(encrytedMsisdn);
				if (kycDetails == null) {
					kycDetails = new KycDetails();
					kycDetails.setMsisdn(encrytedMsisdn);
					kycDetails.setCreatedBy(kycDetailsDTO.getUserId());
					isNewKyc = true;
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
				kycDetails = kycDetailsRepository.save(kycDetails);
				List<KycDocumentsDTO> newDocumentsList = kycDetailsDTO.getKycDocuments();
				List<KycDocuments> oldDocumentsLists = kycDocumentsRepository.findByMsisdn(encrytedMsisdn);
				if (oldDocumentsLists == null || oldDocumentsLists.isEmpty()) {
					for (KycDocumentsDTO kycDocumentsDTO : newDocumentsList) {
						if (isNewKyc) {
							KycDocuments kycDocuments = new KycDocuments();
							persistKycDocuments(kycDocuments, kycDocumentsDTO, encrytedMsisdn);
						}
					}
				} else {
					for (KycDocumentsDTO kycDocumentsDTO : newDocumentsList) {
						oldDocumentsLists = kycDocumentsRepository.findByMsisdnAndDocumentType(encrytedMsisdn,
								kycDocumentsDTO.getDocumentType());
						oldDocumentsLists.forEach(f -> {
							if (f.getDocumentSide() != null
									&& f.getDocumentSide().equalsIgnoreCase(kycDocumentsDTO.getDocumentSide())) {
								persistKycDocuments(f, kycDocumentsDTO, encrytedMsisdn);

							}
						});
					}
				}
				KycDetailsList.add(kycDetails);
			}
		}
		return KycDetailsList;
	}

	@Transactional
	private void persistKycDocuments(KycDocuments kycDocuments, KycDocumentsDTO kycDocumentsDTO,
			String encrytedMsisdn) {
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
		kycDocumentsRepository.save(kycDocuments);
	}

	@Override
	public KycDetailsDTO getKycDetails(String msisdn, boolean masked) {
		String encrytedMsisdn = EncoderDecoderUtil.base64Encode(msisdn);
		KycDetails kycDetails = kycDetailsRepository.findByMsisdn(encrytedMsisdn);
		if (kycDetails != null) {
			List<KycDocuments> oldDocumentsLists = kycDocumentsRepository.findByMsisdn(encrytedMsisdn);
			return KycDtoHelper.prepareDto(kycDetails, oldDocumentsLists, masked);
		}
		return null;
	}

}
