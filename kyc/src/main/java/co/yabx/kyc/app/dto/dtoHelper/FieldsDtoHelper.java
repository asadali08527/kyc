package co.yabx.kyc.app.dto.dtoHelper;

import java.io.Serializable;
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
import co.yabx.kyc.app.dto.SubFieldsDTO;
import co.yabx.kyc.app.entities.Fields;
import co.yabx.kyc.app.entities.Sections;
import co.yabx.kyc.app.entities.filter.Filters;
import co.yabx.kyc.app.entities.filter.SubFields;
import co.yabx.kyc.app.entities.filter.SubGroups;
import co.yabx.kyc.app.enums.AddressProof;
import co.yabx.kyc.app.enums.AddressType;
import co.yabx.kyc.app.enums.AttachmentType;
import co.yabx.kyc.app.enums.BankAccountType;
import co.yabx.kyc.app.enums.BusinessSector;
import co.yabx.kyc.app.enums.BusinessType;
import co.yabx.kyc.app.enums.Currency;
import co.yabx.kyc.app.enums.DocumentSide;
import co.yabx.kyc.app.enums.DocumentType;
import co.yabx.kyc.app.enums.EducationalQualification;
import co.yabx.kyc.app.enums.FacilityDetails;
import co.yabx.kyc.app.enums.FacilityType;
import co.yabx.kyc.app.enums.Gender;
import co.yabx.kyc.app.enums.IdentityProof;
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
				prepareLiabilitiesDetails(dynamicFields, retailers, appDynamicFieldsDTOSet, filter);
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

			if (dynamicFields.getSavedData() != null && !dynamicFields.getSavedData().isEmpty())
				filledFields++;
		}
		filledVsUnfilled.put("filledFields", filledFields);
		filledVsUnfilled.put("totalFields", totalFields);
		appDynamicFieldsDTOSet.add(appDynamicFieldsDTOSet.stream().max(Comparator.comparing(FieldsDTO::getId)).get());
		return appDynamicFieldsDTOSet;
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
			for (IntroducerDetails introducerDetails : introducerDetailsSet) {
				if (dynamicFields.getFieldId().equals("name")) {
					dynamicFields.setSavedData(introducerDetails.getName());
				} else if (dynamicFields.getFieldId().equals("accountNumber")) {
					dynamicFields.setSavedData(introducerDetails.getAccountNumber() + "");
				} else if (dynamicFields.getFieldId().equals("isSignatureVerified")) {
					dynamicFields.setSavedData(introducerDetails.isSignatureVerified() + "");
				} else if (dynamicFields.getFieldId().equals("signature")) {
					AttachmentDetails attachmentDetails = introducerDetails.getSignature();
					Optional<Attachments> optionalAttachments = attachmentDetails != null
							? attachmentDetails.getAttachments() != null
									&& !attachmentDetails.getAttachments().isEmpty()
											? attachmentDetails.getAttachments().stream().findFirst()
											: Optional.empty()
							: Optional.empty();
					dynamicFields.setSavedData(optionalAttachments != null && optionalAttachments.isPresent()
							? optionalAttachments.get().getDocumentUrl()
							: null);
				} else if (dynamicFields.getFieldId().equals("relationship")) {
					try {
						dynamicFields.setSavedData(introducerDetails.getRelationship() != null
								? String.valueOf(introducerDetails.getRelationship())
								: null);
					} catch (Exception e) {
						LOGGER.error("Exceptiong while parsing Relationship={},error={}",
								introducerDetails.getRelationship(), e.getMessage());
					}
				}
				appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
			}

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
				appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
			} else {
				Set<WorkEducationDetails> WorkEducationDetailsSet = retailers.getWorkEducationDetails();
				for (WorkEducationDetails workEducationDetails : WorkEducationDetailsSet) {
					if (dynamicFields.getFieldId().equals("occupation")) {
						dynamicFields.setSavedData(workEducationDetails.getOccupation());
					} else if (dynamicFields.getFieldId().equals("designation")) {
						dynamicFields.setSavedData(workEducationDetails.getDesignation());
					} else if (dynamicFields.getFieldId().equals("employer")) {
						dynamicFields.setSavedData(workEducationDetails.getEmployer());
					} else if (dynamicFields.getFieldId().equals("educationalQualification")) {
						dynamicFields.setSavedData(workEducationDetails.getEducationalQualification() != null
								? workEducationDetails.getEducationalQualification().toString()
								: null);
						List<String> options = new ArrayList<String>();
						EducationalQualification[] accountTypes = EducationalQualification.values();
						for (EducationalQualification statuses : accountTypes) {
							options.add(statuses.getName());
						}
						dynamicFields.setOptions(options);
					} else if (dynamicFields.getFieldId().equals("experience")) {
						try {
							dynamicFields.setSavedData(workEducationDetails.getExperience() != null
									? String.valueOf(workEducationDetails.getExperience())
									: null);
						} catch (Exception e) {
							LOGGER.error("Exceptiong while parsing experience={},error={}",
									workEducationDetails.getExperience(), e.getMessage());
						}
					}
					appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
				}
			}
		}

	}

	private static void prepareMonthlyTransactionProfile(Fields dynamicFields, User retailers,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter) {
		if (retailers == null || retailers.getMonthlyTransactionProfiles() == null
				|| retailers.getMonthlyTransactionProfiles().isEmpty()) {

			appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
		} else {
			Set<MonthlyTransactionProfiles> monthlyTransactionProfiles = retailers.getMonthlyTransactionProfiles();
			for (MonthlyTransactionProfiles monthlyTransactionProfile : monthlyTransactionProfiles) {
				if (dynamicFields.getFieldId().equals("monthlyTurnOver")) {
					dynamicFields.setSavedData(monthlyTransactionProfile.getMonthlyTurnOver() + "");
				} else if (dynamicFields.getFieldId().equals("deposits")) {
					dynamicFields.setSavedData(monthlyTransactionProfile.getDeposits() + "");
				} else if (dynamicFields.getFieldId().equals("withdrawls")) {
					dynamicFields.setSavedData(monthlyTransactionProfile.getWithdrawls() + "");
				} else if (dynamicFields.getFieldId().equals("initialDeposit")) {
					dynamicFields.setSavedData(monthlyTransactionProfile.getInitialDeposit() + "");

				}
				appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
			}

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
			for (BusinessDetails businessDetails : businessDetailsSet) {
				LicenseDetails licenseDetails = businessDetails.getLicenseDetails();
				if (dynamicFields.getFieldId().equals("licenseNumber")) {
					dynamicFields.setSavedData(licenseDetails.getLicenseNumber());
				} else if (dynamicFields.getFieldId().equals("licenseExpiryDate")) {
					dynamicFields.setSavedData(licenseDetails.getLicenseExpiryDate());
				} else if (dynamicFields.getFieldId().equals("licenseIssuingAuthority")) {
					dynamicFields.setSavedData(licenseDetails.getLicenseIssuingAuthority());
				} else if (dynamicFields.getFieldId().equals("licenseType")) {
					dynamicFields.setSavedData(
							licenseDetails.getLicenseType() != null ? licenseDetails.getLicenseType().toString()
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

	}

	private static void prepareBusinessInformation(Fields dynamicFields, User retailers,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter) {

		if (retailers == null || retailers.getBusinessDetails() == null || retailers.getBusinessDetails().isEmpty()) {
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
			for (BusinessDetails businessDetails : BusinessDetailsSet) {
				if (dynamicFields.getFieldId().equals("businessPhone")) {
					dynamicFields.setSavedData(businessDetails.getBusinessPhone());
				} else if (dynamicFields.getFieldId().equals("businessName")) {
					dynamicFields.setSavedData(businessDetails.getBusinessName());
				} else if (dynamicFields.getFieldId().equals("directorOrPartnerName")) {
					dynamicFields.setSavedData(businessDetails.getDirectorOrPartnerName());
				} else if (dynamicFields.getFieldId().equals("facilityDetails")) {
					dynamicFields.setSavedData(businessDetails.getFacilityDetails() != null
							? businessDetails.getFacilityDetails().toString()
							: null);
					List<String> options = new ArrayList<String>();
					FacilityDetails[] accountTypes = FacilityDetails.values();
					for (FacilityDetails statuses : accountTypes) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("facilityType")) {
					dynamicFields.setSavedData(
							businessDetails.getFacilityType() != null ? businessDetails.getFacilityType().toString()
									: null);
					List<String> options = new ArrayList<String>();
					FacilityType[] accountTypes = FacilityType.values();
					for (FacilityType statuses : accountTypes) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("fixedAssetPurchase")) {
					dynamicFields.setSavedData(businessDetails.getFixedAssetPurchase());
				} else if (dynamicFields.getFieldId().equals("fixedAssetName")) {
					dynamicFields.setSavedData(businessDetails.getFixedAssetName());
				} else if (dynamicFields.getFieldId().equals("price")) {
					dynamicFields.setSavedData(businessDetails.getPrice() + "");
				} else if (dynamicFields.getFieldId().equals("origin")) {
					dynamicFields.setSavedData(businessDetails.getOrigin());
				} else if (dynamicFields.getFieldId().equals("proposedCollateral")) {
					dynamicFields.setSavedData(businessDetails.getProposedCollateral());
				} else if (dynamicFields.getFieldId().equals("businessType")) {
					dynamicFields.setSavedData(
							businessDetails.getBusinessType() != null ? businessDetails.getBusinessType().toString()
									: null);
					List<String> options = new ArrayList<String>();
					BusinessType[] accountTypes = BusinessType.values();
					for (BusinessType statuses : accountTypes) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("sector")) {
					dynamicFields.setSavedData(
							businessDetails.getSector() != null ? businessDetails.getSector().toString() : null);
					List<String> options = new ArrayList<String>();
					BusinessSector[] accountTypes = BusinessSector.values();
					for (BusinessSector statuses : accountTypes) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("detailOfBusness")) {
					dynamicFields.setSavedData(businessDetails.getDetailOfBusness());
				} else if (dynamicFields.getFieldId().equals("initialCapital")) {
					dynamicFields.setSavedData(businessDetails.getInitialCapital() + "");
				} else if (dynamicFields.getFieldId().equals("fundSource")) {
					dynamicFields.setSavedData(businessDetails.getFundSource());
				} else if (dynamicFields.getFieldId().equals("vatRegistrationNumber")) {
					dynamicFields.setSavedData(businessDetails.getVatRegistrationNumber());
				} else if (dynamicFields.getFieldId().equals("businessStartDate")) {
					dynamicFields.setSavedData(businessDetails.getBusinessStartDate());
				} else if (dynamicFields.getFieldId().equals("businessTin")) {
					dynamicFields.setSavedData(businessDetails.getBusinessTin());
				} else if (dynamicFields.getFieldId().equals("annualSales")) {
					dynamicFields.setSavedData(businessDetails.getAnnualSales() + "");
				} else if (dynamicFields.getFieldId().equals("annualGrossProfit")) {
					dynamicFields.setSavedData(businessDetails.getAnnualGrossProfit() + "");
				} else if (dynamicFields.getFieldId().equals("annualExpenses")) {
					dynamicFields.setSavedData(businessDetails.getAnnualExpenses() + "");
				} else if (dynamicFields.getFieldId().equals("valueOfFixedAssets")) {
					dynamicFields.setSavedData(businessDetails.getValueOfFixedAssets() + "");
				} else if (dynamicFields.getFieldId().equals("numberOfEmployees")) {
					dynamicFields.setSavedData(businessDetails.getNumberOfEmployees() + "");
				} else if (dynamicFields.getFieldId().equals("stockValue")) {
					dynamicFields.setSavedData(businessDetails.getStockValue() + "");
				} else if (dynamicFields.getFieldId().equals("deposits")) {
					dynamicFields.setSavedData(businessDetails.getDeposits() + "");
				} else if (dynamicFields.getFieldId().equals("withdrawls")) {
					dynamicFields.setSavedData(businessDetails.getWithdrawls() + "");
				} else if (dynamicFields.getFieldId().equals("initialDeposit")) {
					dynamicFields.setSavedData(businessDetails.getInitialDeposit() + "");
				}
				appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
			}

		}

	}

	private static void prepareLiabilitiesDetails(Fields dynamicFields, User retailers,
			List<FieldsDTO> appDynamicFieldsDTOSet, Filters filter) {
		if (retailers == null || retailers.getLiabilitiesDetails() == null
				|| retailers.getLiabilitiesDetails().isEmpty()) {
			appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
		} else {
			Set<LiabilitiesDetails> LiabilitiesDetailsSet = retailers.getLiabilitiesDetails();
			for (LiabilitiesDetails liabilitiesDetails : LiabilitiesDetailsSet) {
				if (dynamicFields.getFieldId().equals("loanAmount")) {
					dynamicFields.setSavedData(liabilitiesDetails.getLoanAmount() + "");
				} else if (dynamicFields.getFieldId().equals("bankOrNbfiName")) {
					dynamicFields.setSavedData(liabilitiesDetails.getBankOrNbfiName());
				} else if (dynamicFields.getFieldId().equals("liabilityFromOtherOrganization")) {
					dynamicFields.setSavedData(liabilitiesDetails.getLiabilityFromOtherOrganization());
				} else if (dynamicFields.getFieldId().equals("loanAmountFromOtherOrganization")) {
					dynamicFields.setSavedData(liabilitiesDetails.getLoanAmount() + "");
				}
				appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
			}

		}

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
			for (BankAccountDetails bankAccountDetails : bankAccountDetailsSet) {
				if (dynamicFields.getFieldId().equals("accountTitle")) {
					dynamicFields.setSavedData(bankAccountDetails.getAccountTitle());
				} else if (dynamicFields.getFieldId().equals("typeOfConcern")) {
					dynamicFields.setSavedData(bankAccountDetails.getTypeOfConcern() != null
							? bankAccountDetails.getTypeOfConcern().toString()
							: null);
					List<String> options = new ArrayList<String>();
					TypeOfConcern[] concerns = TypeOfConcern.values();
					for (TypeOfConcern concern : concerns) {
						options.add(concern.toString());
					}
					dynamicFields.setOptions(options);

				} else if (dynamicFields.getFieldId().equals("bankName")) {
					dynamicFields.setSavedData(bankAccountDetails.getBankName());
				} else if (dynamicFields.getFieldId().equals("accountNumber")) {
					dynamicFields.setSavedData(bankAccountDetails.getAccountNumber() + "");
				} else if (dynamicFields.getFieldId().equals("branch")) {
					dynamicFields.setSavedData(bankAccountDetails.getBranch());
				} else if (dynamicFields.getFieldId().equals("accountPurpose")) {
					dynamicFields.setSavedData(bankAccountDetails.getAccountPurpose());
				} else if (dynamicFields.getFieldId().equals("modeOfOperation")) {
					dynamicFields.setSavedData(bankAccountDetails.getModeOfOperation() != null
							? bankAccountDetails.getModeOfOperation().toString()
							: null);
					List<String> options = new ArrayList<String>();
					ModeOfOperation[] currencies = ModeOfOperation.values();
					for (ModeOfOperation statuses : currencies) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("currency")) {
					dynamicFields.setSavedData(
							bankAccountDetails.getCurrency() != null ? bankAccountDetails.getCurrency().toString()
									: null);
					List<String> options = new ArrayList<String>();
					Currency[] currencies = Currency.values();
					for (Currency statuses : currencies) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("bankAccountType")) {
					dynamicFields.setSavedData(bankAccountDetails.getBankAccountType() != null
							? bankAccountDetails.getBankAccountType().toString()
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

	}

	private static void prepareAddress(Fields dynamicFields, Set<AddressDetails> addressDetailsSet,
			List<FieldsDTO> appDynamicFieldsDTOSet, SubGroups subGroups, Filters filter) {
		if (addressDetailsSet == null || addressDetailsSet.isEmpty()) {
			appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
		} else {
			AddressDetails addressDetails = getAddressDetails(subGroups, addressDetailsSet);
			if (addressDetailsSet != null) {
				if (dynamicFields.getFieldId().equals("address")) {
					dynamicFields.setSavedData(addressDetails.getAddress());
				} else if (dynamicFields.getFieldId().equals("upazilaThana")) {
					dynamicFields.setSavedData(addressDetails.getUpazilaThana());
				} else if (dynamicFields.getFieldId().equals("cityDsitrict")) {
					dynamicFields.setSavedData(addressDetails.getCityDsitrict());
				} else if (dynamicFields.getFieldId().equals("division")) {
					dynamicFields.setSavedData(addressDetails.getDivision());
				} else if (dynamicFields.getFieldId().equals("zipCode")) {
					dynamicFields.setSavedData(addressDetails.getZipCode() + "");
				} else if (dynamicFields.getFieldId().equals("landmark")) {
					dynamicFields.setSavedData(addressDetails.getLandmark());
				} else if (dynamicFields.getFieldId().equals("territory")) {
					dynamicFields.setSavedData(addressDetails.getTerritory());
				} else if (dynamicFields.getFieldId().equals("country")) {
					dynamicFields.setSavedData(addressDetails.getCountry());
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
		if (retailers != null) {
			if (checkFilterCriteria(filter, dynamicFields.getFieldId())) {
				if (dynamicFields.getFieldId().equals("firstName")) {
					dynamicFields.setSavedData(retailers.getFirstName());
				} else if (dynamicFields.getFieldId().equals("lastName")) {
					dynamicFields.setSavedData(retailers.getLastName());
				} else if (dynamicFields.getFieldId().equals("middleName")) {
					dynamicFields.setSavedData(retailers.getMiddleName());
				} else if (dynamicFields.getFieldId().equals("dob")) {
					dynamicFields.setSavedData(retailers.getDob());
				} else if (dynamicFields.getFieldId().equals("pob")) {
					dynamicFields.setSavedData(retailers.getPob());
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
				appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
			}
		} else {
			if (checkFilterCriteria(filter, dynamicFields.getFieldId())) {
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
				appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
			}
		}

	}

	private static FieldsDTO getAppDynamicFieldDTO(Fields dynamicFields) {
		FieldsDTO appDynamicFieldsDTO = new FieldsDTO();
		appDynamicFieldsDTO.setCamera(dynamicFields.isCamera());
		appDynamicFieldsDTO.setDataType(dynamicFields.getDataType());
		appDynamicFieldsDTO.setEditable(dynamicFields.isEditable());
		appDynamicFieldsDTO.setFieldId(dynamicFields.getFieldId());
		appDynamicFieldsDTO.setFieldName(dynamicFields.getFieldName());
		appDynamicFieldsDTO.setId(dynamicFields.getId());
		appDynamicFieldsDTO.setMandatory(dynamicFields.isMandatory());
		appDynamicFieldsDTO.setOptions(dynamicFields.getOptions());
		appDynamicFieldsDTO.setPlaceHolderText(dynamicFields.getPlaceHolderText());
		appDynamicFieldsDTO.setSavedData(dynamicFields.getSavedData());
		appDynamicFieldsDTO.setType(dynamicFields.getType());
		appDynamicFieldsDTO.setValidation(dynamicFields.getValidation());
		appDynamicFieldsDTO.setDisplayOrder(dynamicFields.getDisplayOrder());
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
