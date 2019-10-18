package co.yabx.kyc.app.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.dto.AppPagesDTO;
import co.yabx.kyc.app.dto.AppPagesSectionsDTO;
import co.yabx.kyc.app.dto.dtoHelper.AppPagesDynamicDtoHeper;
import co.yabx.kyc.app.entities.AppPages;
import co.yabx.kyc.app.enums.Relationship;
import co.yabx.kyc.app.enums.UserType;
import co.yabx.kyc.app.fullKyc.entity.AddressDetails;
import co.yabx.kyc.app.fullKyc.entity.BankAccountDetails;
import co.yabx.kyc.app.fullKyc.entity.BusinessDetails;
import co.yabx.kyc.app.fullKyc.entity.DSRUser;
import co.yabx.kyc.app.fullKyc.entity.IntroducerDetails;
import co.yabx.kyc.app.fullKyc.entity.LiabilitiesDetails;
import co.yabx.kyc.app.fullKyc.entity.MonthlyTransactionProfiles;
import co.yabx.kyc.app.fullKyc.entity.Nominees;
import co.yabx.kyc.app.fullKyc.entity.Retailers;
import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.fullKyc.entity.UserRelationships;
import co.yabx.kyc.app.fullKyc.entity.WorkEducationDetails;
import co.yabx.kyc.app.fullKyc.repository.DSRUserRepository;
import co.yabx.kyc.app.fullKyc.repository.RetailersRepository;
import co.yabx.kyc.app.fullKyc.repository.UserRelationshipsRepository;
import co.yabx.kyc.app.fullKyc.repository.UserRepository;
import co.yabx.kyc.app.repositories.AppPagesRepository;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.AppPagesSectionService;
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
	private AppPagesSectionService appPagesSectionService;

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
			appPagesDTOList.add(AppPagesDynamicDtoHeper.prepareAppPagesDto(pages, user, nominee, userAddressDetailsSet,
					nomineeAddressDetailsSet, businessAddressDetailsSet, userBankAccountDetailsSet,
					nomineeBankAccountDetailsSet, businessBankAccountDetailsSet, type));

		}

		return appPagesDTOList;

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
	public User persistOrUpdateUserInfo(AppPagesDTO appPagesDTO, User dsrUser, User retailer) throws Exception {

		if (appPagesDTO != null && dsrUser != null) {
			Boolean isNew = false;
			Boolean isDsrUser = false;
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
			Set<MonthlyTransactionProfiles> monthlyTransactionProfilesSet = null;
			Set<WorkEducationDetails> workEducationDetailsSet = null;
			Set<IntroducerDetails> introducerDetailsSet = null;

			if (retailer == null) {
				// It means DSR profile need to be persisted
				retailer = dsrUser;
				isDsrUser = true;
				userAddressDetailsSet = dsrUser.getAddressDetails() != null ? dsrUser.getAddressDetails()
						: new HashSet<AddressDetails>();
				userBankAccountDetailsSet = dsrUser.getBankAccountDetails() != null ? dsrUser.getBankAccountDetails()
						: new HashSet<BankAccountDetails>();
				liabilitiesDetailsSet = dsrUser.getLiabilitiesDetails() != null ? dsrUser.getLiabilitiesDetails()
						: new HashSet<LiabilitiesDetails>();
				userBankAccountDetailsSet = dsrUser.getBankAccountDetails() != null ? dsrUser.getBankAccountDetails()
						: new HashSet<BankAccountDetails>();
				nomineeRelationship = userRelationshipsRepository.findByMsisdnAndRelationship(dsrUser.getMsisdn(),
						Relationship.NOMINEE);
				nominees = nomineeRelationship != null ? nomineeRelationship.getRelative() : null;
				if (nominees != null) {
					nomineeAddressDetailsSet = nominees.getAddressDetails() != null ? nominees.getAddressDetails()
							: new HashSet<AddressDetails>();
					nomineeBankAccountDetailsSet = nominees.getBankAccountDetails() != null
							? nominees.getBankAccountDetails()
							: new HashSet<BankAccountDetails>();
				} else {
					isNew = true;
				}

				businessDetailsSet = dsrUser.getBusinessDetails() != null ? dsrUser.getBusinessDetails()
						: new HashSet<BusinessDetails>();
				if (businessDetailsSet != null && !businessDetailsSet.isEmpty()) {
					for (BusinessDetails businessDetails : businessDetailsSet) {
						businessAddressDetailsSet.addAll(businessDetails.getAddressDetails());
						businessBankAccountDetailsSet.addAll(businessDetails.getBankAccountDetails());
					}
				} else {
					businessDetailsSet = businessDetailsSet == null ? new HashSet<BusinessDetails>()
							: businessDetailsSet;
					businessAddressDetailsSet = new HashSet<AddressDetails>();
					businessBankAccountDetailsSet = new HashSet<BankAccountDetails>();
				}
				workEducationDetailsSet = dsrUser.getWorkEducationDetails();

			} else {
				userBankAccountDetailsSet = retailer.getBankAccountDetails() != null ? retailer.getBankAccountDetails()
						: new HashSet<BankAccountDetails>();
				userAddressDetailsSet = retailer.getAddressDetails() != null ? retailer.getAddressDetails()
						: new HashSet<AddressDetails>();
				businessDetailsSet = retailer.getBusinessDetails() != null ? retailer.getBusinessDetails()
						: new HashSet<BusinessDetails>();
				liabilitiesDetailsSet = retailer.getLiabilitiesDetails() != null ? retailer.getLiabilitiesDetails()
						: new HashSet<LiabilitiesDetails>();
				nomineeRelationship = userRelationshipsRepository.findByMsisdnAndRelationship(retailer.getMsisdn(),
						Relationship.NOMINEE);
				nominees = nomineeRelationship != null ? nomineeRelationship.getRelative() : null;
				if (nominees != null) {
					nomineeAddressDetailsSet = nominees.getAddressDetails() != null ? nominees.getAddressDetails()
							: new HashSet<AddressDetails>();
					nomineeBankAccountDetailsSet = nominees.getBankAccountDetails() != null
							? nominees.getBankAccountDetails()
							: new HashSet<BankAccountDetails>();
				} else {
					isNew = true;
					nominees = new Nominees();
				}

				if (businessDetailsSet != null && !businessDetailsSet.isEmpty()) {
					for (BusinessDetails businessDetails : businessDetailsSet) {
						businessAddressDetailsSet.addAll(businessDetails.getAddressDetails());
						businessBankAccountDetailsSet.addAll(businessDetails.getBankAccountDetails());
					}
				} else {
					businessDetailsSet = businessDetailsSet == null ? new HashSet<BusinessDetails>()
							: businessDetailsSet;
					businessAddressDetailsSet = new HashSet<AddressDetails>();
					businessBankAccountDetailsSet = new HashSet<BankAccountDetails>();
				}
				monthlyTransactionProfilesSet = retailer.getMonthlyTransactionProfiles();
				workEducationDetailsSet = retailer.getWorkEducationDetails();
				introducerDetailsSet = retailer.getIntroducerDetails();

			}
			List<AppPagesSectionsDTO> appPagesSectionsDTOList = appPagesDTO.getSections();
			if (appPagesSectionsDTOList != null && !appPagesSectionsDTOList.isEmpty()) {
				appPagesSectionService.prepareUserDetails(appPagesSectionsDTOList, retailer, nominees,
						userAddressDetailsSet, userBankAccountDetailsSet, nomineeAddressDetailsSet,
						nomineeBankAccountDetailsSet, businessDetailsSet, businessAddressDetailsSet,
						businessBankAccountDetailsSet, liabilitiesDetailsSet, workEducationDetailsSet,
						introducerDetailsSet, monthlyTransactionProfilesSet);
				return persistUser(retailer, nominees, userAddressDetailsSet, userBankAccountDetailsSet,
						liabilitiesDetailsSet, isNew, nomineeRelationship, nomineeAddressDetailsSet, isDsrUser,
						businessDetailsSet, nomineeBankAccountDetailsSet, monthlyTransactionProfilesSet,
						workEducationDetailsSet, introducerDetailsSet);
			}
		}
		return dsrUser;

	}

	@Transactional
	private User persistUser(User retailer, User nominees, Set<AddressDetails> userAddressDetailsSet,
			Set<BankAccountDetails> userBankAccountDetailsSet, Set<LiabilitiesDetails> liabilitiesDetails,
			Boolean isNew, UserRelationships nomineeRelationship, Set<AddressDetails> nomineeAddressDetailsSet,
			Boolean isDsrUser, Set<BusinessDetails> businessDetailsSet,
			Set<BankAccountDetails> nomineeBankAccountDetailsSet,
			Set<MonthlyTransactionProfiles> monthlyTransactionProfilesSet,
			Set<WorkEducationDetails> workEducationDetailsSet, Set<IntroducerDetails> introducerDetailsSet)
			throws Exception {
		if (isDsrUser)
			retailer.setUserType(UserType.DISTRIBUTORS.name());
		else
			retailer.setUserType(UserType.RETAILERS.name());
		if (neitherNullNorEmpty(userAddressDetailsSet))
			retailer.setAddressDetails(userAddressDetailsSet);
		if (neitherNullNorEmpty(userBankAccountDetailsSet))
			retailer.setBankAccountDetails(userBankAccountDetailsSet);
		if (neitherNullNorEmpty(businessDetailsSet))
			retailer.setBusinessDetails(businessDetailsSet);
		if (neitherNullNorEmpty(liabilitiesDetails))
			retailer.setLiabilitiesDetails(liabilitiesDetails);
		if (neitherNullNorEmpty(workEducationDetailsSet))
			retailer.setWorkEducationDetails(workEducationDetailsSet);
		if (neitherNullNorEmpty(monthlyTransactionProfilesSet))
			retailer.setMonthlyTransactionProfiles(monthlyTransactionProfilesSet);
		if (neitherNullNorEmpty(introducerDetailsSet))
			retailer.setIntroducerDetails(introducerDetailsSet);
		retailer = userRepository.save(retailer);
		if (nominees != null) {
			nominees.setUserType(UserType.NOMINEES.name());
			if (nomineeAddressDetailsSet != null)
				nominees.setAddressDetails(nomineeAddressDetailsSet);
			if (nomineeBankAccountDetailsSet != null)
				nominees.setBankAccountDetails(nomineeBankAccountDetailsSet);
			nominees = userRepository.save(nominees);
			persistUserRelationship(retailer, nominees, UserType.NOMINEES, isNew, nomineeRelationship);
		}
		return retailer;
	}

	private boolean neitherNullNorEmpty(Set<?> set) {
		return set != null && !set.isEmpty();
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

}
