package co.yabx.kyc.app.fullKyc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import co.yabx.kyc.app.enums.BankAccountIdentifier;
import co.yabx.kyc.app.enums.BankAccountType;
import co.yabx.kyc.app.enums.Currency;
import co.yabx.kyc.app.enums.ModeOfOperation;
import co.yabx.kyc.app.enums.TypeOfConcern;

@Entity
@Table(name = "bank_account_details", indexes = { @Index(name = "bank_name", columnList = "bank_name"),
		@Index(name = "account_number", columnList = "account_number") })
public class BankAccountDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "msisdn")
	private String msisdn;

	@Column(name = "account_title")
	private String accountTitle;

	@Column(name = "concern_type", length = 100, columnDefinition = "varchar(32) default 'Proprietorship' ")
	@Enumerated(value = EnumType.STRING)
	private TypeOfConcern typeOfConcern;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "account_number")
	private Long accountNumber;

	@Column(name = "branch")
	private String branch;

	@Column(name = "purpose_of_account", length = 100, columnDefinition = "varchar(32) default 'Business'")
	private String accountPurpose;

	@Column(name = "currency", length = 100, columnDefinition = "varchar(32) default 'BDT'")
	@Enumerated(value = EnumType.STRING)
	private Currency currency;

	@Column(name = "operation_mode", length = 100, columnDefinition = "varchar(32) default 'Single'")
	@Enumerated(value = EnumType.STRING)
	private ModeOfOperation modeOfOperation;

	@Column(name = "account_type", length = 100, columnDefinition = "varchar(32) default 'Current'")
	@Enumerated(value = EnumType.STRING)
	private BankAccountType bankAccountType;

	@Column(name = "account_identifier")
	private BankAccountIdentifier bankAccountIdentifier;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	User user;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = BusinessDetails.class)
	BusinessDetails businessDetails;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = CustomerInformations.class)
	CustomerInformations customerInformations;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "update_by")
	private String updatedBy;

	public Long getId() {
		return id;
	}

	public BankAccountIdentifier getBankAccountIdentifier() {
		return bankAccountIdentifier;
	}

	public void setBankAccountIdentifier(BankAccountIdentifier bankAccountIdentifier) {
		this.bankAccountIdentifier = bankAccountIdentifier;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
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

	@PrePersist
	private void prePersist() {
		if (createdAt == null) {
			createdAt = new Date();
			updatedAt = new Date();
		}
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public BankAccountType getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(BankAccountType bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BusinessDetails getBusinessDetails() {
		return businessDetails;
	}

	public void setBusinessDetails(BusinessDetails businessDetails) {
		this.businessDetails = businessDetails;
	}

	@PreUpdate
	private void preUpdate() {
		updatedAt = new Date();

	}

	public String getAccountTitle() {
		return accountTitle;
	}

	public void setAccountTitle(String accountTitle) {
		this.accountTitle = accountTitle;
	}

	public TypeOfConcern getTypeOfConcern() {
		return typeOfConcern;
	}

	public void setTypeOfConcern(TypeOfConcern typeOfConcern) {
		this.typeOfConcern = typeOfConcern;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public ModeOfOperation getModeOfOperation() {
		return modeOfOperation;
	}

	public void setModeOfOperation(ModeOfOperation modeOfOperation) {
		this.modeOfOperation = modeOfOperation;
	}

	public CustomerInformations getCustomerInformations() {
		return customerInformations;
	}

	public void setCustomerInformations(CustomerInformations customerInformations) {
		this.customerInformations = customerInformations;
	}

	public String getAccountPurpose() {
		return accountPurpose;
	}

	public void setAccountPurpose(String accountPurpose) {
		this.accountPurpose = accountPurpose;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((accountPurpose == null) ? 0 : accountPurpose.hashCode());
		result = prime * result + ((accountTitle == null) ? 0 : accountTitle.hashCode());
		result = prime * result + ((bankAccountIdentifier == null) ? 0 : bankAccountIdentifier.hashCode());
		result = prime * result + ((bankAccountType == null) ? 0 : bankAccountType.hashCode());
		result = prime * result + ((bankName == null) ? 0 : bankName.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((businessDetails == null) ? 0 : businessDetails.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((customerInformations == null) ? 0 : customerInformations.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modeOfOperation == null) ? 0 : modeOfOperation.hashCode());
		result = prime * result + ((msisdn == null) ? 0 : msisdn.hashCode());
		result = prime * result + ((typeOfConcern == null) ? 0 : typeOfConcern.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		BankAccountDetails other = (BankAccountDetails) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (accountPurpose == null) {
			if (other.accountPurpose != null)
				return false;
		} else if (!accountPurpose.equals(other.accountPurpose))
			return false;
		if (accountTitle == null) {
			if (other.accountTitle != null)
				return false;
		} else if (!accountTitle.equals(other.accountTitle))
			return false;
		if (bankAccountIdentifier != other.bankAccountIdentifier)
			return false;
		if (bankAccountType != other.bankAccountType)
			return false;
		if (bankName == null) {
			if (other.bankName != null)
				return false;
		} else if (!bankName.equals(other.bankName))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (businessDetails == null) {
			if (other.businessDetails != null)
				return false;
		} else if (!businessDetails.equals(other.businessDetails))
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
		if (currency != other.currency)
			return false;
		if (customerInformations == null) {
			if (other.customerInformations != null)
				return false;
		} else if (!customerInformations.equals(other.customerInformations))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (modeOfOperation != other.modeOfOperation)
			return false;
		if (msisdn == null) {
			if (other.msisdn != null)
				return false;
		} else if (!msisdn.equals(other.msisdn))
			return false;
		if (typeOfConcern != other.typeOfConcern)
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
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
