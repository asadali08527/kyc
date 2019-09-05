package co.yabx.kyc.app.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncoderDecoderUtil {

	public static String base64Encode(String data) {
		if (data != null)
			return new String(base64Encode(data.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
		else
			return data;
	}

	public static byte[] base64Encode(byte[] byteArray) {
		return Base64.getEncoder().encode(byteArray);
	}

	public static String base64Decode(String str) {
		return new String(base64Decode(str.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
	}

	public static byte[] base64Decode(byte[] byteArray) {
		return Base64.getDecoder().decode(byteArray);
	}
}
