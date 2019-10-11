package co.yabx.kyc.app.dto.dtoHelper;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.yabx.kyc.app.dto.AppDynamicFieldsDTO;
import co.yabx.kyc.app.dto.AppPagesDTO;
import co.yabx.kyc.app.dto.AppPagesSectionGroupsDTO;
import co.yabx.kyc.app.dto.AppPagesSectionsDTO;
import co.yabx.kyc.app.dto.ResponseDTO;
import co.yabx.kyc.app.dto.RetailersDTO;
import co.yabx.kyc.app.entities.AppDynamicFields;
import co.yabx.kyc.app.entities.AppPages;
import co.yabx.kyc.app.entities.AppPagesSectionGroups;
import co.yabx.kyc.app.entities.AppPagesSections;
import co.yabx.kyc.app.entities.SectionGroupRelationship;
import co.yabx.kyc.app.enums.AddressType;
import co.yabx.kyc.app.enums.BankAccountIdentifier;
import co.yabx.kyc.app.enums.BankAccountType;
import co.yabx.kyc.app.enums.Currency;
import co.yabx.kyc.app.enums.DsrProfileStatus;
import co.yabx.kyc.app.enums.Gender;
import co.yabx.kyc.app.enums.KycStatus;
import co.yabx.kyc.app.enums.LiabilityType;
import co.yabx.kyc.app.enums.LicenseType;
import co.yabx.kyc.app.enums.MaritalStatuses;
import co.yabx.kyc.app.enums.Nationality;
import co.yabx.kyc.app.enums.Relationship;
import co.yabx.kyc.app.enums.ResidentStatus;
import co.yabx.kyc.app.fullKyc.dto.AddressDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.BankAccountDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.BusinessDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.IncomeDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.IntroducerDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.LiabilitiesDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.LicenseDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.UserDTO;
import co.yabx.kyc.app.fullKyc.dto.WorkEducationDetailsDTO;
import co.yabx.kyc.app.fullKyc.entity.AddressDetails;
import co.yabx.kyc.app.fullKyc.entity.BankAccountDetails;
import co.yabx.kyc.app.fullKyc.entity.BusinessDetails;
import co.yabx.kyc.app.fullKyc.entity.LiabilitiesDetails;
import co.yabx.kyc.app.fullKyc.entity.LicenseDetails;
import co.yabx.kyc.app.fullKyc.entity.Retailers;
import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.repositories.AppPagesRepository;
import co.yabx.kyc.app.repositories.SectionGroupRelationshipRepository;
import co.yabx.kyc.app.util.DTOConverter;
import co.yabx.kyc.app.util.SpringUtil;

public class RetailersDtoHelper implements Serializable {

	@Autowired
	private AppPagesRepository appPagesRepository;

	public static ResponseDTO getResponseDTO(String msisdn, String status, String statusCode,
			DsrProfileStatus dsrProfileStatus) {
		ResponseDTO loginDTO = new ResponseDTO();
		loginDTO.setMessage(status);
		loginDTO.setStatusCode(statusCode);
		loginDTO.setDsrProfileStatus(dsrProfileStatus);
		return loginDTO;
	}

	public static ResponseDTO getSummary(List<User> retailers2) {
		ResponseDTO loginDTO = getResponseDTO(null, "SUCCESS", "200", null);
		List<RetailersDTO> retailersList = new ArrayList<RetailersDTO>();
		if (retailers2 != null && !retailers2.isEmpty()) {
			for (User retailers : retailers2) {
				RetailersDTO retailersDTO = new RetailersDTO();
				retailersDTO.setRetailerId(retailers.getId());
				retailersDTO.setRetailerName(retailers.getFirstName());
				retailersDTO.setKycStatus(KycStatus.NEW);
				retailersDTO.setComments("n/a");
				retailersList.add(retailersDTO);
			}
			loginDTO.setRetailers(retailersList);
			loginDTO.setTotalCount(retailersList.size());
			return loginDTO;
		} else {
			RetailersDTO retailersDTO = new RetailersDTO();
			retailersDTO.setRetailerId(123l);
			retailersDTO.setRetailerName("abc");
			retailersDTO.setKycStatus(KycStatus.NEW);
			retailersDTO.setComments("n/a");
			retailersList.add(retailersDTO);
			RetailersDTO retailersDTO2 = new RetailersDTO();
			retailersDTO2.setRetailerId(2343l);
			retailersDTO2.setRetailerName("adocbc");
			retailersDTO2.setKycStatus(KycStatus.APPROVED);
			retailersDTO2.setComments("all ok");
			retailersList.add(retailersDTO2);
			loginDTO.setRetailers(retailersList);
			loginDTO.setTotalCount(2);
			return loginDTO;
		}
	}

	public static ResponseDTO getCompletRetailerInfo(String dsrMsisdn, Long retailerId) {
		ResponseDTO loginDTO = getResponseDTO(null, "SUCCESS", "200", null);
		UserDTO userDto = new UserDTO();
		userDto.setName("asad");
		userDto.setDob("09/26/2000");
		userDto.setPob("Gurugram");
		userDto.setFathersName("ABC");
		userDto.setMothersName("PQR");
		userDto.setMaritalStatus(MaritalStatuses.SINGLE);
		userDto.setSpouseName("LMN");
		userDto.setNumberOfDependents(0);
		userDto.setRetailerPhoto("");
		userDto.setAlternateMobileNumber("9876543210");
		userDto.setBirthRegistrationNumber("AP987GHM2562");
		userDto.setDrivingLicenseNumber("90KL9772L");
		userDto.setEmail("abc@abc.com");
		userDto.setGender(Gender.MALE);
		userDto.setId(19876l);
		userDto.setMsisdn("9789876543");
		userDto.setSisterConcernedOrAllied("yes");
		userDto.setTaxIdentificationNumber("ABC876L87");
		userDto.setResidentialStatus(ResidentStatus.CITIZEN);
		userDto.setPassportNumber("PB8765M");
		userDto.setPassportExpiryDate("09/26/2023");
		userDto.setNationality(Nationality.Bangladeshi);
		userDto.setNationalIdNumber("ABCP897L0976");

		// Bank account details
		List<BankAccountDetailsDTO> accountDetails = new ArrayList<BankAccountDetailsDTO>();
		BankAccountDetailsDTO bankAccountDetails = new BankAccountDetailsDTO();
		bankAccountDetails.setAccountNumber(12345l);
		bankAccountDetails.setBankAccountType(BankAccountType.SHAHDHIN);
		bankAccountDetails.setBankName("ICICI BAnk");
		bankAccountDetails.setBranch("Bengaluru");
		bankAccountDetails.setBankAccountIdentifier(BankAccountIdentifier.PRIMARY);
		accountDetails.add(bankAccountDetails);
		userDto.setBankAccountDetails(accountDetails);
		// Income Details
		List<IncomeDetailsDTO> incomeDetails = new ArrayList<IncomeDetailsDTO>();
		IncomeDetailsDTO incomeDetailsDTO = new IncomeDetailsDTO();
		incomeDetailsDTO.setMonthlyExpenditure(22000.00);
		incomeDetailsDTO.setMonthlyIncome(12345.000);
		incomeDetailsDTO.setMonthlySurplus(543.00);
		incomeDetailsDTO.setTotalAssets(654000.00);
		incomeDetailsDTO.setTotalLiabilities(7654.00);
		incomeDetailsDTO.setMonthlyIncomeFromOtherSource(5446.000);
		incomeDetails.add(incomeDetailsDTO);
		userDto.setIncomeDetails(incomeDetails);
		// Introducer Details
		List<IntroducerDetailsDTO> introducerDetails = new ArrayList<IntroducerDetailsDTO>();
		IntroducerDetailsDTO introducerDetailsDTO = new IntroducerDetailsDTO();
		introducerDetailsDTO.setAccountNumber(123467l);
		introducerDetailsDTO.setName("abc");
		introducerDetailsDTO.setRelationship(Relationship.FRIEND);
		introducerDetailsDTO.setSignatureVerified(true);
		introducerDetails.add(introducerDetailsDTO);
		userDto.setIntroducerDetails(introducerDetails);
		// Work education details
		List<WorkEducationDetailsDTO> workEducationDetails = new ArrayList<WorkEducationDetailsDTO>();
		WorkEducationDetailsDTO workEducationDetailsDTO = new WorkEducationDetailsDTO();
		workEducationDetailsDTO.setDesignation("Lead executive");
		workEducationDetailsDTO.setEducationalQualification("SSC");
		workEducationDetailsDTO.setEmployer("ABC pvt ltd");
		workEducationDetailsDTO.setOccupation("Salesman");
		workEducationDetailsDTO.setExperience(5);
		workEducationDetails.add(workEducationDetailsDTO);
		userDto.setWorkEducationDetails(workEducationDetails);
		// Liabilities details
		List<LiabilitiesDetailsDTO> liabilitiesDetails = new ArrayList<LiabilitiesDetailsDTO>();
		LiabilitiesDetailsDTO liabilitiesDetailsDTO = new LiabilitiesDetailsDTO();
		liabilitiesDetailsDTO.setNameOfTheOrganization("ABC bank");
		liabilitiesDetailsDTO.setLiabilityFrom("NBFI");
		liabilitiesDetailsDTO.setTypeOfLiablity(LiabilityType.PERSONAL);
		liabilitiesDetailsDTO.setLoanAmount(87654);
		liabilitiesDetails.add(liabilitiesDetailsDTO);

		LiabilitiesDetailsDTO liabilitiesDetailsDTO2 = new LiabilitiesDetailsDTO();
		liabilitiesDetailsDTO2.setNameOfTheOrganization("ICICI bank");
		liabilitiesDetailsDTO2.setLiabilityFrom("BANK");
		liabilitiesDetailsDTO2.setTypeOfLiablity(LiabilityType.PERSONAL);
		liabilitiesDetailsDTO2.setLoanAmount(8349.0);
		liabilitiesDetails.add(liabilitiesDetailsDTO2);
		userDto.setLiabilitiesDetails(liabilitiesDetails);
		// Business Details
		List<BusinessDetailsDTO> businessDetails = new ArrayList<BusinessDetailsDTO>();
		BusinessDetailsDTO businessDetailsDTO = new BusinessDetailsDTO();
		businessDetailsDTO.setAnnualExpenses(98765.00);
		businessDetailsDTO.setAnnualGrossProfit(9800.0);
		businessDetailsDTO.setAnnualSales(567.0);
		List<BankAccountDetailsDTO> businessAccountDetails = new ArrayList<BankAccountDetailsDTO>();
		BankAccountDetailsDTO businessBankAccountDetails = new BankAccountDetailsDTO();
		businessBankAccountDetails.setAccountNumber(12345l);
		businessBankAccountDetails.setBankAccountType(BankAccountType.SHAHDHIN);
		businessBankAccountDetails.setBankName("ICICI BAnk");
		businessBankAccountDetails.setBranch("Bengaluru");
		businessBankAccountDetails.setBankAccountIdentifier(BankAccountIdentifier.PRIMARY);
		businessAccountDetails.add(businessBankAccountDetails);
		businessDetailsDTO.setBankAccountDetails(businessAccountDetails);
		businessDetailsDTO.setBusinessName("ABC Handset finance ..");
		businessDetailsDTO.setBusinessPhone("9876543210");
		businessDetailsDTO.setBusinessStartDate("09/26/2019");
		businessDetailsDTO.setBusinessTin("P56689gf5");
		businessDetailsDTO.setWithdrawls(34556.00);
		businessDetailsDTO.setVatRegistrationNumber("9876345PLM");
		businessDetailsDTO.setValueOfFixedAssets(8776532.00);
		businessDetailsDTO.setStockValue(13427.00);
		businessDetailsDTO.setSector("Micro Lending");
		businessDetailsDTO.setProposedCollateral("abc");
		businessDetailsDTO.setPrice(9875.00);
		businessDetailsDTO.setOrigin("abc");
		businessDetailsDTO.setNumberOfEmployees(113);
		businessDetailsDTO.setMonthlyTurnOver(345567.00);
		businessDetailsDTO.setBusinessType("pqr");
		businessDetailsDTO.setDeposits(76543219.00);
		businessDetailsDTO.setDetailOfBusness("micro loan finance");
		businessDetailsDTO.setDirectorOrPartnerName("Asad Ali");
		businessDetailsDTO.setFacilityDetails("abc");
		businessDetailsDTO.setFacilityType("pqr");
		businessDetailsDTO.setFixedAssetName("Stock etc");
		businessDetailsDTO.setFixedAssetPurchase("abc");
		businessDetailsDTO.setFundSource("Investors");
		businessDetailsDTO.setInitialCapital(123876.00);
		businessDetailsDTO.setInitialDeposit(76543.00);
		// Business License Details
		LicenseDetailsDTO licenseDetailsDTO = new LicenseDetailsDTO();
		// licenseDetailsDTO.setLicenseExpiryDate(new Date().getTime());
		licenseDetailsDTO.setLicenseIssuingAuthority("Govt.");
		licenseDetailsDTO.setLicenseNumber("AB768Mn01");
		licenseDetailsDTO.setLicenseType(LicenseType.TRADE);
		businessDetailsDTO.setLicenseDetails(licenseDetailsDTO);
		// Business Address Details
		List<AddressDetailsDTO> addressDetails = new ArrayList<AddressDetailsDTO>();
		AddressDetailsDTO addressDetailsDTO = new AddressDetailsDTO();
		addressDetailsDTO.setHouseNumberOrStreetName("ABC Building");
		addressDetailsDTO.setAddressType(AddressType.BUSINESS_OFFICE_ADDRESS);
		addressDetailsDTO.setArea("Dhaka");
		addressDetailsDTO.setCity("Dhaka");
		addressDetailsDTO.setRegion("North");
		addressDetailsDTO.setZipCode(189267);
		addressDetails.add(addressDetailsDTO);
		businessDetailsDTO.setAddressDetails(addressDetails);
		businessDetails.add(businessDetailsDTO);
		userDto.setBusinessDetails(businessDetails);
		// User Address details
		List<AddressDetailsDTO> addressDetailsList = new ArrayList<AddressDetailsDTO>();
		AddressDetailsDTO addressDetailsDTOs = new AddressDetailsDTO();
		AddressDetailsDTO addressDetailsDTO1 = new AddressDetailsDTO();
		addressDetailsDTO1.setHouseNumberOrStreetName("ABC Building");
		addressDetailsDTO1.setAddressType(AddressType.PERMANNET);
		addressDetailsDTO1.setArea("Dhaka");
		addressDetailsDTO1.setCity("Dhaka");
		addressDetailsDTO1.setRegion("North");
		addressDetailsDTO1.setZipCode(189267);
		addressDetailsList.add(addressDetailsDTO);
		addressDetailsDTOs.setHouseNumberOrStreetName("PQR Building");
		addressDetailsDTOs.setAddressType(AddressType.PRESENT);
		addressDetailsDTOs.setArea("Dhaka");
		addressDetailsDTOs.setCity("Dhaka");
		addressDetailsDTOs.setRegion("North");
		addressDetailsDTOs.setZipCode(189262);
		addressDetailsList.add(addressDetailsDTOs);
		userDto.setAddressDetails(addressDetailsList);
		// User Bank Account details
		userDto.setBankAccountDetails(businessAccountDetails);
		// Nominees details
		List<UserDTO> nominees = new ArrayList<UserDTO>();
		UserDTO nominee = new UserDTO();
		nominee.setName("apm");
		nominee.setRetailerId("2");
		nominee.setDob("23/07/1988");
		nominee.setAge(31);
		nominee.setGender(Gender.FEMALE);
		nominee.setFathersName("mls");
		nominee.setMothersName("pdq");
		nominee.setSpouseName("pkdls");
		nominee.setMsisdn("123456789");
		nominee.setWorkEducationDetails(workEducationDetails);
		nominee.setAddressDetails(addressDetailsList);
		nominees.add(nominee);
		userDto.setNomineesDetails(nominees);
		// loginDTO.setRetailerInfo(userDto);
		return loginDTO;
	}

	public static List<AppPagesDTO> getRetailersDetails(User retailers) {
		Iterable<AppPages> appPages = null;
		if (retailers == null)
			appPages = SpringUtil.bean(AppPagesRepository.class).findAll();
		else if ("retailers".equalsIgnoreCase(retailers.getUser_type())) {
			appPages = SpringUtil.bean(AppPagesRepository.class).findByPageName(retailers.getUser_type());
		}
		List<AppPagesDTO> appPagesDTOList = new ArrayList<AppPagesDTO>();
		if (appPages != null) {
			Iterator<AppPages> iterator = appPages.iterator();
			while (iterator.hasNext()) {
				AppPages pages = iterator.next();
				AppPagesDTO appPagesDTO = new AppPagesDTO();
				Set<AppPagesSections> appPagesSectionsSet = pages.getAppPagesSections();
				if (appPagesSectionsSet != null && !appPagesSectionsSet.isEmpty()) {
					List<AppPagesSectionsDTO> appPagesSectionSet = getSections(appPagesSectionsSet, retailers);
					appPagesDTO.setSections(
							appPagesSectionSet.stream().sorted(Comparator.comparing(AppPagesSectionsDTO::getSectionId))
									.collect(Collectors.toList()));
					appPagesDTO.setEnable(pages.isEnable());
					appPagesDTO.setPageId(pages.getPageId());
					//appPagesDTO.setPageName(pages.getPageName());
					appPagesDTO.setPageTitle(pages.getPageTitle());
				}
				appPagesDTOList.add(appPagesDTO);
			}
		}

		return appPagesDTOList;
	}

	private static List<AppPagesSectionsDTO> getSections(Set<AppPagesSections> appPagesSectionsSet, User retailers) {

		List<AppPagesSectionsDTO> appPagesSectionDTOSet = new ArrayList<AppPagesSectionsDTO>();
		for (AppPagesSections appPagesSections : appPagesSectionsSet) {
			AppPagesSectionsDTO appPagesSectionsDTO = new AppPagesSectionsDTO();
			List<SectionGroupRelationship> sectionGroupRelationships = SpringUtil
					.bean(SectionGroupRelationshipRepository.class).findBySectionId(appPagesSections.getSectionId());
			Set<AppPagesSectionGroups> appPagesSectionGroupsSet = sectionGroupRelationships.stream()
					.map(f -> f.getAppPagesSectionGroups()).collect(Collectors.toSet());
			if (appPagesSectionGroupsSet != null && !appPagesSectionGroupsSet.isEmpty()) {
				List<AppPagesSectionGroupsDTO> appPagesSectionGroupSet = getGroups(appPagesSectionGroupsSet, retailers);
				appPagesSectionsDTO.setGroups(appPagesSectionGroupSet.stream()
						.sorted(Comparator.comparing(AppPagesSectionGroupsDTO::getGroupId))
						.collect(Collectors.toList()));
				appPagesSectionsDTO.setEnable(appPagesSections.isEnable());
				appPagesSectionsDTO.setSectionId(appPagesSections.getSectionId());
				appPagesSectionsDTO.setSectionName(appPagesSections.getSectionName());
				appPagesSectionsDTO.setSectionTitle(appPagesSections.getSectionTitle());
			}
			appPagesSectionDTOSet.add(appPagesSectionsDTO);
		}
		return appPagesSectionDTOSet;

	}

	private static List<AppPagesSectionGroupsDTO> getGroups(Set<AppPagesSectionGroups> appPagesSectionGroupsSet,
			User retailers) {

		List<AppPagesSectionGroupsDTO> appPagesSectionGroupSet = new ArrayList<AppPagesSectionGroupsDTO>();
		for (AppPagesSectionGroups appPagesSectionGroups : appPagesSectionGroupsSet) {
			AppPagesSectionGroupsDTO appPagesSectionGroupsDTO = new AppPagesSectionGroupsDTO();
			Set<AppDynamicFields> appDynamicFieldsSet = appPagesSectionGroups.getAppDynamicFields();
			if (appDynamicFieldsSet != null && !appDynamicFieldsSet.isEmpty()) {
				List<AppDynamicFieldsDTO> fields = getFields(appDynamicFieldsSet, retailers);
				appPagesSectionGroupsDTO.setFields(fields.stream()
						.sorted(Comparator.comparing(AppDynamicFieldsDTO::getId)).collect(Collectors.toList()));
				appPagesSectionGroupsDTO.setEnable(appPagesSectionGroups.isEnable());
				appPagesSectionGroupsDTO.setGroupId(appPagesSectionGroups.getGroupId());
				appPagesSectionGroupsDTO.setGroupName(appPagesSectionGroups.getGroupName());
				appPagesSectionGroupsDTO.setGroupTitle(appPagesSectionGroups.getGroupTitle());
			}
			appPagesSectionGroupSet.add(appPagesSectionGroupsDTO);
		}
		return appPagesSectionGroupSet;

	}

	private static List<AppDynamicFieldsDTO> getFields(Set<AppDynamicFields> appDynamicFieldsSet, User retailers) {
		List<AppDynamicFieldsDTO> appDynamicFieldsDTOSet = new ArrayList<AppDynamicFieldsDTO>();
		for (AppDynamicFields dynamicFields : appDynamicFieldsSet) {
			if (dynamicFields.getGroups().getGroupId() == 1) {
				prepareProfileInformation(dynamicFields, retailers, appDynamicFieldsDTOSet);

			} else if (dynamicFields.getGroups().getGroupId() == 2) {
				prepareAddress(dynamicFields, retailers, appDynamicFieldsDTOSet);
			} else if (dynamicFields.getGroups().getGroupId() == 3) {
				prepareAccountInformations(dynamicFields, retailers, appDynamicFieldsDTOSet);
			} else if (dynamicFields.getGroups().getGroupId() == 4) {
				prepareLiabilitiesDetails(dynamicFields, retailers, appDynamicFieldsDTOSet);
			} else if (dynamicFields.getGroups().getGroupId() == 5) {
				prepareBusinessInformation(dynamicFields, retailers, appDynamicFieldsDTOSet);
			} else if (dynamicFields.getGroups().getGroupId() == 6) {
				prepareLicenseDetails(dynamicFields, retailers, appDynamicFieldsDTOSet);
			}

		}
		return appDynamicFieldsDTOSet;

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

	private static void prepareAccountInformations(AppDynamicFields dynamicFields, User retailers,
			List<AppDynamicFieldsDTO> appDynamicFieldsDTOSet) {
		if (retailers == null || retailers.getBankAccountDetails() == null
				|| retailers.getBankAccountDetails().isEmpty()) {
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
			Set<BankAccountDetails> bankAccountDetailsSet = retailers.getBankAccountDetails();
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

	private static void prepareAddress(AppDynamicFields dynamicFields, User retailers,
			List<AppDynamicFieldsDTO> appDynamicFieldsDTOSet) {
		if (retailers == null || retailers.getAddressDetails() == null || retailers.getAddressDetails().isEmpty()) {
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
			Set<AddressDetails> addressDetailsSet = retailers.getAddressDetails();
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

}
