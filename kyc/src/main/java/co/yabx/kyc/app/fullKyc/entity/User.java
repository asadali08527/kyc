package co.yabx.kyc.app.fullKyc.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import co.yabx.kyc.app.enums.Gender;
import co.yabx.kyc.app.enums.MaritalStatuses;
import co.yabx.kyc.app.enums.Nationality;
import co.yabx.kyc.app.enums.ResidentStatus;

@Entity
@Table(name = "users", indexes = { @Index(name = "msisdn", columnList = "msisdn"),
		@Index(name = "created_at", columnList = "created_at"),
		@Index(name = "created_by", columnList = "created_by") })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Least normalisation strategy
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(insertable = false, updatable = false)
	private String user_type;

	@Column(name = "msisdn")
	private String msisdn;

	@Column(name = "alternate_msisdn")
	private String alternateMobileNumber;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;
	/**
	 * Date of birth
	 */
	@Column(name = "dob")
	private String dob;
	/**
	 * Place of birth
	 */
	@Column(name = "pob")
	private String pob;

	@Column(name = "gender")
	private Gender gender;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "update_by")
	private String updatedBy;

	@Column(name = "email")
	private String email;

	@Column(name = "fathers_name")
	private String fathersName;

	@Column(name = "mothers_name")
	private String mothersName;

	@Column(name = "spouse_name")
	private String spouseName;

	@Column(name = "marital_status")
	private MaritalStatuses maritalStatus;

	@Column(name = "dependents")
	private Integer numberOfDependents;

	@Column(name = "sister_concerned_or_allied")
	private String sisterConcernedOrAllied;

	@Column(name = "nationality")
	private Nationality nationality;

	@Column(name = "resident_status")
	private ResidentStatus residentialStatus;

	@Column(name = "tax_identification_number")
	private String taxIdentificationNumber;

	@Column(name = "natonal_id_number")
	private String nationalIdNumber;

	@Column(name = "birth_registration_number")
	private String birthRegistrationNumber;

	@Column(name = "passport_number")
	private String passportNumber;

	@Column(name = "driving_license_number")
	private String drivingLicenseNumber;

	@Column(name = "passport_expiry_date")
	private String passportExpiryDate;

	@Column(name = "business_address")
	private String businessAddress;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<IncomeDetails> incomeDetails;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<IntroducerDetails> introducerDetails;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<WorkEducationDetails> workEducationDetails;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<LiabilitiesDetails> liabilitiesDetails;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<BusinessDetails> businessDetails;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<AddressDetails> addressDetails;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<BankAccountDetails> bankAccountDetails;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<AttachmentDetails> attachmentDetails;

	@Column(name = "minor")
	private String minor;

	@Column(name = "yabx_token")
	private String yabxToken;

	@Column(name = "secret_key")
	private String secretKey;

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Set<IncomeDetails> getIncomeDetails() {
		return incomeDetails;
	}

	public void setIncomeDetails(Set<IncomeDetails> incomeDetails) {
		this.incomeDetails = incomeDetails;
	}

	public Set<IntroducerDetails> getIntroducerDetails() {
		return introducerDetails;
	}

	public void setIntroducerDetails(Set<IntroducerDetails> introducerDetails) {
		this.introducerDetails = introducerDetails;
	}

	public Set<WorkEducationDetails> getWorkEducationDetails() {
		return workEducationDetails;
	}

	public void setWorkEducationDetails(Set<WorkEducationDetails> workEducationDetails) {
		this.workEducationDetails = workEducationDetails;
	}

	public Set<LiabilitiesDetails> getLiabilitiesDetails() {
		return liabilitiesDetails;
	}

	public void setLiabilitiesDetails(Set<LiabilitiesDetails> liabilitiesDetails) {
		this.liabilitiesDetails = liabilitiesDetails;
	}

	public Set<BusinessDetails> getBusinessDetails() {
		return businessDetails;
	}

	public void setBusinessDetails(Set<BusinessDetails> businessDetails) {
		this.businessDetails = businessDetails;
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

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getPob() {
		return pob;
	}

	public void setPob(String pob) {
		this.pob = pob;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFathersName() {
		return fathersName;
	}

	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}

	public String getMothersName() {
		return mothersName;
	}

	public void setMothersName(String mothersName) {
		this.mothersName = mothersName;
	}

	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	public MaritalStatuses getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatuses maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Integer getNumberOfDependents() {
		return numberOfDependents;
	}

	public void setNumberOfDependents(Integer numberOfDependents) {
		this.numberOfDependents = numberOfDependents;
	}

	public String getSisterConcernedOrAllied() {
		return sisterConcernedOrAllied;
	}

	public void setSisterConcernedOrAllied(String sisterConcernedOrAllied) {
		this.sisterConcernedOrAllied = sisterConcernedOrAllied;
	}

	public Nationality getNationality() {
		return nationality;
	}

	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}

	public ResidentStatus getResidentialStatus() {
		return residentialStatus;
	}

	public void setResidentialStatus(ResidentStatus residentialStatus) {
		this.residentialStatus = residentialStatus;
	}

	public String getAlternateMobileNumber() {
		return alternateMobileNumber;
	}

	public void setAlternateMobileNumber(String alternateMobileNumber) {
		this.alternateMobileNumber = alternateMobileNumber;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getTaxIdentificationNumber() {
		return taxIdentificationNumber;
	}

	public void setTaxIdentificationNumber(String taxIdentificationNumber) {
		this.taxIdentificationNumber = taxIdentificationNumber;
	}

	public String getNationalIdNumber() {
		return nationalIdNumber;
	}

	public void setNationalIdNumber(String nationalIdNumber) {
		this.nationalIdNumber = nationalIdNumber;
	}

	public String getBirthRegistrationNumber() {
		return birthRegistrationNumber;
	}

	public void setBirthRegistrationNumber(String birthRegistrationNumber) {
		this.birthRegistrationNumber = birthRegistrationNumber;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getDrivingLicenseNumber() {
		return drivingLicenseNumber;
	}

	public void setDrivingLicenseNumber(String drivingLicenseNumber) {
		this.drivingLicenseNumber = drivingLicenseNumber;
	}

	public String getPassportExpiryDate() {
		return passportExpiryDate;
	}

	public void setPassportExpiryDate(String passportExpiryDate) {
		this.passportExpiryDate = passportExpiryDate;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public String getMinor() {
		return minor;
	}

	public void setMinor(String minor) {
		this.minor = minor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<AttachmentDetails> getAttachmentDetails() {
		return attachmentDetails;
	}

	public void setAttachmentDetails(Set<AttachmentDetails> attachmentDetails) {
		this.attachmentDetails = attachmentDetails;
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

	public String getYabxToken() {
		return yabxToken;
	}

	public void setYabxToken(String yabxToken) {
		this.yabxToken = yabxToken;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

}
