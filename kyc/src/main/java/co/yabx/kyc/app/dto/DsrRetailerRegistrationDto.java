package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DsrRetailerRegistrationDto implements Serializable {

	private List<UserDto> dsr;

	public List<UserDto> getDsr() {
		return dsr;
	}

	public void setDsr(List<UserDto> dsr) {
		this.dsr = dsr;
	}

	@Override
	public String toString() {
		return "DsrRetailerRegistrationDto [dsr=" + dsr + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dsr == null) ? 0 : dsr.hashCode());
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
		DsrRetailerRegistrationDto other = (DsrRetailerRegistrationDto) obj;
		if (dsr == null) {
			if (other.dsr != null)
				return false;
		} else if (!dsr.equals(other.dsr))
			return false;
		return true;
	}

}
