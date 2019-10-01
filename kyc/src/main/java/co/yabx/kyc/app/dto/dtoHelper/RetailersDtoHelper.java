package co.yabx.kyc.app.dto.dtoHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import co.yabx.kyc.app.dto.KycDetailsDTO;
import co.yabx.kyc.app.dto.KycDocumentsDTO;
import co.yabx.kyc.app.dto.ResponseDTO;
import co.yabx.kyc.app.dto.RetailerRequestDTO;
import co.yabx.kyc.app.dto.RetailersDTO;
import co.yabx.kyc.app.enums.AddressType;
import co.yabx.kyc.app.enums.BankAccountIdentifier;
import co.yabx.kyc.app.enums.BankAccountType;
import co.yabx.kyc.app.enums.DsrProfileStatus;
import co.yabx.kyc.app.enums.KycStatus;
import co.yabx.kyc.app.enums.LicenseType;
import co.yabx.kyc.app.enums.MaritalStatuses;
import co.yabx.kyc.app.enums.Relationship;
import co.yabx.kyc.app.fullKyc.dto.AddressDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.BankAccountDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.BusinessDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.IncomeDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.IntroducerDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.LiabilitiesDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.LicenseDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.UserDTO;
import co.yabx.kyc.app.fullKyc.dto.WorkEducationDetailsDTO;
import co.yabx.kyc.app.fullKyc.entity.BankAccountDetails;
import co.yabx.kyc.app.miniKyc.entity.KycDetails;
import co.yabx.kyc.app.miniKyc.entity.KycDocuments;
import co.yabx.kyc.app.util.EncoderDecoderUtil;
import co.yabx.kyc.app.util.MaskUtil;

public class RetailersDtoHelper implements Serializable {

	public static ResponseDTO getLoginDTO(String msisdn, String status, String statusCode,
			DsrProfileStatus dsrProfileStatus) {
		ResponseDTO loginDTO = new ResponseDTO();
		loginDTO.setMessage(status);
		loginDTO.setStatusCode(statusCode);
		loginDTO.setDsrProfileStatus(dsrProfileStatus);
		return loginDTO;
	}

	public static ResponseDTO getSummary(RetailerRequestDTO retailerRequestDTO) {
		ResponseDTO loginDTO = getLoginDTO(null, "SUCCESS", "200", null);
		List<RetailersDTO> retailers = new ArrayList<RetailersDTO>();
		RetailersDTO retailersDTO = new RetailersDTO();
		retailersDTO.setMerchantId("123");
		retailersDTO.setMerchantName("abc");
		retailersDTO.setKycStatus(KycStatus.NEW);
		retailersDTO.setComments("n/a");
		retailers.add(retailersDTO);
		loginDTO.setRetailers(retailers);
		return loginDTO;
	}

	public static ResponseDTO getCompletRetailerInfo(String dsrMsisdn, String merchantId) {
		ResponseDTO loginDTO = getLoginDTO(null, "SUCCESS", "200", null);
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
		workEducationDetails.add(workEducationDetailsDTO);
		userDto.setWorkEducationDetails(workEducationDetails);
		// Liabilities details
		List<LiabilitiesDetailsDTO> liabilitiesDetails = new ArrayList<LiabilitiesDetailsDTO>();
		LiabilitiesDetailsDTO liabilitiesDetailsDTO = new LiabilitiesDetailsDTO();
		liabilitiesDetailsDTO.setBankOrNbfiName("ABC bank");
		liabilitiesDetailsDTO.setLoanAmount(87654);
		liabilitiesDetails.add(liabilitiesDetailsDTO);
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
		businessDetailsDTO.setBusinessStartDate(new Date().getTime());
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
		addressDetailsDTOs.setAddressType(AddressType.PRESENT);
		addressDetailsDTOs.setAddress("BEST WESTERN La Vinchi Building,54 Kwaran Bazar, Dhaka City, 1215");
		addressDetailsList.add(addressDetailsDTOs);
		AddressDetailsDTO addressDetailsDTOss = new AddressDetailsDTO();
		addressDetailsDTOss.setAddress("BEST WESTERN La Vinchi Building,54 Kwaran Bazar, Dhaka City, 1215");
		addressDetailsDTOss.setAddressType(AddressType.PERMANNET);
		addressDetailsList.add(addressDetailsDTOss);
		userDto.setAddressDetails(addressDetailsList);
		// User Bank Accoount details
		userDto.setBankAccountDetails(businessAccountDetails);
		loginDTO.setRetailerInfo(userDto);
		return loginDTO;
	}
}
