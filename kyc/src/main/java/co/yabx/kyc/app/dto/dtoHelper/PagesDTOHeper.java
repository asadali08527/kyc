package co.yabx.kyc.app.dto.dtoHelper;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.yabx.kyc.app.dto.ActionDTO;
import co.yabx.kyc.app.dto.PagesDTO;
import co.yabx.kyc.app.dto.SectionsDTO;
import co.yabx.kyc.app.entities.Pages;
import co.yabx.kyc.app.entities.Sections;
import co.yabx.kyc.app.enums.UserType;
import co.yabx.kyc.app.fullKyc.entity.AddressDetails;
import co.yabx.kyc.app.fullKyc.entity.BankAccountDetails;
import co.yabx.kyc.app.fullKyc.entity.User;

public class PagesDTOHeper implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(PagesDTOHeper.class);

	public static PagesDTO prepareAppPagesDto(Pages pages, User retailers, User nominee,
			Set<AddressDetails> userAddressDetailsSet, Set<AddressDetails> nomineeAddressDetailsSet,
			Set<AddressDetails> businessAddressDetailsSet, Set<BankAccountDetails> userBankAccountDetailsSet,
			Set<BankAccountDetails> nomineeBankAccountDetailsSet, Set<BankAccountDetails> businessBankAccountDetailsSet,
			String type) {
		PagesDTO appPagesDTO = new PagesDTO();
		Map<String, Integer> filledVsUnfilled = new HashMap<String, Integer>();
		filledVsUnfilled.put("filledFields", 0);
		filledVsUnfilled.put("totalFields", 0);
		Set<Sections> appPagesSectionsSet = pages.getSections();
		if (appPagesSectionsSet != null && !appPagesSectionsSet.isEmpty()) {
			List<SectionsDTO> appPagesSectionSet = SectionDtoHelper.getSections(appPagesSectionsSet, retailers,
					filledVsUnfilled, nominee, userAddressDetailsSet, nomineeAddressDetailsSet,
					businessAddressDetailsSet, userBankAccountDetailsSet, nomineeBankAccountDetailsSet,
					businessBankAccountDetailsSet);
			appPagesDTO.setSections(appPagesSectionSet.stream().sorted(Comparator.comparing(SectionsDTO::getSectionId))
					.collect(Collectors.toList()));
			appPagesDTO.setEnable(pages.isEnable());
			appPagesDTO.setPageId(pages.getPageId());
			// appPagesDTO.setPageName(pages.getPageName());
			appPagesDTO.setPageTitle(pages.getPageTitle());
			appPagesDTO.setTotalFields(filledVsUnfilled.get("totalFields"));
			appPagesDTO.setFilledFields(filledVsUnfilled.get("filledFields"));
			appPagesDTO.setDisplayOrder(pages.getDisplayOrder());
			if (UserType.RETAILERS.toString().equals(type))
				appPagesDTO.setRetailerId(retailers != null ? retailers.getId() : null);
			else if (UserType.DISTRIBUTORS.toString().equals(type))
				appPagesDTO.setDsrId(retailers != null ? retailers.getId() : null);
			appPagesDTO.setPageCompletion(appPagesDTO.getTotalFields() != 0
					? ((appPagesDTO.getFilledFields() * 100) / appPagesDTO.getTotalFields()) + "%"
					: "0%");
			appPagesDTO.setAction(prepareActions());
		}
		return appPagesDTO;
	}

	private static ActionDTO prepareActions() {
		ActionDTO actionDTO = new ActionDTO();
		actionDTO.setData("Submit");
		actionDTO.setName("submitForm");
		actionDTO.setType("submit");
		return actionDTO;
	}

}
