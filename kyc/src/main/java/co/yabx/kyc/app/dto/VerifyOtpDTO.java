package co.yabx.kyc.app.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerifyOtpDTO implements Serializable {
	private String dsrMSISDN;
	private String otp;

	public String getDsrMSISDN() {
		return dsrMSISDN;
	}

	public void setDsrMSISDN(String dsrMSISDN) {
		this.dsrMSISDN = dsrMSISDN;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	@Override
	public String toString() {
		return "VerifyOtpDTO [dsrMSISDN=" + dsrMSISDN + ", otp=" + otp + "]";
	}

}
