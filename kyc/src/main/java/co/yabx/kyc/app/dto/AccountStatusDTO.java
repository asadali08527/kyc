package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.entity.AccountStatus;
import co.yabx.kyc.app.entity.KycVerified;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountStatusDTO implements Serializable {

	private String msisdn;

	private boolean isKycAvailable;

	private KycVerified kycVerified;

	private String amlCftStatus;

	private AccountStatus accountStatus;

	private String transactionStatus;

	private String createdBy;

	private String updatedBy;

	private String updateReason;

	private Date createdAt;

	private Date updatedAt;

	private List<AccountStatusTrackerDTO> accountStatusTrackerDTO;

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public boolean isKycAvailable() {
		return isKycAvailable;
	}

	public void setKycAvailable(boolean isKycAvailable) {
		this.isKycAvailable = isKycAvailable;
	}

	public String getAmlCftStatus() {
		return amlCftStatus;
	}

	public void setAmlCftStatus(String amlCftStatus) {
		this.amlCftStatus = amlCftStatus;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public List<AccountStatusTrackerDTO> getAccountStatusTrackerDTO() {
		return accountStatusTrackerDTO;
	}

	public void setAccountStatusTrackerDTO(List<AccountStatusTrackerDTO> accountStatusTrackerDTO) {
		this.accountStatusTrackerDTO = accountStatusTrackerDTO;
	}

	public KycVerified getKycVerified() {
		return kycVerified;
	}

	public void setKycVerified(KycVerified kycVerified) {
		this.kycVerified = kycVerified;
	}

	public String getUpdateReason() {
		return updateReason;
	}

	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "AccountStatusDTO [msisdn=" + msisdn + ", isKycAvailable=" + isKycAvailable + ", kycVerified="
				+ kycVerified + ", amlCftStatus=" + amlCftStatus + ", accountStatus=" + accountStatus
				+ ", transactionStatus=" + transactionStatus + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy
				+ ", accountStatusTrackerDTO=" + accountStatusTrackerDTO + "]";
	}

}