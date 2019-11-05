package co.yabx.kyc.app.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerifyOtpDTO implements Serializable {
	private String dsrMSISDN;
	private String otp;
	private String email;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dsrMSISDN == null) ? 0 : dsrMSISDN.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((otp == null) ? 0 : otp.hashCode());
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
		VerifyOtpDTO other = (VerifyOtpDTO) obj;
		if (dsrMSISDN == null) {
			if (other.dsrMSISDN != null)
				return false;
		} else if (!dsrMSISDN.equals(other.dsrMSISDN))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (otp == null) {
			if (other.otp != null)
				return false;
		} else if (!otp.equals(other.otp))
			return false;
		return true;
	}

}
