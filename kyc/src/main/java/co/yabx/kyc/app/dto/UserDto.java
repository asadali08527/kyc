package co.yabx.kyc.app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
	private String name;
	private String msisdn;
	private List<UserDto> retailer;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public List<UserDto> getRetailer() {
		return retailer;
	}

	public void setRetailer(List<UserDto> retailer) {
		this.retailer = retailer;
	}

	@Override
	public String toString() {
		return "UserDto [name=" + name + ", msisdn=" + msisdn + ", retailer=" + retailer + "]";
	}

}
