package co.yabx.kyc.app.service;

import java.util.List;
import java.util.Set;

import co.yabx.kyc.app.dto.SectionsDTO;
import co.yabx.kyc.app.fullKyc.entity.AddressDetails;
import co.yabx.kyc.app.fullKyc.entity.BankAccountDetails;
import co.yabx.kyc.app.fullKyc.entity.BusinessDetails;
import co.yabx.kyc.app.fullKyc.entity.IntroducerDetails;
import co.yabx.kyc.app.fullKyc.entity.LiabilitiesDetails;
import co.yabx.kyc.app.fullKyc.entity.MonthlyTransactionProfiles;
import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.fullKyc.entity.WorkEducationDetails;

/**
 * 
 * @author Asad.ali
 *
 */
public interface AppPagesSectionService {

	void prepareUserDetails(List<SectionsDTO> appPagesSectionsDTOList, User retailer, User nominees,
			Set<AddressDetails> userAddressDetailsSet, Set<BankAccountDetails> userBankAccountDetailsSet,
			Set<AddressDetails> nomineeAddressDetailsSet, Set<BankAccountDetails> nomineeBankAccountDetailsSet,
			Set<BusinessDetails> businessDetailsSet, Set<AddressDetails> businessAddressDetailsSet,
			Set<BankAccountDetails> businessBankAccountDetailsSet, Set<LiabilitiesDetails> liabilitiesDetailsSet,
			Set<WorkEducationDetails> workEducationDetailsSet, Set<IntroducerDetails> introducerDetailsSet,
			Set<MonthlyTransactionProfiles> monthlyTransactionProfilesSet);

}
