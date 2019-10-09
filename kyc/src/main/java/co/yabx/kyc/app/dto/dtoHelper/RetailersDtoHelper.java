package co.yabx.kyc.app.dto.dtoHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import co.yabx.kyc.app.dto.ResponseDTO;
import co.yabx.kyc.app.dto.RetailersDTO;
import co.yabx.kyc.app.entities.AppDynamicFields;
import co.yabx.kyc.app.enums.AddressType;
import co.yabx.kyc.app.enums.BankAccountIdentifier;
import co.yabx.kyc.app.enums.BankAccountType;
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
import co.yabx.kyc.app.fullKyc.entity.User;

public class RetailersDtoHelper implements Serializable {

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
		licenseDetailsDTO.setLicenseExpiryDate(new Date().getTime());
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

	public static List<AppDynamicFields> getRetailersDetails(User retailers,
			Iterable<AppDynamicFields> appDynamicFields) {
		List<AppDynamicFields> fields = new ArrayList<AppDynamicFields>();
		Iterator<AppDynamicFields> iterator = appDynamicFields.iterator();
		while (iterator.hasNext()) {
			AppDynamicFields dynamicFields = iterator.next();
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
				if (dynamicFields.getSavedData() == null) {
					List<String> options = new ArrayList<String>();
					MaritalStatuses[] maritalStatuses = MaritalStatuses.values();
					for (MaritalStatuses statuses : maritalStatuses) {
						options.add(statuses.name());
					}
					dynamicFields.setOptions(options);
				}
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
				if (dynamicFields.getSavedData() == null) {
					List<String> options = new ArrayList<String>();
					Gender[] genders = Gender.values();
					for (Gender statuses : genders) {
						options.add(statuses.name());
					}
					dynamicFields.setOptions(options);
				}
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
				if (dynamicFields.getSavedData() == null) {
					List<String> options = new ArrayList<String>();
					ResidentStatus[] residentStatuses = ResidentStatus.values();
					for (ResidentStatus statuses : residentStatuses) {
						options.add(statuses.name());
					}
					dynamicFields.setOptions(options);
				}
			} else if (dynamicFields.getFieldId().equals("passportNumber")) {
				dynamicFields.setSavedData(retailers.getPassportNumber());
			} else if (dynamicFields.getFieldId().equals("passportExpiryDate")) {
				dynamicFields.setSavedData(retailers.getPassportExpiryDate());
			} else if (dynamicFields.getFieldId().equals("nationality")) {
				dynamicFields
						.setSavedData(retailers.getNationality() != null ? retailers.getNationality().name() : null);
				if (dynamicFields.getSavedData() == null) {
					List<String> options = new ArrayList<String>();
					Nationality[] nationalities = Nationality.values();
					for (Nationality statuses : nationalities) {
						options.add(statuses.name());
					}
					dynamicFields.setOptions(options);
				}
			} else if (dynamicFields.getFieldId().equals("nationalIdNumber")) {
				dynamicFields.setSavedData(retailers.getNationalIdNumber());
			}
			fields.add(dynamicFields);
		}
		return fields;
	}

}
