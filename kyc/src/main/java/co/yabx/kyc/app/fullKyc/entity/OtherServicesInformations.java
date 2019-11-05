package co.yabx.kyc.app.fullKyc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import co.yabx.kyc.app.enums.Frequency;
import co.yabx.kyc.app.enums.Status;

@Entity
@Table(name = "other_services", indexes = { @Index(name = "sms_banking", columnList = "sms_banking"),
		@Index(name = "e_statement", columnList = "e_statement") })
public class OtherServicesInformations implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name_on_card")
	private String nameToBePrintedOnCard;

	@Column(name = "statement_frequency")
	private Frequency accountStatementFrequency;

	@Column(name = "sms_banking")
	private Status smsBankingfacility;

	@Column(name = "e_statement")
	private Status eStatementFacility;

	@Column(name = "msisdn")
	private String phoneNumber;

	@Column(name = "email_facility")
	private Status emailfacility;

	@Column(name = "alternate_email")
	private String alternateEmail;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "update_by")
	private String updatedBy;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@PrePersist
	private void prePersist() {
		if (createdAt == null) {
			createdAt = new Date();
			updatedAt = new Date();
		}
	}

	@PreUpdate
	private void preUpdate() {
		updatedAt = new Date();

	}

	public String getNameToBePrintedOnCard() {
		return nameToBePrintedOnCard;
	}

	public void setNameToBePrintedOnCard(String nameToBePrintedOnCard) {
		this.nameToBePrintedOnCard = nameToBePrintedOnCard;
	}

	public Frequency getAccountStatementFrequency() {
		return accountStatementFrequency;
	}

	public void setAccountStatementFrequency(Frequency accountStatementFrequency) {
		this.accountStatementFrequency = accountStatementFrequency;
	}

	public Status getSmsBankingfacility() {
		return smsBankingfacility;
	}

	public void setSmsBankingfacility(Status smsBankingfacility) {
		this.smsBankingfacility = smsBankingfacility;
	}

	public Status geteStatementFacility() {
		return eStatementFacility;
	}

	public void seteStatementFacility(Status eStatementFacility) {
		this.eStatementFacility = eStatementFacility;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Status getEmailfacility() {
		return emailfacility;
	}

	public void setEmailfacility(Status emailfacility) {
		this.emailfacility = emailfacility;
	}

	public String getAlternateEmail() {
		return alternateEmail;
	}

	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountStatementFrequency == null) ? 0 : accountStatementFrequency.hashCode());
		result = prime * result + ((alternateEmail == null) ? 0 : alternateEmail.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((eStatementFacility == null) ? 0 : eStatementFacility.hashCode());
		result = prime * result + ((emailfacility == null) ? 0 : emailfacility.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nameToBePrintedOnCard == null) ? 0 : nameToBePrintedOnCard.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((smsBankingfacility == null) ? 0 : smsBankingfacility.hashCode());
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
		OtherServicesInformations other = (OtherServicesInformations) obj;
		if (accountStatementFrequency != other.accountStatementFrequency)
			return false;
		if (alternateEmail == null) {
			if (other.alternateEmail != null)
				return false;
		} else if (!alternateEmail.equals(other.alternateEmail))
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
		if (eStatementFacility != other.eStatementFacility)
			return false;
		if (emailfacility != other.emailfacility)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nameToBePrintedOnCard == null) {
			if (other.nameToBePrintedOnCard != null)
				return false;
		} else if (!nameToBePrintedOnCard.equals(other.nameToBePrintedOnCard))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (smsBankingfacility != other.smsBankingfacility)
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
