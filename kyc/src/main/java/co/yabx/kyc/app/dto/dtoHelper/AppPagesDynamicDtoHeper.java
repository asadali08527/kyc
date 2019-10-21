package co.yabx.kyc.app.dto.dtoHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.yabx.kyc.app.dto.ActionDTO;
import co.yabx.kyc.app.dto.AppDynamicFieldsDTO;
import co.yabx.kyc.app.dto.AppPagesDTO;
import co.yabx.kyc.app.dto.AppPagesSectionGroupsDTO;
import co.yabx.kyc.app.dto.AppPagesSectionsDTO;
import co.yabx.kyc.app.entities.AppDynamicFields;
import co.yabx.kyc.app.entities.AppPages;
import co.yabx.kyc.app.entities.AppPagesSectionGroups;
import co.yabx.kyc.app.entities.AppPagesSections;
import co.yabx.kyc.app.entities.SectionGroupRelationship;
import co.yabx.kyc.app.entities.filter.Filters;
import co.yabx.kyc.app.enums.AddressType;
import co.yabx.kyc.app.enums.BankAccountType;
import co.yabx.kyc.app.enums.Currency;
import co.yabx.kyc.app.enums.Gender;
import co.yabx.kyc.app.enums.LiabilityType;
import co.yabx.kyc.app.enums.LicenseType;
import co.yabx.kyc.app.enums.MaritalStatuses;
import co.yabx.kyc.app.enums.Nationality;
import co.yabx.kyc.app.enums.ResidentStatus;
import co.yabx.kyc.app.enums.UserType;
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
import co.yabx.kyc.app.repositories.SectionGroupRelationshipRepository;
import co.yabx.kyc.app.util.SpringUtil;

public class AppPagesDynamicDtoHeper implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppPagesDynamicDtoHeper.class);

	public static AppPagesDTO prepareAppPagesDto(AppPages pages, User retailers, User nominee,
			Set<AddressDetails> userAddressDetailsSet, Set<AddressDetails> nomineeAddressDetailsSet,
			Set<AddressDetails> businessAddressDetailsSet, Set<BankAccountDetails> userBankAccountDetailsSet,
			Set<BankAccountDetails> nomineeBankAccountDetailsSet, Set<BankAccountDetails> businessBankAccountDetailsSet,
			String type) {
		AppPagesDTO appPagesDTO = new AppPagesDTO();
		Map<String, Integer> filledVsUnfilled = new HashMap<String, Integer>();
		filledVsUnfilled.put("filledFields", 0);
		filledVsUnfilled.put("totalFields", 0);
		Set<AppPagesSections> appPagesSectionsSet = pages.getAppPagesSections();
		if (appPagesSectionsSet != null && !appPagesSectionsSet.isEmpty()) {
			List<AppPagesSectionsDTO> appPagesSectionSet = getSections(appPagesSectionsSet, retailers, filledVsUnfilled,
					nominee, userAddressDetailsSet, nomineeAddressDetailsSet, businessAddressDetailsSet,
					userBankAccountDetailsSet, nomineeBankAccountDetailsSet, businessBankAccountDetailsSet);
			appPagesDTO.setSections(appPagesSectionSet.stream()
					.sorted(Comparator.comparing(AppPagesSectionsDTO::getSectionId)).collect(Collectors.toList()));
			appPagesDTO.setEnable(pages.isEnable());
			appPagesDTO.setPageId(pages.getPageId());
			// appPagesDTO.setPageName(pages.getPageName());
			appPagesDTO.setPageTitle(pages.getPageTitle());
			appPagesDTO.setTotalFields(filledVsUnfilled.get("totalFields"));
			appPagesDTO.setFilledFields(filledVsUnfilled.get("filledFields"));
			if (UserType.RETAILERS.name().equals(type))
				appPagesDTO.setRetailerId(retailers != null ? retailers.getId() : null);
			else if (UserType.DISTRIBUTORS.name().equals(type))
				appPagesDTO.setDsrId(retailers != null ? retailers.getId() : null);
			appPagesDTO.setPageCompletion(appPagesDTO.getTotalFields() != 0
					? ((appPagesDTO.getFilledFields() * 100) / appPagesDTO.getTotalFields()) + "%"
					: "0%");
			appPagesDTO.setAction(prepareActions());
		}
		return appPagesDTO;
	}

	private static List<AppPagesSectionsDTO> getSections(Set<AppPagesSections> appPagesSectionsSet, User retailers,
			Map<String, Integer> filledVsUnfilled, User nominee, Set<AddressDetails> userAddressDetailsSet,
			Set<AddressDetails> nomineeAddressDetailsSet, Set<AddressDetails> businessAddressDetailsSet,
			Set<BankAccountDetails> userBankAccountDetailsSet, Set<BankAccountDetails> nomineeBankAccountDetailsSet,
			Set<BankAccountDetails> businessBankAccountDetailsSet) {
		List<AppPagesSectionsDTO> appPagesSectionDTOSet = new ArrayList<AppPagesSectionsDTO>();
		for (AppPagesSections appPagesSections : appPagesSectionsSet) {
			Map<String, Integer> section = new HashMap<String, Integer>();
			section.put("filledFields", 0);
			section.put("totalFields", 0);
			AppPagesSectionsDTO appPagesSectionsDTO = new AppPagesSectionsDTO();

			List<AppPagesSectionGroupsDTO> appPagesSectionGroupSet = getGroups(retailers, section, appPagesSections,
					nominee, userAddressDetailsSet, nomineeAddressDetailsSet, businessAddressDetailsSet,
					userBankAccountDetailsSet, nomineeBankAccountDetailsSet, businessBankAccountDetailsSet);
			appPagesSectionsDTO.setGroups(appPagesSectionGroupSet.stream()
					.sorted(Comparator.comparing(AppPagesSectionGroupsDTO::getGroupId)).collect(Collectors.toList()));
			appPagesSectionsDTO.setEnable(appPagesSections.isEnable());
			appPagesSectionsDTO.setSectionId(appPagesSections.getSectionId());
			appPagesSectionsDTO.setSectionName(appPagesSections.getSectionName());
			appPagesSectionsDTO.setSectionTitle(appPagesSections.getSectionTitle());
			appPagesSectionsDTO.setFilledFields(section.get("filledFields"));
			appPagesSectionsDTO.setTotalFields(section.get("totalFields"));
			if (appPagesSections.getSectionId() == 2)
				appPagesSectionsDTO.setNomineeId(nominee != null ? nominee.getId() : null);

			appPagesSectionDTOSet.add(appPagesSectionsDTO);
			filledVsUnfilled.put("filledFields", filledVsUnfilled.get("filledFields") + section.get("filledFields"));
			filledVsUnfilled.put("totalFields", filledVsUnfilled.get("totalFields") + section.get("totalFields"));
		}

		return appPagesSectionDTOSet;

	}

	private static List<AppPagesSectionGroupsDTO> getGroups(User retailers, Map<String, Integer> filledVsUnfilled,
			AppPagesSections appPagesSections, User nominee, Set<AddressDetails> userAddressDetailsSet,
			Set<AddressDetails> nomineeAddressDetailsSet, Set<AddressDetails> businessAddressDetailsSet,
			Set<BankAccountDetails> userBankAccountDetailsSet, Set<BankAccountDetails> nomineeBankAccountDetailsSet,
			Set<BankAccountDetails> businessBankAccountDetailsSet) {
		List<SectionGroupRelationship> sectionGroupRelationships = SpringUtil
				.bean(SectionGroupRelationshipRepository.class).findBySectionId(appPagesSections.getSectionId());
		List<AppPagesSectionGroupsDTO> appPagesSectionGroupSet = new ArrayList<AppPagesSectionGroupsDTO>();
		for (SectionGroupRelationship sectionGroupRelationship : sectionGroupRelationships) {
			if (sectionGroupRelationship.isActive()) {
				AppPagesSectionGroups appPagesSectionGroups = sectionGroupRelationship.getAppPagesSectionGroups();
				Set<Filters> filters = sectionGroupRelationship.getFilters();
				if (sectionGroupRelationship.isMultiple()) {
					String[] titles = sectionGroupRelationship.getMultipleTitlle() != null
							? sectionGroupRelationship.getMultipleTitlle().split(",")
							: null;
					for (String title : titles) {
						prepareGroups(title, retailers, appPagesSections, nominee, userAddressDetailsSet,
								nomineeAddressDetailsSet, businessAddressDetailsSet, userBankAccountDetailsSet,
								nomineeBankAccountDetailsSet, businessBankAccountDetailsSet, appPagesSectionGroups,
								appPagesSectionGroupSet, filledVsUnfilled, filters);
					}

				} else {
					prepareGroups(null, retailers, appPagesSections, nominee, userAddressDetailsSet,
							nomineeAddressDetailsSet, businessAddressDetailsSet, userBankAccountDetailsSet,
							nomineeBankAccountDetailsSet, businessBankAccountDetailsSet, appPagesSectionGroups,
							appPagesSectionGroupSet, filledVsUnfilled, filters);
				}
			}
		}
		return appPagesSectionGroupSet;

	}

	private static void prepareGroups(String title, User retailers, AppPagesSections appPagesSections, User nominee,
			Set<AddressDetails> userAddressDetailsSet, Set<AddressDetails> nomineeAddressDetailsSet,
			Set<AddressDetails> businessAddressDetailsSet, Set<BankAccountDetails> userBankAccountDetailsSet,
			Set<BankAccountDetails> nomineeBankAccountDetailsSet, Set<BankAccountDetails> businessBankAccountDetailsSet,
			AppPagesSectionGroups appPagesSectionGroups, List<AppPagesSectionGroupsDTO> appPagesSectionGroupSet,
			Map<String, Integer> filledVsUnfilled, Set<Filters> filters) {

		if (appPagesSectionGroups != null) {
			Map<String, Integer> groups = new HashMap<String, Integer>();
			groups.put("filledFields", 0);
			groups.put("totalFields", 0);
			AppPagesSectionGroupsDTO appPagesSectionGroupsDTO = new AppPagesSectionGroupsDTO();
			Set<AppDynamicFields> appDynamicFieldsSet = appPagesSectionGroups.getAppDynamicFields();
			Filters filter = fieldsTobeFiltered(filters, appPagesSectionGroups.getGroupName());
			if (appDynamicFieldsSet != null && !appDynamicFieldsSet.isEmpty()) {
				List<AppDynamicFieldsDTO> fields = getFields(appDynamicFieldsSet, retailers, groups, appPagesSections,
						nominee, userAddressDetailsSet, nomineeAddressDetailsSet, businessAddressDetailsSet,
						userBankAccountDetailsSet, nomineeBankAccountDetailsSet, businessBankAccountDetailsSet, title,
						filter);
				Set<AppDynamicFieldsDTO> appDynamicFieldsDTOs = fields.stream()
						.sorted(Comparator.comparing(AppDynamicFieldsDTO::getId)).collect(Collectors.toSet());
				appPagesSectionGroupsDTO.setFields(appDynamicFieldsDTOs.stream()
						.sorted(Comparator.comparing(AppDynamicFieldsDTO::getId)).collect(Collectors.toList()));
				appPagesSectionGroupsDTO.setEnable(appPagesSectionGroups.isEnable());
				appPagesSectionGroupsDTO.setGroupId(appPagesSectionGroups.getGroupId());
				appPagesSectionGroupsDTO.setGroupName(appPagesSectionGroups.getGroupName());
				appPagesSectionGroupsDTO.setGroupTitle(title != null ? title : appPagesSectionGroups.getGroupTitle());
				appPagesSectionGroupsDTO.setTotalFields(groups.get("totalFields"));
				appPagesSectionGroupsDTO.setFilledFields(groups.get("filledFields"));
			}
			appPagesSectionGroupSet.add(appPagesSectionGroupsDTO);
			filledVsUnfilled.put("filledFields", filledVsUnfilled.get("filledFields") + groups.get("filledFields"));
			filledVsUnfilled.put("totalFields", filledVsUnfilled.get("totalFields") + groups.get("totalFields"));
		}

	}

	private static Filters fieldsTobeFiltered(Set<Filters> filters, String groupName) {
		if (filters != null && !filters.isEmpty() && groupName != null && !groupName.isEmpty()) {
			Optional<Filters> filter = filters.stream().filter(f -> groupName.equalsIgnoreCase(f.getGroupName()))
					.findFirst();
			return filter.isPresent() ? filter.get() : null;
		}
		return null;
	}

	private static ActionDTO prepareActions() {
		ActionDTO actionDTO = new ActionDTO();
		actionDTO.setData("Submit");
		actionDTO.setName("submitForm");
		actionDTO.setType("submit");
		return actionDTO;
	}

	private static List<AppDynamicFieldsDTO> getFields(Set<AppDynamicFields> appDynamicFieldsSet, User retailers,
			Map<String, Integer> filledVsUnfilled, AppPagesSections appPagesSections, User nominee,
			Set<AddressDetails> userAddressDetailsSet, Set<AddressDetails> nomineeAddressDetailsSet,
			Set<AddressDetails> businessAddressDetailsSet, Set<BankAccountDetails> userBankAccountDetailsSet,
			Set<BankAccountDetails> nomineeBankAccountDetailsSet, Set<BankAccountDetails> businessBankAccountDetailsSet,
			String title, Filters filter) {
		Integer totalFields = 0;
		Integer filledFields = 0;
		List<AppDynamicFieldsDTO> appDynamicFieldsDTOSet = new ArrayList<AppDynamicFieldsDTO>();
		for (AppDynamicFields dynamicFields : appDynamicFieldsSet) {
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
				prepareAddress(dynamicFields, userAddressDetailsSet, appDynamicFieldsDTOSet, title);
			} else if (dynamicFields.getGroups().getGroupId() == 2 && appPagesSections.getSectionId() == 2) {
				// nominee address details
				prepareAddress(dynamicFields, nomineeAddressDetailsSet, appDynamicFieldsDTOSet, title);
			} else if (dynamicFields.getGroups().getGroupId() == 2 && appPagesSections.getSectionId() == 5) {
				// Business address details
				prepareAddress(dynamicFields, businessAddressDetailsSet, appDynamicFieldsDTOSet, title);
			} else if (dynamicFields.getGroups().getGroupId() == 3
					&& (appPagesSections.getSectionId() == 1 || appPagesSections.getSectionId() == 3)) {
				// user account details
				prepareAccountInformations(dynamicFields, userBankAccountDetailsSet, appDynamicFieldsDTOSet);
			} else if (dynamicFields.getGroups().getGroupId() == 3 && appPagesSections.getSectionId() == 2) {
				// nominee account details
				prepareAccountInformations(dynamicFields, nomineeBankAccountDetailsSet, appDynamicFieldsDTOSet);
			} else if (dynamicFields.getGroups().getGroupId() == 3 && appPagesSections.getSectionId() == 5) {
				// business account details
				prepareAccountInformations(dynamicFields, businessBankAccountDetailsSet, appDynamicFieldsDTOSet);
			} else if (dynamicFields.getGroups().getGroupId() == 4) {
				prepareLiabilitiesDetails(dynamicFields, retailers, appDynamicFieldsDTOSet);
			} else if (dynamicFields.getGroups().getGroupId() == 5) {
				prepareBusinessInformation(dynamicFields, retailers, appDynamicFieldsDTOSet);
			} else if (dynamicFields.getGroups().getGroupId() == 6) {
				prepareLicenseDetails(dynamicFields, retailers, appDynamicFieldsDTOSet);
			} else if (dynamicFields.getGroups().getGroupId() == 7) {
				prepareMonthlyTransactionProfile(dynamicFields, retailers, appDynamicFieldsDTOSet);
			} else if (dynamicFields.getGroups().getGroupId() == 8
					&& (appPagesSections.getSectionId() == 1 || appPagesSections.getSectionId() == 3)) {
				// user or distributor work education
				prepareWorkEducationDetails(dynamicFields, retailers, appDynamicFieldsDTOSet);
			} else if (dynamicFields.getGroups().getGroupId() == 8 && appPagesSections.getSectionId() == 2) {
				// nominee work education
				prepareWorkEducationDetails(dynamicFields, nominee, appDynamicFieldsDTOSet);
			} else if (dynamicFields.getGroups().getGroupId() == 9) {
				// Introducer Detaiils
				prepareIntroducerDetails(dynamicFields, retailers, appDynamicFieldsDTOSet);
			}

			if (dynamicFields.getSavedData() != null && !dynamicFields.getSavedData().isEmpty())
				filledFields++;
		}
		filledVsUnfilled.put("filledFields", filledFields);
		filledVsUnfilled.put("totalFields", totalFields);
		appDynamicFieldsDTOSet
				.add(appDynamicFieldsDTOSet.stream().max(Comparator.comparing(AppDynamicFieldsDTO::getId)).get());
		return appDynamicFieldsDTOSet;

	}

	private static void prepareIntroducerDetails(AppDynamicFields dynamicFields, User retailers,
			List<AppDynamicFieldsDTO> appDynamicFieldsDTOSet) {

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

	private static void prepareWorkEducationDetails(AppDynamicFields dynamicFields, User retailers,
			List<AppDynamicFieldsDTO> appDynamicFieldsDTOSet) {

		if (retailers == null || retailers.getWorkEducationDetails() == null
				|| retailers.getWorkEducationDetails().isEmpty()) {
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
					dynamicFields.setSavedData(workEducationDetails.getEducationalQualification());
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

	private static void prepareMonthlyTransactionProfile(AppDynamicFields dynamicFields, User retailers,
			List<AppDynamicFieldsDTO> appDynamicFieldsDTOSet) {
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

	private static void prepareLicenseDetails(AppDynamicFields dynamicFields, User retailers,
			List<AppDynamicFieldsDTO> appDynamicFieldsDTOSet) {
		if (retailers == null || retailers.getBusinessDetails() == null || retailers.getBusinessDetails().isEmpty()) {
			if (dynamicFields.getFieldId().equals("licenseType")) {
				List<String> options = new ArrayList<String>();
				LicenseType[] accountTypes = LicenseType.values();
				for (LicenseType statuses : accountTypes) {
					options.add(statuses.name());
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
							licenseDetails.getLicenseType() != null ? licenseDetails.getLicenseType().name() : null);
					List<String> options = new ArrayList<String>();
					LicenseType[] accountTypes = LicenseType.values();
					for (LicenseType statuses : accountTypes) {
						options.add(statuses.name());
					}
					dynamicFields.setOptions(options);
				}
				appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
			}

		}

	}

	private static void prepareBusinessInformation(AppDynamicFields dynamicFields, User retailers,
			List<AppDynamicFieldsDTO> appDynamicFieldsDTOSet) {

		if (retailers == null || retailers.getBusinessDetails() == null || retailers.getBusinessDetails().isEmpty()) {
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
					dynamicFields.setSavedData(businessDetails.getFacilityDetails());
				} else if (dynamicFields.getFieldId().equals("facilityType")) {
					dynamicFields.setSavedData(businessDetails.getFacilityType());
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
					dynamicFields.setSavedData(businessDetails.getBusinessType());
				} else if (dynamicFields.getFieldId().equals("sector")) {
					dynamicFields.setSavedData(businessDetails.getSector());
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

	private static void prepareLiabilitiesDetails(AppDynamicFields dynamicFields, User retailers,
			List<AppDynamicFieldsDTO> appDynamicFieldsDTOSet) {
		if (retailers == null || retailers.getLiabilitiesDetails() == null
				|| retailers.getLiabilitiesDetails().isEmpty()) {
			if (dynamicFields.getFieldId().equals("typeOfLiablity")) {
				List<String> options = new ArrayList<String>();
				LiabilityType[] accountTypes = LiabilityType.values();
				for (LiabilityType statuses : accountTypes) {
					options.add(statuses.name());
				}
				dynamicFields.setOptions(options);
			}
			appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
		} else {
			Set<LiabilitiesDetails> LiabilitiesDetailsSet = retailers.getLiabilitiesDetails();
			for (LiabilitiesDetails liabilitiesDetails : LiabilitiesDetailsSet) {
				if (dynamicFields.getFieldId().equals("loanAmount")) {
					dynamicFields.setSavedData(liabilitiesDetails.getLoanAmount() + "");
				} else if (dynamicFields.getFieldId().equals("bankOrNbfiName")) {
					dynamicFields.setSavedData(liabilitiesDetails.getBankOrNbfiName());
				} else if (dynamicFields.getFieldId().equals("liabilityFrom")) {
					dynamicFields.setSavedData(liabilitiesDetails.getLiabilityFrom());
				} else if (dynamicFields.getFieldId().equals("typeOfLiablity")) {
					dynamicFields.setSavedData(liabilitiesDetails.getTypeOfLiablity() != null
							? liabilitiesDetails.getTypeOfLiablity().name()
							: null);
					List<String> options = new ArrayList<String>();
					LiabilityType[] liabilityTypes = LiabilityType.values();
					for (LiabilityType statuses : liabilityTypes) {
						options.add(statuses.name());
					}
					dynamicFields.setOptions(options);
				}
				appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
			}

		}

	}

	private static void prepareAccountInformations(AppDynamicFields dynamicFields,
			Set<BankAccountDetails> bankAccountDetailsSet, List<AppDynamicFieldsDTO> appDynamicFieldsDTOSet) {
		if (bankAccountDetailsSet == null || bankAccountDetailsSet.isEmpty()) {
			if (dynamicFields.getFieldId().equals("bankAccountType")) {
				List<String> options = new ArrayList<String>();
				BankAccountType[] accountTypes = BankAccountType.values();
				for (BankAccountType statuses : accountTypes) {
					options.add(statuses.name());
				}
				dynamicFields.setOptions(options);
			} else if (dynamicFields.getFieldId().equals("currency")) {
				List<String> options = new ArrayList<String>();
				Currency[] currencies = Currency.values();
				for (Currency statuses : currencies) {
					options.add(statuses.name());
				}
				dynamicFields.setOptions(options);
			}
			appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
		} else {
			for (BankAccountDetails bankAccountDetails : bankAccountDetailsSet) {
				if (dynamicFields.getFieldId().equals("accountTitle")) {
					dynamicFields.setSavedData(bankAccountDetails.getAccountTitle());
				} else if (dynamicFields.getFieldId().equals("typeOfConcern")) {
					dynamicFields.setSavedData(bankAccountDetails.getTypeOfConcern());
				} else if (dynamicFields.getFieldId().equals("bankName")) {
					dynamicFields.setSavedData(bankAccountDetails.getBankName());
				} else if (dynamicFields.getFieldId().equals("accountNumber")) {
					dynamicFields.setSavedData(bankAccountDetails.getAccountNumber() + "");
				} else if (dynamicFields.getFieldId().equals("branch")) {
					dynamicFields.setSavedData(bankAccountDetails.getBranch() + "");
				} else if (dynamicFields.getFieldId().equals("modeOfOperation")) {
					dynamicFields.setSavedData(bankAccountDetails.getModeOfOperation() + "");
				} else if (dynamicFields.getFieldId().equals("currency")) {
					dynamicFields.setSavedData(
							bankAccountDetails.getCurrency() != null ? bankAccountDetails.getCurrency().name() : null);
					List<String> options = new ArrayList<String>();
					Currency[] currencies = Currency.values();
					for (Currency statuses : currencies) {
						options.add(statuses.name());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("bankAccountType")) {
					dynamicFields.setSavedData(bankAccountDetails.getBankAccountType() != null
							? bankAccountDetails.getBankAccountType().name()
							: null);
					List<String> options = new ArrayList<String>();
					BankAccountType[] accountTypes = BankAccountType.values();
					for (BankAccountType statuses : accountTypes) {
						options.add(statuses.name());
					}
					dynamicFields.setOptions(options);
				}
				appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
			}

		}

	}

	private static void prepareAddress(AppDynamicFields dynamicFields, Set<AddressDetails> addressDetailsSet,
			List<AppDynamicFieldsDTO> appDynamicFieldsDTOSet, String title) {
		if (addressDetailsSet == null || addressDetailsSet.isEmpty()) {
			if (dynamicFields.getFieldId().equals("addressType")) {
				List<String> options = new ArrayList<String>();
				AddressType[] addressTypes = AddressType.values();
				for (AddressType statuses : addressTypes) {
					options.add(statuses.name());
				}
				dynamicFields.setOptions(options);
			}
			appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
		} else {
			for (AddressDetails addressDetails : addressDetailsSet) {
				if (dynamicFields.getFieldId().equals("houseNumberOrStreetName")) {
					dynamicFields.setSavedData(addressDetails.getHouseNumberOrStreetName());
				} else if (dynamicFields.getFieldId().equals("area")) {
					dynamicFields.setSavedData(addressDetails.getArea());
				} else if (dynamicFields.getFieldId().equals("city")) {
					dynamicFields.setSavedData(addressDetails.getCity());
				} else if (dynamicFields.getFieldId().equals("region")) {
					dynamicFields.setSavedData(addressDetails.getRegion());
				} else if (dynamicFields.getFieldId().equals("zipCode")) {
					dynamicFields.setSavedData(addressDetails.getZipCode() + "");
				} else if (dynamicFields.getFieldId().equals("addressType")) {
					dynamicFields.setSavedData(
							addressDetails.getAddressType() != null ? addressDetails.getAddressType().name() : null);
					List<String> options = new ArrayList<String>();
					AddressType[] addressTypes = AddressType.values();
					for (AddressType statuses : addressTypes) {
						options.add(statuses.toString());
					}
					dynamicFields.setOptions(options);
				}
				appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
			}

		}

	}

	private static void prepareProfileInformation(AppDynamicFields dynamicFields, User retailers,
			List<AppDynamicFieldsDTO> appDynamicFieldsDTOSet, Filters filter) {
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
							retailers.getMaritalStatus() != null ? retailers.getMaritalStatus().name() : null);
					List<String> options = new ArrayList<String>();
					MaritalStatuses[] maritalStatuses = MaritalStatuses.values();
					for (MaritalStatuses statuses : maritalStatuses) {
						options.add(statuses.name());
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
					dynamicFields.setSavedData(retailers.getGender() != null ? retailers.getGender().name() : null);
					List<String> options = new ArrayList<String>();
					Gender[] genders = Gender.values();
					for (Gender statuses : genders) {
						options.add(statuses.name());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("id")) {
					dynamicFields.setSavedData(retailers.getId() + "");
				} else if (dynamicFields.getFieldId().equals("msisdn")) {
					dynamicFields.setSavedData(retailers.getMsisdn());
				} else if (dynamicFields.getFieldId().equals("sisterConcernedOrAllied")
						&& filter.filter(dynamicFields.getFieldId())) {
					dynamicFields.setSavedData(retailers.getSisterConcernedOrAllied());
				} else if (dynamicFields.getFieldId().equals("taxIdentificationNumber")) {
					dynamicFields.setSavedData(retailers.getTaxIdentificationNumber());
				} else if (dynamicFields.getFieldId().equals("residentialStatus")) {
					dynamicFields.setSavedData(
							retailers.getResidentialStatus() != null ? retailers.getResidentialStatus().name() : null);
					List<String> options = new ArrayList<String>();
					ResidentStatus[] residentStatuses = ResidentStatus.values();
					for (ResidentStatus statuses : residentStatuses) {
						options.add(statuses.name());
					}
					dynamicFields.setOptions(options);

				} else if (dynamicFields.getFieldId().equals("passportNumber")) {
					dynamicFields.setSavedData(retailers.getPassportNumber());
				} else if (dynamicFields.getFieldId().equals("passportExpiryDate")) {
					dynamicFields.setSavedData(retailers.getPassportExpiryDate());
				} else if (dynamicFields.getFieldId().equals("nationality")) {
					dynamicFields.setSavedData(
							retailers.getNationality() != null ? retailers.getNationality().name() : null);
					List<String> options = new ArrayList<String>();
					Nationality[] nationalities = Nationality.values();
					for (Nationality statuses : nationalities) {
						options.add(statuses.name());
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
						options.add(statuses.name());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("residentialStatus")) {

					List<String> options = new ArrayList<String>();
					ResidentStatus[] residentStatuses = ResidentStatus.values();
					for (ResidentStatus statuses : residentStatuses) {
						options.add(statuses.name());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("gender")) {
					List<String> options = new ArrayList<String>();
					Gender[] genders = Gender.values();
					for (Gender statuses : genders) {
						options.add(statuses.name());
					}
					dynamicFields.setOptions(options);
				} else if (dynamicFields.getFieldId().equals("maritalStatus")) {

					List<String> options = new ArrayList<String>();
					MaritalStatuses[] maritalStatuses = MaritalStatuses.values();
					for (MaritalStatuses statuses : maritalStatuses) {
						options.add(statuses.name());
					}
					dynamicFields.setOptions(options);
				}
				appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
			}
		}

	}

	private static boolean checkFilterCriteria(Filters filter, String fieldId) {
		if (filter != null) {
			return filter.filter(fieldId);
		}
		return true;
	}

	private static AppDynamicFieldsDTO getAppDynamicFieldDTO(AppDynamicFields dynamicFields) {
		AppDynamicFieldsDTO appDynamicFieldsDTO = new AppDynamicFieldsDTO();
		appDynamicFieldsDTO.setCamera(dynamicFields.isCamera());
		appDynamicFieldsDTO.setDataType(dynamicFields.getDataType());
		appDynamicFieldsDTO.setEditable(dynamicFields.isEditable());
		appDynamicFieldsDTO.setFieldId(dynamicFields.getFieldId());
		appDynamicFieldsDTO.setFieldName(dynamicFields.getFieldName());
		appDynamicFieldsDTO.setId(dynamicFields.getId());
		appDynamicFieldsDTO.setOrder(dynamicFields.getDisplayOrder());
		appDynamicFieldsDTO.setMandatory(dynamicFields.isMandatory());
		appDynamicFieldsDTO.setOptions(dynamicFields.getOptions());
		appDynamicFieldsDTO.setPlaceHolderText(dynamicFields.getPlaceHolderText());
		appDynamicFieldsDTO.setSavedData(dynamicFields.getSavedData());
		appDynamicFieldsDTO.setType(dynamicFields.getType());
		appDynamicFieldsDTO.setValidation(dynamicFields.getValidation());
		return appDynamicFieldsDTO;
	}

}
