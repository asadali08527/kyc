package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.enums.DsrProfileStatus;
import co.yabx.kyc.app.fullKyc.dto.UserDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO implements Serializable {
	private String message;
	private String statusCode;
	private DsrProfileStatus dsrProfileStatus;
	private List<RetailersDTO> retailers;
	private UserDTO retailerInfo;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public DsrProfileStatus getDsrProfileStatus() {
		return dsrProfileStatus;
	}

	public void setDsrProfileStatus(DsrProfileStatus dsrProfileStatus) {
		this.dsrProfileStatus = dsrProfileStatus;
	}

	public List<RetailersDTO> getRetailers() {
		return retailers;
	}

	public void setRetailers(List<RetailersDTO> retailers) {
		this.retailers = retailers;
	}

	public UserDTO getRetailerInfo() {
		return retailerInfo;
	}

	public void setRetailerInfo(UserDTO retailerInfo) {
		this.retailerInfo = retailerInfo;
	}

	@Override
	public String toString() {
		return "ResponseDTO [message=" + message + ", statusCode=" + statusCode + ", dsrProfileStatus="
				+ dsrProfileStatus + ", retailers=" + retailers + ", retailerInfo=" + retailerInfo + "]";
	}

}
