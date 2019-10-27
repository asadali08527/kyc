package co.yabx.kyc.app.enums;

public enum AddressProof {
	DRIVING_LICENSE, PASSPORT;

	public static AddressProof getAddressProof(String value) {
		if (value.equalsIgnoreCase("DRIVING LICENSE")) {
			return AddressProof.DRIVING_LICENSE;
		} else {
			return AddressProof.valueOf(value);
		}
	}

	@Override
	public String toString() {
		if (super.toString().equals("DRIVING_LICENSE")) {
			return "DRIVING LICENSE";
		} else {
			return super.toString();
		}
	}
}
