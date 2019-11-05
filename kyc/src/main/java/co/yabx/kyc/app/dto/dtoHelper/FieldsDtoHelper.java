package co.yabx.kyc.app.dto.dtoHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.yabx.kyc.app.dto.FieldsDTO;
import co.yabx.kyc.app.dto.Functionality;
import co.yabx.kyc.app.dto.SubFieldsDTO;
import co.yabx.kyc.app.entities.Fields;
import co.yabx.kyc.app.entities.Sections;
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
import co.yabx.kyc.app.fullKyc.entity.IntroducerDetails;
import co.yabx.kyc.app.fullKyc.entity.LiabilitiesDetails;
import co.yabx.kyc.app.fullKyc.entity.LicenseDetails;
import co.yabx.kyc.app.fullKyc.entity.MonthlyTransactionProfiles;
import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.fullKyc.entity.WorkEducationDetails;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.util.SpringUtil;

public class FieldsDtoHelper implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(FieldsDtoHelper.class);

	public static List<FieldsDTO> getFields(Set<Fields> appDynamicFieldsSet, User retailers,
			Map<String, Integer> filledVsUnfilled, Sections appPagesSections, User nominee,
			Set<AddressDetails> userAddressDetailsSet, Set<AddressDetails> nomineeAddressDetailsSet,
			Set<AddressDetails> businessAddressDetailsSet, Set<BankAccountDetails> userBankAccountDetailsSet,
			Set<BankAccountDetails> nomineeBankAccountDetailsSet, Set<BankAccountDetails> businessBankAccountDetailsSet,
			SubGroups subGroups, Filters filter) {
		Integer totalFields = 0;
		Integer filledFields = 0;
		List<FieldsDTO> appDynamicFieldsDTOSet = new ArrayList<FieldsDTO>();
		for (Fields dynamicFields : appDynamicFieldsSet) {
			totalFields++;
			if (dynamicFields.getGroups().getGroupId() == 1
					&& (appPagesSections.getSectionId() == 1 || appPagesSections.getSectionId() == 3)) {
				// User personal Info
				prepareProfileInformation(dynamicFields, retailers, appDynamicFieldsDTOSet, filter);
			} else if (dynamicFields.getGroups().getGroupId() == 1 && appPagesSections.getSectionId() == 2) {
				// nominee
				prepareProfileInformation(dynamicFields, nominee, appDynamicFieldsDTOSet, filter);
			} else if (dynamicFields.getGroups().getGroupId() == 2
					&& (appPagesSections.getSectionId() == 1 || appPagesSections.getSectionId() == 3)) {
				// user address details
				prepareAddress(dynamicFields, userAddressDetailsSet, appDynamicFieldsDTOSet, subGroups, filter);
			} else if (dynamicFields.getGroups().getGroupId() == 2 && appPagesSections.getSectionId() == 2) {
				// nominee address details
				prepareAddress(dynamicFields, nomineeAddressDetailsSet, appDynamicFieldsDTOSet, subGroups, filter);
			} else if (dynamicFields.getGroups().getGroupId() == 2 && appPagesSections.getSectionId() == 5) {
				// Business address details
				prepareAddress(dynamicFields, businessAddressDetailsSet, appDynamicFieldsDTOSet, subGroups, filter);
			} else if (dynamicFields.getGroups().getGroupId() == 3
					&& (appPagesSections.getSectionId() == 1 || appPagesSections.getSectionId() == 3)) {
				// user account details
				prepareAccountInformations(dynamicFields, userBankAccountDetailsSet, appDynamicFieldsDTOSet, filter);
			} else if (dynamicFields.getGroups().getGroupId() == 3 && appPagesSections.getSectionId() == 2) {
				// nominee account details
				prepareAccountInformations(dynamicFields, nomineeBankAccountDetailsSet, appDynamicFieldsDTOSet, filter);
			} else if (dynamicFields.getGroups().getGroupId() == 3 && appPagesSections.getSectionId() == 5) {
				// business account details
				prepareAccountInformations(dynamicFields, businessBankAccountDetailsSet, appDynamicFieldsDTOSet,
						filter);
			} else if (dynamicFields.getGroups().getGroupId() == 4) {
				prepareLiabilitiesDetails(dynamicFields, retailers, appDynamicFieldsDTOSet, filter, subGroups);
			} else if (dynamicFields.getGroups().getGroupId() == 5) {
				prepareBusinessInformation(dynamicFields, retailers, appDynamicFieldsDTOSet, filter);
			} else if (dynamicFields.getGroups().getGroupId() == 6) {
				prepareLicenseDetails(dynamicFields, retailers, appDynamicFieldsDTOSet, filter);
			} else if (dynamicFields.getGroups().getGroupId() == 7) {
				prepareMonthlyTransactionProfile(dynamicFields, retailers, appDynamicFieldsDTOSet, filter);
			} else if (dynamicFields.getGroups().getGroupId() == 8
					&& (appPagesSections.getSectionId() == 1 || appPagesSections.getSectionId() == 3)) {
				// user or distributor work education
				prepareWorkEducationDetails(dynamicFields, retailers, appDynamicFieldsDTOSet, filter);
			} else if (dynamicFields.getGroups().getGroupId() == 8 && appPagesSections.getSectionId() == 2) {
				// nominee work education
				prepareWorkEducationDetails(dynamicFields, nominee, appDynamicFieldsDTOSet, filter);
			} else if (dynamicFields.getGroups().getGroupId() == 9) {
				// Introducer Detaiils
				prepareIntroducerDetails(dynamicFields, retailers, appDynamicFieldsDTOSet, filter);
			} else if (dynamicFields.getGroups().getGroupId() == 10) {
				// Attachment Detaiils
				prepareAttachmentDetails(dynamicFields, retailers, appDynamicFieldsDTOSet, filter);
			}

			if (dynamicFields.getSavedData() != null && !dynamicFields.getSavedData().isEmpty()) {
				filledFields++;
			} else if (checkSubFields(dynamicFields)) {
				filledFields++;
				totalFields++;
			}
			if (isHavingSubFields(dynamicFields)) {
				totalFields++;
			}
		}
		filledVsUnfilled.put("filledFields", filledFields);
		filledVsUnfilled.put("totalFields", totalFields);
		// appDynamicFieldsDTOSet.add(appDynamicFieldsDTOSet.stream().max(Comparator.comparing(FieldsDTO::getId)).get());
		appDynamicFieldsDTOSet.addAll(appDynamicFieldsDTOSet);
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
					return field.getSavedData() != null && !field.getSavedData().isEmpty();
				}
			}
		}
		return false;
	}

	private static void prepareAttachmentDetails(Fields dynamicFields, User retailers,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter) {

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
			FieldsDTO fieldsDTO = getAppDynamicFieldDTO(dynamicFields);
			fieldsDTO.setSubFields(getSubFileds(dynamicFields, null));
			appDynamicFieldsDTOSet.add(fieldsDTO);
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
						&& f.getDocumentType().equals(DocumentType.TIN_CERTIFICATE)).findFirst();
				setSavedAttachment(dynamicFields, attachmentDetails);

			} else if (dynamicFields.getFieldId().equals("tradeLicense")) {
				attachmentDetails = attachmentDetailsSet.stream().filter(f -> f != null && f.getDocumentType() != null
						&& f.getDocumentType().equals(DocumentType.TRADE_LICENSE)).findFirst();
				setSavedAttachment(dynamicFields, attachmentDetails);

			} else if (dynamicFields.getFieldId().equals("nomineePhoto")) {
				attachmentDetails = attachmentDetailsSet.stream().filter(f -> f != null && f.getDocumentType() != null
						&& f.getDocumentType().equals(DocumentType.NOMINEE_PHOTO)).findFirst();
				setSavedAttachment(dynamicFields, attachmentDetails);

			} else if (dynamicFields.getFieldId().equals("signature")) {
				attachmentDetails = attachmentDetailsSet.stream().filter(f -> f != null && f.getDocumentType() != null
						&& f.getDocumentType().equals(DocumentType.SIGNATURE)).findFirst();
				setSavedAttachment(dynamicFields, attachmentDetails);
			}
			FieldsDTO fieldsDTO = getAppDynamicFieldDTO(dynamicFields);
			fieldsDTO.setSubFields(getSubFileds(dynamicFields, attachmentDetails));
			appDynamicFieldsDTOSet.add(fieldsDTO);

		}

	}

	private static void setSavedAttachment(Fields dynamicFields, Optional<AttachmentDetails> attachmentDetails) {
		if (attachmentDetails != null && attachmentDetails.isPresent()) {
			Set<Attachments> attachments = attachmentDetails.get().getAttachments();
			if (attachments != null && !attachments.isEmpty()) {
				Optional<Attachments> attachmentOptional = attachments.stream().findFirst();
				if (attachmentOptional.isPresent())
					dynamicFields.setSavedData(
							SpringUtil.bean(AppConfigService.class).getProperty("DOCUMENT_STORAGE_BASE_URL",
									"https://yabx.co/") + attachmentOptional.get().getDocumentUrl());
			}
		}
	}

	private static void prepareIntroducerDetails(Fields dynamicFields, User retailers,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter) {

		if (retailers == null || retailers.getIntroducerDetails() == null
				|| retailers.getIntroducerDetails().isEmpty()) {
			appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
		} else {
			Set<IntroducerDetails> introducerDetailsSet = retailers.getIntroducerDetails();
			Optional<IntroducerDetails> introducerDetailsOptional = introducerDetailsSet.stream().findFirst();
			if (dynamicFields.getFieldId().equals("name")) {
				dynamicFields.setSavedData(
						introducerDetailsOptional.isPresent() ? introducerDetailsOptional.get().getName() : null);
			} else if (dynamicFields.getFieldId().equals("accountNumber")) {
				dynamicFields
						.setSavedData(introducerDetailsOptional.isPresent()
								? introducerDetailsOptional.get().getAccountNumber() != null
										? introducerDetailsOptional.get().getAccountNumber() + ""
										: null
								: null);
			} else if (dynamicFields.getFieldId().equals("isSignatureVerified")) {
				dynamicFields.setSavedData(introducerDetailsOptional.isPresent()
						? introducerDetailsOptional.get().isSignatureVerified() + ""
						: null);
			} else if (dynamicFields.getFieldId().equals("relationship")) {
				dynamicFields.setSavedData(
						introducerDetailsOptional.isPresent() ? introducerDetailsOptional.get().getRelationship() + ""
								: null);
			}
			appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));

		}

	}

	private static void prepareWorkEducationDetails(Fields dynamicFields, User retailers,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter) {
		if (checkFilterCriteria(filter, dynamicFields.getFieldId())) {
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
					dynamicFields.setSavedData(workEducationDetails != null && workEducationDetails.isPresent()
							? workEducationDetails.get().getOccupation()
							: null);
				} else if (dynamicFields.getFieldId().equals("designation")) {
					Optional<WorkEducationDetails> workEducationDetails = WorkEducationDetailsSet.stream()
							.filter(f -> f.getDesignation() != null).findFirst();
					dynamicFields.setSavedData(workEducationDetails != null && workEducationDetails.isPresent()
							? workEducationDetails.get().getDesignation()
							: null);
				} else if (dynamicFields.getFieldId().equals("employer")) {
					Optional<WorkEducationDetails> workEducationDetails = WorkEducationDetailsSet.stream()
							.filter(f -> f.getEmployer() != null).findFirst();
					dynamicFields.setSavedData(workEducationDetails != null && workEducationDetails.isPresent()
							? workEducationDetails.get().getEmployer()
							: null);
				} else if (dynamicFields.getFieldId().equals("educationalQualification")) {

					Optional<WorkEducationDetails> workEducationDetails = WorkEducationDetailsSet.stream()
							.filter(f -> f.getDesignation() != null).findFirst();
					try {
						dynamicFields
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
						dynamicFields.setSavedData(workEducationDetails != null && workEducationDetails.isPresent()
								? workEducationDetails.get().getExperience() + ""
								: null);
					} catch (Exception e) {
						LOGGER.error("Exceptiong while parsing experience={},error={}",
								workEducationDetails.get().getExperience(), e.getMessage());
					}

				}
			}
			appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
		}

	}

	private static void prepareMonthlyTransactionProfile(Fields dynamicFields, User retailers,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter) {
		if (retailers == null || retailers.getMonthlyTransactionProfiles() == null
				|| retailers.getMonthlyTransactionProfiles().isEmpty()) {

			appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
		} else {
			Set<MonthlyTransactionProfiles> monthlyTransactionProfiles = retailers.getMonthlyTransactionProfiles();
			Optional<MonthlyTransactionProfiles> monthlyTransactionProfileOptional = monthlyTransactionProfiles.stream()
					.findFirst();
			if (dynamicFields.getFieldId().equals("monthlyTurnOver")) {
				dynamicFields.setSavedData(monthlyTransactionProfileOptional.isPresent()
						? monthlyTransactionProfileOptional.get().getMonthlyTurnOver() != 0.0
								? monthlyTransactionProfileOptional.get().getMonthlyTurnOver() + ""
								: null
						: null);
			} else if (dynamicFields.getFieldId().equals("deposits")) {
				dynamicFields.setSavedData(monthlyTransactionProfileOptional.isPresent()
						? monthlyTransactionProfileOptional.get().getDeposits() != 0.0
								? monthlyTransactionProfileOptional.get().getDeposits() + ""
								: null
						: null);
			} else if (dynamicFields.getFieldId().equals("withdrawls")) {
				dynamicFields.setSavedData(monthlyTransactionProfileOptional.isPresent()
						? monthlyTransactionProfileOptional.get().getWithdrawls() != 0.0
								? monthlyTransactionProfileOptional.get().getWithdrawls() + ""
								: null
						: null);
			} else if (dynamicFields.getFieldId().equals("initialDeposit")) {
				dynamicFields.setSavedData(monthlyTransactionProfileOptional.isPresent()
						? monthlyTransactionProfileOptional.get().getInitialDeposit() != 0.0
								? monthlyTransactionProfileOptional.get().getInitialDeposit() + ""
								: null
						: null);

			}
			appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));

		}

	}

	private static void prepareLicenseDetails(Fields dynamicFields, User retailers,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter) {
		if (retailers == null || retailers.getBusinessDetails() == null || retailers.getBusinessDetails().isEmpty()) {
			if (dynamicFields.getFieldId().equals("licenseType")) {
				List<String> options = new ArrayList<String>();
				LicenseType[] accountTypes = LicenseType.values();
				for (LicenseType statuses : accountTypes) {
					options.add(statuses.toString());
				}
				dynamicFields.setOptions(options);
			}
			appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
		} else {
			Set<BusinessDetails> businessDetailsSet = retailers.getBusinessDetails();
			Optional<BusinessDetails> optional = businessDetailsSet.stream().findFirst();
			LicenseDetails licenseDetails = optional.isPresent() ? optional.get().getLicenseDetails() : null;
			if (dynamicFields.getFieldId().equals("licenseNumber")) {
				dynamicFields.setSavedData(licenseDetails != null ? licenseDetails.getLicenseNumber() : null);
			} else if (dynamicFields.getFieldId().equals("licenseExpiryDate")) {
				dynamicFields.setSavedData(licenseDetails != null ? licenseDetails.getLicenseExpiryDate() : null);
			} else if (dynamicFields.getFieldId().equals("licenseIssuingAuthority")) {
				dynamicFields.setSavedData(licenseDetails != null ? licenseDetails.getLicenseIssuingAuthority() : null);
			} else if (dynamicFields.getFieldId().equals("licenseType")) {
				dynamicFields.setSavedData(licenseDetails != null && licenseDetails.getLicenseType() != null
						? licenseDetails.getLicenseType().toString()
						: null);
				List<String> options = new ArrayList<String>();
				LicenseType[] accountTypes = LicenseType.values();
				for (LicenseType statuses : accountTypes) {
					options.add(statuses.toString());
				}
				dynamicFields.setOptions(options);
			}
			appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
		}

	}

	private static void prepareBusinessInformation(Fields dynamicFields, User retailers,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter) {
		if (checkFilterCriteria(filter, dynamicFields.getFieldId())) {
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
					FacilityType[] accountTypes = FacilityType.values();
					for (FacilityType statuses : accountTypes) {
						options.add(statuses.getName());
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
				appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
			} else {
				Set<BusinessDetails> BusinessDetailsSet = retailers.getBusinessDetails();
				Optional<BusinessDetails> businessDetailsOptional = BusinessDetailsSet.stream().findFirst();
				if (dynamicFields.getFieldId().equals("businessPhone")) {
					dynamicFields.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getBusinessPhone()
									: null);
				} else if (dynamicFields.getFieldId().equals("businessName")) {
					dynamicFields.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getBusinessName()
									: null);
				} else if (dynamicFields.getFieldId().equals("directorOrPartnerName")) {
					dynamicFields.setSavedData(businessDetailsOptional.isPresent()
							? businessDetailsOptional.get().getDirectorOrPartnerName()
							: null);
				} else if (dynamicFields.getFieldId().equals("facilityDetails")) {
					dynamicFields
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
					dynamicFields
							.setSavedData(businessDetailsOptional.isPresent()
									? businessDetailsOptional.get().getFacilityType() != null
											? businessDetailsOptional.get().getFacilityType().toString()
											: null
									: null);
					List<String> options = new ArrayList<String>();
					FacilityType[] accountTypes = FacilityType.values();
					for (FacilityType statuses : accountTypes) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("fixedAssetPurchase")) {
					dynamicFields.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getFixedAssetPurchase()
									: null);
				} else if (dynamicFields.getFieldId().equals("fixedAssetName")) {
					dynamicFields.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getFixedAssetName()
									: null);
				} else if (dynamicFields.getFieldId().equals("price")) {
					dynamicFields.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getPrice() + "" : null);
				} else if (dynamicFields.getFieldId().equals("origin")) {
					dynamicFields.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getOrigin() : null);
				} else if (dynamicFields.getFieldId().equals("proposedCollateral")) {
					dynamicFields.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getProposedCollateral()
									: null);
				} else if (dynamicFields.getFieldId().equals("businessType")) {
					dynamicFields
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
					dynamicFields
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
					dynamicFields.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getDetailOfBusness()
									: null);
				} else if (dynamicFields.getFieldId().equals("initialCapital")) {
					dynamicFields.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getInitialCapital() + ""
									: null);
				} else if (dynamicFields.getFieldId().equals("fundSource")) {
					dynamicFields.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getFundSource() : null);
				} else if (dynamicFields.getFieldId().equals("vatRegistrationNumber")) {
					dynamicFields.setSavedData(businessDetailsOptional.isPresent()
							? businessDetailsOptional.get().getVatRegistrationNumber()
							: null);
				} else if (dynamicFields.getFieldId().equals("businessStartDate")) {
					dynamicFields.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getBusinessStartDate()
									: null);
				} else if (dynamicFields.getFieldId().equals("businessTin")) {
					dynamicFields.setSavedData(
							businessDetailsOptional.isPresent() ? businessDetailsOptional.get().getBusinessTin()
									: null);
				} else if (dynamicFields.getFieldId().equals("annualSales")) {
					dynamicFields
							.setSavedData(businessDetailsOptional.isPresent()
									? businessDetailsOptional.get().getAnnualSales() != 0.0
											? businessDetailsOptional.get().getAnnualSales() + ""
											: null
									: null);
				} else if (dynamicFields.getFieldId().equals("annualGrossProfit")) {
					dynamicFields
							.setSavedData(businessDetailsOptional.isPresent()
									? businessDetailsOptional.get().getAnnualGrossProfit() != 0.0
											? businessDetailsOptional.get().getAnnualGrossProfit() + ""
											: null
									: null);
				} else if (dynamicFields.getFieldId().equals("annualExpenses")) {
					dynamicFields
							.setSavedData(businessDetailsOptional.isPresent()
									? businessDetailsOptional.get().getAnnualExpenses() != 0.0
											? businessDetailsOptional.get().getAnnualExpenses() + ""
											: null
									: null);
				} else if (dynamicFields.getFieldId().equals("valueOfFixedAssets")) {
					dynamicFields
							.setSavedData(businessDetailsOptional.isPresent()
									? businessDetailsOptional.get().getValueOfFixedAssets() != 0.0
											? businessDetailsOptional.get().getValueOfFixedAssets() + ""
											: null
									: null);
				} else if (dynamicFields.getFieldId().equals("numberOfEmployees")) {
					dynamicFields
							.setSavedData(businessDetailsOptional.isPresent()
									? businessDetailsOptional.get().getNumberOfEmployees() != 0
											? businessDetailsOptional.get().getNumberOfEmployees() + ""
											: null
									: null);
				} else if (dynamicFields.getFieldId().equals("stockValue")) {
					dynamicFields
							.setSavedData(businessDetailsOptional.isPresent()
									? businessDetailsOptional.get().getStockValue() != 0.0
											? businessDetailsOptional.get().getStockValue() + ""
											: null
									: null);
				} else if (dynamicFields.getFieldId().equals("deposits")) {
					dynamicFields
							.setSavedData(businessDetailsOptional.isPresent()
									? businessDetailsOptional.get().getDeposits() != 0.0
											? businessDetailsOptional.get().getDeposits() + ""
											: null
									: null);
				} else if (dynamicFields.getFieldId().equals("withdrawls")) {
					dynamicFields
							.setSavedData(businessDetailsOptional.isPresent()
									? businessDetailsOptional.get().getWithdrawls() != 0.0
											? businessDetailsOptional.get().getWithdrawls() + ""
											: null
									: null);
				} else if (dynamicFields.getFieldId().equals("initialDeposit")) {
					dynamicFields
							.setSavedData(businessDetailsOptional.isPresent()
									? businessDetailsOptional.get().getInitialDeposit() != 0.0
											? businessDetailsOptional.get().getInitialDeposit() + ""
											: null
									: null);
				}
				appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
			}
		}

	}

	private static void prepareLiabilitiesDetails(Fields dynamicFields, User retailers,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter, SubGroups subGroups) {
		if (retailers == null || retailers.getLiabilitiesDetails() == null
				|| retailers.getLiabilitiesDetails().isEmpty()) {
			appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
		} else {
			Set<LiabilitiesDetails> LiabilitiesDetailsSet = retailers.getLiabilitiesDetails();
			LiabilitiesDetails liabilitiesDetails = getLiabilitiesDetails(subGroups, LiabilitiesDetailsSet);
			if (dynamicFields.getFieldId().equals("loanAmount")) {
				dynamicFields.setSavedData(liabilitiesDetails != null ? liabilitiesDetails.getLoanAmount() + "" : null);
			} else if (dynamicFields.getFieldId().equals("bankOrNbfiName")) {
				dynamicFields.setSavedData(liabilitiesDetails != null ? liabilitiesDetails.getBankOrNbfiName() : null);
			} else if (dynamicFields.getFieldId().equals("liabilityFromOtherOrganization")) {
				dynamicFields.setSavedData(
						liabilitiesDetails != null ? liabilitiesDetails.getLiabilityFromOtherOrganization() : null);
			} else if (dynamicFields.getFieldId().equals("loanAmountFromOtherOrganization")) {
				dynamicFields.setSavedData(liabilitiesDetails != null ? liabilitiesDetails.getLoanAmount() + "" : null);
			}
			appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
		}

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

	private static void prepareAccountInformations(Fields dynamicFields, Set<BankAccountDetails> bankAccountDetailsSet,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter) {
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
			appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
		} else {
			Optional<BankAccountDetails> bankAccountDetailsOptional = bankAccountDetailsSet.stream().findFirst();

			if (dynamicFields.getFieldId().equals("accountTitle")) {
				dynamicFields.setSavedData(
						bankAccountDetailsOptional.isPresent() ? bankAccountDetailsOptional.get().getAccountTitle()
								: null);
			} else if (dynamicFields.getFieldId().equals("typeOfConcern")) {
				dynamicFields
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
				dynamicFields.setSavedData(
						bankAccountDetailsOptional.isPresent() ? bankAccountDetailsOptional.get().getBankName() : null);
			} else if (dynamicFields.getFieldId().equals("accountNumber")) {
				dynamicFields
						.setSavedData(bankAccountDetailsOptional.isPresent()
								? bankAccountDetailsOptional.get().getAccountNumber() != null
										? bankAccountDetailsOptional.get().getAccountNumber() + ""
										: null
								: null);
			} else if (dynamicFields.getFieldId().equals("branch")) {
				dynamicFields.setSavedData(
						bankAccountDetailsOptional.isPresent() ? bankAccountDetailsOptional.get().getBranch() : null);
			} else if (dynamicFields.getFieldId().equals("accountPurpose")) {
				dynamicFields
						.setSavedData(bankAccountDetailsOptional.isPresent()
								? bankAccountDetailsOptional.get().getAccountPurpose() != null
										? bankAccountDetailsOptional.get().getAccountPurpose().toString()
										: null
								: null);
			} else if (dynamicFields.getFieldId().equals("modeOfOperation")) {
				dynamicFields
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
				dynamicFields
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
				dynamicFields
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
			appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));

		}

	}

	private static void prepareAddress(Fields dynamicFields, Set<AddressDetails> addressDetailsSet,
			List<FieldsDTO> appDynamicFieldsDTOSet, SubGroups subGroups, Filters filter) {
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
			appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
		} else {
			AddressDetails addressDetails = getAddressDetails(subGroups, addressDetailsSet);
			if (addressDetailsSet != null) {
				if (dynamicFields.getFieldId().equals("address")) {
					dynamicFields.setSavedData(addressDetails.getAddress());
				} else if (dynamicFields.getFieldId().equals("upazilaThana")) {
					dynamicFields.setSavedData(addressDetails.getUpazilaThana());
				} else if (dynamicFields.getFieldId().equals("cityDsitrict")) {
					dynamicFields.setSavedData(
							addressDetails.getCityDsitrict() != null ? addressDetails.getCityDsitrict().toString()
									: null);
					List<String> options = new ArrayList<String>();
					Cities[] accountTypes = Cities.values();
					for (Cities statuses : accountTypes) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("division")) {
					dynamicFields.setSavedData(
							addressDetails.getDivision() != null ? addressDetails.getDivision().toString() : null);
					List<String> options = new ArrayList<String>();
					Divisions[] accountTypes = Divisions.values();
					for (Divisions statuses : accountTypes) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);

				} else if (dynamicFields.getFieldId().equals("zipCode")) {
					dynamicFields.setSavedData(addressDetails != null ? addressDetails.getZipCode() + "" : null);
				} else if (dynamicFields.getFieldId().equals("landmark")) {
					dynamicFields.setSavedData(addressDetails.getLandmark());
				} else if (dynamicFields.getFieldId().equals("territory")) {
					dynamicFields.setSavedData(addressDetails.getTerritory());
				} else if (dynamicFields.getFieldId().equals("country")) {
					dynamicFields.setSavedData(
							addressDetails.getCountry() != null ? addressDetails.getCountry().toString() : null);
					List<String> options = new ArrayList<String>();
					Countries[] accountTypes = Countries.values();
					for (Countries statuses : accountTypes) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("mobileNumber")) {
					dynamicFields.setSavedData(addressDetails.getMobileNumber());
				} else if (dynamicFields.getFieldId().equals("phoneNumber")) {
					dynamicFields.setSavedData(addressDetails.getPhoneNumber());
				} else if (dynamicFields.getFieldId().equals("email")) {
					dynamicFields.setSavedData(addressDetails.getEmail());
				}
				appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
			}
		}

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

	private static void prepareProfileInformation(Fields dynamicFields, User retailers,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter) {
		if (checkFilterCriteria(filter, dynamicFields.getFieldId())) {
			if (retailers != null) {
				if (dynamicFields.getFieldId().equals("firstName")) {
					dynamicFields.setSavedData(retailers.getFirstName());
				} else if (dynamicFields.getFieldId().equals("lastName")) {
					dynamicFields.setSavedData(retailers.getLastName());
				} else if (dynamicFields.getFieldId().equals("middleName")) {
					dynamicFields.setSavedData(retailers.getMiddleName());
				} else if (dynamicFields.getFieldId().equals("dob")) {
					dynamicFields.setSavedData(retailers.getDob());
				} else if (dynamicFields.getFieldId().equals("pob")) {
					dynamicFields.setSavedData(retailers.getPob() != null ? retailers.getPob().toString() : null);
					List<String> options = new ArrayList<String>();
					Cities[] cities = Cities.values();
					for (Cities statuses : cities) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("fathersName")) {
					dynamicFields.setSavedData(retailers.getFathersName());
				} else if (dynamicFields.getFieldId().equals("mothersName")) {
					dynamicFields.setSavedData(retailers.getMothersName());
				} else if (dynamicFields.getFieldId().equals("maritalStatus")) {
					dynamicFields.setSavedData(
							retailers.getMaritalStatus() != null ? retailers.getMaritalStatus().toString() : null);
					List<String> options = new ArrayList<String>();
					MaritalStatuses[] maritalStatuses = MaritalStatuses.values();
					for (MaritalStatuses statuses : maritalStatuses) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);

				} else if (dynamicFields.getFieldId().equals("spouseName")) {
					dynamicFields.setSavedData(retailers.getSpouseName());
				} else if (dynamicFields.getFieldId().equals("numberOfDependents")) {
					dynamicFields.setSavedData(String.valueOf(retailers.getNumberOfDependents()));
				} else if (dynamicFields.getFieldId().equals("alternateMobileNumber")) {
					dynamicFields.setSavedData(retailers.getAlternateMobileNumber());
				}
				// userDto.setRetailerPhoto("");
				else if (dynamicFields.getFieldId().equals("birthRegistrationNumber")) {
					dynamicFields.setSavedData(retailers.getBirthRegistrationNumber());
				} else if (dynamicFields.getFieldId().equals("drivingLicenseNumber")) {
					dynamicFields.setSavedData(retailers.getDrivingLicenseNumber());
				} else if (dynamicFields.getFieldId().equals("email")) {
					dynamicFields.setSavedData(retailers.getEmail());
				} else if (dynamicFields.getFieldId().equals("gender")) {
					dynamicFields.setSavedData(retailers.getGender() != null ? retailers.getGender().toString() : null);
					List<String> options = new ArrayList<String>();
					Gender[] genders = Gender.values();
					for (Gender statuses : genders) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("id")) {
					dynamicFields.setSavedData(retailers.getId() + "");
				} else if (dynamicFields.getFieldId().equals("msisdn")) {
					dynamicFields.setSavedData(retailers.getMsisdn());
				} else if (dynamicFields.getFieldId().equals("sisterConcernedOrAllied")) {
					dynamicFields.setSavedData(retailers.getSisterConcernedOrAllied());
				} else if (dynamicFields.getFieldId().equals("taxIdentificationNumber")) {
					dynamicFields.setSavedData(retailers.getTaxIdentificationNumber());
				} else if (dynamicFields.getFieldId().equals("residentialStatus")) {
					dynamicFields.setSavedData(
							retailers.getResidentialStatus() != null ? retailers.getResidentialStatus().toString()
									: null);
					List<String> options = new ArrayList<String>();
					ResidentStatus[] residentStatuses = ResidentStatus.values();
					for (ResidentStatus statuses : residentStatuses) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);

				} else if (dynamicFields.getFieldId().equals("passportNumber")) {
					dynamicFields.setSavedData(retailers.getPassportNumber());
				} else if (dynamicFields.getFieldId().equals("passportExpiryDate")) {
					dynamicFields.setSavedData(retailers.getPassportExpiryDate());
				} else if (dynamicFields.getFieldId().equals("nationality")) {
					dynamicFields.setSavedData(
							retailers.getNationality() != null ? retailers.getNationality().toString() : null);
					List<String> options = new ArrayList<String>();
					Nationality[] nationalities = Nationality.values();
					for (Nationality statuses : nationalities) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);

				} else if (dynamicFields.getFieldId().equals("nationalIdNumber")) {
					dynamicFields.setSavedData(retailers.getNationalIdNumber());
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
			FieldsDTO fieldsDTO = getAppDynamicFieldDTO(dynamicFields);
			addfunctionality(fieldsDTO, dynamicFields);
			appDynamicFieldsDTOSet.add(fieldsDTO);
		}

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

	private static FieldsDTO getAppDynamicFieldDTO(Fields dynamicFields) {
		FieldsDTO appDynamicFieldsDTO = new FieldsDTO();
		appDynamicFieldsDTO.setCamera(dynamicFields.isCamera());
		appDynamicFieldsDTO.setDataType(dynamicFields.getDataType());
		appDynamicFieldsDTO.setFieldId(dynamicFields.getFieldId());
		appDynamicFieldsDTO.setFieldName(dynamicFields.getFieldName());
		appDynamicFieldsDTO.setId(dynamicFields.getId());
		appDynamicFieldsDTO.setMandatory(dynamicFields.isMandatory());
		appDynamicFieldsDTO.setOptions(dynamicFields.getOptions());
		appDynamicFieldsDTO.setPlaceHolderText(dynamicFields.getPlaceHolderText());
		appDynamicFieldsDTO.setSavedData(dynamicFields.getSavedData());
		appDynamicFieldsDTO.setEditable(dynamicFields.getSavedData() != null && !dynamicFields.getSavedData().isEmpty()
				? dynamicFields.isEditable()
				: true);
		appDynamicFieldsDTO.setType(dynamicFields.getType());
		appDynamicFieldsDTO.setValidation(dynamicFields.getValidation());
		appDynamicFieldsDTO.setDisplayOrder(dynamicFields.getDisplayOrder());
		appDynamicFieldsDTO.setDefaultValue(dynamicFields.getDefaultValue());
		return appDynamicFieldsDTO;
	}

	private static List<SubFieldsDTO> getSubFileds(Fields dynamicFields,
			Optional<AttachmentDetails> attachmentDetailsOptional) {
		Set<SubFields> subFieldsSet = dynamicFields.getSubFields();
		if (subFieldsSet != null && !subFieldsSet.isEmpty()) {
			List<SubFieldsDTO> subFieldsDTOs = new ArrayList<SubFieldsDTO>();
			for (SubFields subFields : subFieldsSet) {
				SubFieldsDTO subFieldsDTO = new SubFieldsDTO();
				Fields subChildField = subFields.getChild();
				String side = subChildField.getFieldName();
				FieldsDTO fieldsDTO = getAppDynamicFieldDTO(subChildField);
				if (attachmentDetailsOptional != null && attachmentDetailsOptional.isPresent()) {
					AttachmentDetails attachmentDetails = attachmentDetailsOptional.get();
					Set<Attachments> attachmentsSet = attachmentDetails.getAttachments();
					if (attachmentsSet != null && !attachmentsSet.isEmpty()) {
						setsavedAttachement(attachmentsSet, side, fieldsDTO);
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

	private static void setsavedAttachement(Set<Attachments> attachmentsSet, String side, FieldsDTO fieldsDTO) {

		if (side != null && DocumentSide.FRONT.toString().equalsIgnoreCase(side)) {
			Optional<Attachments> frontDoc = attachmentsSet.stream()
					.filter(f -> f.getDocumentSide().equals(DocumentSide.FRONT)).findFirst();
			if (frontDoc.isPresent()) {
				if (frontDoc.get() != null) {
					fieldsDTO.setSavedData(SpringUtil.bean(AppConfigService.class).getProperty(
							"DOCUMENT_STORAGE_BASE_URL", "https://yabx.co/") + frontDoc.get().getDocumentUrl());
				}
			}
		} else {
			Optional<Attachments> backDoc = attachmentsSet.stream()
					.filter(f -> f.getDocumentSide().equals(DocumentSide.BACK)).findFirst();
			if (backDoc.isPresent()) {
				if (backDoc.get() != null) {
					fieldsDTO.setSavedData(SpringUtil.bean(AppConfigService.class).getProperty(
							"DOCUMENT_STORAGE_BASE_URL", "https://yabx.co/") + backDoc.get().getDocumentUrl());
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
