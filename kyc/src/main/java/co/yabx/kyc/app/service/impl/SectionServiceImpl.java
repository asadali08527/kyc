package co.yabx.kyc.app.service.impl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.dto.GroupsDTO;
import co.yabx.kyc.app.dto.SectionsDTO;
import co.yabx.kyc.app.fullKyc.entity.AddressDetails;
import co.yabx.kyc.app.fullKyc.entity.AttachmentDetails;
import co.yabx.kyc.app.fullKyc.entity.BankAccountDetails;
import co.yabx.kyc.app.fullKyc.entity.BusinessDetails;
import co.yabx.kyc.app.fullKyc.entity.IntroducerDetails;
import co.yabx.kyc.app.fullKyc.entity.LiabilitiesDetails;
import co.yabx.kyc.app.fullKyc.entity.LicenseDetails;
import co.yabx.kyc.app.fullKyc.entity.MonthlyTransactionProfiles;
import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.fullKyc.entity.WorkEducationDetails;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.SectionService;
import co.yabx.kyc.app.service.FieldService;

@Service
public class SectionServiceImpl implements SectionService {

	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private FieldService dynamicFieldService;

	private static final Logger LOGGER = LoggerFactory.getLogger(SectionServiceImpl.class);

	@Override
	public void prepareUserDetails(List<SectionsDTO> appPagesSectionsDTOList, User retailer, User nominees,
			Set<AddressDetails> userAddressDetailsSet, Set<BankAccountDetails> userBankAccountDetailsSet,
			Set<AddressDetails> nomineeAddressDetailsSet, Set<BankAccountDetails> nomineeBankAccountDetailsSet,
			Set<BusinessDetails> businessDetailsSet, Set<AddressDetails> businessAddressDetailsSet,
			Set<BankAccountDetails> businessBankAccountDetailsSet, Set<LiabilitiesDetails> liabilitiesDetailsSet,
			Set<WorkEducationDetails> workEducationDetailsSet, Set<IntroducerDetails> introducerDetailsSet,
			Set<MonthlyTransactionProfiles> monthlyTransactionProfilesSet, Set<AttachmentDetails> attachmentDetailsSet,
			Set<LicenseDetails> licenseDetailsSet) {
		for (SectionsDTO appPagesSectionsDTO : appPagesSectionsDTOList) {
			List<GroupsDTO> appPagesSectionGroupsDTOList = appPagesSectionsDTO.getGroups();
			if (appPagesSectionGroupsDTOList != null && !appPagesSectionGroupsDTOList.isEmpty()) {
				for (GroupsDTO appPagesSectionGroupsDTO : appPagesSectionGroupsDTOList) {
					dynamicFieldService.prepareFields(retailer, nominees, appPagesSectionGroupsDTO,
							userAddressDetailsSet, userBankAccountDetailsSet, nomineeAddressDetailsSet,
							nomineeBankAccountDetailsSet, businessDetailsSet, businessAddressDetailsSet,
							businessBankAccountDetailsSet, liabilitiesDetailsSet, appPagesSectionsDTO,
							monthlyTransactionProfilesSet, workEducationDetailsSet, introducerDetailsSet,
							attachmentDetailsSet, licenseDetailsSet);
				}
			}

		}
	}

}
