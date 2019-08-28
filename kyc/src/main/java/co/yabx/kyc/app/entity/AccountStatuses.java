package co.yabx.kyc.app.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "account_statuses", indexes = { @Index(name = "is_kyc_verified", columnList = "is_kyc_verified"),
		@Index(name = "aml_cft_status", columnList = "aml_cft_status"),
		@Index(name = "is_kyc_available", columnList = "is_kyc_available") })
public class AccountStatuses implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "msisdn", unique = true, nullable = false)
	private String msisdn;

	@Column(name = "is_kyc_available")
	private boolean isKycAvailable;

	@Column(name = "is_kyc_verified")
	private boolean isKycVerified;

	@Column(name = "aml_cft_status")
	private String amlCftStatus;

	@Column(name = "account_status")
	private String accountStatus;

	@Column(name = "transaction_status")
	private String transactionStatus;

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

	public boolean isKycVerified() {
		return isKycVerified;
	}

	public void setKycVerified(boolean isKycVerified) {
		this.isKycVerified = isKycVerified;
	}

	public String getAmlCftStatus() {
		return amlCftStatus;
	}

	public void setAmlCftStatus(String amlCftStatus) {
		this.amlCftStatus = amlCftStatus;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	@Override
	public String toString() {
		return "AccountStatuses [msisdn=" + msisdn + ", isKycAvailable=" + isKycAvailable + ", isKycVerified="
				+ isKycVerified + ", amlCftStatus=" + amlCftStatus + ", accountStatus=" + accountStatus
				+ ", transactionStatus=" + transactionStatus + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ "]";
	}

}
