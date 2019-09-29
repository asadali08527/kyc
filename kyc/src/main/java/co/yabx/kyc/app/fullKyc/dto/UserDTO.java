package co.yabx.kyc.app.fullKyc.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import co.yabx.kyc.app.enums.MaritalStatuses;
import co.yabx.kyc.app.enums.Nationality;
import co.yabx.kyc.app.enums.ResidentStatus;
import co.yabx.kyc.app.fullKyc.entity.AttachmentDetails;

public class UserDTO implements Serializable {

	private Long id;

	private String msisdn;

	private String firstName;

	private String middleName;

	private String lastName;

	private Date createdAt;

	private Date updatedAt;
	/**
	 * Date of birth
	 */
	private Long dob;
	/**
	 * Place of birth
	 */
	private String pob;

	private String gender;

	private String createdBy;

	private String updatedBy;

	private String email;

	private String fathersName;

	private String mothersName;

	private String spouseName;

	private MaritalStatuses maritalStatus;

	private short numberOfDependents;

	private String sisterConcernedOrAllied;

	private Nationality nationality;

	private ResidentStatus residentialStatus;

	private String taxIdentificationNumber;

	private String nationalIdNumber;

	private String birthRegistrationNumber;

	private String passportNumber;

	private String drivingLicenseNumber;

	private Long passportExpiryDate;

	private String businessName;

	private String experience;

	private String businessAddress;

	private Set<IncomeDetailsDTO> incomeDetails;

	private Set<IntroducerDetailsDTO> introducerDetails;

	private Set<WorkEducationDetailsDTO> workEducationDetails;

	private Set<LiabilitiesDetailsDTO> liabilitiesDetails;

	private Set<BusinessDetailsDTO> businessDetails;

	private Set<AddressDetailsDTO> addressDetails;

	private Set<BankAccountDetailsDTO> bankAccountDetails;

	private Set<AttachmentDetails> attachmentDetails;

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

	public Set<IncomeDetailsDTO> getIncomeDetails() {
		return incomeDetails;
	}

	public void setIncomeDetails(Set<IncomeDetailsDTO> incomeDetails) {
		this.incomeDetails = incomeDetails;
	}

	public Set<IntroducerDetailsDTO> getIntroducerDetails() {
		return introducerDetails;
	}

	public void setIntroducerDetails(Set<IntroducerDetailsDTO> introducerDetails) {
		this.introducerDetails = introducerDetails;
	}

	public Set<WorkEducationDetailsDTO> getWorkEducationDetails() {
		return workEducationDetails;
	}

	public void setWorkEducationDetails(Set<WorkEducationDetailsDTO> workEducationDetails) {
		this.workEducationDetails = workEducationDetails;
	}

	public Set<LiabilitiesDetailsDTO> getLiabilitiesDetails() {
		return liabilitiesDetails;
	}

	public void setLiabilitiesDetails(Set<LiabilitiesDetailsDTO> liabilitiesDetails) {
		this.liabilitiesDetails = liabilitiesDetails;
	}

	public Set<BusinessDetailsDTO> getBusinessDetails() {
		return businessDetails;
	}

	public void setBusinessDetails(Set<BusinessDetailsDTO> businessDetails) {
		this.businessDetails = businessDetails;
	}

	public Set<AddressDetailsDTO> getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(Set<AddressDetailsDTO> addressDetails) {
		this.addressDetails = addressDetails;
	}

	public Set<BankAccountDetailsDTO> getBankAccountDetails() {
		return bankAccountDetails;
	}

	public void setBankAccountDetails(Set<BankAccountDetailsDTO> bankAccountDetails) {
		this.bankAccountDetails = bankAccountDetails;
	}

	public Set<AttachmentDetails> getAttachmentDetails() {
		return attachmentDetails;
	}

	public void setAttachmentDetails(Set<AttachmentDetails> attachmentDetails) {
		this.attachmentDetails = attachmentDetails;
	}

}
