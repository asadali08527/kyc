package co.yabx.kyc.app.util;

import co.yabx.kyc.app.service.AppConfigService;

public class MaskUtil {

	public static String showDataInXXXFormat(String data, String field, boolean masked) {
		if (masked) {
			String maskedData = "";
			if (neitherBlankNorNull(data)) {
				int dataLength = data.length();
				boolean processInitialCharacters = false;
				boolean processed = false;
				String terminal = data;
				int visibleDigitsLength = SpringUtil.bean(AppConfigService.class)
						.getIntProperty("LETTER_TO_BE_SHOWN_FOR_" + field + "_IN_KYC", 2);
				if (SpringUtil.bean(AppConfigService.class)
						.getProperty("FILED_WHOSE_STARTING_CHARACTERS_TO_BE_SHOWN", "email").contains(field)) {
					// fetching first n letter
					data = data.substring(0, visibleDigitsLength).trim();
					terminal = terminal.substring(dataLength - visibleDigitsLength, dataLength).trim();
					processInitialCharacters = true;
				} else {
					// fetching last n letter
					data = data.substring(dataLength - visibleDigitsLength, dataLength).trim();
				}
				for (int i = 0; i < dataLength; i++) {
					if (i != dataLength - visibleDigitsLength) {
						/*
						 * if (processInitialCharacters && i < visibleDigitsLength) { maskedData = data
						 * + "*"; continue; } else
						 */ if (processInitialCharacters && i >= visibleDigitsLength) {
							maskedData = data;
							processInitialCharacters = false;
							processed = true;
						}
						maskedData = maskedData + "*";
					} else if (processed) {
						maskedData = maskedData + terminal;
						return maskedData;
					} else {
						maskedData = maskedData + data;
						return maskedData;
					}
				}

			}

			return maskedData;
		}
		return data;
	}

	private static boolean neitherBlankNorNull(String data) {
		if (data != null && !data.isEmpty())
			return true;
		return false;
	}
}
