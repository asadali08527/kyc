package co.yabx.kyc.app.dto.dtoHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.yabx.kyc.app.dto.KycDetailsDTO;
import co.yabx.kyc.app.dto.KycDocumentsDTO;
import co.yabx.kyc.app.entity.KycDetails;
import co.yabx.kyc.app.entity.KycDocuments;

public class KycDtoHelper implements Serializable {

	public static KycDetailsDTO prepareDto(KycDetails kycDetails, List<KycDocuments> oldDocumentsLists) {
		if (kycDetails != null) {
			KycDetailsDTO kycDetailsDTO = new KycDetailsDTO();
			kycDetailsDTO.setMsisdn(kycDetails.getMsisdn());
			kycDetailsDTO.setFirstName(kycDetails.getFirstName());
			kycDetailsDTO.setMiddleName(kycDetails.getMiddleName());
			kycDetailsDTO.setLastName(kycDetails.getLastName());
			kycDetailsDTO.setHouseNumberOrStreetName(kycDetails.getHouseNumberOrStreetName());
			kycDetailsDTO.setArea(kycDetails.getArea());
			kycDetailsDTO.setCity(kycDetails.getCity());
			kycDetailsDTO.setZipCode(kycDetails.getZipCode());
			kycDetailsDTO.setDob(kycDetails.getDob());
			kycDetailsDTO.setGender(kycDetails.getGender());
			kycDetailsDTO.setCreatedBy(kycDetails.getCreatedBy());
			kycDetailsDTO.setUpdatedBy(kycDetails.getUpdatedBy());
			kycDetailsDTO.setCreatedAt(kycDetails.getCreatedAt());
			kycDetailsDTO.setUpdatedAt(kycDetails.getUpdatedAt());
			kycDetailsDTO.setKycDocuments(prepareDocumentsDto(oldDocumentsLists));
			return kycDetailsDTO;
		}
		return null;
	}

	private static List<KycDocumentsDTO> prepareDocumentsDto(List<KycDocuments> oldDocumentsLists2) {
		if (oldDocumentsLists2 != null) {
			List<KycDocumentsDTO> kycDocumentList = new ArrayList<KycDocumentsDTO>();
			for (KycDocuments kycDocuments : oldDocumentsLists2) {
				KycDocumentsDTO kycDocumentsDto = new KycDocumentsDTO();
				kycDocumentsDto.setDocumentExpiryDate(kycDocuments.getDocumentExpiryDate());
				kycDocumentsDto.setDocumentIssueDate(kycDocuments.getDocumentIssueDate());
				kycDocumentsDto.setDocumentNumber(kycDocuments.getDocumentNumber());
				kycDocumentsDto.setDocumentSide(kycDocuments.getDocumentSide());
				kycDocumentsDto.setDocumentType(kycDocuments.getDocumentType());
				kycDocumentsDto.setDocumentUrl(kycDocuments.getDocumentUrl());
				kycDocumentsDto.setSelfie(kycDocuments.isSelfie());
				kycDocumentsDto.setSnapTime(kycDocuments.getSnapTime());
				kycDocumentsDto.setCreatedAt(kycDocuments.getCreatedAt());
				kycDocumentsDto.setUpdatedAt(kycDocuments.getUpdatedAt());
				kycDocumentList.add(kycDocumentsDto);

			}
			return kycDocumentList;

		}
		return null;
	}

}
