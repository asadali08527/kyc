package co.yabx.kyc.app.enums;

public enum AddressType {
	PRESENT, PERMANNET, BUSINESS, OFFICE, BUSINESS_REGISTERED_ADDRESS, BUSINESS_OFFICE_ADDRESS,
	BUSINESS_FACTORY_ADDRESS, OTHERS;

	public static AddressType getAddressType(String value) {
		if (value.equalsIgnoreCase("BUSINESS REGISTERED ADDRESS")) {
			return AddressType.BUSINESS_REGISTERED_ADDRESS;
		} else if (value.equalsIgnoreCase("BUSINESS OFFICE ADDRESS")) {
			return AddressType.BUSINESS_OFFICE_ADDRESS;
		} else if (value.equalsIgnoreCase("BUSINESS FACTORY ADDRESS")) {
			return AddressType.BUSINESS_FACTORY_ADDRESS;
		} else {
			return AddressType.valueOf(value);
		}
	}

	@Override
	public String toString() {
		if (super.toString().equals("BUSINESS_REGISTERED_ADDRESS")) {
			return "BUSINESS REGISTERED ADDRESS";
		} else if (super.toString().equals("BUSINESS_OFFICE_ADDRESS")) {
			return "BUSINESS OFFICE ADDRESS";
		} else if (super.toString().equals("BUSINESS_FACTORY_ADDRESS")) {
			return "BUSINESS FACTORY ADDRESS";
		} else {
			return super.toString();
		}
	}
}
