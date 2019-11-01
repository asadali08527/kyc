package co.yabx.kyc.app.enums;

public enum FunctionalityType {
	Copy, Compare;

	public static FunctionalityType getFunctionalityType(String value) {
		if (value.equalsIgnoreCase("COPY")) {
			return FunctionalityType.Copy;
		} else if (value.equalsIgnoreCase("COMPARE")) {
			return FunctionalityType.Compare;
		} else {
			return FunctionalityType.valueOf(value);
		}
	}

	@Override
	public String toString() {
		if (super.toString().equals("Compare")) {
			return "COMPARE";
		} else if (super.toString().equals("Copy")) {
			return "COPY";
		} else {
			return super.toString();
		}
	}
}
