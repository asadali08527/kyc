package co.yabx.kyc.app.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.enums.KycStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RetailersDTO implements Serializable {
	private Long retailerId;
	private String retailerName;
	private String kycStatus;
	private String comments;
	private String dsrMsisdn;
	private String submitButton;

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

	public String getKycStatus() {
		return kycStatus;
	}

	public void setKycStatus(String kycStatus) {
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

	public String getSubmitButton() {
		return submitButton;
	}

	public void setSubmitButton(String submitButton) {
		this.submitButton = submitButton;
	}

}
