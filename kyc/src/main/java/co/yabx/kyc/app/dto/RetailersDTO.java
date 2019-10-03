package co.yabx.kyc.app.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.enums.KycStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RetailersDTO implements Serializable {
	private String merchantId;
	private String merchantName;
	private KycStatus kycStatus;
	private String comments;
	private String retailerId;
	private String dsrMsisdn;

	public String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(String retailerId) {
		this.retailerId = retailerId;
	}

	public String getDsrMsisdn() {
		return dsrMsisdn;
	}

	public void setDsrMsisdn(String dsrMsisdn) {
		this.dsrMsisdn = dsrMsisdn;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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

}
