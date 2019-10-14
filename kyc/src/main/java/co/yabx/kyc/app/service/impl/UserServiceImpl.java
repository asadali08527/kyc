package co.yabx.kyc.app.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import co.yabx.kyc.app.enums.AddressType;
import co.yabx.kyc.app.enums.BankAccountType;
import co.yabx.kyc.app.enums.Currency;
import co.yabx.kyc.app.enums.Gender;
import co.yabx.kyc.app.enums.LiabilityType;
import co.yabx.kyc.app.enums.LicenseType;
import co.yabx.kyc.app.enums.MaritalStatuses;
import co.yabx.kyc.app.enums.Nationality;
import co.yabx.kyc.app.enums.Relationship;
import co.yabx.kyc.app.enums.ResidentStatus;
import co.yabx.kyc.app.enums.UserType;
import co.yabx.kyc.app.fullKyc.entity.AddressDetails;
import co.yabx.kyc.app.fullKyc.entity.BankAccountDetails;
import co.yabx.kyc.app.fullKyc.entity.BusinessDetails;
import co.yabx.kyc.app.fullKyc.entity.DSRUser;
import co.yabx.kyc.app.fullKyc.entity.LiabilitiesDetails;
import co.yabx.kyc.app.fullKyc.entity.LicenseDetails;
import co.yabx.kyc.app.fullKyc.entity.MonthlyTransactionProfiles;
import co.yabx.kyc.app.fullKyc.entity.Nominees;
import co.yabx.kyc.app.fullKyc.entity.Retailers;
import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.fullKyc.entity.UserRelationships;
import co.yabx.kyc.app.fullKyc.entity.WorkEducationDetails;
import co.yabx.kyc.app.fullKyc.repository.DSRUserRepository;
import co.yabx.kyc.app.fullKyc.repository.NomineesRepository;
import co.yabx.kyc.app.fullKyc.repository.RetailersRepository;
import co.yabx.kyc.app.fullKyc.repository.UserRelationshipsRepository;
import co.yabx.kyc.app.fullKyc.repository.UserRepository;
import co.yabx.kyc.app.repositories.AppPagesRepository;
import co.yabx.kyc.app.repositories.SectionGroupRelationshipRepository;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.UserService;
import co.yabx.kyc.app.util.SpringUtil;

/**
 * 
 * @author Asad.ali
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private RetailersRepository retailersRepository;

	@Autowired
	private DSRUserRepository dsrUserRepository;

	@Autowired
	private NomineesRepository nomineesRepository;

	@Autowired
	private UserRelationshipsRepository userRelationshipsRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	@Transactional
	public void persistYabxTokenAndSecretKey(User user, String yabxToken, String aPI_SECRET_KEY) {
		/*
		 * if (user != null) { user.setYabxToken(yabxToken);
		 * user.setSecretKey(aPI_SECRET_KEY); userRepository.save(user); }
		 */}

	@Override
	public List<AppPagesDTO> getUserDetails(User user, String type) {

		List<AppPages> appPages = SpringUtil.bean(AppPagesRepository.class).findByPageType(type);
		if (appPages == null)
			return null;

		// Nominee Personal Info
		User nominee = null;
		Set<AddressDetails> userAddressDetailsSet = null;
		Set<AddressDetails> nomineeAddressDetailsSet = null;
		Set<AddressDetails> businessAddressDetailsSet = null;
		Set<BankAccountDetails> userBankAccountDetailsSet = null;
		Set<BankAccountDetails> nomineeBankAccountDetailsSet = null;
		Set<BankAccountDetails> businessBankAccountDetailsSet = null;
		List<AppPagesDTO> appPagesDTOList = new ArrayList<AppPagesDTO>();
		if (user != null) {
			UserRelationships userRelationships = userRelationshipsRepository
					.findByMsisdnAndRelationship(user.getMsisdn(), Relationship.NOMINEE);
			nominee = userRelationships != null ? userRelationships.getRelative() : null;
			userAddressDetailsSet = user.getAddressDetails();
			nomineeAddressDetailsSet = nominee != null ? nominee.getAddressDetails() : null;
			userBankAccountDetailsSet = user.getBankAccountDetails();
			nomineeBankAccountDetailsSet = nominee != null ? nominee.getBankAccountDetails() : null;
			if (user.getBusinessDetails() != null) {
				user.getBusinessDetails().forEach(f -> {
					businessAddressDetailsSet.addAll(f.getAddressDetails());
				});
				user.getBusinessDetails().forEach(f -> {
					businessBankAccountDetailsSet.addAll(f.getBankAccountDetails());
				});
			}
		}
		for (AppPages pages : appPages) {
			appPagesDTOList.add(preparePages(pages, user, nominee, userAddressDetailsSet, nomineeAddressDetailsSet,
					businessAddressDetailsSet, userBankAccountDetailsSet, nomineeBankAccountDetailsSet,
					businessBankAccountDetailsSet, type));
		}

		return appPagesDTOList;

	}

	private static AppPagesDTO preparePages(AppPages pages, User retailers, User nominee,
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
			appPagesDTO.setPageCompletion(((appPagesDTO.getFilledFields() * 100) / appPagesDTO.getTotalFields()) + "%");
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
			List<SectionGroupRelationship> sectionGroupRelationships = SpringUtil
					.bean(SectionGroupRelationshipRepository.class).findBySectionId(appPagesSections.getSectionId());
			Set<AppPagesSectionGroups> appPagesSectionGroupsSet = sectionGroupRelationships.stream()
					.map(f -> f.getAppPagesSectionGroups()).collect(Collectors.toSet());
			if (appPagesSectionGroupsSet != null && !appPagesSectionGroupsSet.isEmpty()) {
				List<AppPagesSectionGroupsDTO> appPagesSectionGroupSet = getGroups(appPagesSectionGroupsSet, retailers,
						section, appPagesSections, nominee, userAddressDetailsSet, nomineeAddressDetailsSet,
						businessAddressDetailsSet, userBankAccountDetailsSet, nomineeBankAccountDetailsSet,
						businessBankAccountDetailsSet);
				appPagesSectionsDTO.setGroups(appPagesSectionGroupSet.stream()
						.sorted(Comparator.comparing(AppPagesSectionGroupsDTO::getGroupId))
						.collect(Collectors.toList()));
				appPagesSectionsDTO.setEnable(appPagesSections.isEnable());
				appPagesSectionsDTO.setSectionId(appPagesSections.getSectionId());
				appPagesSectionsDTO.setSectionName(appPagesSections.getSectionName());
				appPagesSectionsDTO.setSectionTitle(appPagesSections.getSectionTitle());
				appPagesSectionsDTO.setFilledFields(section.get("filledFields"));
				appPagesSectionsDTO.setTotalFields(section.get("totalFields"));
				if (appPagesSections.getSectionId() == 2)
					appPagesSectionsDTO.setNomineeId(nominee != null ? nominee.getId() : null);
			}
			appPagesSectionDTOSet.add(appPagesSectionsDTO);
			filledVsUnfilled.put("filledFields", filledVsUnfilled.get("filledFields") + section.get("filledFields"));
			filledVsUnfilled.put("totalFields", filledVsUnfilled.get("totalFields") + section.get("totalFields"));
		}

		return appPagesSectionDTOSet;

	}

	private static List<AppPagesSectionGroupsDTO> getGroups(Set<AppPagesSectionGroups> appPagesSectionGroupsSet,
			User retailers, Map<String, Integer> filledVsUnfilled, AppPagesSections appPagesSections, User nominee,
			Set<AddressDetails> userAddressDetailsSet, Set<AddressDetails> nomineeAddressDetailsSet,
			Set<AddressDetails> businessAddressDetailsSet, Set<BankAccountDetails> userBankAccountDetailsSet,
			Set<BankAccountDetails> nomineeBankAccountDetailsSet,
			Set<BankAccountDetails> businessBankAccountDetailsSet) {

		List<AppPagesSectionGroupsDTO> appPagesSectionGroupSet = new ArrayList<AppPagesSectionGroupsDTO>();
		for (AppPagesSectionGroups appPagesSectionGroups : appPagesSectionGroupsSet) {
			Map<String, Integer> groups = new HashMap<String, Integer>();
			groups.put("filledFields", 0);
			groups.put("totalFields", 0);
			AppPagesSectionGroupsDTO appPagesSectionGroupsDTO = new AppPagesSectionGroupsDTO();
			Set<AppDynamicFields> appDynamicFieldsSet = appPagesSectionGroups.getAppDynamicFields();
			if (appDynamicFieldsSet != null && !appDynamicFieldsSet.isEmpty()) {
				List<AppDynamicFieldsDTO> fields = getFields(appDynamicFieldsSet, retailers, groups, appPagesSections,
						nominee, userAddressDetailsSet, nomineeAddressDetailsSet, businessAddressDetailsSet,
						userBankAccountDetailsSet, nomineeBankAccountDetailsSet, businessBankAccountDetailsSet);
				Set<AppDynamicFieldsDTO> appDynamicFieldsDTOs = fields.stream()
						.sorted(Comparator.comparing(AppDynamicFieldsDTO::getId)).collect(Collectors.toSet());
				appPagesSectionGroupsDTO.setFields(appDynamicFieldsDTOs.stream().collect(Collectors.toList()));
				appPagesSectionGroupsDTO.setEnable(appPagesSectionGroups.isEnable());
				appPagesSectionGroupsDTO.setGroupId(appPagesSectionGroups.getGroupId());
				appPagesSectionGroupsDTO.setGroupName(appPagesSectionGroups.getGroupName());
				appPagesSectionGroupsDTO.setGroupTitle(appPagesSectionGroups.getGroupTitle());
				appPagesSectionGroupsDTO.setTotalFields(groups.get("totalFields"));
				appPagesSectionGroupsDTO.setFilledFields(groups.get("filledFields"));
			}
			appPagesSectionGroupSet.add(appPagesSectionGroupsDTO);
			filledVsUnfilled.put("filledFields", filledVsUnfilled.get("filledFields") + groups.get("filledFields"));
			filledVsUnfilled.put("totalFields", filledVsUnfilled.get("totalFields") + groups.get("totalFields"));
		}

		return appPagesSectionGroupSet;

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
			Set<BankAccountDetails> nomineeBankAccountDetailsSet,
			Set<BankAccountDetails> businessBankAccountDetailsSet) {
		Integer totalFields = 0;
		Integer filledFields = 0;
		List<AppDynamicFieldsDTO> appDynamicFieldsDTOSet = new ArrayList<AppDynamicFieldsDTO>();
		for (AppDynamicFields dynamicFields : appDynamicFieldsSet) {
			totalFields++;
			if (dynamicFields.getGroups().getGroupId() == 1
					&& (appPagesSections.getSectionId() == 1 || appPagesSections.getSectionId() == 3)) {
				// User personal Info
				prepareProfileInformation(dynamicFields, retailers, appDynamicFieldsDTOSet);
			} else if (dynamicFields.getGroups().getGroupId() == 1 && appPagesSections.getSectionId() == 2) {
				// nominee
				prepareProfileInformation(dynamicFields, nominee, appDynamicFieldsDTOSet);
			} else if (dynamicFields.getGroups().getGroupId() == 2
					&& (appPagesSections.getSectionId() == 1 || appPagesSections.getSectionId() == 3)) {
				// user address details
				prepareAddress(dynamicFields, userAddressDetailsSet, appDynamicFieldsDTOSet);
			} else if (dynamicFields.getGroups().getGroupId() == 2 && appPagesSections.getSectionId() == 2) {
				// nominee address details
				prepareAddress(dynamicFields, nomineeAddressDetailsSet, appDynamicFieldsDTOSet);
			} else if (dynamicFields.getGroups().getGroupId() == 2 && appPagesSections.getSectionId() == 5) {
				// Business address details
				prepareAddress(dynamicFields, businessAddressDetailsSet, appDynamicFieldsDTOSet);
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
			List<AppDynamicFieldsDTO> appDynamicFieldsDTOSet) {
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
						options.add(statuses.name());
					}
					dynamicFields.setOptions(options);
				}
				appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));
			}

		}

	}

	private static void prepareProfileInformation(AppDynamicFields dynamicFields, User retailers,
			List<AppDynamicFieldsDTO> appDynamicFieldsDTOSet) {
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
			} else if (dynamicFields.getFieldId().equals("sisterConcernedOrAllied")) {
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
				dynamicFields
						.setSavedData(retailers.getNationality() != null ? retailers.getNationality().name() : null);
				List<String> options = new ArrayList<String>();
				Nationality[] nationalities = Nationality.values();
				for (Nationality statuses : nationalities) {
					options.add(statuses.name());
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
		}
		appDynamicFieldsDTOSet.add(getAppDynamicFieldDTO(dynamicFields));

	}

	private static AppDynamicFieldsDTO getAppDynamicFieldDTO(AppDynamicFields dynamicFields) {
		AppDynamicFieldsDTO appDynamicFieldsDTO = new AppDynamicFieldsDTO();
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
		return appDynamicFieldsDTO;
	}

	@Override
	public DSRUser getDSRByMsisdn(String dsrMsisdn) {
		if (dsrMsisdn != null && !dsrMsisdn.isEmpty())
			return dsrUserRepository.findByMsisdn(dsrMsisdn);
		return null;
	}

	@Override
	public Retailers getRetailerById(Long retailerId) {
		if (retailerId != null) {
			Optional<Retailers> retailer = retailersRepository.findById(retailerId);
			if (retailer.isPresent() && retailer.get().getUserType().equals(UserType.RETAILERS.name()))
				return retailer.get();
		}
		return null;
	}

	@Override
	public void persistOrUpdateRetailerInfo(AppPagesDTO appPagesDTO, User dsrUser, User retailer) {

		if (appPagesDTO != null && dsrUser != null) {
			Boolean isNew = false;
			User nominees = null;
			Set<AddressDetails> userAddressDetailsSet = null;
			Set<AddressDetails> nomineeAddressDetailsSet = null;
			Set<AddressDetails> businessAddressDetailsSet = null;
			Set<BankAccountDetails> userBankAccountDetailsSet = null;
			Set<BankAccountDetails> nomineeBankAccountDetailsSet = null;
			Set<BankAccountDetails> businessBankAccountDetailsSet = null;
			Set<BusinessDetails> businessDetailsSet = null;
			Set<LiabilitiesDetails> liabilitiesDetailsSet = null;
			UserRelationships nomineeRelationship = null;
			if (retailer == null) {
				retailer = new Retailers();
				isNew = true;
				nominees = new Nominees();
				userAddressDetailsSet = new HashSet<AddressDetails>();
				nomineeAddressDetailsSet = new HashSet<AddressDetails>();
				businessAddressDetailsSet = new HashSet<AddressDetails>();
				userBankAccountDetailsSet = new HashSet<BankAccountDetails>();
				nomineeBankAccountDetailsSet = new HashSet<BankAccountDetails>();
				businessBankAccountDetailsSet = new HashSet<BankAccountDetails>();
				businessDetailsSet = new HashSet<BusinessDetails>();
			} else {
				userBankAccountDetailsSet = retailer.getBankAccountDetails();
				userAddressDetailsSet = retailer.getAddressDetails();
				businessDetailsSet = retailer.getBusinessDetails();
				liabilitiesDetailsSet = retailer.getLiabilitiesDetails();
				nomineeRelationship = userRelationshipsRepository.findByMsisdnAndRelationship(retailer.getMsisdn(),
						Relationship.NOMINEE);
				nominees = nomineeRelationship != null ? nomineeRelationship.getRelative() : null;
				if (nominees != null) {
					nomineeAddressDetailsSet = nominees.getAddressDetails();
					nomineeBankAccountDetailsSet = nominees.getBankAccountDetails();
				} else {
					isNew = true;

				}
				/*
				 * if (businessDetailsSet != null && !businessDetailsSet.isEmpty()) {
				 * businessDetailsSet.forEach(f ->
				 * businessAddressDetailsSet.addAll(f.getAddressDetails()));
				 * businessDetailsSet.forEach(f ->
				 * businessBankAccountDetailsSet.addAll(f.getBankAccountDetails())); }
				 */
			}
			List<AppPagesSectionsDTO> appPagesSectionsDTOList = appPagesDTO.getSections();
			if (appPagesSectionsDTOList != null && !appPagesSectionsDTOList.isEmpty()) {
				for (AppPagesSectionsDTO appPagesSectionsDTO : appPagesSectionsDTOList) {
					List<AppPagesSectionGroupsDTO> appPagesSectionGroupsDTOList = appPagesSectionsDTO.getGroups();
					if (appPagesSectionGroupsDTOList != null && !appPagesSectionGroupsDTOList.isEmpty()) {
						for (AppPagesSectionGroupsDTO appPagesSectionGroupsDTO : appPagesSectionGroupsDTOList) {
							List<AppDynamicFieldsDTO> appDynamicFieldsDTOList = appPagesSectionGroupsDTO.getFields();
							AddressDetails addressDetails = null;
							BankAccountDetails bankAccountDetails = null;
							LiabilitiesDetails liabilitiesDetails = null;
							if (appPagesSectionGroupsDTO.getGroupId() == 2 && (appPagesSectionsDTO.getSectionId() == 1
									|| appPagesSectionsDTO.getSectionId() == 3)) {
								// user address details
								addressDetails = new AddressDetails();
								addressDetails = prepareAddress(appDynamicFieldsDTOList, addressDetails);
								if (addressDetails != null) {
									if (userAddressDetailsSet == null) {
										userAddressDetailsSet = new HashSet<AddressDetails>();
									}
									userAddressDetailsSet.add(addressDetails);
								}
								continue;
							} else if (appPagesSectionGroupsDTO.getGroupId() == 2
									&& appPagesSectionsDTO.getSectionId() == 2) {
								// nominee address details
								addressDetails = new AddressDetails();
								addressDetails = prepareAddress(appDynamicFieldsDTOList, addressDetails);
								if (addressDetails != null) {
									if (nomineeAddressDetailsSet == null) {
										nomineeAddressDetailsSet = new HashSet<AddressDetails>();
									}
									nomineeAddressDetailsSet.add(addressDetails);
								}
								continue;
							} else if (appPagesSectionGroupsDTO.getGroupId() == 2
									&& appPagesSectionsDTO.getSectionId() == 5) {
								// Business address details
								addressDetails = new AddressDetails();
								addressDetails = prepareAddress(appDynamicFieldsDTOList, addressDetails);
								if (addressDetails != null) {
									if (businessAddressDetailsSet == null) {
										businessAddressDetailsSet = new HashSet<AddressDetails>();
									}
									businessAddressDetailsSet.add(addressDetails);
								}
								continue;
							} else if (appPagesSectionGroupsDTO.getGroupId() == 3
									&& (appPagesSectionsDTO.getSectionId() == 1
											|| appPagesSectionsDTO.getSectionId() == 3)) {
								bankAccountDetails = new BankAccountDetails();
								bankAccountDetails = preparebankAccounts(appDynamicFieldsDTOList, bankAccountDetails);
								if (userBankAccountDetailsSet == null)
									userBankAccountDetailsSet = new HashSet<BankAccountDetails>();
								userBankAccountDetailsSet.add(bankAccountDetails);
								continue;

							} else if (appPagesSectionGroupsDTO.getGroupId() == 3
									&& appPagesSectionsDTO.getSectionId() == 2) {
								bankAccountDetails = new BankAccountDetails();
								bankAccountDetails = preparebankAccounts(appDynamicFieldsDTOList, bankAccountDetails);
								if (nomineeBankAccountDetailsSet == null)
									nomineeBankAccountDetailsSet = new HashSet<BankAccountDetails>();
								nomineeBankAccountDetailsSet.add(bankAccountDetails);
								continue;

							} else if (appPagesSectionGroupsDTO.getGroupId() == 3
									&& appPagesSectionsDTO.getSectionId() == 5) {
								bankAccountDetails = new BankAccountDetails();
								bankAccountDetails = preparebankAccounts(appDynamicFieldsDTOList, bankAccountDetails);
								if (businessBankAccountDetailsSet == null)
									businessBankAccountDetailsSet = new HashSet<BankAccountDetails>();
								businessBankAccountDetailsSet.add(bankAccountDetails);
								continue;
							} else if (appPagesSectionGroupsDTO.getGroupId() == 4) {
								liabilitiesDetails = new LiabilitiesDetails();
								liabilitiesDetails = prepareLiabilities(appDynamicFieldsDTOList, liabilitiesDetails);
								if (liabilitiesDetailsSet == null)
									liabilitiesDetailsSet = new HashSet<LiabilitiesDetails>();
								liabilitiesDetailsSet.add(liabilitiesDetails);
								continue;
							} else if (appPagesSectionGroupsDTO.getGroupId() == 5) {
								BusinessDetails businessDetails = null;
								if (businessDetailsSet == null || businessDetailsSet.isEmpty()) {
									businessDetails = new BusinessDetails();
								} else {
									Optional<BusinessDetails> businessDetailsOptional = businessDetailsSet.stream()
											.findFirst();
									if (!businessDetailsOptional.isPresent())
										businessDetails = new BusinessDetails();
									else
										businessDetails = businessDetailsOptional.get();
								}
								businessDetails = prepareBusinessInformation(appDynamicFieldsDTOList, businessDetails);
								if (businessDetails != null) {
									if (businessDetailsSet == null) {
										businessDetailsSet = new HashSet<BusinessDetails>();
									}
									businessDetailsSet.add(businessDetails);
								}
								continue;
							} else if (appPagesSectionGroupsDTO.getGroupId() == 6) {
								LicenseDetails licenseDetails = null;
								licenseDetails = prepareLicenseDetails(appDynamicFieldsDTOList, licenseDetails);
								if (licenseDetails != null) {
									if (businessDetailsSet == null || businessDetailsSet.isEmpty()) {
										BusinessDetails businessDetails = new BusinessDetails();
										businessDetails.setLicenseDetails(licenseDetails);
										businessDetailsSet = new HashSet<BusinessDetails>();
										businessDetailsSet.add(businessDetails);
									} else {
										BusinessDetails businessDetails = null;
										Optional<BusinessDetails> businessDetailsOptional = businessDetailsSet.stream()
												.findFirst();
										if (!businessDetailsOptional.isPresent())
											businessDetails = new BusinessDetails();
										else
											businessDetails = businessDetailsOptional.get();
										businessDetails.setLicenseDetails(licenseDetails);
										businessDetailsSet.add(businessDetails);
									}
								}
								continue;
							}
							for (AppDynamicFieldsDTO appDynamicFieldsDTO : appDynamicFieldsDTOList) {
								if (appPagesSectionGroupsDTO.getGroupId() == 1
										&& (appPagesSectionsDTO.getSectionId() == 1
												|| appPagesSectionsDTO.getSectionId() == 3)) {
									// User personal Info
									retailer = prepareProfileInformation(appDynamicFieldsDTO, retailer);
								} else if (appPagesSectionGroupsDTO.getGroupId() == 1
										&& appPagesSectionsDTO.getSectionId() == 2) {
									// nominee
									if (nominees == null)
										nominees = new Nominees();
									nominees = prepareProfileInformation(appDynamicFieldsDTO, nominees);
								}

							}
						}
					}

				}
				persistUser(retailer, nominees, userAddressDetailsSet, userBankAccountDetailsSet, liabilitiesDetailsSet,
						isNew, nomineeRelationship, nomineeAddressDetailsSet);
			}
		}

	}

	private LiabilitiesDetails prepareLiabilities(List<AppDynamicFieldsDTO> appDynamicFieldsDTOList,
			LiabilitiesDetails liabilitiesDetails) {
		for (AppDynamicFieldsDTO appDynamicFieldsDTO : appDynamicFieldsDTOList) {
			if (appDynamicFieldsDTO != null) {
				if (appDynamicFieldsDTO.getFieldId().equals("loanAmount")) {
					try {
						double loanAmount = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Double.valueOf(appDynamicFieldsDTO.getResponse())
								: 0.0;
						liabilitiesDetails.setLoanAmount(loanAmount);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating loanAmount ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("bankOrNbfiName")) {
					liabilitiesDetails.setBankOrNbfiName(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("liabilityFrom")) {
					liabilitiesDetails.setLiabilityFrom(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("typeOfLiablity")) {
					try {
						LiabilityType typeOfLiablity = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? LiabilityType.valueOf(appDynamicFieldsDTO.getResponse())
								: null;
						liabilitiesDetails.setTypeOfLiablity(typeOfLiablity);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating liabilityType ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				}
			}
		}
		return liabilitiesDetails;
	}

	private BankAccountDetails preparebankAccounts(List<AppDynamicFieldsDTO> appDynamicFieldsDTOList,
			BankAccountDetails bankAccountDetails) {
		for (AppDynamicFieldsDTO appDynamicFieldsDTO : appDynamicFieldsDTOList) {
			if (appDynamicFieldsDTO != null) {
				if (appDynamicFieldsDTO.getFieldId().equals("accountTitle")) {
					bankAccountDetails.setAccountTitle(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("typeOfConcern")) {
					bankAccountDetails.setTypeOfConcern(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("bankName")) {
					bankAccountDetails.setBankName(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("accountNumber")) {
					try {
						Long accountNumber = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Long.valueOf(appDynamicFieldsDTO.getResponse())
								: null;
						bankAccountDetails.setAccountNumber(accountNumber);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating accountNumber ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("branch")) {
					bankAccountDetails.setBranch(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("modeOfOperation")) {
					bankAccountDetails.setModeOfOperation(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("currency")) {
					try {
						Currency currency = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Currency.valueOf(appDynamicFieldsDTO.getResponse())
								: null;
						bankAccountDetails.setCurrency(currency);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating currency ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("bankAccountType")) {
					BankAccountType bankAccountType = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
							? BankAccountType.valueOf(appDynamicFieldsDTO.getResponse())
							: null;
					bankAccountDetails.setBankAccountType(bankAccountType);
				}
			}

		}
		return bankAccountDetails;
	}

	private AddressDetails prepareAddress(List<AppDynamicFieldsDTO> appDynamicFieldsDTOList,
			AddressDetails addressDetails) {
		for (AppDynamicFieldsDTO appDynamicFieldsDTO : appDynamicFieldsDTOList) {
			if (appDynamicFieldsDTO != null) {
				if (appDynamicFieldsDTO.getFieldId().equals("houseNumberOrStreetName")) {
					addressDetails.setHouseNumberOrStreetName(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("area")) {
					addressDetails.setArea(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("city")) {
					addressDetails.setCity(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("region")) {
					addressDetails.setRegion(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("zipCode")) {
					try {
						Integer zipCode = appDynamicFieldsDTO.getResponse() != null
								&& !appDynamicFieldsDTO.getResponse().isEmpty()
										? Integer.valueOf(appDynamicFieldsDTO.getResponse())
										: null;
						addressDetails.setZipCode(zipCode);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating zipcode ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("addressType")) {
					try {
						AddressType addressType = appDynamicFieldsDTO.getResponse() != null
								&& !appDynamicFieldsDTO.getResponse().isEmpty()
										? AddressType.valueOf(appDynamicFieldsDTO.getResponse())
										: null;
						addressDetails.setAddressType(addressType);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating addressType ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				}

			}

		}
		return addressDetails;
	}

	@Transactional
	private User persistUser(User retailer, User nominees, Set<AddressDetails> userAddressDetailsSet,
			Set<BankAccountDetails> userBankAccountDetailsSet, Set<LiabilitiesDetails> liabilitiesDetails,
			Boolean isNew, UserRelationships nomineeRelationship, Set<AddressDetails> nomineeAddressDetailsSet) {
		retailer.setUserType(UserType.RETAILERS.name());
		retailer.setAddressDetails(userAddressDetailsSet);
		retailer.setBankAccountDetails(userBankAccountDetailsSet);
		// retailer.setBusinessDetails(businessDetailsSet);
		retailer.setLiabilitiesDetails(liabilitiesDetails);
		retailer = userRepository.save(retailer);
		if (nominees != null) {
			nominees.setUserType(UserType.NOMINEES.name());
			nominees.setAddressDetails(nomineeAddressDetailsSet);
			nominees = userRepository.save(nominees);
			persistUserRelationship(retailer, nominees, UserType.NOMINEES, isNew, nomineeRelationship);
		}
		return retailer;
	}

	private void persistUserRelationship(User retailer, User nominees, UserType nominees2, Boolean isNew,
			UserRelationships nomineeRelationship) {
		if (isNew)
			nomineeRelationship = new UserRelationships();
		nomineeRelationship.setMsisdn(retailer.getMsisdn());
		nomineeRelationship.setRelative(nominees);
		nomineeRelationship.setRelationship(Relationship.NOMINEE);
		userRelationshipsRepository.save(nomineeRelationship);
	}

	private BusinessDetails prepareBusinessInformation(List<AppDynamicFieldsDTO> appDynamicFieldsDTOList,
			BusinessDetails businessDetails) {
		if (appDynamicFieldsDTOList != null && !appDynamicFieldsDTOList.isEmpty()) {
			for (AppDynamicFieldsDTO appDynamicFieldsDTO : appDynamicFieldsDTOList) {
				if (appDynamicFieldsDTO.getFieldId().equals("businessPhone")) {
					businessDetails.setBusinessPhone(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("businessName")) {
					businessDetails.setBusinessName(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("directorOrPartnerName")) {
					businessDetails.setDirectorOrPartnerName(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("facilityDetails")) {
					businessDetails.setFacilityDetails(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("facilityType")) {
					businessDetails.setFacilityType(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("fixedAssetPurchase")) {
					businessDetails.setFixedAssetPurchase(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("fixedAssetName")) {
					businessDetails.setFixedAssetName(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("price")) {
					try {
						double price = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Double.valueOf(appDynamicFieldsDTO.getResponse())
								: 0.0;
						businessDetails.setPrice(price);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating price ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("origin")) {
					businessDetails.setOrigin(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("proposedCollateral")) {
					businessDetails.setProposedCollateral(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("businessType")) {
					businessDetails.setBusinessType(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("sector")) {
					businessDetails.setSector(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("detailOfBusness")) {
					businessDetails.setDetailOfBusness(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("initialCapital")) {
					try {
						double initialCapital = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Double.valueOf(appDynamicFieldsDTO.getResponse())
								: 0.0;
						businessDetails.setInitialCapital(initialCapital);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating initialCapital ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("fundSource")) {
					businessDetails.setFundSource(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("vatRegistrationNumber")) {
					businessDetails.setVatRegistrationNumber(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("businessStartDate")) {
					businessDetails.setBusinessStartDate(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("businessTin")) {
					businessDetails.setBusinessTin(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("annualSales")) {
					try {
						double annualSales = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Double.valueOf(appDynamicFieldsDTO.getResponse())
								: 0.0;
						businessDetails.setAnnualSales(annualSales);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating annualSales ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("annualGrossProfit")) {
					try {
						double annualGrossProfit = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Double.valueOf(appDynamicFieldsDTO.getResponse())
								: 0.0;
						businessDetails.setAnnualGrossProfit(annualGrossProfit);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating annualGrossProfit ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("annualExpenses")) {
					try {
						double annualExpenses = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Double.valueOf(appDynamicFieldsDTO.getResponse())
								: 0.0;
						businessDetails.setAnnualExpenses(annualExpenses);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating annualExpenses ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("valueOfFixedAssets")) {
					try {
						double valueOfFixedAssets = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Double.valueOf(appDynamicFieldsDTO.getResponse())
								: 0.0;
						businessDetails.setValueOfFixedAssets(valueOfFixedAssets);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating valueOfFixedAssets ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("numberOfEmployees")) {
					try {
						int numberOfEmployees = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Integer.valueOf(appDynamicFieldsDTO.getResponse())
								: 0;
						businessDetails.setNumberOfEmployees(numberOfEmployees);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating numberOfEmployees ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("stockValue")) {
					try {
						double stockValue = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Double.valueOf(appDynamicFieldsDTO.getResponse())
								: 0.0;
						businessDetails.setStockValue(stockValue);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating stockValue ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("deposits")) {
					try {
						double deposits = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Double.valueOf(appDynamicFieldsDTO.getResponse())
								: 0.0;
						businessDetails.setDeposits(deposits);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating deposits ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("withdrawls")) {
					try {
						double withdrawls = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Double.valueOf(appDynamicFieldsDTO.getResponse())
								: 0.0;
						businessDetails.setWithdrawls(withdrawls);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating withdrawls ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("initialDeposit")) {
					try {
						double initialDeposit = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Double.valueOf(appDynamicFieldsDTO.getResponse())
								: 0.0;
						businessDetails.setInitialDeposit(initialDeposit);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating initialDeposit ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				}
			}
		}
		return businessDetails;
	}

	private LicenseDetails prepareLicenseDetails(List<AppDynamicFieldsDTO> appDynamicFieldsDTOList,
			LicenseDetails licenseDetails) {
		if (appDynamicFieldsDTOList != null && !appDynamicFieldsDTOList.isEmpty()) {
			licenseDetails = new LicenseDetails();
			for (AppDynamicFieldsDTO appDynamicFieldsDTO : appDynamicFieldsDTOList) {
				if (appDynamicFieldsDTO.getFieldId().equals("licenseNumber")) {
					licenseDetails.setLicenseNumber(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("licenseExpiryDate")) {
					licenseDetails.setLicenseExpiryDate(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("licenseIssuingAuthority")) {
					licenseDetails.setLicenseIssuingAuthority(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("licenseType")) {
					try {
						LicenseType licenseType = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? LicenseType.valueOf(appDynamicFieldsDTO.getResponse())
								: null;
						licenseDetails.setLicenseType(licenseType);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating licenseType ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				}
			}
		}
		return licenseDetails;

	}

	private void prepareLiabilitiesDetails(AppDynamicFieldsDTO appDynamicFieldsDTO,
			Set<LiabilitiesDetails> liabilitiesDetailsSet) {
		if (appDynamicFieldsDTO != null) {
			LiabilitiesDetails liabilitiesDetails = new LiabilitiesDetails();
			if (appDynamicFieldsDTO.getFieldId().equals("loanAmount")) {
				try {
					double loanAmount = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
							? Double.valueOf(appDynamicFieldsDTO.getResponse())
							: 0.0;
					liabilitiesDetails.setLoanAmount(loanAmount);
				} catch (Exception e) {
					LOGGER.error("Exception while evaluating loanAmount ={}, error={}",
							appDynamicFieldsDTO.getResponse(), e.getMessage());
				}
			} else if (appDynamicFieldsDTO.getFieldId().equals("bankOrNbfiName")) {
				liabilitiesDetails.setBankOrNbfiName(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("liabilityFrom")) {
				liabilitiesDetails.setLiabilityFrom(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("typeOfLiablity")) {
				try {
					LiabilityType typeOfLiablity = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
							? LiabilityType.valueOf(appDynamicFieldsDTO.getResponse())
							: null;
					liabilitiesDetails.setTypeOfLiablity(typeOfLiablity);
				} catch (Exception e) {
					LOGGER.error("Exception while evaluating liabilityType ={}, error={}",
							appDynamicFieldsDTO.getResponse(), e.getMessage());
				}
			}
			liabilitiesDetailsSet.add(liabilitiesDetails);
		}

	}

	private AddressDetails prepareAddress(AppDynamicFieldsDTO appDynamicFieldsDTO,
			Set<AddressDetails> addressDetailsSet, AddressDetails addressDetails) {
		if (appDynamicFieldsDTO != null && addressDetailsSet != null && !addressDetailsSet.isEmpty()) {
			if (appDynamicFieldsDTO.getFieldId().equals("houseNumberOrStreetName")) {
				addressDetails.setHouseNumberOrStreetName(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("area")) {
				addressDetails.setArea(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("city")) {
				addressDetails.setCity(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("region")) {
				addressDetails.setRegion(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("zipCode")) {
				try {
					Integer zipCode = appDynamicFieldsDTO.getResponse() != null
							&& !appDynamicFieldsDTO.getResponse().isEmpty()
									? Integer.valueOf(appDynamicFieldsDTO.getResponse())
									: null;
					addressDetails.setZipCode(zipCode);
				} catch (Exception e) {
					LOGGER.error("Exception while evaluating zipcode ={}, error={}", appDynamicFieldsDTO.getResponse(),
							e.getMessage());
				}
			} else if (appDynamicFieldsDTO.getFieldId().equals("addressType")) {
				try {
					AddressType addressType = appDynamicFieldsDTO.getResponse() != null
							&& !appDynamicFieldsDTO.getResponse().isEmpty()
									? AddressType.valueOf(appDynamicFieldsDTO.getResponse())
									: null;
					addressDetails.setAddressType(addressType);
				} catch (Exception e) {
					LOGGER.error("Exception while evaluating addressType ={}, error={}",
							appDynamicFieldsDTO.getResponse(), e.getMessage());
				}
			}

		}
		return addressDetails;
	}

	private void prepareAccountInformations(AppDynamicFieldsDTO appDynamicFieldsDTO,
			Set<BankAccountDetails> businessBankAccountDetailsSet) {
		if (appDynamicFieldsDTO != null) {
			BankAccountDetails bankAccountDetails = new BankAccountDetails();
			if (appDynamicFieldsDTO.getFieldId().equals("accountTitle")) {
				bankAccountDetails.setAccountTitle(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("typeOfConcern")) {
				bankAccountDetails.setTypeOfConcern(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("bankName")) {
				bankAccountDetails.setBankName(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("accountNumber")) {
				try {
					Long accountNumber = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
							? Long.valueOf(appDynamicFieldsDTO.getResponse())
							: null;
					bankAccountDetails.setAccountNumber(accountNumber);
				} catch (Exception e) {
					LOGGER.error("Exception while evaluating accountNumber ={}, error={}",
							appDynamicFieldsDTO.getResponse(), e.getMessage());
				}
			} else if (appDynamicFieldsDTO.getFieldId().equals("branch")) {
				bankAccountDetails.setBranch(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("modeOfOperation")) {
				bankAccountDetails.setModeOfOperation(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("currency")) {
				try {
					Currency currency = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
							? Currency.valueOf(appDynamicFieldsDTO.getResponse())
							: null;
					bankAccountDetails.setCurrency(currency);
				} catch (Exception e) {
					LOGGER.error("Exception while evaluating currency ={}, error={}", appDynamicFieldsDTO.getResponse(),
							e.getMessage());
				}
			} else if (appDynamicFieldsDTO.getFieldId().equals("bankAccountType")) {
				BankAccountType bankAccountType = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
						? BankAccountType.valueOf(appDynamicFieldsDTO.getResponse())
						: null;
				bankAccountDetails.setBankAccountType(bankAccountType);
			}
			businessBankAccountDetailsSet.add(bankAccountDetails);
		}
	}

	private boolean neitherBlankNorNull(String response) {
		return response != null && !response.isEmpty();
	}

	private User prepareProfileInformation(AppDynamicFieldsDTO appDynamicFieldsDTO, User user) {
		if (appDynamicFieldsDTO != null && user != null) {
			if (appDynamicFieldsDTO.getFieldId().equals("firstName")) {
				user.setFirstName(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("lastName")) {
				user.setLastName(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("middleName")) {
				user.setMiddleName(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("dob")) {
				user.setDob(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("pob")) {
				user.setPob(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("fathersName")) {
				user.setFathersName(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("mothersName")) {
				user.setMothersName(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("maritalStatus")) {
				try {
					MaritalStatuses maritalStatuses = appDynamicFieldsDTO.getResponse() != null
							? MaritalStatuses.valueOf(appDynamicFieldsDTO.getResponse())
							: null;
					user.setMaritalStatus(maritalStatuses);
				} catch (Exception e) {
					LOGGER.error("Exception while evaluating marital statuses ={}, error={}",
							appDynamicFieldsDTO.getResponse(), e.getMessage());
				}
			} else if (appDynamicFieldsDTO.getFieldId().equals("spouseName")) {
				user.setSpouseName(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("numberOfDependents")) {
				Integer nod = appDynamicFieldsDTO.getResponse() != null && !appDynamicFieldsDTO.getResponse().isEmpty()
						? Integer.valueOf(appDynamicFieldsDTO.getResponse())
						: null;
				user.setNumberOfDependents(nod);
			} else if (appDynamicFieldsDTO.getFieldId().equals("alternateMobileNumber")) {
				user.setAlternateMobileNumber(appDynamicFieldsDTO.getResponse());
			}
			// userDto.setRetailerPhoto("");
			else if (appDynamicFieldsDTO.getFieldId().equals("birthRegistrationNumber")) {
				user.setBirthRegistrationNumber(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("drivingLicenseNumber")) {
				user.setDrivingLicenseNumber(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("email")) {
				user.setEmail(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("gender")) {
				try {
					Gender gender = appDynamicFieldsDTO.getResponse() != null
							&& !appDynamicFieldsDTO.getResponse().isEmpty()
									? Gender.valueOf(appDynamicFieldsDTO.getResponse().toUpperCase())
									: null;
					user.setGender(gender);
				} catch (Exception e) {
					LOGGER.error("Exception while evaluating gender ={}, error={}", appDynamicFieldsDTO.getResponse(),
							e.getMessage());
				}
			} else if (appDynamicFieldsDTO.getFieldId().equals("msisdn")) {
				user.setMsisdn(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("sisterConcernedOrAllied")) {
				user.setSisterConcernedOrAllied(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("taxIdentificationNumber")) {
				user.setTaxIdentificationNumber(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("residentialStatus")) {
				try {
					ResidentStatus residentialStatus = appDynamicFieldsDTO.getResponse() != null
							&& !appDynamicFieldsDTO.getResponse().isEmpty()
									? ResidentStatus.valueOf(appDynamicFieldsDTO.getResponse())
									: null;
					user.setResidentialStatus(residentialStatus);
				} catch (Exception e) {
					LOGGER.error("Exception while evaluating residential status ={}, error={}",
							appDynamicFieldsDTO.getResponse(), e.getMessage());
				}
			} else if (appDynamicFieldsDTO.getFieldId().equals("passportNumber")) {
				user.setPassportNumber(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("passportExpiryDate")) {
				user.setPassportExpiryDate(appDynamicFieldsDTO.getResponse());
			} else if (appDynamicFieldsDTO.getFieldId().equals("nationality")) {
				try {
					Nationality nationality = appDynamicFieldsDTO.getResponse() != null
							&& !appDynamicFieldsDTO.getResponse().isEmpty()
									? Nationality.valueOf(appDynamicFieldsDTO.getResponse())
									: null;
					user.setNationality(nationality);
				} catch (Exception e) {
					LOGGER.error("Exception while evaluating nationality ={}, error={}",
							appDynamicFieldsDTO.getResponse(), e.getMessage());
				}
			} else if (appDynamicFieldsDTO.getFieldId().equals("nationalIdNumber")) {
				user.setNationalIdNumber(appDynamicFieldsDTO.getResponse());
			}

		}
		return user;
	}

}
