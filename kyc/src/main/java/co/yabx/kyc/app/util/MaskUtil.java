package co.yabx.kyc.app.util;

import org.springframework.beans.factory.annotation.Autowired;

import co.yabx.kyc.app.service.AppConfigService;

public class MaskUtil {

	public static String showDataInXXXFormat(String data, String field) {

		String maskedData = "";
		if (neitherBlankNorNull(data)) {
			int dataLength = data.length();
			int visibleDigitsLength = SpringUtil.bean(AppConfigService.class)
					.getIntProperty("LETTER_TO_BE_SHOWN_FOR_" + field + "_IN_KYC", 2);
			// fetching last n letter
			data = data.substring(dataLength - visibleDigitsLength, dataLength).trim();
			for (int i = 0; i < dataLength; i++) {
				if (i != dataLength - visibleDigitsLength) {
					maskedData = maskedData + "*";
				} else {
					maskedData = maskedData + data;
					return maskedData;
				}
			}

		}
		return maskedData;
	}

	private static boolean neitherBlankNorNull(String data) {
		if (data != null && !data.isEmpty())
			return true;
		return false;
	}
}
