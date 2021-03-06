package co.yabx.kyc.app.dto.dtoHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import co.yabx.kyc.app.dto.ResponseDTO;
import co.yabx.kyc.app.dto.RetailersDTO;
import co.yabx.kyc.app.enums.AddressType;
import co.yabx.kyc.app.enums.BankAccountIdentifier;
import co.yabx.kyc.app.enums.BankAccountType;
import co.yabx.kyc.app.enums.EducationalQualification;
import co.yabx.kyc.app.enums.Gender;
import co.yabx.kyc.app.enums.KycStatus;
import co.yabx.kyc.app.enums.LiabilityType;
import co.yabx.kyc.app.enums.LicenseType;
import co.yabx.kyc.app.enums.MaritalStatuses;
import co.yabx.kyc.app.enums.Nationality;
import co.yabx.kyc.app.enums.Relationship;
import co.yabx.kyc.app.enums.ResidentStatus;
import co.yabx.kyc.app.enums.UserStatus;
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
import co.yabx.kyc.app.miniKyc.entity.AccountStatuses;
import co.yabx.kyc.app.miniKyc.repository.AccountStatusesRepository;
import co.yabx.kyc.app.repositories.PagesRepository;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.util.SpringUtil;

public class RetailersDtoHelper implements Serializable {

	@Autowired
	private PagesRepository appPagesRepository;

	public static ResponseDTO getResponseDTO(String msisdn, String status, String statusCode,
			UserStatus dsrProfileStatus) {
		ResponseDTO loginDTO = new ResponseDTO();
		loginDTO.setMessage(status);
		loginDTO.setStatusCode(statusCode);
		loginDTO.setDsrProfileStatus(dsrProfileStatus);
		return loginDTO;
	}

	public static ResponseDTO getSummary(List<User> retailers2) {
		ResponseDTO responseDTO = getResponseDTO(null, "SUCCESS", "200", null);
		List<RetailersDTO> retailersList = new ArrayList<RetailersDTO>();
		if (retailers2 != null && !retailers2.isEmpty()) {
			for (User retailers : retailers2) {
				RetailersDTO retailersDTO = new RetailersDTO();
				retailersDTO.setRetailerId(retailers.getId());
				retailersDTO.setRetailerName(retailers.getFirstName());
				AccountStatuses accountStatuses = retailers.getMsisdn() != null
						? SpringUtil.bean(AccountStatusesRepository.class).findByMsisdn(retailers.getMsisdn())
						: null;
				if (accountStatuses != null)
					retailersDTO.setKycStatus(
							accountStatuses.getKycVerified() != null ? accountStatuses.getKycVerified().toString()
									: null);
				else
					retailersDTO.setKycStatus(KycStatus.NEW.name());
				retailersDTO.setComments("n/a");
				retailersDTO.setLastUpdatedTime(prepareUpdatedAt(accountStatuses));
				retailersDTO.setSubmitButton(prepreSubmitButton(retailersDTO.getKycStatus()));
				retailersList.add(retailersDTO);
			}
			responseDTO.setPageTitle("List of retailers");
			responseDTO.setSubPageTitles(prepareSubPagesTitle());
			responseDTO.setCardTitle("Customer ID");
			responseDTO.setSubCardTitles(prepareSubCardTitle());
			responseDTO.setRetailers(retailersList);
			responseDTO.setTotalCount(retailersList.size());
			return responseDTO;
		}
		return responseDTO;
	}

	private static Map<String, Long> prepareUpdatedAt(AccountStatuses accountStatuses) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("Updated At",
				accountStatuses != null && accountStatuses.getUpdatedAt() != null
						? accountStatuses.getUpdatedAt().getTime()
						: new Date().getTime());
		return map;
	}

	private static Map<String, String> prepareSubPagesTitle() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("title1", "E-KYC");
		map.put("title2", "DRAFT KYC");
		return map;
	}

	private static Map<String, String> prepareSubCardTitle() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("title1", "Name");
		map.put("title2", "KYC Status");
		return map;
	}

	private static String prepreSubmitButton(String kycStatus) {
		if (kycStatus.equals("IN PROGRESS")) {
			return "Continue";
		} else if (kycStatus.equals("APPROVED")) {
			return "View";
		} else if (kycStatus.equals("NEW")) {
			return "Start KYC";
		} else
			return "View";

	}

	public static ResponseDTO getCompletRetailerInfo(String dsrMsisdn, Long retailerId) {
		ResponseDTO loginDTO = getResponseDTO(null, "SUCCESS", "200", null);
		UserDTO userDto = new UserDTO();
		userDto.setName("asad");
		userDto.setDob("09/26/2000");
		userDto.setPob("Gurugram");
		userDto.setFathersName("ABC");
		userDto.setMothersName("PQR");
		userDto.setSpouseName("LMN");
		userDto.setNumberOfDependents(0);
		userDto.setRetailerPhoto("");
		userDto.setAlternateMobileNumber("9876543210");
		userDto.setBirthRegistrationNumber("AP987GHM2562");
		userDto.setDrivingLicenseNumber("90KL9772L");
		userDto.setEmail("abc@abc.com");
		userDto.setId(19876l);
		userDto.setMsisdn("9789876543");
		userDto.setSisterConcernedOrAllied("yes");
		userDto.setTaxIdentificationNumber("ABC876L87");
		userDto.setResidentialStatus(ResidentStatus.Resident);
		userDto.setPassportNumber("PB8765M");
		userDto.setPassportExpiryDate("09/26/2023");
		userDto.setNationality(Nationality.Bengali);
		userDto.setNationalIdNumber("ABCP897L0976");

		// Bank account details
		List<BankAccountDetailsDTO> accountDetails = new ArrayList<BankAccountDetailsDTO>();
		BankAccountDetailsDTO bankAccountDetails = new BankAccountDetailsDTO();
		bankAccountDetails.setAccountNumber(12345l);
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
		introducerDetailsDTO.setSignatureVerified(true);
		introducerDetails.add(introducerDetailsDTO);
		userDto.setIntroducerDetails(introducerDetails);
		// Work education details
		List<WorkEducationDetailsDTO> workEducationDetails = new ArrayList<WorkEducationDetailsDTO>();
		WorkEducationDetailsDTO workEducationDetailsDTO = new WorkEducationDetailsDTO();
		workEducationDetailsDTO.setDesignation("Lead executive");
		workEducationDetailsDTO.setEducationalQualification(EducationalQualification.HIGH_SCHOOL);
		workEducationDetailsDTO.setEmployer("ABC pvt ltd");
		workEducationDetailsDTO.setOccupation("Salesman");
		workEducationDetailsDTO.setExperience(5);
		workEducationDetails.add(workEducationDetailsDTO);
		userDto.setWorkEducationDetails(workEducationDetails);
		// Liabilities details
		List<LiabilitiesDetailsDTO> liabilitiesDetails = new ArrayList<LiabilitiesDetailsDTO>();
		LiabilitiesDetailsDTO liabilitiesDetailsDTO = new LiabilitiesDetailsDTO();
		liabilitiesDetailsDTO.setNameOfTheOrganization("ABC bank");
		liabilitiesDetailsDTO.setTypeOfLiablity(LiabilityType.PERSONAL);
		liabilitiesDetailsDTO.setLoanAmount(87654);
		liabilitiesDetails.add(liabilitiesDetailsDTO);

		LiabilitiesDetailsDTO liabilitiesDetailsDTO2 = new LiabilitiesDetailsDTO();
		liabilitiesDetailsDTO2.setNameOfTheOrganization("ICICI bank");
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

}
