package co.yabx.kyc.app.miniKyc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import co.yabx.kyc.app.entity.entityListener.AccountStatusesTrackerListener;
import co.yabx.kyc.app.enums.AccountStatus;
import co.yabx.kyc.app.enums.AmlCftStatus;
import co.yabx.kyc.app.enums.KycStatus;

@Entity
@EntityListeners({ AccountStatusesTrackerListener.class })
@Table(name = "account_statuses", indexes = { @Index(name = "kyc_verified", columnList = "kyc_verified"),
		@Index(name = "aml_cft_status", columnList = "aml_cft_status"),
		@Index(name = "account_status", columnList = "account_status") })
public class AccountStatuses implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "msisdn", unique = true, nullable = false)
	private String msisdn;

	@Column(name = "is_kyc_available", nullable = false, columnDefinition = "boolean default false")
	private boolean isKycAvailable;

	@Column(name = "kyc_verified", nullable = false, columnDefinition = "varchar(32) default 'NO'")
	@Enumerated(value = EnumType.STRING)
	private KycStatus KycVerified;

	@Column(name = "aml_cft_status", nullable = false, columnDefinition = "varchar(32) default 'NO'")
	@Enumerated(value = EnumType.STRING)
	private AmlCftStatus amlCftStatus;

	@Column(name = "account_status", nullable = false, columnDefinition = "varchar(32) default 'NEW'")
	@Enumerated(value = EnumType.STRING)
	private AccountStatus accountStatus;

	@Column(name = "transaction_status")
	private String transactionStatus;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "update_by")
	private String updatedBy;

	@Column(name = "reason")
	private String updateReason;

	public String getCreatedBy() {
		return createdBy;
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

	@PrePersist
	protected void insertDates() {
		if (createdAt == null) {
			createdAt = new Date();
		}
		if (updatedAt == null) {
			updatedAt = new Date();
		}
	}

	@PreUpdate
	protected void updateTime() {
		updatedAt = new Date();
	}

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

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

	public KycStatus getKycVerified() {
		return KycVerified;
	}

	public void setKycVerified(KycStatus kycVerified) {
		KycVerified = kycVerified;
	}

	public String getUpdateReason() {
		return updateReason;
	}

	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}

	public AmlCftStatus getAmlCftStatus() {
		return amlCftStatus;
	}

	public void setAmlCftStatus(AmlCftStatus amlCftStatus) {
		this.amlCftStatus = amlCftStatus;
	}

	@Override
	public String toString() {
		return "AccountStatuses [msisdn=" + msisdn + ", isKycAvailable=" + isKycAvailable + ", KycVerified="
				+ KycVerified + ", amlCftStatus=" + amlCftStatus + ", accountStatus=" + accountStatus
				+ ", transactionStatus=" + transactionStatus + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy
				+ ", updateReason=" + updateReason + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((KycVerified == null) ? 0 : KycVerified.hashCode());
		result = prime * result + ((accountStatus == null) ? 0 : accountStatus.hashCode());
		result = prime * result + ((amlCftStatus == null) ? 0 : amlCftStatus.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + (isKycAvailable ? 1231 : 1237);
		result = prime * result + ((msisdn == null) ? 0 : msisdn.hashCode());
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
		AccountStatuses other = (AccountStatuses) obj;
		if (KycVerified != other.KycVerified)
			return false;
		if (accountStatus != other.accountStatus)
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
		if (msisdn == null) {
			if (other.msisdn != null)
				return false;
		} else if (!msisdn.equals(other.msisdn))
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
