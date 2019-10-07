package co.yabx.kyc.app.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.dto.DsrProfileDTO;
import co.yabx.kyc.app.dto.QuestionAnswerDTO;
import co.yabx.kyc.app.dto.ResponseDTO;
import co.yabx.kyc.app.dto.RetailerRequestDTO;
import co.yabx.kyc.app.dto.RetailersDTO;
import co.yabx.kyc.app.dto.VerifyOtpDTO;
import co.yabx.kyc.app.dto.dtoHelper.DsrDtoHelper;
import co.yabx.kyc.app.dto.dtoHelper.QuestionAnswerDTOHelper;
import co.yabx.kyc.app.dto.dtoHelper.RetailersDtoHelper;
import co.yabx.kyc.app.entities.OTP;
import co.yabx.kyc.app.enums.DsrProfileStatus;
import co.yabx.kyc.app.enums.OtpType;
import co.yabx.kyc.app.fullKyc.dto.BankAccountDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.BusinessDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.LiabilitiesDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.UserDTO;
import co.yabx.kyc.app.fullKyc.entity.BankAccountDetails;
import co.yabx.kyc.app.fullKyc.entity.BusinessDetails;
import co.yabx.kyc.app.fullKyc.entity.DsrRetailersRelationships;
import co.yabx.kyc.app.fullKyc.entity.Nominees;
import co.yabx.kyc.app.fullKyc.entity.Retailers;
import co.yabx.kyc.app.fullKyc.repository.DsrRetailersRelationshipsRepository;
import co.yabx.kyc.app.fullKyc.repository.RetailersRepository;
import co.yabx.kyc.app.fullKyc.repository.UserRepository;
import co.yabx.kyc.app.repositories.OtpRepository;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.DSRService;
import co.yabx.kyc.app.service.OtpService;
import co.yabx.kyc.app.service.RetailerService;
import co.yabx.kyc.app.util.UtilHelper;

/**
 * 
 * @author Asad.ali
 *
 */
@Service
public class RetailerServiceImpl implements RetailerService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private RetailersRepository retailersRepository;

	@Autowired
	private DsrRetailersRelationshipsRepository dsrRetailersRelationshipsRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(RetailerServiceImpl.class);

	@Override
	public ResponseDTO getSummaries(RetailerRequestDTO retailerRequestDTO) {
		if (retailerRequestDTO != null && retailerRequestDTO.getDsrMSISDN() != null
				&& !retailerRequestDTO.getDsrMSISDN().isEmpty()) {
			List<DsrRetailersRelationships> dsrRetailersRelationships = dsrRetailersRelationshipsRepository
					.findByDsrMsisdn(retailerRequestDTO.getDsrMSISDN());
			if (dsrRetailersRelationships != null && !dsrRetailersRelationships.isEmpty()) {
				List<Retailers> retailers = dsrRetailersRelationships.stream().map(f -> f.getRetailers())
						.collect(Collectors.toList());
				return RetailersDtoHelper.getSummary(retailers);
			}
			return RetailersDtoHelper.getSummary(null);
			// return RetailersDtoHelper.getResponseDTO(null, "No Retailers Found for the
			// DSR", "404", null);
		}
		return RetailersDtoHelper.getResponseDTO(null, "Either DSR msisdn is missing or no data passed", "403", null);
	}

	@Override
	public ResponseDTO retailerDetails(String dsrMsisdn, String retailerId) {
		DsrRetailersRelationships dsrRetailersRelationships = dsrRetailersRelationshipsRepository
				.findByDsrMsisdnAndRetailers(dsrMsisdn, retailerId);

		return RetailersDtoHelper.getCompletRetailerInfo(dsrMsisdn, retailerId);
	}

	@Override
	public ResponseDTO submitRetailerProfile(UserDTO userDTO) {
		Retailers retailers = new Retailers();
		retailers.setRetailerId(userDTO.getRetailerId());
		retailers.setFirstName(userDTO.getName());
		retailers.setDob(userDTO.getDob());
		retailers.setPob(userDTO.getPob());
		retailers.setFathersName(userDTO.getFathersName());
		retailers.setMothersName(userDTO.getMothersName());
		retailers.setMaritalStatus(userDTO.getMaritalStatus());
		retailers.setSpouseName(userDTO.getSpouseName());
		retailers.setNumberOfDependents(userDTO.getNumberOfDependents());
		retailers.setEmail(userDTO.getEmail());
		retailers.setTaxIdentificationNumber(userDTO.getTaxIdentificationNumber());
		retailers.setNationalIdNumber(userDTO.getNationalIdNumber());
		retailers.setBirthRegistrationNumber(userDTO.getBirthRegistrationNumber());
		retailers.setPassportNumber(userDTO.getPassportNumber());
		retailers.setPassportExpiryDate(userDTO.getPassportExpiryDate());
		retailers.setBankAccountDetails(getBankAccountDetails(userDTO.getBankAccountDetails()));
		retailers.setBusinessDetails(getBusinessDetails(userDTO.getBusinessDetails()));
		userRepository.save(retailers);
		return RetailersDtoHelper.getResponseDTO(null, "SUCCESS", "200", null);
	}

	private Set<BusinessDetails> getBusinessDetails(List<BusinessDetailsDTO> businessDetailsDtos) {
		Set<BusinessDetails> businessDetailsSet = new HashSet<BusinessDetails>();
		for (BusinessDetailsDTO businessDetailsDTO : businessDetailsDtos) {
			BusinessDetails businessDetails = new BusinessDetails();
			businessDetails.setBusinessName(businessDetailsDTO.getBusinessName());
			businessDetails.setBusinessPhone(businessDetailsDTO.getBusinessPhone());
			businessDetails.setBusinessStartDate(businessDetailsDTO.getBusinessStartDate());
			businessDetails.setBusinessTin(businessDetailsDTO.getBusinessTin());
			businessDetails.setWithdrawls(businessDetailsDTO.getWithdrawls());
			businessDetails.setVatRegistrationNumber(businessDetailsDTO.getVatRegistrationNumber());
			businessDetails.setValueOfFixedAssets(businessDetailsDTO.getValueOfFixedAssets());
			businessDetails.setStockValue(businessDetailsDTO.getStockValue());
			businessDetails.setSector(businessDetailsDTO.getSector());
			businessDetails.setProposedCollateral(businessDetailsDTO.getProposedCollateral());
			businessDetails.setPrice(businessDetailsDTO.getPrice());
			businessDetails.setOrigin(businessDetailsDTO.getOrigin());
			businessDetails.setNumberOfEmployees(businessDetailsDTO.getNumberOfEmployees());
			businessDetails.setMonthlyTurnOver(businessDetailsDTO.getMonthlyTurnOver());
			businessDetails.setBusinessType(businessDetailsDTO.getBusinessType());
			businessDetails.setDeposits(businessDetailsDTO.getDeposits());
			businessDetails.setDetailOfBusness(businessDetailsDTO.getDetailOfBusness());
			businessDetails.setDirectorOrPartnerName(businessDetailsDTO.getDirectorOrPartnerName());
			businessDetails.setFacilityDetails(businessDetailsDTO.getFacilityDetails());
			businessDetails.setFacilityType(businessDetailsDTO.getFacilityType());
			businessDetails.setFixedAssetName(businessDetailsDTO.getFixedAssetName());
			businessDetails.setFixedAssetPurchase(businessDetailsDTO.getFixedAssetPurchase());
			businessDetails.setFundSource(businessDetailsDTO.getFundSource());
			businessDetails.setInitialCapital(businessDetailsDTO.getInitialCapital());
			businessDetails.setInitialDeposit(businessDetailsDTO.getInitialDeposit());

			businessDetailsSet.add(businessDetails);
		}
		return businessDetailsSet;
	}

	private Set<BankAccountDetails> getBankAccountDetails(List<BankAccountDetailsDTO> bankAccountDetails) {
		Set<BankAccountDetails> set = new HashSet<BankAccountDetails>();
		for (BankAccountDetailsDTO bankAccountDetailsDTO : bankAccountDetails) {
			BankAccountDetails accountDetails = new BankAccountDetails();
			accountDetails.setAccountNumber(bankAccountDetailsDTO.getAccountNumber());
			accountDetails.setBankAccountIdentifier(bankAccountDetailsDTO.getBankAccountIdentifier());
			accountDetails.setBankAccountType(bankAccountDetailsDTO.getBankAccountType());
			accountDetails.setBankName(bankAccountDetailsDTO.getBankName());
			accountDetails.setBranch(bankAccountDetailsDTO.getBranch());
			set.add(accountDetails);
		}
		return set;
	}

	@Override
	public ResponseDTO submitRetailerNomineeProfile(UserDTO userDTO) {
		Nominees retailers = new Nominees();
		retailers.setFirstName(userDTO.getName());
		retailers.setDob(userDTO.getDob());
		retailers.setPob(userDTO.getPob());
		retailers.setFathersName(userDTO.getFathersName());
		retailers.setMothersName(userDTO.getMothersName());
		retailers.setMaritalStatus(userDTO.getMaritalStatus());
		retailers.setSpouseName(userDTO.getSpouseName());
		retailers.setNumberOfDependents(userDTO.getNumberOfDependents());
		retailers.setEmail(userDTO.getEmail());
		retailers.setTaxIdentificationNumber(userDTO.getTaxIdentificationNumber());
		retailers.setNationalIdNumber(userDTO.getNationalIdNumber());
		retailers.setBirthRegistrationNumber(userDTO.getBirthRegistrationNumber());
		retailers.setPassportNumber(userDTO.getPassportNumber());
		retailers.setPassportExpiryDate(userDTO.getPassportExpiryDate());
		userRepository.save(retailers);
		return RetailersDtoHelper.getResponseDTO(null, "SUCCESS", "200", null);
	}

	@Override
	public ResponseDTO submitRetailerBusinessInfo(BusinessDetailsDTO businessDetailsDTO) {
		Retailers retailers = retailersRepository.findByRetailerId(businessDetailsDTO.getRetailerId());

		return RetailersDtoHelper.getResponseDTO(null, "SUCCESS", "200", null);
	}

	@Override
	public ResponseDTO submitLiabilitiesInfo(LiabilitiesDetailsDTO liabilitiesDetailsDTO) {
		// TODO Auto-generated method stub
		return RetailersDtoHelper.getResponseDTO(null, "SUCCESS", "200", null);
	}

	@Override
	public ResponseDTO submitKyc(RetailersDTO retailersDTO) {
		// TODO Auto-generated method stub
		return RetailersDtoHelper.getResponseDTO(null, "SUCCESS", "200", null);
	}

	@Override
	public ResponseDTO getRetailerQuestion(Integer questionId) {
		// TODO Auto-generated method stub
		ResponseDTO responseDTO = RetailersDtoHelper.getResponseDTO(null, "SUCCESS", "200", null);
		return QuestionAnswerDTOHelper.getquestion(responseDTO);

	}

	@Override
	public ResponseDTO getRetailerAllQuestions(String retailerId) {
		ResponseDTO responseDTO = RetailersDtoHelper.getResponseDTO(null, "SUCCESS", "200", null);
		return QuestionAnswerDTOHelper.getAllquestion(responseDTO);
	}

	@Override
	public ResponseDTO persistRetailerAnswer(QuestionAnswerDTO questionAnswerDTO) {
		// TODO Auto-generated method stub
		return RetailersDtoHelper.getResponseDTO(null, "SUCCESS", "200", null);
	}

	@Override
	public ResponseDTO searchRetailerByDsr(String dsrMsisdn, String retailerId) {
		if (dsrMsisdn != null && retailerId != null && !dsrMsisdn.isEmpty() && !retailerId.isEmpty()) {
			List<DsrRetailersRelationships> dsrRetailersRelationships = dsrRetailersRelationshipsRepository
					.findByDsrMsisdn(dsrMsisdn);
			if (dsrRetailersRelationships != null && !dsrRetailersRelationships.isEmpty()) {
				List<Retailers> retailers = dsrRetailersRelationships.stream()
						.filter(f -> f.getRetailers().getRetailerId().equalsIgnoreCase(retailerId))
						.map(f -> f.getRetailers()).collect(Collectors.toList());
				return RetailersDtoHelper.getSummary(retailers);
			}
			return RetailersDtoHelper.getResponseDTO(null, "No Retailers Found for the DSR", "404", null);
		}
		return RetailersDtoHelper.getResponseDTO(null, "Either DSR msisdn or retailers id is missing", "403", null);
	}

}
