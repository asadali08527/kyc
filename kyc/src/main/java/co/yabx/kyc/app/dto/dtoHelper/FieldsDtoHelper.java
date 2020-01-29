package co.yabx.kyc.app.dto.dtoHelper;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.yabx.kyc.app.dto.FieldsDTO;
import co.yabx.kyc.app.dto.Functionality;
import co.yabx.kyc.app.dto.GroupsDTO;
import co.yabx.kyc.app.dto.SubFieldsDTO;
import co.yabx.kyc.app.entities.Fields;
import co.yabx.kyc.app.entities.SectionGroupRelationship;
import co.yabx.kyc.app.entities.Sections;
import co.yabx.kyc.app.entities.TextTemplates;
import co.yabx.kyc.app.entities.filter.Filters;
import co.yabx.kyc.app.entities.filter.Operations;
import co.yabx.kyc.app.entities.filter.SubFields;
import co.yabx.kyc.app.entities.filter.SubGroups;
import co.yabx.kyc.app.enums.AddressProof;
import co.yabx.kyc.app.enums.AddressType;
import co.yabx.kyc.app.enums.AttachmentType;
import co.yabx.kyc.app.enums.BankAccountType;
import co.yabx.kyc.app.enums.BusinessSector;
import co.yabx.kyc.app.enums.BusinessType;
import co.yabx.kyc.app.enums.Cities;
import co.yabx.kyc.app.enums.Countries;
import co.yabx.kyc.app.enums.Currency;
import co.yabx.kyc.app.enums.Divisions;
import co.yabx.kyc.app.enums.DocumentSide;
import co.yabx.kyc.app.enums.DocumentType;
import co.yabx.kyc.app.enums.EducationalQualification;
import co.yabx.kyc.app.enums.FacilityDetails;
import co.yabx.kyc.app.enums.FacilityType;
import co.yabx.kyc.app.enums.FunctionalityType;
import co.yabx.kyc.app.enums.Gender;
import co.yabx.kyc.app.enums.IdentityProof;
import co.yabx.kyc.app.enums.LiabilityType;
import co.yabx.kyc.app.enums.LicenseType;
import co.yabx.kyc.app.enums.MaritalStatuses;
import co.yabx.kyc.app.enums.ModeOfOperation;
import co.yabx.kyc.app.enums.Nationality;
import co.yabx.kyc.app.enums.ResidentStatus;
import co.yabx.kyc.app.enums.TypeOfConcern;
import co.yabx.kyc.app.fullKyc.entity.AddressDetails;
import co.yabx.kyc.app.fullKyc.entity.AttachmentDetails;
import co.yabx.kyc.app.fullKyc.entity.Attachments;
import co.yabx.kyc.app.fullKyc.entity.BankAccountDetails;
import co.yabx.kyc.app.fullKyc.entity.BusinessDetails;
import co.yabx.kyc.app.fullKyc.entity.FieldRemarks;
import co.yabx.kyc.app.fullKyc.entity.IntroducerDetails;
import co.yabx.kyc.app.fullKyc.entity.LiabilitiesDetails;
import co.yabx.kyc.app.fullKyc.entity.LicenseDetails;
import co.yabx.kyc.app.fullKyc.entity.MonthlyTransactionProfiles;
import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.fullKyc.entity.WorkEducationDetails;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.TextTemplateService;
import co.yabx.kyc.app.util.SpringUtil;

public class FieldsDtoHelper implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(FieldsDtoHelper.class);

	private static DecimalFormat decimalFormat = new DecimalFormat("0.000");

	public static List<FieldsDTO> getFields(Set<Fields> appDynamicFieldsSet, User retailers,
			Map<String, Integer> filledVsUnfilled, Sections appPagesSections, User nominee,
			Set<AddressDetails> userAddressDetailsSet, Set<AddressDetails> nomineeAddressDetailsSet,
			Set<AddressDetails> businessAddressDetailsSet, Set<BankAccountDetails> userBankAccountDetailsSet,
			Set<BankAccountDetails> nomineeBankAccountDetailsSet, Set<BankAccountDetails> businessBankAccountDetailsSet,
			SubGroups subGroups, Filters filter, SectionGroupRelationship sectionGroupRelationship,
			List<GroupsDTO> groupsDTOList, List<FieldRemarks> fieldRemarksList, List<TextTemplates> textTemplatesList) {
		Integer totalFields = 0;
		List<FieldsDTO> appDynamicFieldsDTOSet = new ArrayList<FieldsDTO>();

		for (Fields dynamicFields : appDynamicFieldsSet) {
			boolean isProcessed = false;
			if (dynamicFields.getGroups().getGroupId() == 1
					&& (appPagesSections.getSectionId() == 1 || appPagesSections.getSectionId() == 3)) {
				// User personal Info
				isProcessed = prepareProfileInformation(dynamicFields, retailers, appDynamicFieldsDTOSet, filter,
						filledVsUnfilled, fieldRemarksList, textTemplatesList);
			} else if (dynamicFields.getGroups().getGroupId() == 1 && appPagesSections.getSectionId() == 2) {
				// nominee
				if (dynamicFields.getFieldId().equals("msisdn")) {
					dynamicFields.setEditable(true);
				}
				isProcessed = prepareProfileInformation(dynamicFields, nominee, appDynamicFieldsDTOSet, filter,
						filledVsUnfilled, fieldRemarksList, textTemplatesList);
			} else if (dynamicFields.getGroups().getGroupId() == 2
					&& (appPagesSections.getSectionId() == 1 || appPagesSections.getSectionId() == 3)) {
				// user address details
				isProcessed = prepareAddress(dynamicFields, userAddressDetailsSet, appDynamicFieldsDTOSet, subGroups,
						filter, filledVsUnfilled, fieldRemarksList, textTemplatesList);
			} else if (dynamicFields.getGroups().getGroupId() == 2 && appPagesSections.getSectionId() == 2) {
				// nominee address details
				LOGGER.info("Nominee Address ={}", nomineeAddressDetailsSet);
				isProcessed = prepareAddress(dynamicFields, nomineeAddressDetailsSet, appDynamicFieldsDTOSet, subGroups,
						filter, filledVsUnfilled, fieldRemarksList, textTemplatesList);
			} else if (dynamicFields.getGroups().getGroupId() == 2 && appPagesSections.getSectionId() == 5) {
				// Business address details
				isProcessed = prepareAddress(dynamicFields, businessAddressDetailsSet, appDynamicFieldsDTOSet,
						subGroups, filter, filledVsUnfilled, fieldRemarksList, textTemplatesList);
			} else if (dynamicFields.getGroups().getGroupId() == 3
					&& (appPagesSections.getSectionId() == 1 || appPagesSections.getSectionId() == 3)) {
				// user account details
				isProcessed = prepareAccountInformations(dynamicFields, userBankAccountDetailsSet,
						appDynamicFieldsDTOSet, filter, filledVsUnfilled, fieldRemarksList, textTemplatesList);
			} else if (dynamicFields.getGroups().getGroupId() == 3 && appPagesSections.getSectionId() == 2) {
				// nominee account details
				LOGGER.info("Nominee Bank Account ={}", nomineeBankAccountDetailsSet);
				isProcessed = prepareAccountInformations(dynamicFields, nomineeBankAccountDetailsSet,
						appDynamicFieldsDTOSet, filter, filledVsUnfilled, fieldRemarksList, textTemplatesList);
			} else if (dynamicFields.getGroups().getGroupId() == 3 && appPagesSections.getSectionId() == 5) {
				// business account details
				isProcessed = prepareAccountInformations(dynamicFields, businessBankAccountDetailsSet,
						appDynamicFieldsDTOSet, filter, filledVsUnfilled, fieldRemarksList, textTemplatesList);
			} else if (dynamicFields.getGroups().getGroupId() == 4) {
				isProcessed = prepareLiabilitiesDetails(dynamicFields, retailers, appDynamicFieldsDTOSet, filter,
						subGroups, filledVsUnfilled, fieldRemarksList, textTemplatesList);
			} else if (dynamicFields.getGroups().getGroupId() == 5) {
				isProcessed = prepareBusinessInformation(dynamicFields, retailers, appDynamicFieldsDTOSet, filter,
						filledVsUnfilled, fieldRemarksList, textTemplatesList);
			} else if (dynamicFields.getGroups().getGroupId() == 6) {
				isProcessed = prepareLicenseDetails(dynamicFields, retailers, appDynamicFieldsDTOSet, filter,
						filledVsUnfilled, subGroups, fieldRemarksList, textTemplatesList);
			} else if (dynamicFields.getGroups().getGroupId() == 7) {
				isProcessed = prepareMonthlyTransactionProfile(dynamicFields, retailers, appDynamicFieldsDTOSet, filter,
						filledVsUnfilled, fieldRemarksList, textTemplatesList);
			} else if (dynamicFields.getGroups().getGroupId() == 8
					&& (appPagesSections.getSectionId() == 1 || appPagesSections.getSectionId() == 3)) {
				// user or distributor work education
				isProcessed = prepareWorkEducationDetails(dynamicFields, retailers, appDynamicFieldsDTOSet, filter,
						filledVsUnfilled, fieldRemarksList, textTemplatesList);
			} else if (dynamicFields.getGroups().getGroupId() == 8 && appPagesSections.getSectionId() == 2) {
				// nominee work education
				isProcessed = prepareWorkEducationDetails(dynamicFields, nominee, appDynamicFieldsDTOSet, filter,
						filledVsUnfilled, fieldRemarksList, textTemplatesList);
			} else if (dynamicFields.getGroups().getGroupId() == 9) {
				// Introducer Detaiils
				isProcessed = prepareIntroducerDetails(dynamicFields, retailers, appDynamicFieldsDTOSet, filter,
						filledVsUnfilled, fieldRemarksList, textTemplatesList);
			} else if (dynamicFields.getGroups().getGroupId() == 10) {
				// Attachment Detaiils
				isProcessed = prepareAttachmentDetails(dynamicFields, retailers, appDynamicFieldsDTOSet, filter,
						filledVsUnfilled, fieldRemarksList, textTemplatesList);
			}
			if (isProcessed)
				totalFields++;
			if (isHavingSubFields(dynamicFields)) {
				totalFields++;
			}
		}

		filledVsUnfilled.put("totalFields", totalFields);
		// appDynamicFieldsDTOSet.add(appDynamicFieldsDTOSet.stream().max(Comparator.comparing(FieldsDTO::getId)).get());
		return appDynamicFieldsDTOSet;
	}

	private static boolean isHavingSubFields(Fields dynamicFields) {
		if (dynamicFields != null) {
			Set<SubFields> subFields = dynamicFields.getSubFields();
			if (subFields != null && !subFields.isEmpty()) {
				return true;
			}
		}
		return false;

	}

	private static boolean checkSubFields(Fields dynamicFields) {
		if (dynamicFields != null) {
			Set<SubFields> subFields = dynamicFields.getSubFields();
			if (subFields != null && !subFields.isEmpty()) {
				for (SubFields subField : subFields) {
					Fields field = subField.getChild();
					return field.getSavedData() != null;
				}
			}
		}
		return false;
	}

	private static boolean prepareAttachmentDetails(Fields dynamicFields, User retailers,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter, Map<String, Integer> filledVsUnfilled,
			List<FieldRemarks> fieldRemarksList, List<TextTemplates> textTemplatesList) {
		FieldsDTO appDynamicFieldsDTO = new FieldsDTO();
		if (retailers == null || retailers.getAttachmentDetails() == null
				|| retailers.getAttachmentDetails().isEmpty()) {
			if (dynamicFields.getFieldId().equals("idProof")) {
				List<String> options = new ArrayList<String>();
				IdentityProof[] idProof = IdentityProof.values();
				for (IdentityProof proof : idProof) {
					options.add(proof.toString());
				}
				dynamicFields.setOptions(options);

			} else if (dynamicFields.getFieldId().equals("addressProof")) {
				AddressProof[] addressProofs = AddressProof.values();
				List<String> options = new ArrayList<String>();
				for (AddressProof proof : addressProofs) {
					options.add(proof.toString());
				}
				dynamicFields.setOptions(options);
			}
			FieldsDTO fieldsDTO = getAppDynamicFieldDTO(dynamicFields, fieldRemarksList, textTemplatesList,
					appDynamicFieldsDTO);
			List<SubFieldsDTO> subFieldsDTOs = getSubFileds(dynamicFields, null, filledVsUnfilled, fieldRemarksList,
					textTemplatesList);
			/*
			 * if (subFieldsDTOs != null)
			 * subFieldsDTOs.stream().sorted().collect(Collectors.toList());
			 */
			fieldsDTO.setSubFields(subFieldsDTOs);
			appDynamicFieldsDTOSet.add(fieldsDTO);
			return true;
		} else {
			Set<AttachmentDetails> attachmentDetailsSet = retailers.getAttachmentDetails();
			Optional<AttachmentDetails> attachmentDetails = null;
			if (dynamicFields.getFieldId().equals("idProof")) {
				attachmentDetails = attachmentDetailsSet.stream().filter(f -> f != null && f.getAttachmentType() != null
						&& f.getAttachmentType().equals(AttachmentType.IdentityProof)).findFirst();
				List<String> options = new ArrayList<String>();
				IdentityProof[] idProof = IdentityProof.values();
				for (IdentityProof proof : idProof) {
					options.add(proof.toString());
				}
				dynamicFields.setOptions(options);
			} else if (dynamicFields.getFieldId().equals("addressProof")) {
				attachmentDetails = attachmentDetailsSet.stream().filter(f -> f != null && f.getAttachmentType() != null
						&& f.getAttachmentType().equals(AttachmentType.AddressProof)).findFirst();
				AddressProof[] addressProofs = AddressProof.values();
				List<String> options = new ArrayList<String>();
				for (AddressProof proof : addressProofs) {
					options.add(proof.toString());
				}
				dynamicFields.setOptions(options);

			} else if (dynamicFields.getFieldId().equals("tinCertificates")) {
				attachmentDetails = attachmentDetailsSet.stream().filter(f -> f != null && f.getDocumentType() != null
						&& f.getDocumentType().equals(DocumentType.TIN_CERTIFICATE.toString())).findFirst();
				setSavedAttachment(dynamicFields, attachmentDetails, filledVsUnfilled);

			} else if (dynamicFields.getFieldId().equals("tradeLicense")) {
				attachmentDetails = attachmentDetailsSet.stream().filter(f -> f != null && f.getDocumentType() != null
						&& f.getDocumentType().equals(DocumentType.TRADE_LICENSE.toString())).findFirst();
				setSavedAttachment(dynamicFields, attachmentDetails, filledVsUnfilled);

			} else if (dynamicFields.getFieldId().equals("nomineePhoto")) {
				attachmentDetails = attachmentDetailsSet.stream().filter(f -> f != null && f.getDocumentType() != null
						&& f.getDocumentType().equals(DocumentType.NOMINEE_PHOTO.toString())).findFirst();
				setSavedAttachment(dynamicFields, attachmentDetails, filledVsUnfilled);

			} else if (dynamicFields.getFieldId().equals("signature")) {
				attachmentDetails = attachmentDetailsSet.stream().filter(f -> f != null && f.getDocumentType() != null
						&& f.getDocumentType().equals(DocumentType.SIGNATURE.toString())).findFirst();
				setSavedAttachment(dynamicFields, attachmentDetails, filledVsUnfilled);
			}
			FieldsDTO fieldsDTO = getAppDynamicFieldDTO(dynamicFields, fieldRemarksList, textTemplatesList,
					appDynamicFieldsDTO);
			List<SubFieldsDTO> subFieldsDTOs = getSubFileds(dynamicFields, attachmentDetails, filledVsUnfilled,
					fieldRemarksList, textTemplatesList);
			if (subFieldsDTOs != null)
				subFieldsDTOs.stream().sorted(Comparator.comparing(SubFieldsDTO::getId)).collect(Collectors.toList());
			fieldsDTO.setSubFields(subFieldsDTOs);
			appDynamicFieldsDTOSet.add(fieldsDTO);
			return true;
		}

	}

	private static void setSavedAttachment(Fields dynamicFields, Optional<AttachmentDetails> attachmentDetails,
			Map<String, Integer> filledVsUnfilled) {
		if (attachmentDetails != null && attachmentDetails.isPresent()) {
			Set<Attachments> attachments = attachmentDetails.get().getAttachments();
			if (attachments != null && !attachments.isEmpty()) {
				Optional<Attachments> attachmentOptional = attachments.stream().findFirst();
				if (attachmentOptional.isPresent()) {
					dynamicFields.setSavedData(
							SpringUtil.bean(AppConfigService.class).getProperty("DOCUMENT_STORAGE_BASE_URL", "")
									+ attachmentOptional.get().getDocumentUrl());
					Integer count = filledVsUnfilled.get("filledFields");
					filledVsUnfilled.put("filledFields", count + 1);

				}
			}
		}
	}

	private static boolean prepareIntroducerDetails(Fields dynamicFields, User retailers,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter, Map<String, Integer> filledVsUnfilled,
			List<FieldRemarks> fieldRemarksList, List<TextTemplates> textTemplatesList) {
		FieldsDTO fieldsDTO = new FieldsDTO();
		if (retailers != null && retailers.getIntroducerDetails() != null
				&& retailers.getIntroducerDetails().isEmpty()) {
			Set<IntroducerDetails> introducerDetailsSet = retailers.getIntroducerDetails();
			Optional<IntroducerDetails> introducerDetailsOptional = introducerDetailsSet.stream().findFirst();
			if (dynamicFields.getFieldId().equals("name")) {
				fieldsDTO.setSavedData(
						introducerDetailsOptional.isPresent() ? introducerDetailsOptional.get().getName() : null);
			} else if (dynamicFields.getFieldId().equals("accountNumber")) {
				fieldsDTO
						.setSavedData(introducerDetailsOptional.isPresent()
								? introducerDetailsOptional.get().getAccountNumber() != null
										? introducerDetailsOptional.get().getAccountNumber()
										: null
								: null);
			} else if (dynamicFields.getFieldId().equals("isSignatureVerified")) {
				fieldsDTO.setSavedData(
						introducerDetailsOptional.isPresent() ? introducerDetailsOptional.get().isSignatureVerified()
								: null);
			} else if (dynamicFields.getFieldId().equals("relationship")) {
				fieldsDTO.setSavedData(
						introducerDetailsOptional.isPresent() ? introducerDetailsOptional.get().getRelationship()
								: null);
			}
		}
		increamentFilledFields(fieldsDTO, filledVsUnfilled);
		appDynamicFieldsDTOSet
				.add(getAppDynamicFieldDTO(dynamicFields, fieldRemarksList, textTemplatesList, fieldsDTO));
		return true;
	}

	private static void increamentFilledFields(FieldsDTO fieldsDTO, Map<String, Integer> filledVsUnfilled) {
		if (fieldsDTO.getSavedData() != null) {
			Integer count = filledVsUnfilled.get("filledFields");
			filledVsUnfilled.put("filledFields", count != null ? count + 1 : 1);
		}
	}

	private static boolean prepareWorkEducationDetails(Fields dynamicFields, User retailers,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter, Map<String, Integer> filledVsUnfilled,
			List<FieldRemarks> fieldRemarksList, List<TextTemplates> textTemplatesList) {
		if (checkFilterCriteria(filter, dynamicFields.getFieldId())) {
			FieldsDTO fieldsDTO = new FieldsDTO();
			if (retailers == null || retailers.getWorkEducationDetails() == null
					|| retailers.getWorkEducationDetails().isEmpty()) {
				if (dynamicFields.getFieldId().equals("educationalQualification")) {
					List<String> options = new ArrayList<String>();
					EducationalQualification[] accountTypes = EducationalQualification.values();
					for (EducationalQualification statuses : accountTypes) {
						options.add(statuses.getName());
					}
					dynamicFields.setOptions(options);
				}
			} else {
				Set<WorkEducationDetails> WorkEducationDetailsSet = retailers.getWorkEducationDetails();
				if (dynamicFields.getFieldId().equals("occupation")) {
					Optional<WorkEducationDetails> workEducationDetails = WorkEducationDetailsSet.stream()
							.filter(f -> f.getOccupation() != null).findFirst();
					fieldsDTO.setSavedData(workEducationDetails != null && workEducationDetails.isPresent()
							? workEducationDetails.get().getOccupation()
							: null);
				} else if (dynamicFields.getFieldId().equals("designation")) {
					Optional<WorkEducationDetails> workEducationDetails = WorkEducationDetailsSet.stream()
							.filter(f -> f.getDesignation() != null).findFirst();
					fieldsDTO.setSavedData(workEducationDetails != null && workEducationDetails.isPresent()
							? workEducationDetails.get().getDesignation()
							: null);
				} else if (dynamicFields.getFieldId().equals("employer")) {
					Optional<WorkEducationDetails> workEducationDetails = WorkEducationDetailsSet.stream()
							.filter(f -> f.getEmployer() != null).findFirst();
					fieldsDTO.setSavedData(workEducationDetails != null && workEducationDetails.isPresent()
							? workEducationDetails.get().getEmployer()
							: null);
				} else if (dynamicFields.getFieldId().equals("educationalQualification")) {

					Optional<WorkEducationDetails> workEducationDetails = WorkEducationDetailsSet.stream()
							.filter(f -> f.getDesignation() != null).findFirst();
					try {
						fieldsDTO
								.setSavedData(
										workEducationDetails != null && workEducationDetails.isPresent()
												? workEducationDetails.get().getEducationalQualification() != null
														? workEducationDetails.get().getEducationalQualification()
																.getName()
														: null
												: null);
					} catch (Exception e) {
						LOGGER.error("Exceptiong while parsing educationalQualification={},error={}",
								workEducationDetails, e.getMessage());
					}
					List<String> options = new ArrayList<String>();
					EducationalQualification[] accountTypes = EducationalQualification.values();
					for (EducationalQualification statuses : accountTypes) {
						options.add(statuses.getName());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("experience")) {
					Optional<WorkEducationDetails> workEducationDetails = WorkEducationDetailsSet.stream()
							.filter(f -> f.getDesignation() != null).findFirst();
					try {
						fieldsDTO.setSavedData(workEducationDetails != null && workEducationDetails.isPresent()
								? workEducationDetails.get().getExperience()
								: null);
					} catch (Exception e) {
						LOGGER.error("Exceptiong while parsing experience={},error={}",
								workEducationDetails.get().getExperience(), e.getMessage());
					}

				}
			}
			increamentFilledFields(fieldsDTO, filledVsUnfilled);
			appDynamicFieldsDTOSet
					.add(getAppDynamicFieldDTO(dynamicFields, fieldRemarksList, textTemplatesList, fieldsDTO));
			return true;
		}
		return false;

	}

	private static boolean prepareMonthlyTransactionProfile(Fields dynamicFields, User retailers,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter, Map<String, Integer> filledVsUnfilled,
			List<FieldRemarks> fieldRemarksList, List<TextTemplates> textTemplatesList) {
		FieldsDTO fieldsDTO = new FieldsDTO();
		if (retailers != null && retailers.getMonthlyTransactionProfiles() != null
				&& retailers.getMonthlyTransactionProfiles().isEmpty()) {
			Set<MonthlyTransactionProfiles> monthlyTransactionProfiles = retailers.getMonthlyTransactionProfiles();
			Optional<MonthlyTransactionProfiles> monthlyTransactionProfileOptional = monthlyTransactionProfiles.stream()
					.findFirst();
			if (dynamicFields.getFieldId().equals("monthlyTurnOver")) {
				fieldsDTO
						.setSavedData(
								monthlyTransactionProfileOptional.isPresent()
										? monthlyTransactionProfileOptional.get().getMonthlyTurnOver() != 0.0
												? decimalFormat.format(
														monthlyTransactionProfileOptional.get().getMonthlyTurnOver())
												: null
										: null);
			} else if (dynamicFields.getFieldId().equals("deposits")) {
				fieldsDTO
						.setSavedData(
								monthlyTransactionProfileOptional.isPresent()
										? monthlyTransactionProfileOptional.get().getDeposits() != 0.0 ? decimalFormat
												.format(monthlyTransactionProfileOptional.get().getDeposits()) : null
										: null);
			} else if (dynamicFields.getFieldId().equals("withdrawls")) {
				fieldsDTO
						.setSavedData(
								monthlyTransactionProfileOptional.isPresent()
										? monthlyTransactionProfileOptional.get().getWithdrawls() != 0.0 ? decimalFormat
												.format(monthlyTransactionProfileOptional.get().getWithdrawls()) : null
										: null);
			} else if (dynamicFields.getFieldId().equals("initialDeposit")) {
				fieldsDTO
						.setSavedData(
								monthlyTransactionProfileOptional.isPresent()
										? monthlyTransactionProfileOptional.get().getInitialDeposit() != 0.0
												? decimalFormat.format(
														monthlyTransactionProfileOptional.get().getInitialDeposit())
												: null
										: null);

			}
		}
		increamentFilledFields(fieldsDTO, filledVsUnfilled);
		appDynamicFieldsDTOSet
				.add(getAppDynamicFieldDTO(dynamicFields, fieldRemarksList, textTemplatesList, fieldsDTO));
		return true;

	}

	private static boolean prepareLicenseDetails(Fields dynamicFields, User retailers,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter, Map<String, Integer> filledVsUnfilled,
			SubGroups subGroups, List<FieldRemarks> fieldRemarksList, List<TextTemplates> textTemplatesList) {
		FieldsDTO fieldsDTO = new FieldsDTO();
		if (retailers != null && retailers.getBusinessDetails() != null && !retailers.getBusinessDetails().isEmpty()) {
			Set<BusinessDetails> businessDetailsSet = retailers.getBusinessDetails();
			Optional<BusinessDetails> optional = businessDetailsSet.stream().findFirst();
			Set<LicenseDetails> licenseDetailsSet = optional.isPresent() ? optional.get().getLicenseDetails() : null;
			Optional<LicenseDetails> optionalLicenseDetails = licenseDetailsSet != null
					? licenseDetailsSet.stream()
							.filter(f -> f != null && getLicenseType(subGroups).equals(f.getLicenseType())).findFirst()
					: Optional.empty();
			LicenseDetails licenseDetails = optionalLicenseDetails.isPresent() ? optionalLicenseDetails.get() : null;
			if (dynamicFields.getFieldId().equals("licenseNumber")) {
				fieldsDTO.setSavedData(licenseDetails != null ? licenseDetails.getLicenseNumber() : null);
			} else if (dynamicFields.getFieldId().equals("licenseExpiryDate")) {
				fieldsDTO.setSavedData(licenseDetails != null ? licenseDetails.getLicenseExpiryDate() : null);
			} else if (dynamicFields.getFieldId().equals("licenseIssuingAuthority")) {
				fieldsDTO.setSavedData(licenseDetails != null ? licenseDetails.getLicenseIssuingAuthority() : null);
			} else if (dynamicFields.getFieldId().equals("licenseType")) {
				fieldsDTO.setSavedData(licenseDetails != null && licenseDetails.getLicenseType() != null
						? licenseDetails.getLicenseType().toString()
						: null);
			}
		}
		increamentFilledFields(fieldsDTO, filledVsUnfilled);
		appDynamicFieldsDTOSet
				.add(getAppDynamicFieldDTO(dynamicFields, fieldRemarksList, textTemplatesList, fieldsDTO));
		return true;

	}

	private static LicenseType getLicenseType(SubGroups subGroups) {
		if (subGroups != null) {
			if ("Trade License".equalsIgnoreCase(subGroups.getGroupType()))
				return LicenseType.TRADE;
			else
				return LicenseType.OTHER;

		}
		return null;

	}

	private static boolean prepareBusinessInformation(Fields dynamicFields, User retailers,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter, Map<String, Integer> filledVsUnfilled,
			List<FieldRemarks> fieldRemarksList, List<TextTemplates> textTemplatesList) {
		if (checkFilterCriteria(filter, dynamicFields.getFieldId())) {
			FieldsDTO fieldsDTO = new FieldsDTO();
			if (retailers == null || retailers.getBusinessDetails() == null
					|| retailers.getBusinessDetails().isEmpty()) {
				if (dynamicFields.getFieldId().equals("facilityDetails")) {
					List<String> options = new ArrayList<String>();
					FacilityDetails[] accountTypes = FacilityDetails.values();
					for (FacilityDetails statuses : accountTypes) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("facilityType")) {
					List<String> options = new ArrayList<String>();
					FacilityType[] facilityTypes = FacilityType.values();
					for (FacilityType facilityType : facilityTypes) {
						options.add(facilityType.getName());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("businessType")) {
					List<String> options = new ArrayList<String>();
					BusinessType[] accountTypes = BusinessType.values();
					for (BusinessType statuses : accountTypes) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("sector")) {
					List<String> options = new ArrayList<String>();
					BusinessSector[] accountTypes = BusinessSector.values();
					for (BusinessSector statuses : accountTypes) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				}
				increamentFilledFields(fieldsDTO, filledVsUnfilled);
				appDynamicFieldsDTOSet
						.add(getAppDynamicFieldDTO(dynamicFields, fieldRemarksList, textTemplatesList, fieldsDTO));
				return true;
			} else {
				Set<BusinessDetails> BusinessDetailsSet = retailers.getBusinessDetails();
				Optional<BusinessDetails> businessDetailsOptional = BusinessDetailsSet.stream().findFirst();
				if (dynamicFields.getFieldId().equals("businessPhone")) {
					fieldsDTO.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getBusinessPhone()
									: null);
				} else if (dynamicFields.getFieldId().equals("businessName")) {
					fieldsDTO.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getBusinessName()
									: null);
				} else if (dynamicFields.getFieldId().equals("directorOrPartnerName")) {
					fieldsDTO.setSavedData(businessDetailsOptional.isPresent()
							? businessDetailsOptional.get().getDirectorOrPartnerName()
							: null);
				} else if (dynamicFields.getFieldId().equals("facilityDetails")) {
					fieldsDTO
							.setSavedData(businessDetailsOptional.isPresent()
									? businessDetailsOptional.get().getFacilityDetails() != null
											? businessDetailsOptional.get().getFacilityDetails().toString()
											: null
									: null);
					List<String> options = new ArrayList<String>();
					FacilityDetails[] accountTypes = FacilityDetails.values();
					for (FacilityDetails statuses : accountTypes) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("facilityType")) {
					fieldsDTO
							.setSavedData(businessDetailsOptional.isPresent()
									? businessDetailsOptional.get().getFacilityType() != null
											? businessDetailsOptional.get().getFacilityType().toString()
											: null
									: null);
					List<String> options = new ArrayList<String>();
					FacilityType[] accountTypes = FacilityType.values();
					for (FacilityType statuses : accountTypes) {
						options.add(statuses.getName());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("fixedAssetPurchase")) {
					fieldsDTO.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getFixedAssetPurchase()
									: null);
				} else if (dynamicFields.getFieldId().equals("fixedAssetName")) {
					fieldsDTO.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getFixedAssetName()
									: null);
				} else if (dynamicFields.getFieldId().equals("price")) {
					fieldsDTO.setSavedData(businessDetailsOptional.isPresent()
							? decimalFormat.format(businessDetailsOptional.get().getPrice())
							: null);
				} else if (dynamicFields.getFieldId().equals("origin")) {
					fieldsDTO.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getOrigin() : null);
				} else if (dynamicFields.getFieldId().equals("proposedCollateral")) {
					fieldsDTO.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getProposedCollateral()
									: null);
				} else if (dynamicFields.getFieldId().equals("businessType")) {
					fieldsDTO
							.setSavedData(businessDetailsOptional.isPresent()
									? businessDetailsOptional.get().getBusinessType() != null
											? businessDetailsOptional.get().getBusinessType().toString()
											: null
									: null);
					List<String> options = new ArrayList<String>();
					BusinessType[] accountTypes = BusinessType.values();
					for (BusinessType statuses : accountTypes) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("sector")) {
					fieldsDTO
							.setSavedData(businessDetailsOptional.isPresent()
									? businessDetailsOptional.get().getSector() != null
											? businessDetailsOptional.get().getSector().toString()
											: null
									: null);
					List<String> options = new ArrayList<String>();
					BusinessSector[] accountTypes = BusinessSector.values();
					for (BusinessSector statuses : accountTypes) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("detailOfBusness")) {
					fieldsDTO.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getDetailOfBusness()
									: null);
				} else if (dynamicFields.getFieldId().equals("initialCapital")) {
					fieldsDTO.setSavedData(businessDetailsOptional.isPresent()
							? decimalFormat.format(businessDetailsOptional.get().getInitialCapital())
							: null);
				} else if (dynamicFields.getFieldId().equals("fundSource")) {
					fieldsDTO.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getFundSource() : null);
				} else if (dynamicFields.getFieldId().equals("vatRegistrationNumber")) {
					fieldsDTO.setSavedData(businessDetailsOptional.isPresent()
							? businessDetailsOptional.get().getVatRegistrationNumber()
							: null);
				} else if (dynamicFields.getFieldId().equals("businessStartDate")) {
					fieldsDTO.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getBusinessStartDate()
									: null);
				} else if (dynamicFields.getFieldId().equals("businessTin")) {
					fieldsDTO.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getBusinessTin()
									: null);
				} else if (dynamicFields.getFieldId().equals("annualSales")) {
					fieldsDTO
							.setSavedData(
									businessDetailsOptional.isPresent()
											? businessDetailsOptional.get().getAnnualSales() != 0.0 ? decimalFormat
													.format(businessDetailsOptional.get().getAnnualSales()) : null
											: null);
				} else if (dynamicFields.getFieldId().equals("annualGrossProfit")) {
					fieldsDTO
							.setSavedData(businessDetailsOptional.isPresent()
									? businessDetailsOptional.get().getAnnualGrossProfit() != 0.0
											? decimalFormat.format(businessDetailsOptional.get().getAnnualGrossProfit())
											: null
									: null);
				} else if (dynamicFields.getFieldId().equals("annualExpenses")) {
					fieldsDTO
							.setSavedData(
									businessDetailsOptional.isPresent()
											? businessDetailsOptional.get().getAnnualExpenses() != 0.0 ? decimalFormat
													.format(businessDetailsOptional.get().getAnnualExpenses()) : null
											: null);
				} else if (dynamicFields.getFieldId().equals("valueOfFixedAssets")) {
					fieldsDTO
							.setSavedData(
									businessDetailsOptional.isPresent()
											? businessDetailsOptional.get().getValueOfFixedAssets() != 0.0
													? decimalFormat.format(
															businessDetailsOptional.get().getValueOfFixedAssets())
													: null
											: null);
				} else if (dynamicFields.getFieldId().equals("numberOfEmployees")) {
					fieldsDTO
							.setSavedData(businessDetailsOptional.isPresent()
									? businessDetailsOptional.get().getNumberOfEmployees() != 0
											? businessDetailsOptional.get().getNumberOfEmployees()
											: null
									: null);
				} else if (dynamicFields.getFieldId().equals("stockValue")) {
					fieldsDTO
							.setSavedData(
									businessDetailsOptional.isPresent()
											? businessDetailsOptional.get().getStockValue() != 0.0 ? decimalFormat
													.format(businessDetailsOptional.get().getStockValue()) : null
											: null);
				} else if (dynamicFields.getFieldId().equals("deposits")) {
					fieldsDTO
							.setSavedData(businessDetailsOptional.isPresent()
									? businessDetailsOptional.get().getDeposits() != 0.0
											? decimalFormat.format(businessDetailsOptional.get().getDeposits())
											: null
									: null);
				} else if (dynamicFields.getFieldId().equals("withdrawls")) {
					fieldsDTO
							.setSavedData(
									businessDetailsOptional.isPresent()
											? businessDetailsOptional.get().getWithdrawls() != 0.0 ? decimalFormat
													.format(businessDetailsOptional.get().getWithdrawls()) : null
											: null);
				} else if (dynamicFields.getFieldId().equals("initialDeposit")) {
					fieldsDTO
							.setSavedData(
									businessDetailsOptional.isPresent()
											? businessDetailsOptional.get().getInitialDeposit() != 0.0 ? decimalFormat
													.format(businessDetailsOptional.get().getInitialDeposit()) : null
											: null);
				}
				increamentFilledFields(fieldsDTO, filledVsUnfilled);
				appDynamicFieldsDTOSet
						.add(getAppDynamicFieldDTO(dynamicFields, fieldRemarksList, textTemplatesList, fieldsDTO));
				return true;
			}
		}
		return false;

	}

	private static boolean prepareLiabilitiesDetails(Fields dynamicFields, User retailers,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter, SubGroups subGroups,
			Map<String, Integer> filledVsUnfilled, List<FieldRemarks> fieldRemarksList,
			List<TextTemplates> textTemplatesList) {
		FieldsDTO fieldsDTO = new FieldsDTO();
		if (retailers != null
				&& retailers.getLiabilitiesDetails() != null != retailers.getLiabilitiesDetails().isEmpty()) {
			Set<LiabilitiesDetails> LiabilitiesDetailsSet = retailers.getLiabilitiesDetails();
			LiabilitiesDetails liabilitiesDetails = getLiabilitiesDetails(subGroups, LiabilitiesDetailsSet);
			if (dynamicFields.getFieldId().equals("loanAmount")) {
				fieldsDTO.setSavedData(
						liabilitiesDetails != null ? decimalFormat.format(liabilitiesDetails.getLoanAmount()) : null);
			} else if (dynamicFields.getFieldId().equals("bankOrNbfiName")) {
				fieldsDTO.setSavedData(liabilitiesDetails != null ? liabilitiesDetails.getBankOrNbfiName() : null);
			} else if (dynamicFields.getFieldId().equals("liabilityFromOtherOrganization")) {
				fieldsDTO.setSavedData(
						liabilitiesDetails != null ? liabilitiesDetails.getLiabilityFromOtherOrganization() : null);
			} else if (dynamicFields.getFieldId().equals("loanAmountFromOtherOrganization")) {
				fieldsDTO.setSavedData(
						liabilitiesDetails != null ? decimalFormat.format(liabilitiesDetails.getLoanAmount()) : null);
			}
		}
		increamentFilledFields(fieldsDTO, filledVsUnfilled);
		appDynamicFieldsDTOSet
				.add(getAppDynamicFieldDTO(dynamicFields, fieldRemarksList, textTemplatesList, fieldsDTO));
		return true;

	}

	private static LiabilitiesDetails getLiabilitiesDetails(SubGroups subGroups,
			Set<LiabilitiesDetails> liabilitiesDetailsSet) {

		Optional<LiabilitiesDetails> liabilitiesDetailsOptional = liabilitiesDetailsSet.stream().filter(f -> f != null
				&& f.getLiabilityType() != null && f.getLiabilityType().equals(getLiabilityType(subGroups)))
				.findFirst();
		if (liabilitiesDetailsOptional.isPresent())
			return liabilitiesDetailsOptional.get();
		return null;
	}

	private static LiabilityType getLiabilityType(SubGroups subGroups) {
		if (subGroups != null) {
			String groupType = subGroups.getGroupType();
			if ("Personal Liabilities".equalsIgnoreCase(groupType)) {
				return LiabilityType.PERSONAL;
			} else if ("Business Liabilities".equalsIgnoreCase(groupType)) {
				return LiabilityType.BUSINESS;
			}
		}
		return null;
	}

	private static boolean prepareAccountInformations(Fields dynamicFields,
			Set<BankAccountDetails> bankAccountDetailsSet, List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter,
			Map<String, Integer> filledVsUnfilled, List<FieldRemarks> fieldRemarksList,
			List<TextTemplates> textTemplatesList) {
		FieldsDTO fieldsDTO = new FieldsDTO();
		if (bankAccountDetailsSet == null || bankAccountDetailsSet.isEmpty()) {
			if (dynamicFields.getFieldId().equals("bankAccountType")) {
				List<String> options = new ArrayList<String>();
				BankAccountType[] accountTypes = BankAccountType.values();
				for (BankAccountType statuses : accountTypes) {
					options.add(statuses.toString());
				}
				dynamicFields.setOptions(options);
			} else if (dynamicFields.getFieldId().equals("currency")) {
				List<String> options = new ArrayList<String>();
				Currency[] currencies = Currency.values();
				for (Currency statuses : currencies) {
					options.add(statuses.toString());
				}
				dynamicFields.setOptions(options);
			} else if (dynamicFields.getFieldId().equals("typeOfConcern")) {
				List<String> options = new ArrayList<String>();
				TypeOfConcern[] concerns = TypeOfConcern.values();
				for (TypeOfConcern concern : concerns) {
					options.add(concern.toString());
				}
				dynamicFields.setOptions(options);
			} else if (dynamicFields.getFieldId().equals("modeOfOperation")) {
				List<String> options = new ArrayList<String>();
				ModeOfOperation[] currencies = ModeOfOperation.values();
				for (ModeOfOperation statuses : currencies) {
					options.add(statuses.toString());
				}
				dynamicFields.setOptions(options);
			}
		} else {
			Optional<BankAccountDetails> bankAccountDetailsOptional = bankAccountDetailsSet.stream().findFirst();

			if (dynamicFields.getFieldId().equals("accountTitle")) {
				fieldsDTO.setSavedData(
						bankAccountDetailsOptional.isPresent() ? bankAccountDetailsOptional.get().getAccountTitle()
								: null);
			} else if (dynamicFields.getFieldId().equals("typeOfConcern")) {
				fieldsDTO
						.setSavedData(bankAccountDetailsOptional.isPresent()
								? bankAccountDetailsOptional.get().getTypeOfConcern() != null
										? bankAccountDetailsOptional.get().getTypeOfConcern().toString()
										: null
								: null);
				List<String> options = new ArrayList<String>();
				TypeOfConcern[] concerns = TypeOfConcern.values();
				for (TypeOfConcern concern : concerns) {
					options.add(concern.toString());
				}
				dynamicFields.setOptions(options);

			} else if (dynamicFields.getFieldId().equals("bankName")) {
				fieldsDTO.setSavedData(
						bankAccountDetailsOptional.isPresent() ? bankAccountDetailsOptional.get().getBankName() : null);
			} else if (dynamicFields.getFieldId().equals("accountNumber")) {
				fieldsDTO
						.setSavedData(bankAccountDetailsOptional.isPresent()
								? bankAccountDetailsOptional.get().getAccountNumber() != null
										? bankAccountDetailsOptional.get().getAccountNumber()
										: null
								: null);
			} else if (dynamicFields.getFieldId().equals("branch")) {
				fieldsDTO.setSavedData(
						bankAccountDetailsOptional.isPresent() ? bankAccountDetailsOptional.get().getBranch() : null);
			} else if (dynamicFields.getFieldId().equals("accountPurpose")) {
				fieldsDTO
						.setSavedData(bankAccountDetailsOptional.isPresent()
								? bankAccountDetailsOptional.get().getAccountPurpose() != null
										? bankAccountDetailsOptional.get().getAccountPurpose().toString()
										: null
								: null);
			} else if (dynamicFields.getFieldId().equals("modeOfOperation")) {
				fieldsDTO
						.setSavedData(bankAccountDetailsOptional.isPresent()
								? bankAccountDetailsOptional.get().getModeOfOperation() != null
										? bankAccountDetailsOptional.get().getModeOfOperation().toString()
										: null
								: null);
				List<String> options = new ArrayList<String>();
				ModeOfOperation[] currencies = ModeOfOperation.values();
				for (ModeOfOperation statuses : currencies) {
					options.add(statuses.toString());
				}
				dynamicFields.setOptions(options);
			} else if (dynamicFields.getFieldId().equals("currency")) {
				fieldsDTO
						.setSavedData(bankAccountDetailsOptional.isPresent()
								? bankAccountDetailsOptional.get().getCurrency() != null
										? bankAccountDetailsOptional.get().getCurrency().toString()
										: null
								: null);
				List<String> options = new ArrayList<String>();
				Currency[] currencies = Currency.values();
				for (Currency statuses : currencies) {
					options.add(statuses.toString());
				}
				dynamicFields.setOptions(options);
			} else if (dynamicFields.getFieldId().equals("bankAccountType")) {
				fieldsDTO
						.setSavedData(bankAccountDetailsOptional.isPresent()
								? bankAccountDetailsOptional.get().getBankAccountType() != null
										? bankAccountDetailsOptional.get().getBankAccountType().toString()
										: null
								: null);
				List<String> options = new ArrayList<String>();
				BankAccountType[] accountTypes = BankAccountType.values();
				for (BankAccountType statuses : accountTypes) {
					options.add(statuses.toString());
				}
				dynamicFields.setOptions(options);
			}
		}
		increamentFilledFields(fieldsDTO, filledVsUnfilled);
		appDynamicFieldsDTOSet
				.add(getAppDynamicFieldDTO(dynamicFields, fieldRemarksList, textTemplatesList, fieldsDTO));
		return true;

	}

	private static boolean prepareAddress(Fields dynamicFields, Set<AddressDetails> addressDetailsSet,
			List<FieldsDTO> appDynamicFieldsDTOSet, SubGroups subGroups, Filters filter,
			Map<String, Integer> filledVsUnfilled, List<FieldRemarks> fieldRemarksList,
			List<TextTemplates> textTemplatesList) {
		FieldsDTO fieldsDTO = new FieldsDTO();
		if (addressDetailsSet == null || addressDetailsSet.isEmpty()) {
			if (dynamicFields.getFieldId().equals("country")) {
				List<String> options = new ArrayList<String>();
				Countries[] accountTypes = Countries.values();
				for (Countries statuses : accountTypes) {
					options.add(statuses.toString());
				}
				dynamicFields.setOptions(options);
			} else if (dynamicFields.getFieldId().equals("cityDsitrict")) {
				List<String> options = new ArrayList<String>();
				Cities[] accountTypes = Cities.values();
				for (Cities statuses : accountTypes) {
					options.add(statuses.toString());
				}
				dynamicFields.setOptions(options);
			} else if (dynamicFields.getFieldId().equals("division")) {
				List<String> options = new ArrayList<String>();
				Divisions[] accountTypes = Divisions.values();
				for (Divisions statuses : accountTypes) {
					options.add(statuses.toString());
				}
				dynamicFields.setOptions(options);
			}
			appDynamicFieldsDTOSet
					.add(getAppDynamicFieldDTO(dynamicFields, fieldRemarksList, textTemplatesList, fieldsDTO));
		} else {
			AddressDetails addressDetails = getAddressDetails(subGroups, addressDetailsSet);
			if (addressDetailsSet != null) {
				if (dynamicFields.getFieldId().equals("address")) {
					fieldsDTO.setSavedData(addressDetails.getAddress());
				} else if (dynamicFields.getFieldId().equals("upazilaThana")) {
					fieldsDTO.setSavedData(addressDetails.getUpazilaThana());
				} else if (dynamicFields.getFieldId().equals("cityDsitrict")) {
					fieldsDTO.setSavedData(
							addressDetails.getCityDsitrict() != null ? addressDetails.getCityDsitrict().toString()
									: null);
					List<String> options = new ArrayList<String>();
					Cities[] accountTypes = Cities.values();
					for (Cities statuses : accountTypes) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("division")) {
					fieldsDTO.setSavedData(
							addressDetails.getDivision() != null ? addressDetails.getDivision().toString() : null);
					List<String> options = new ArrayList<String>();
					Divisions[] accountTypes = Divisions.values();
					for (Divisions statuses : accountTypes) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);

				} else if (dynamicFields.getFieldId().equals("zipCode")) {
					fieldsDTO.setSavedData(addressDetails.getZipCode() != null ? addressDetails.getZipCode() : null);
				} else if (dynamicFields.getFieldId().equals("landmark")) {
					fieldsDTO.setSavedData(addressDetails.getLandmark());
				} else if (dynamicFields.getFieldId().equals("territory")) {
					fieldsDTO.setSavedData(addressDetails.getTerritory());
				} else if (dynamicFields.getFieldId().equals("country")) {
					fieldsDTO.setSavedData(
							addressDetails.getCountry() != null ? addressDetails.getCountry().toString() : null);
					List<String> options = new ArrayList<String>();
					Countries[] accountTypes = Countries.values();
					for (Countries statuses : accountTypes) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("mobileNumber")) {
					fieldsDTO.setSavedData(addressDetails.getMobileNumber());
				} else if (dynamicFields.getFieldId().equals("phoneNumber")) {
					fieldsDTO.setSavedData(addressDetails.getPhoneNumber());
				} else if (dynamicFields.getFieldId().equals("email")) {
					fieldsDTO.setSavedData(addressDetails.getEmail());
				}
			}
			increamentFilledFields(fieldsDTO, filledVsUnfilled);
			appDynamicFieldsDTOSet
					.add(getAppDynamicFieldDTO(dynamicFields, fieldRemarksList, textTemplatesList, fieldsDTO));
		}
		return true;

	}

	private static AddressDetails getAddressDetails(SubGroups subGroups, Set<AddressDetails> addressDetailsSet) {
		Optional<AddressDetails> addressDetailsOptional = addressDetailsSet.stream()
				.filter(f -> f != null && f.getAddressType().equals(getAddressType(subGroups))).findFirst();
		if (addressDetailsOptional.isPresent())
			return addressDetailsOptional.get();
		return null;
	}

	private static AddressType getAddressType(SubGroups subGroups) {
		if (subGroups != null) {
			String groupType = subGroups.getGroupType();
			if ("Permanent Address".equalsIgnoreCase(groupType)) {
				return AddressType.PERMANNET;
			} else if ("Present Address".equalsIgnoreCase(groupType)) {
				return AddressType.PRESENT;
			} else if ("Registered Address".equalsIgnoreCase(groupType)) {
				return AddressType.BUSINESS_REGISTERED_ADDRESS;
			} else if ("Office Address".equalsIgnoreCase(groupType)) {
				return AddressType.BUSINESS_OFFICE_ADDRESS;
			} else if ("Factory Address".equalsIgnoreCase(groupType)) {
				return AddressType.BUSINESS_FACTORY_ADDRESS;
			} else if ("Other Address".equalsIgnoreCase(groupType)) {
				return AddressType.BUSINESS_OTHER_ADDRESS;
			}
		}
		return null;
	}

	private static boolean prepareProfileInformation(Fields dynamicFields, User retailers,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter, Map<String, Integer> filledVsUnfilled,
			List<FieldRemarks> fieldRemarksList, List<TextTemplates> textTemplatesList) {
		if (checkFilterCriteria(filter, dynamicFields.getFieldId())) {
			FieldsDTO fieldsDTO = new FieldsDTO();
			if (retailers != null) {
				if (dynamicFields.getFieldId().equals("firstName")) {
					fieldsDTO.setSavedData(retailers.getFirstName());
				} else if (dynamicFields.getFieldId().equals("lastName")) {
					fieldsDTO.setSavedData(retailers.getLastName());
				} else if (dynamicFields.getFieldId().equals("middleName")) {
					fieldsDTO.setSavedData(retailers.getMiddleName());
				} else if (dynamicFields.getFieldId().equals("dob")) {
					fieldsDTO.setSavedData(retailers.getDob());
				} else if (dynamicFields.getFieldId().equals("pob")) {
					fieldsDTO.setSavedData(retailers.getPob() != null ? retailers.getPob().toString() : null);
					List<String> options = new ArrayList<String>();
					Cities[] cities = Cities.values();
					for (Cities statuses : cities) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("fathersName")) {
					fieldsDTO.setSavedData(retailers.getFathersName());
				} else if (dynamicFields.getFieldId().equals("mothersName")) {
					fieldsDTO.setSavedData(retailers.getMothersName());
				} else if (dynamicFields.getFieldId().equals("maritalStatus")) {
					fieldsDTO.setSavedData(
							retailers.getMaritalStatus() != null ? retailers.getMaritalStatus().toString() : null);
					List<String> options = new ArrayList<String>();
					MaritalStatuses[] maritalStatuses = MaritalStatuses.values();
					for (MaritalStatuses statuses : maritalStatuses) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);

				} else if (dynamicFields.getFieldId().equals("spouseName")) {
					fieldsDTO.setSavedData(retailers.getSpouseName());
				} else if (dynamicFields.getFieldId().equals("numberOfDependents")) {
					fieldsDTO.setSavedData(String.valueOf(retailers.getNumberOfDependents()));
				} else if (dynamicFields.getFieldId().equals("alternateMobileNumber")) {
					fieldsDTO.setSavedData(retailers.getAlternateMobileNumber());
				}
				// userDto.setRetailerPhoto("");
				else if (dynamicFields.getFieldId().equals("birthRegistrationNumber")) {
					fieldsDTO.setSavedData(retailers.getBirthRegistrationNumber());
				} else if (dynamicFields.getFieldId().equals("drivingLicenseNumber")) {
					fieldsDTO.setSavedData(retailers.getDrivingLicenseNumber());
				} else if (dynamicFields.getFieldId().equals("email")) {
					fieldsDTO.setSavedData(retailers.getEmail());
				} else if (dynamicFields.getFieldId().equals("gender")) {
					fieldsDTO.setSavedData(retailers.getGender() != null ? retailers.getGender() : null);
					List<String> options = new ArrayList<String>();
					Gender[] genders = Gender.values();
					for (Gender statuses : genders) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("userId")) {
					fieldsDTO.setSavedData(retailers.getId());
				} else if (dynamicFields.getFieldId().equals("msisdn")) {
					fieldsDTO.setSavedData(retailers.getMsisdn());
				} else if (dynamicFields.getFieldId().equals("sisterConcernedOrAllied")) {
					fieldsDTO.setSavedData(retailers.getSisterConcernedOrAllied());
				} else if (dynamicFields.getFieldId().equals("taxIdentificationNumber")) {
					fieldsDTO.setSavedData(retailers.getTaxIdentificationNumber());
				} else if (dynamicFields.getFieldId().equals("residentialStatus")) {
					fieldsDTO.setSavedData(
							retailers.getResidentialStatus() != null ? retailers.getResidentialStatus().toString()
									: null);
					List<String> options = new ArrayList<String>();
					ResidentStatus[] residentStatuses = ResidentStatus.values();
					for (ResidentStatus statuses : residentStatuses) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);

				} else if (dynamicFields.getFieldId().equals("passportNumber")) {
					fieldsDTO.setSavedData(retailers.getPassportNumber());
				} else if (dynamicFields.getFieldId().equals("passportExpiryDate")) {
					fieldsDTO.setSavedData(retailers.getPassportExpiryDate());
				} else if (dynamicFields.getFieldId().equals("nationality")) {
					fieldsDTO.setSavedData(
							retailers.getNationality() != null ? retailers.getNationality().toString() : null);
					List<String> options = new ArrayList<String>();
					Nationality[] nationalities = Nationality.values();
					for (Nationality statuses : nationalities) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);

				} else if (dynamicFields.getFieldId().equals("nationalIdNumber")) {
					fieldsDTO.setSavedData(retailers.getNationalIdNumber());
				}
			} else {
				if (dynamicFields.getFieldId().equals("nationality")) {
					List<String> options = new ArrayList<String>();
					Nationality[] nationalities = Nationality.values();
					for (Nationality statuses : nationalities) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("residentialStatus")) {

					List<String> options = new ArrayList<String>();
					ResidentStatus[] residentStatuses = ResidentStatus.values();
					for (ResidentStatus statuses : residentStatuses) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("gender")) {
					List<String> options = new ArrayList<String>();
					Gender[] genders = Gender.values();
					for (Gender statuses : genders) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("maritalStatus")) {

					List<String> options = new ArrayList<String>();
					MaritalStatuses[] maritalStatuses = MaritalStatuses.values();
					for (MaritalStatuses statuses : maritalStatuses) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				}
			}
			FieldsDTO fieldDTO = getAppDynamicFieldDTO(dynamicFields, fieldRemarksList, textTemplatesList, fieldsDTO);
			addfunctionality(fieldDTO, dynamicFields);
			appDynamicFieldsDTOSet.add(fieldDTO);
			increamentFilledFields(fieldsDTO, filledVsUnfilled);
			return true;
		}
		return false;

	}

	private static void addfunctionality(FieldsDTO fieldsDTO, Fields dynamicFields) {
		if (dynamicFields.getOperations() != null) {
			Set<Operations> operations = dynamicFields.getOperations();
			Operations operation = operations.stream().findFirst().isPresent() ? operations.stream().findFirst().get()
					: null;
			if (operation != null) {
				Functionality functionality = new Functionality();
				try {
					functionality.setType(FunctionalityType.getFunctionalityType(operation.getOperationType()));
					functionality.setFieldToCompare(operation.getCompareWith());
					functionality.setId(operation.getId());
					functionality.setMinThreshold(operation.getMinThreshold());
					functionality.setMaxThreshold(operation.getMaxThreshold());
					fieldsDTO.setFunctionality(functionality);
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error("Exception raised while adding functionality={},error={}", operation, e.getMessage());
				}
			}
		}
	}

	private static FieldsDTO getAppDynamicFieldDTO(Fields dynamicFields, List<FieldRemarks> fieldRemarksList,
			List<TextTemplates> textTemplatesList, FieldsDTO appDynamicFieldsDTO) {
		appDynamicFieldsDTO.setRemark(getRemarks(dynamicFields, fieldRemarksList));
		appDynamicFieldsDTO.setCamera(dynamicFields.isCamera());
		appDynamicFieldsDTO.setDataType(dynamicFields.getDataType());
		appDynamicFieldsDTO.setFieldId(dynamicFields.getFieldId());
		appDynamicFieldsDTO.setId(dynamicFields.getId());
		appDynamicFieldsDTO.setMandatory(dynamicFields.isMandatory());
		appDynamicFieldsDTO.setOptions(dynamicFields.getOptions());
		// locale based text
		if (textTemplatesList != null && !textTemplatesList.isEmpty())
			appDynamicFieldsDTO.setPlaceHolderText(SpringUtil.bean(TextTemplateService.class)
					.getTemplate(textTemplatesList, dynamicFields.getPlaceHolderText()));
		else
			appDynamicFieldsDTO.setPlaceHolderText(dynamicFields.getPlaceHolderText());
		if (textTemplatesList != null && !textTemplatesList.isEmpty())
			appDynamicFieldsDTO.setHelp(SpringUtil.bean(TextTemplateService.class).getTemplate(textTemplatesList,
					dynamicFields.getHelp() != null ? dynamicFields.getHelp().trim() : dynamicFields.getHelp()));
		else
			appDynamicFieldsDTO.setHelp(
					dynamicFields.getHelp() != null ? dynamicFields.getHelp().trim() : dynamicFields.getHelp());
		if (textTemplatesList != null && !textTemplatesList.isEmpty())
			appDynamicFieldsDTO.setFieldName(SpringUtil.bean(TextTemplateService.class).getTemplate(textTemplatesList,
					dynamicFields.getFieldName()));
		else
			appDynamicFieldsDTO.setFieldName(dynamicFields.getFieldName());
		if (appDynamicFieldsDTO.getSavedData() == null)
			appDynamicFieldsDTO.setSavedData(dynamicFields.getDefaultValue());
		appDynamicFieldsDTO.setEditable(appDynamicFieldsDTO.getSavedData() != null ? dynamicFields.isEditable() : true);
		appDynamicFieldsDTO.setType(dynamicFields.getType());
		appDynamicFieldsDTO.setValidation(dynamicFields.getValidation());
		appDynamicFieldsDTO.setDisplayOrder(dynamicFields.getDisplayOrder());
		appDynamicFieldsDTO
				.setDefaultValue(dynamicFields.getDefaultValue() != null ? dynamicFields.getDefaultValue().trim()
						: dynamicFields.getDefaultValue());
		appDynamicFieldsDTO.setEnableFutureDates(dynamicFields.getEnableFutureDates());
		appDynamicFieldsDTO.setEnablePastDates(dynamicFields.getEnablePastDates());
		appDynamicFieldsDTO.setInternationalRepresentation(dynamicFields.getInternationalRepresentation());
		/*
		 * appDynamicFieldsDTO.setLocaleText(SpringUtil.bean(TextTemplateService.class).
		 * getTemplate(textTemplatesList, appDynamicFieldsDTO.getFieldName()));
		 */
		return appDynamicFieldsDTO;

	}

	private static String getRemarks(Fields dynamicFields, List<FieldRemarks> fieldRemarksList) {

		Optional<FieldRemarks> optional = fieldRemarksList != null
				? fieldRemarksList.stream().filter(f -> dynamicFields.getFieldId().equalsIgnoreCase(f.getFieldId()))
						.findFirst()
				: Optional.empty();

		return optional.isPresent() ? optional.get().getRemark() : null;
	}

	private static List<SubFieldsDTO> getSubFileds(Fields dynamicFields,
			Optional<AttachmentDetails> attachmentDetailsOptional, Map<String, Integer> filledVsUnfilled,
			List<FieldRemarks> fieldRemarksList, List<TextTemplates> textTemplatesList) {
		Set<SubFields> subFieldsSet = dynamicFields.getSubFields();
		if (subFieldsSet != null && !subFieldsSet.isEmpty()) {
			List<SubFieldsDTO> subFieldsDTOs = new ArrayList<SubFieldsDTO>();
			for (SubFields subFields : subFieldsSet) {
				SubFieldsDTO subFieldsDTO = new SubFieldsDTO();
				Fields subChildField = subFields.getChild();
				String side = subChildField.getFieldName();
				FieldsDTO fieldsDTO = new FieldsDTO();
				getAppDynamicFieldDTO(subChildField, fieldRemarksList, textTemplatesList, fieldsDTO);
				fieldsDTO.setRemark(getSideRemarks(dynamicFields, fieldRemarksList, side));
				if (attachmentDetailsOptional != null && attachmentDetailsOptional.isPresent()) {
					AttachmentDetails attachmentDetails = attachmentDetailsOptional.get();
					Set<Attachments> attachmentsSet = attachmentDetails.getAttachments();
					if (attachmentsSet != null && !attachmentsSet.isEmpty()) {
						setsavedAttachement(attachmentsSet, side, fieldsDTO, filledVsUnfilled);
					}
				}
				subFieldsDTO.setFields(fieldsDTO);
				subFieldsDTO.setId(subFields.getId());
				subFieldsDTOs.add(subFieldsDTO);
			}
			return subFieldsDTOs;
		}
		return null;
	}

	private static String getSideRemarks(Fields dynamicFields, List<FieldRemarks> fieldRemarksList, String side) {
		Optional<FieldRemarks> optional = fieldRemarksList != null
				? fieldRemarksList.stream().filter(f -> dynamicFields.getFieldId().equalsIgnoreCase(f.getFieldId())
						&& side != null && side.equalsIgnoreCase(f.getSide())).findFirst()
				: Optional.empty();
		return optional.isPresent() ? optional.get().getRemark() : null;
	}

	private static void setsavedAttachement(Set<Attachments> attachmentsSet, String side, FieldsDTO fieldsDTO,
			Map<String, Integer> filledVsUnfilled) {

		if (side != null && DocumentSide.FRONT.toString().equalsIgnoreCase(side)) {
			Optional<Attachments> frontDoc = attachmentsSet.stream()
					.filter(f -> f.getDocumentSide().equals(DocumentSide.FRONT)).findFirst();
			if (frontDoc.isPresent()) {
				if (frontDoc.get() != null) {
					fieldsDTO.setSavedData(
							SpringUtil.bean(AppConfigService.class).getProperty("DOCUMENT_STORAGE_BASE_URL", "")
									+ frontDoc.get().getDocumentUrl());
					Integer count = filledVsUnfilled.get("filledFields");
					filledVsUnfilled.put("filledFields", count + 1);
				}
			}
		} else {
			Optional<Attachments> backDoc = attachmentsSet.stream()
					.filter(f -> f.getDocumentSide().equals(DocumentSide.BACK)).findFirst();
			if (backDoc.isPresent()) {
				if (backDoc.get() != null) {
					fieldsDTO.setSavedData(
							SpringUtil.bean(AppConfigService.class).getProperty("DOCUMENT_STORAGE_BASE_URL", "")
									+ backDoc.get().getDocumentUrl());
					Integer count = filledVsUnfilled.get("filledFields");
					filledVsUnfilled.put("filledFields", count + 1);
				}
			}
		}

	}

	private static boolean checkFilterCriteria(Filters filter, String fieldId) {
		if (filter != null) {
			return filter.filter(fieldId);
		}
		return true;
	}

}
