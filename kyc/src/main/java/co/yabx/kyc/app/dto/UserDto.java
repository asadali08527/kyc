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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((msisdn == null) ? 0 : msisdn.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((retailer == null) ? 0 : retailer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDto other = (UserDto) obj;
		if (msisdn == null) {
			if (other.msisdn != null)
				return false;
		} else if (!msisdn.equals(other.msisdn))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (retailer == null) {
			if (other.retailer != null)
				return false;
		} else if (!retailer.equals(other.retailer))
			return false;
		return true;
	}

}
