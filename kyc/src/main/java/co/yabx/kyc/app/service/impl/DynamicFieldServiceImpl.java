package co.yabx.kyc.app.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.dto.AppDynamicFieldsDTO;
import co.yabx.kyc.app.dto.AppPagesSectionGroupsDTO;
import co.yabx.kyc.app.dto.AppPagesSectionsDTO;
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
import co.yabx.kyc.app.fullKyc.entity.AddressDetails;
import co.yabx.kyc.app.fullKyc.entity.AttachmentDetails;
import co.yabx.kyc.app.fullKyc.entity.BankAccountDetails;
import co.yabx.kyc.app.fullKyc.entity.BusinessDetails;
import co.yabx.kyc.app.fullKyc.entity.IntroducerDetails;
import co.yabx.kyc.app.fullKyc.entity.LiabilitiesDetails;
import co.yabx.kyc.app.fullKyc.entity.LicenseDetails;
import co.yabx.kyc.app.fullKyc.entity.MonthlyTransactionProfiles;
import co.yabx.kyc.app.fullKyc.entity.Nominees;
import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.fullKyc.entity.WorkEducationDetails;
import co.yabx.kyc.app.fullKyc.repository.UserRepository;
import co.yabx.kyc.app.repositories.AuthInfoRepository;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.DynamicFieldService;

/**
 * 
 * @author Asad.ali
 *
 */
@Service
public class DynamicFieldServiceImpl implements DynamicFieldService {

	@Autowired
	private AuthInfoRepository authInfoRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AppConfigService appConfigService;

	private static final Logger LOGGER = LoggerFactory.getLogger(DynamicFieldServiceImpl.class);

	@Override
	public void prepareFields(User retailerOrDsrUser, User nominees, AppPagesSectionGroupsDTO appPagesSectionGroupsDTO,
			Set<AddressDetails> userAddressDetailsSet, Set<BankAccountDetails> userBankAccountDetailsSet,
			Set<AddressDetails> nomineeAddressDetailsSet, Set<BankAccountDetails> nomineeBankAccountDetailsSet,
			Set<BusinessDetails> businessDetailsSet, Set<AddressDetails> businessAddressDetailsSet,
			Set<BankAccountDetails> businessBankAccountDetailsSet, Set<LiabilitiesDetails> liabilitiesDetailsSet,
			AppPagesSectionsDTO appPagesSectionsDTO, Set<MonthlyTransactionProfiles> monthlyTransactionProfilesSet,
			Set<WorkEducationDetails> workEducationDetailsSet, Set<IntroducerDetails> introducerDetailsSet) {

		List<AppDynamicFieldsDTO> appDynamicFieldsDTOList = appPagesSectionGroupsDTO.getFields();
		AddressDetails addressDetails = null;
		BankAccountDetails bankAccountDetails = null;
		LiabilitiesDetails liabilitiesDetails = null;
		if (appPagesSectionGroupsDTO.getGroupId() == 2
				&& (appPagesSectionsDTO.getSectionId() == 1 || appPagesSectionsDTO.getSectionId() == 3)) {
			// user address details
			addressDetails = new AddressDetails();
			addressDetails = prepareAddress(appDynamicFieldsDTOList, addressDetails);
			if (addressDetails != null) {
				if (userAddressDetailsSet == null) {
					userAddressDetailsSet = new HashSet<AddressDetails>();
				}
				userAddressDetailsSet.clear();
				userAddressDetailsSet.add(addressDetails);
			}
			return;
		} else if (appPagesSectionGroupsDTO.getGroupId() == 2 && appPagesSectionsDTO.getSectionId() == 2) {
			// nominee address details
			addressDetails = new AddressDetails();
			addressDetails = prepareAddress(appDynamicFieldsDTOList, addressDetails);
			if (addressDetails != null) {
				if (nomineeAddressDetailsSet == null) {
					nomineeAddressDetailsSet = new HashSet<AddressDetails>();
				}
				nomineeAddressDetailsSet.clear();
				nomineeAddressDetailsSet.add(addressDetails);
			}
			return;
		} else if (appPagesSectionGroupsDTO.getGroupId() == 2 && appPagesSectionsDTO.getSectionId() == 5) {
			// Business address details
			addressDetails = new AddressDetails();
			addressDetails = prepareAddress(appDynamicFieldsDTOList, addressDetails);
			if (addressDetails != null) {
				if (businessAddressDetailsSet == null) {
					businessAddressDetailsSet = new HashSet<AddressDetails>();
				}
				businessAddressDetailsSet.clear();
				businessAddressDetailsSet.add(addressDetails);
			}
			return;
		} else if (appPagesSectionGroupsDTO.getGroupId() == 3
				&& (appPagesSectionsDTO.getSectionId() == 1 || appPagesSectionsDTO.getSectionId() == 3)) {
			bankAccountDetails = new BankAccountDetails();
			bankAccountDetails = preparebankAccounts(appDynamicFieldsDTOList, bankAccountDetails);
			if (userBankAccountDetailsSet == null) {
				userBankAccountDetailsSet = new HashSet<BankAccountDetails>();
			}
			userBankAccountDetailsSet.clear();
			userBankAccountDetailsSet.add(bankAccountDetails);
			return;

		} else if (appPagesSectionGroupsDTO.getGroupId() == 3 && appPagesSectionsDTO.getSectionId() == 2) {
			bankAccountDetails = new BankAccountDetails();
			bankAccountDetails = preparebankAccounts(appDynamicFieldsDTOList, bankAccountDetails);
			if (nomineeBankAccountDetailsSet == null) {
				nomineeBankAccountDetailsSet = new HashSet<BankAccountDetails>();
			}
			nomineeBankAccountDetailsSet.clear();
			nomineeBankAccountDetailsSet.add(bankAccountDetails);
			return;

		} else if (appPagesSectionGroupsDTO.getGroupId() == 3 && appPagesSectionsDTO.getSectionId() == 5) {
			bankAccountDetails = new BankAccountDetails();
			bankAccountDetails = preparebankAccounts(appDynamicFieldsDTOList, bankAccountDetails);
			if (businessBankAccountDetailsSet == null) {
				businessBankAccountDetailsSet = new HashSet<BankAccountDetails>();
			}
			businessBankAccountDetailsSet.clear();
			businessBankAccountDetailsSet.add(bankAccountDetails);
			return;
		} else if (appPagesSectionGroupsDTO.getGroupId() == 4) {
			liabilitiesDetails = prepareLiabilities(appDynamicFieldsDTOList, liabilitiesDetails);
			if (liabilitiesDetailsSet == null) {
				liabilitiesDetailsSet = new HashSet<LiabilitiesDetails>();
			}
			liabilitiesDetailsSet.clear();
			liabilitiesDetailsSet.add(liabilitiesDetails);
			return;
		} else if (appPagesSectionGroupsDTO.getGroupId() == 5) {
			BusinessDetails businessDetails = null;
			if (businessDetailsSet == null || businessDetailsSet.isEmpty()) {
				businessDetails = new BusinessDetails();
			} else {
				Optional<BusinessDetails> businessDetailsOptional = businessDetailsSet.stream().findFirst();
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
				businessDetailsSet.clear();
				businessDetailsSet.add(businessDetails);
			}
			return;
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
					Optional<BusinessDetails> businessDetailsOptional = businessDetailsSet.stream().findFirst();
					if (!businessDetailsOptional.isPresent())
						businessDetails = new BusinessDetails();
					else
						businessDetails = businessDetailsOptional.get();
					businessDetails.setLicenseDetails(licenseDetails);
					businessDetailsSet.add(businessDetails);
				}
			}
			return;
		} else if (appPagesSectionGroupsDTO.getGroupId() == 7) {
			MonthlyTransactionProfiles monthlyTransactionProfiles = null;
			monthlyTransactionProfiles = prepareMonthlyTxnProfiles(appDynamicFieldsDTOList, monthlyTransactionProfiles);
			if (monthlyTransactionProfiles != null) {
				if (monthlyTransactionProfilesSet == null)
					monthlyTransactionProfilesSet = new HashSet<MonthlyTransactionProfiles>();
				monthlyTransactionProfilesSet.add(monthlyTransactionProfiles);
			} else {
				monthlyTransactionProfilesSet.clear();
				monthlyTransactionProfilesSet.add(monthlyTransactionProfiles);
			}
			return;
		} else if (appPagesSectionGroupsDTO.getGroupId() == 8) {
			WorkEducationDetails workEducationDetails = null;
			workEducationDetails = prepareWorkEducationDetails(appDynamicFieldsDTOList, workEducationDetails);
			if (workEducationDetails != null) {
				if (workEducationDetailsSet == null) {
					workEducationDetailsSet = new HashSet<WorkEducationDetails>();
					workEducationDetailsSet.add(workEducationDetails);
				} else {
					workEducationDetailsSet.clear();
					workEducationDetailsSet.add(workEducationDetails);
				}
			}
			return;
		} else if (appPagesSectionGroupsDTO.getGroupId() == 9) {
			IntroducerDetails introducerDetails = null;
			introducerDetails = prepareIntroducerDetails(appDynamicFieldsDTOList, introducerDetails);
			if (introducerDetails != null) {
				if (introducerDetailsSet == null) {
					introducerDetailsSet = new HashSet<IntroducerDetails>();
					introducerDetailsSet.add(introducerDetails);
				} else {
					introducerDetailsSet.clear();
					introducerDetailsSet.add(introducerDetails);
				}
			}
			return;
		}
		for (AppDynamicFieldsDTO appDynamicFieldsDTO : appDynamicFieldsDTOList) {
			if (appPagesSectionGroupsDTO.getGroupId() == 1
					&& (appPagesSectionsDTO.getSectionId() == 1 || appPagesSectionsDTO.getSectionId() == 3)) {
				// User personal Info
				retailerOrDsrUser = prepareProfileInformation(appDynamicFieldsDTO, retailerOrDsrUser);
			} else if (appPagesSectionGroupsDTO.getGroupId() == 1 && appPagesSectionsDTO.getSectionId() == 2) {
				// nominee
				if (nominees == null)
					nominees = new Nominees();
				nominees = prepareProfileInformation(appDynamicFieldsDTO, nominees);
			}

		}

	}

	private IntroducerDetails prepareIntroducerDetails(List<AppDynamicFieldsDTO> appDynamicFieldsDTOList,
			IntroducerDetails introducerDetails) {
		if (appDynamicFieldsDTOList != null && !appDynamicFieldsDTOList.isEmpty()) {
			introducerDetails = new IntroducerDetails();
			for (AppDynamicFieldsDTO appDynamicFieldsDTO : appDynamicFieldsDTOList) {
				if (appDynamicFieldsDTO.getFieldId().equals("accountNumber")) {
					try {
						Long accountNumber = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Long.valueOf(appDynamicFieldsDTO.getResponse())
								: 0;
						introducerDetails.setAccountNumber(accountNumber);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating accountNumber ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("name")) {
					introducerDetails.setName(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("isSignatureVerified")) {
					try {
						introducerDetails.setSignatureVerified(appDynamicFieldsDTO.getResponse() != null
								? Boolean.valueOf(appDynamicFieldsDTO.getResponse())
								: false);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating isSignatureVerified ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("signature")) {

				} else if (appDynamicFieldsDTO.getFieldId().equals("relationship")) {
					try {
						Relationship relationship = appDynamicFieldsDTO.getResponse() != null
								? Relationship.valueOf(appDynamicFieldsDTO.getResponse())
								: null;
						introducerDetails.setRelationship(relationship);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating relationship ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				}
			}
		}
		return introducerDetails;

	}

	private WorkEducationDetails prepareWorkEducationDetails(List<AppDynamicFieldsDTO> appDynamicFieldsDTOList,
			WorkEducationDetails workEducationDetails) {
		if (appDynamicFieldsDTOList != null && !appDynamicFieldsDTOList.isEmpty()) {
			workEducationDetails = new WorkEducationDetails();
			for (AppDynamicFieldsDTO appDynamicFieldsDTO : appDynamicFieldsDTOList) {
				if (appDynamicFieldsDTO.getFieldId().equals("experience")) {
					try {
						Integer experience = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Integer.valueOf(appDynamicFieldsDTO.getResponse())
								: 0;
						workEducationDetails.setExperience(experience);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating experience ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("occupation")) {
					workEducationDetails.setOccupation(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("designation")) {
					workEducationDetails.setDesignation(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("employer")) {
					workEducationDetails.setEmployer(appDynamicFieldsDTO.getResponse());
				} else if (appDynamicFieldsDTO.getFieldId().equals("educationalQualification")) {
					workEducationDetails.setEducationalQualification(appDynamicFieldsDTO.getResponse());
				}
			}
		}
		return workEducationDetails;

	}

	private MonthlyTransactionProfiles prepareMonthlyTxnProfiles(List<AppDynamicFieldsDTO> appDynamicFieldsDTOList,
			MonthlyTransactionProfiles monthlyTransactionProfiles) {
		if (appDynamicFieldsDTOList != null) {
			monthlyTransactionProfiles = new MonthlyTransactionProfiles();
			for (AppDynamicFieldsDTO appDynamicFieldsDTO : appDynamicFieldsDTOList) {
				if (appDynamicFieldsDTO.getFieldId().equals("monthlyTurnOver")) {
					try {
						double monthlyTurnOver = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Double.valueOf(appDynamicFieldsDTO.getResponse())
								: 0.0;
						monthlyTransactionProfiles.setMonthlyTurnOver(monthlyTurnOver);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating monthlyTurnOver ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("deposits")) {
					try {
						double deposits = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Double.valueOf(appDynamicFieldsDTO.getResponse())
								: 0.0;
						monthlyTransactionProfiles.setDeposits(deposits);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating deposits ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("withdrawls")) {
					try {
						double withdrawls = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Double.valueOf(appDynamicFieldsDTO.getResponse())
								: 0.0;
						monthlyTransactionProfiles.setWithdrawls(withdrawls);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating withdrawls ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				} else if (appDynamicFieldsDTO.getFieldId().equals("initialDeposit")) {
					try {
						double initialDeposit = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? Double.valueOf(appDynamicFieldsDTO.getResponse())
								: 0.0;
						monthlyTransactionProfiles.setInitialDeposit(initialDeposit);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating initialDeposit ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
				}
			}
		}
		return monthlyTransactionProfiles;

	}

	private LiabilitiesDetails prepareLiabilities(List<AppDynamicFieldsDTO> appDynamicFieldsDTOList,
			LiabilitiesDetails liabilitiesDetails) {
		if (appDynamicFieldsDTOList != null && !appDynamicFieldsDTOList.isEmpty()) {
			liabilitiesDetails = new LiabilitiesDetails();
			for (AppDynamicFieldsDTO appDynamicFieldsDTO : appDynamicFieldsDTOList) {
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
					try {
						BankAccountType bankAccountType = neitherBlankNorNull(appDynamicFieldsDTO.getResponse())
								? BankAccountType.valueOf(appDynamicFieldsDTO.getResponse())
								: null;
						bankAccountDetails.setBankAccountType(bankAccountType);
					} catch (Exception e) {
						LOGGER.error("Exception while evaluating account_type ={}, error={}",
								appDynamicFieldsDTO.getResponse(), e.getMessage());
					}
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
				try {
					Integer nod = appDynamicFieldsDTO.getResponse() != null
							&& !appDynamicFieldsDTO.getResponse().isEmpty()
									? Integer.valueOf(appDynamicFieldsDTO.getResponse())
									: null;
					user.setNumberOfDependents(nod);
				} catch (Exception e) {
					LOGGER.error("Exception while evaluating number of dependents ={}, error={}",
							appDynamicFieldsDTO.getResponse(), e.getMessage());
				}
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
