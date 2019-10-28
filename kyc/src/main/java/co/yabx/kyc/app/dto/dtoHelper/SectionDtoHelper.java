package co.yabx.kyc.app.dto.dtoHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.validator.internal.util.privilegedactions.GetInstancesFromServiceLoader;

import co.yabx.kyc.app.dto.GroupsDTO;
import co.yabx.kyc.app.dto.SectionsDTO;
import co.yabx.kyc.app.entities.Sections;
import co.yabx.kyc.app.fullKyc.entity.AddressDetails;
import co.yabx.kyc.app.fullKyc.entity.BankAccountDetails;
import co.yabx.kyc.app.fullKyc.entity.User;

public class SectionDtoHelper implements Serializable {
	public static List<SectionsDTO> getSections(Set<Sections> appPagesSectionsSet, User retailers,
			Map<String, Integer> filledVsUnfilled, User nominee, Set<AddressDetails> userAddressDetailsSet,
			Set<AddressDetails> nomineeAddressDetailsSet, Set<AddressDetails> businessAddressDetailsSet,
			Set<BankAccountDetails> userBankAccountDetailsSet, Set<BankAccountDetails> nomineeBankAccountDetailsSet,
			Set<BankAccountDetails> businessBankAccountDetailsSet) {
		List<SectionsDTO> appPagesSectionDTOSet = new ArrayList<SectionsDTO>();
		for (Sections appPagesSections : appPagesSectionsSet) {
			Map<String, Integer> section = new HashMap<String, Integer>();
			section.put("filledFields", 0);
			section.put("totalFields", 0);
			SectionsDTO appPagesSectionsDTO = new SectionsDTO();

			List<GroupsDTO> appPagesSectionGroupSet = GroupDtoHelper.getGroups(retailers, section, appPagesSections,
					nominee, userAddressDetailsSet, nomineeAddressDetailsSet, businessAddressDetailsSet,
					userBankAccountDetailsSet, nomineeBankAccountDetailsSet, businessBankAccountDetailsSet);

			/*
			 * appPagesSectionsDTO.setGroups(appPagesSectionGroupSet.stream()
			 * .sorted(Comparator.comparing(GroupsDTO::getGroupId)).collect(Collectors.
			 * toList()));
			 */

			appPagesSectionsDTO.setGroups(appPagesSectionGroupSet);
			appPagesSectionsDTO.setEnable(appPagesSections.isEnable());
			appPagesSectionsDTO.setSectionId(appPagesSections.getSectionId());
			appPagesSectionsDTO.setSectionName(appPagesSections.getSectionName());
			appPagesSectionsDTO.setSectionTitle(appPagesSections.getSectionTitle());
			appPagesSectionsDTO.setFilledFields(section.get("filledFields"));
			appPagesSectionsDTO.setTotalFields(section.get("totalFields"));
			appPagesSectionsDTO.setDisplayOrder(appPagesSections.getDisplayOrder());
			if (appPagesSections.getSectionId() == 2)
				appPagesSectionsDTO.setNomineeId(nominee != null ? nominee.getId() : null);

			appPagesSectionDTOSet.add(appPagesSectionsDTO);
			filledVsUnfilled.put("filledFields", filledVsUnfilled.get("filledFields") + section.get("filledFields"));
			filledVsUnfilled.put("totalFields", filledVsUnfilled.get("totalFields") + section.get("totalFields"));
		}

		return appPagesSectionDTOSet;

	}

}
