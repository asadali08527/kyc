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

}
