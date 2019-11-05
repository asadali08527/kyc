package co.yabx.kyc.app.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.enums.KycStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RetailersDTO implements Serializable {
	private Long retailerId;
	private String retailerName;
	private KycStatus kycStatus;
	private String comments;
	private String dsrMsisdn;

	public Long getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(Long retailerId) {
		this.retailerId = retailerId;
	}

	public String getDsrMsisdn() {
		return dsrMsisdn;
	}

	public void setDsrMsisdn(String dsrMsisdn) {
		this.dsrMsisdn = dsrMsisdn;
	}

	public KycStatus getKycStatus() {
		return kycStatus;
	}

	public void setKycStatus(KycStatus kycStatus) {
		this.kycStatus = kycStatus;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRetailerName() {
		return retailerName;
	}

	public void setRetailerName(String retailerName) {
		this.retailerName = retailerName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((dsrMsisdn == null) ? 0 : dsrMsisdn.hashCode());
		result = prime * result + ((kycStatus == null) ? 0 : kycStatus.hashCode());
		result = prime * result + ((retailerId == null) ? 0 : retailerId.hashCode());
		result = prime * result + ((retailerName == null) ? 0 : retailerName.hashCode());
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
		RetailersDTO other = (RetailersDTO) obj;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (dsrMsisdn == null) {
			if (other.dsrMsisdn != null)
				return false;
		} else if (!dsrMsisdn.equals(other.dsrMsisdn))
			return false;
		if (kycStatus != other.kycStatus)
			return false;
		if (retailerId == null) {
			if (other.retailerId != null)
				return false;
		} else if (!retailerId.equals(other.retailerId))
			return false;
		if (retailerName == null) {
			if (other.retailerName != null)
				return false;
		} else if (!retailerName.equals(other.retailerName))
			return false;
		return true;
	}

}
