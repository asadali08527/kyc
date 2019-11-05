package co.yabx.kyc.app.fullKyc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "customer_informations", indexes = { @Index(name = "loan_amount", columnList = "loan_amount"),
		@Index(name = "customer_id", columnList = "customer_id") })
public class CustomerInformations implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "business_name")
	private String businessName;

	/**
	 * NAME OF THE PROPRIETOR/PARTNERS/MD/DIRECTOR
	 */
	@Column(name = "partner_or_director_name")
	private String directorOrPartnerName;

	@OneToMany(mappedBy = "customerInformations", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<AddressDetails> addressDetails;

	@OneToMany(mappedBy = "customerInformations", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<BankAccountDetails> bankAccountDetails;

	@Column(name = "unit_office")
	private String unitOffice;

	@Column(name = "unit_code")
	private String unitCode;

	@Column(name = "area")
	private String area;

	@Column(name = "territory")
	private String territory;

	@Column(name = "ro_name")
	private String nameOfRO;

	@Column(name = "am_name")
	private String nameOfAM;

	@Column(name = "tm_name")
	private String nameOfTM;

	@Column(name = "loan_amount")
	private double loanAmount;

	@Column(name = "loan_amount_in_word")
	private String loanAmountInWords;

	@Column(name = "period")
	private String period;

	/**
	 * First or Second loan
	 */
	@Column(name = "relationship")
	private String relationship;
	/**
	 * customer id number to be shared by bank
	 */
	@Column(name = "customer_id")
	private String cusomerId;

	@Column(name = "nature_code")
	private String natureCode;

	@Column(name = "sector_code")
	private String sectorCode;

	@Column(name = "economic_purpose_code")
	private String economicPurposeCode;

	@Column(name = "bank_defined_code")
	private String bankDefinedCode;

	@Column(name = "security_code")
	private String securityCode;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getDirectorOrPartnerName() {
		return directorOrPartnerName;
	}

	public void setDirectorOrPartnerName(String directorOrPartnerName) {
		this.directorOrPartnerName = directorOrPartnerName;
	}

	public Set<AddressDetails> getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(Set<AddressDetails> addressDetails) {
		this.addressDetails = addressDetails;
	}

	public Set<BankAccountDetails> getBankAccountDetails() {
		return bankAccountDetails;
	}

	public void setBankAccountDetails(Set<BankAccountDetails> bankAccountDetails) {
		this.bankAccountDetails = bankAccountDetails;
	}

	public String getUnitOffice() {
		return unitOffice;
	}

	public void setUnitOffice(String unitOffice) {
		this.unitOffice = unitOffice;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTerritory() {
		return territory;
	}

	public void setTerritory(String territory) {
		this.territory = territory;
	}

	public String getNameOfRO() {
		return nameOfRO;
	}

	public void setNameOfRO(String nameOfRO) {
		this.nameOfRO = nameOfRO;
	}

	public String getNameOfAM() {
		return nameOfAM;
	}

	public void setNameOfAM(String nameOfAM) {
		this.nameOfAM = nameOfAM;
	}

	public String getNameOfTM() {
		return nameOfTM;
	}

	public void setNameOfTM(String nameOfTM) {
		this.nameOfTM = nameOfTM;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getLoanAmountInWords() {
		return loanAmountInWords;
	}

	public void setLoanAmountInWords(String loanAmountInWords) {
		this.loanAmountInWords = loanAmountInWords;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getCusomerId() {
		return cusomerId;
	}

	public void setCusomerId(String cusomerId) {
		this.cusomerId = cusomerId;
	}

	public String getNatureCode() {
		return natureCode;
	}

	public void setNatureCode(String natureCode) {
		this.natureCode = natureCode;
	}

	public String getSectorCode() {
		return sectorCode;
	}

	public void setSectorCode(String sectorCode) {
		this.sectorCode = sectorCode;
	}

	public String getEconomicPurposeCode() {
		return economicPurposeCode;
	}

	public void setEconomicPurposeCode(String economicPurposeCode) {
		this.economicPurposeCode = economicPurposeCode;
	}

	public String getBankDefinedCode() {
		return bankDefinedCode;
	}

	public void setBankDefinedCode(String bankDefinedCode) {
		this.bankDefinedCode = bankDefinedCode;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
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
		result = prime * result + ((addressDetails == null) ? 0 : addressDetails.hashCode());
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((bankAccountDetails == null) ? 0 : bankAccountDetails.hashCode());
		result = prime * result + ((bankDefinedCode == null) ? 0 : bankDefinedCode.hashCode());
		result = prime * result + ((businessName == null) ? 0 : businessName.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((cusomerId == null) ? 0 : cusomerId.hashCode());
		result = prime * result + ((directorOrPartnerName == null) ? 0 : directorOrPartnerName.hashCode());
		result = prime * result + ((economicPurposeCode == null) ? 0 : economicPurposeCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(loanAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((loanAmountInWords == null) ? 0 : loanAmountInWords.hashCode());
		result = prime * result + ((nameOfAM == null) ? 0 : nameOfAM.hashCode());
		result = prime * result + ((nameOfRO == null) ? 0 : nameOfRO.hashCode());
		result = prime * result + ((nameOfTM == null) ? 0 : nameOfTM.hashCode());
		result = prime * result + ((natureCode == null) ? 0 : natureCode.hashCode());
		result = prime * result + ((period == null) ? 0 : period.hashCode());
		result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
		result = prime * result + ((sectorCode == null) ? 0 : sectorCode.hashCode());
		result = prime * result + ((securityCode == null) ? 0 : securityCode.hashCode());
		result = prime * result + ((territory == null) ? 0 : territory.hashCode());
		result = prime * result + ((unitCode == null) ? 0 : unitCode.hashCode());
		result = prime * result + ((unitOffice == null) ? 0 : unitOffice.hashCode());
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
		CustomerInformations other = (CustomerInformations) obj;
		if (addressDetails == null) {
			if (other.addressDetails != null)
				return false;
		} else if (!addressDetails.equals(other.addressDetails))
			return false;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (bankAccountDetails == null) {
			if (other.bankAccountDetails != null)
				return false;
		} else if (!bankAccountDetails.equals(other.bankAccountDetails))
			return false;
		if (bankDefinedCode == null) {
			if (other.bankDefinedCode != null)
				return false;
		} else if (!bankDefinedCode.equals(other.bankDefinedCode))
			return false;
		if (businessName == null) {
			if (other.businessName != null)
				return false;
		} else if (!businessName.equals(other.businessName))
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
		if (cusomerId == null) {
			if (other.cusomerId != null)
				return false;
		} else if (!cusomerId.equals(other.cusomerId))
			return false;
		if (directorOrPartnerName == null) {
			if (other.directorOrPartnerName != null)
				return false;
		} else if (!directorOrPartnerName.equals(other.directorOrPartnerName))
			return false;
		if (economicPurposeCode == null) {
			if (other.economicPurposeCode != null)
				return false;
		} else if (!economicPurposeCode.equals(other.economicPurposeCode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(loanAmount) != Double.doubleToLongBits(other.loanAmount))
			return false;
		if (loanAmountInWords == null) {
			if (other.loanAmountInWords != null)
				return false;
		} else if (!loanAmountInWords.equals(other.loanAmountInWords))
			return false;
		if (nameOfAM == null) {
			if (other.nameOfAM != null)
				return false;
		} else if (!nameOfAM.equals(other.nameOfAM))
			return false;
		if (nameOfRO == null) {
			if (other.nameOfRO != null)
				return false;
		} else if (!nameOfRO.equals(other.nameOfRO))
			return false;
		if (nameOfTM == null) {
			if (other.nameOfTM != null)
				return false;
		} else if (!nameOfTM.equals(other.nameOfTM))
			return false;
		if (natureCode == null) {
			if (other.natureCode != null)
				return false;
		} else if (!natureCode.equals(other.natureCode))
			return false;
		if (period == null) {
			if (other.period != null)
				return false;
		} else if (!period.equals(other.period))
			return false;
		if (relationship == null) {
			if (other.relationship != null)
				return false;
		} else if (!relationship.equals(other.relationship))
			return false;
		if (sectorCode == null) {
			if (other.sectorCode != null)
				return false;
		} else if (!sectorCode.equals(other.sectorCode))
			return false;
		if (securityCode == null) {
			if (other.securityCode != null)
				return false;
		} else if (!securityCode.equals(other.securityCode))
			return false;
		if (territory == null) {
			if (other.territory != null)
				return false;
		} else if (!territory.equals(other.territory))
			return false;
		if (unitCode == null) {
			if (other.unitCode != null)
				return false;
		} else if (!unitCode.equals(other.unitCode))
			return false;
		if (unitOffice == null) {
			if (other.unitOffice != null)
				return false;
		} else if (!unitOffice.equals(other.unitOffice))
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
