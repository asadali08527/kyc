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
				KycDetails kycDetails = kycDetailsRepository.findOne(msisdn);
				if (kycDetails == null) {
					kycDetails = new KycDetails();
					kycDetails.setMsisdn(msisdn);
					kycDetails.setCreatedBy(kycDetailsDTO.getUserId());
					isNewKyc = true;
				}
				kycDetails.setArea(EncoderDecoderUtil.base64Encode(kycDetailsDTO.getArea()));
				kycDetails.setCity(EncoderDecoderUtil.base64Encode(kycDetailsDTO.getCity()));
				kycDetails.setUpdatedBy(kycDetailsDTO.getUserId());
				kycDetails.setDob(kycDetailsDTO.getDob());
				kycDetails.setFirstName(EncoderDecoderUtil.base64Encode(kycDetailsDTO.getFirstName()));
				kycDetails.setGender(kycDetailsDTO.getGender());
				kycDetails.setHouseNumberOrStreetName(
						EncoderDecoderUtil.base64Encode(kycDetailsDTO.getHouseNumberOrStreetName()));
				kycDetails.setLastName(EncoderDecoderUtil.base64Encode(kycDetailsDTO.getLastName()));
				kycDetails.setMiddleName(EncoderDecoderUtil.base64Encode(kycDetailsDTO.getMiddleName()));
				kycDetails.setZipCode(kycDetailsDTO.getZipCode());
				kycDetails = kycDetailsRepository.save(kycDetails);

				List<KycDocuments> oldDocumentsLists = kycDocumentsRepository.findByMsisdn(msisdn);
				if (oldDocumentsLists == null || oldDocumentsLists.isEmpty()) {
					List<KycDocumentsDTO> newDocumentsList = kycDetailsDTO.getKycDocuments();
					for (KycDocumentsDTO kycDocumentsDTO : newDocumentsList) {
						if (isNewKyc) {
							KycDocuments kycDocuments = new KycDocuments();
							kycDocuments.setDocumentExpiryDate(kycDocumentsDTO.getDocumentExpiryDate());
							kycDocuments.setDocumentIssueDate(kycDocumentsDTO.getDocumentIssueDate());
							kycDocuments.setDocumentNumber(
									EncoderDecoderUtil.base64Encode(kycDocumentsDTO.getDocumentNumber()));
							kycDocuments.setDocumentSide(kycDocumentsDTO.getDocumentSide());
							kycDocuments.setDocumentType(kycDocumentsDTO.getDocumentType());
							kycDocuments
									.setDocumentUrl(EncoderDecoderUtil.base64Encode(kycDocumentsDTO.getDocumentUrl()));
							kycDocuments.setMsisdn(msisdn);
							kycDocuments.setSelfie(kycDocumentsDTO.isSelfie());
							kycDocuments.setSnapTime(kycDocumentsDTO.getSnapTime());
							kycDocumentsRepository.save(kycDocuments);
						}
					}
				}
				KycDetailsList.add(kycDetails);
			}
		}
		return KycDetailsList;
	}

	@Override
	public KycDetailsDTO getKycDetails(String msisdn) {
		KycDetails kycDetails = kycDetailsRepository.findOne(msisdn);
		if (kycDetails != null) {
			List<KycDocuments> oldDocumentsLists = kycDocumentsRepository.findByMsisdn(msisdn);
			return KycDtoHelper.prepareDto(kycDetails, oldDocumentsLists);
		}
		return null;
	}

}
