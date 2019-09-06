package co.yabx.kyc.app.dto.dtoHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.yabx.kyc.app.dto.KycDetailsDTO;
import co.yabx.kyc.app.dto.KycDocumentsDTO;
import co.yabx.kyc.app.entity.KycDetails;
import co.yabx.kyc.app.entity.KycDocuments;
import co.yabx.kyc.app.util.EncoderDecoderUtil;
import co.yabx.kyc.app.util.MaskUtil;

public class KycDtoHelper implements Serializable {

	public static KycDetailsDTO prepareDto(KycDetails kycDetails, List<KycDocuments> oldDocumentsLists,
			boolean masked) {
		if (kycDetails != null) {
			KycDetailsDTO kycDetailsDTO = new KycDetailsDTO();
			kycDetailsDTO.setMsisdn(MaskUtil
					.showDataInXXXFormat(EncoderDecoderUtil.base64Decode(kycDetails.getMsisdn()), "msisdn", masked));
			kycDetailsDTO.setFirstName(MaskUtil.showDataInXXXFormat(
					EncoderDecoderUtil.base64Decode(kycDetails.getFirstName()), "firstName", masked));
			kycDetailsDTO.setMiddleName(MaskUtil.showDataInXXXFormat(
					EncoderDecoderUtil.base64Decode(kycDetails.getMiddleName()), "middleName", masked));
			kycDetailsDTO.setLastName(MaskUtil.showDataInXXXFormat(
					EncoderDecoderUtil.base64Decode(kycDetails.getLastName()), "lastName", masked));
			kycDetailsDTO.setHouseNumberOrStreetName(MaskUtil.showDataInXXXFormat(
					EncoderDecoderUtil.base64Decode(kycDetails.getHouseNumberOrStreetName()), "streetOrHouse", masked));
			kycDetailsDTO.setArea(MaskUtil.showDataInXXXFormat(EncoderDecoderUtil.base64Decode(kycDetails.getArea()),
					"area", masked));
			kycDetailsDTO.setCity(MaskUtil.showDataInXXXFormat(EncoderDecoderUtil.base64Decode(kycDetails.getCity()),
					"city", masked));
			kycDetailsDTO.setRegion(MaskUtil
					.showDataInXXXFormat(EncoderDecoderUtil.base64Decode(kycDetails.getRegion()), "region", masked));
			kycDetailsDTO.setZipCode(kycDetails.getZipCode());
			kycDetailsDTO.setDob(kycDetails.getDob());
			kycDetailsDTO.setPob(
					MaskUtil.showDataInXXXFormat(EncoderDecoderUtil.base64Decode(kycDetails.getPob()), "pob", masked));
			kycDetailsDTO.setGender(kycDetails.getGender());
			kycDetailsDTO.setCreatedBy(kycDetails.getCreatedBy());
			kycDetailsDTO.setUpdatedBy(kycDetails.getUpdatedBy());
			kycDetailsDTO.setCreatedAt(kycDetails.getCreatedAt());
			kycDetailsDTO.setUpdatedAt(kycDetails.getUpdatedAt());
			kycDetailsDTO.setKycDocuments(prepareDocumentsDto(oldDocumentsLists, masked));
			return kycDetailsDTO;
		}
		return null;
	}

	private static List<KycDocumentsDTO> prepareDocumentsDto(List<KycDocuments> oldDocumentsLists2, boolean masked) {
		if (oldDocumentsLists2 != null) {
			List<KycDocumentsDTO> kycDocumentList = new ArrayList<KycDocumentsDTO>();
			for (KycDocuments kycDocuments : oldDocumentsLists2) {
				KycDocumentsDTO kycDocumentsDto = new KycDocumentsDTO();
				kycDocumentsDto.setDocumentExpiryDate(kycDocuments.getDocumentExpiryDate());
				kycDocumentsDto.setDocumentIssueDate(kycDocuments.getDocumentIssueDate());
				kycDocumentsDto.setDocumentNumber(MaskUtil.showDataInXXXFormat(
						EncoderDecoderUtil.base64Decode(kycDocuments.getDocumentNumber()), "documentNumber", masked));
				kycDocumentsDto.setDocumentSide(kycDocuments.getDocumentSide());
				kycDocumentsDto.setDocumentType(kycDocuments.getDocumentType());
				kycDocumentsDto.setPlaceOfIssue(MaskUtil.showDataInXXXFormat(
						EncoderDecoderUtil.base64Decode(kycDocuments.getPlaceOfIssue()), "poi", masked));
				kycDocumentsDto.setDocumentUrl(EncoderDecoderUtil.base64Decode(kycDocuments.getDocumentUrl()));
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
