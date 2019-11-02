package co.yabx.kyc.app.enums;

public enum FunctionalityType {
	Copy, Compare, Validation;

	public static FunctionalityType getFunctionalityType(String value) {
		if (value.equalsIgnoreCase("COPY")) {
			return FunctionalityType.Copy;
		} else if (value.equalsIgnoreCase("COMPARE")) {
			return FunctionalityType.Compare;
		} else if (value.equalsIgnoreCase("VALIDATION")) {
			return FunctionalityType.Validation;
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
		} else if (super.toString().equals("Validation")) {
			return "VALIDATION";
		} else {
			return super.toString();
		}
	}
}
