package co.yabx.kyc.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

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
	@Column(name = "msisdn")
	private String msisdn;

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
	private Long dob;
	/**
	 * Place of birth
	 */
	@Column(name = "pob")
	private String pob;

	@Column(name = "gender")
	private String gender;

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
	private short numberOfDependents;

	@Column(name = "monthly_income_from_other_source")
	private double monthlyIncomeFromOtherSource;

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
	private Long passportExpiryDate;

	@Column(name = "business_name")
	private String businessName;

	@Column(name = "experience")
	private String experience;

	@Column(name = "business_address")
	private String businessAddress;

	@Column(name = "income_details")
	private IncomeDetails incomeDetails;

	@Column(name = "work_education")
	private WorkEducationDetails workEducationDetails;

	@Column(name = "liabilities")
	private LiabilitiesDetails liabilitiesDetails;

	@Column(name = "minor")
	private String minor;

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

	public Long getDob() {
		return dob;
	}

	public void setDob(Long dob) {
		this.dob = dob;
	}

	public String getPob() {
		return pob;
	}

	public void setPob(String pob) {
		this.pob = pob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
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

	public short getNumberOfDependents() {
		return numberOfDependents;
	}

	public void setNumberOfDependents(short numberOfDependents) {
		this.numberOfDependents = numberOfDependents;
	}

	public double getMonthlyIncomeFromOtherSource() {
		return monthlyIncomeFromOtherSource;
	}

	public void setMonthlyIncomeFromOtherSource(double monthlyIncomeFromOtherSource) {
		this.monthlyIncomeFromOtherSource = monthlyIncomeFromOtherSource;
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

	public Long getPassportExpiryDate() {
		return passportExpiryDate;
	}

	public void setPassportExpiryDate(Long passportExpiryDate) {
		this.passportExpiryDate = passportExpiryDate;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public IncomeDetails getIncomeDetails() {
		return incomeDetails;
	}

	public void setIncomeDetails(IncomeDetails incomeDetails) {
		this.incomeDetails = incomeDetails;
	}

	public String getMinor() {
		return minor;
	}

	public void setMinor(String minor) {
		this.minor = minor;
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

	public WorkEducationDetails getWorkEducationDetails() {
		return workEducationDetails;
	}

	public void setWorkEducationDetails(WorkEducationDetails workEducationDetails) {
		this.workEducationDetails = workEducationDetails;
	}

}
