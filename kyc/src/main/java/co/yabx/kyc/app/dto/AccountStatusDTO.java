package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.enums.AccountStatus;
import co.yabx.kyc.app.enums.AmlCftStatus;
import co.yabx.kyc.app.enums.KycStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountStatusDTO implements Serializable {

	private String msisdn;

	private boolean isKycAvailable;

	private KycStatus kycVerified;

	private AmlCftStatus amlCftStatus;

	private AccountStatus accountStatus;

	private String transactionStatus;

	private String createdBy;

	private String updatedBy;

	private String updateReason;

	private Date createdAt;

	private Date updatedAt;

	private Date suspensionThresholdTime;

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

	public AmlCftStatus getAmlCftStatus() {
		return amlCftStatus;
	}

	public void setAmlCftStatus(AmlCftStatus amlCftStatus) {
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

	public KycStatus getKycVerified() {
		return kycVerified;
	}

	public void setKycVerified(KycStatus kycVerified) {
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

	public Date getSuspensionThresholdTime() {
		return suspensionThresholdTime;
	}

	public void setSuspensionThresholdTime(Date suspensionThresholdTime) {
		this.suspensionThresholdTime = suspensionThresholdTime;
	}

	@Override
	public String toString() {
		return "AccountStatusDTO [msisdn=" + msisdn + ", isKycAvailable=" + isKycAvailable + ", kycVerified="
				+ kycVerified + ", amlCftStatus=" + amlCftStatus + ", accountStatus=" + accountStatus
				+ ", transactionStatus=" + transactionStatus + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy
				+ ", updateReason=" + updateReason + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", suspensionThresholdTime=" + suspensionThresholdTime + ", accountStatusTrackerDTO="
				+ accountStatusTrackerDTO + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountStatus == null) ? 0 : accountStatus.hashCode());
		result = prime * result + ((accountStatusTrackerDTO == null) ? 0 : accountStatusTrackerDTO.hashCode());
		result = prime * result + ((amlCftStatus == null) ? 0 : amlCftStatus.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + (isKycAvailable ? 1231 : 1237);
		result = prime * result + ((kycVerified == null) ? 0 : kycVerified.hashCode());
		result = prime * result + ((msisdn == null) ? 0 : msisdn.hashCode());
		result = prime * result + ((suspensionThresholdTime == null) ? 0 : suspensionThresholdTime.hashCode());
		result = prime * result + ((transactionStatus == null) ? 0 : transactionStatus.hashCode());
		result = prime * result + ((updateReason == null) ? 0 : updateReason.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
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
		AccountStatusDTO other = (AccountStatusDTO) obj;
		if (accountStatus != other.accountStatus)
			return false;
		if (accountStatusTrackerDTO == null) {
			if (other.accountStatusTrackerDTO != null)
				return false;
		} else if (!accountStatusTrackerDTO.equals(other.accountStatusTrackerDTO))
			return false;
		if (amlCftStatus != other.amlCftStatus)
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (isKycAvailable != other.isKycAvailable)
			return false;
		if (kycVerified != other.kycVerified)
			return false;
		if (msisdn == null) {
			if (other.msisdn != null)
				return false;
		} else if (!msisdn.equals(other.msisdn))
			return false;
		if (suspensionThresholdTime == null) {
			if (other.suspensionThresholdTime != null)
				return false;
		} else if (!suspensionThresholdTime.equals(other.suspensionThresholdTime))
			return false;
		if (transactionStatus == null) {
			if (other.transactionStatus != null)
				return false;
		} else if (!transactionStatus.equals(other.transactionStatus))
			return false;
		if (updateReason == null) {
			if (other.updateReason != null)
				return false;
		} else if (!updateReason.equals(other.updateReason))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		return true;
	}

}
