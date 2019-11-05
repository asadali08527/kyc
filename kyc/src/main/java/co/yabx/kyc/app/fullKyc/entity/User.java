package co.yabx.kyc.app.fullKyc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import co.yabx.kyc.app.entities.AuthInfo;
import co.yabx.kyc.app.entities.DeviceInformations;
import co.yabx.kyc.app.enums.Cities;
import co.yabx.kyc.app.enums.Gender;
import co.yabx.kyc.app.enums.MaritalStatuses;
import co.yabx.kyc.app.enums.Nationality;
import co.yabx.kyc.app.enums.ResidentStatus;
import co.yabx.kyc.app.enums.UserStatus;

@Entity
@Table(name = "users", indexes = { @Index(name = "msisdn", columnList = "msisdn"),
		@Index(name = "user_type", columnList = "user_type"),
		@Index(name = "auth_info_id", columnList = "auth_info_id") })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Least normalisation strategy
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User implements Serializable {

	private static final long serialVersionUID = -6901150039363185L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "user_type", insertable = false, updatable = false)
	private String userType;

	@Column(name = "user_status", length = 100, nullable = false, columnDefinition = "varchar(32) default 'NEW' ")
	@Enumerated(value = EnumType.STRING)
	private UserStatus userStatus;

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
	@Column(name = "pob", length = 100, columnDefinition = "varchar(32) ")
	@Enumerated(value = EnumType.STRING)
	private Cities pob;

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

	@Column(name = "dependents", length = 2, nullable = false, columnDefinition = "int(11) default 0 ")
	private Integer numberOfDependents;

	@Column(name = "sister_concerned_or_allied")
	private String sisterConcernedOrAllied;

	@Column(name = "nationality", length = 100, nullable = false, columnDefinition = "varchar(32) default 'Bangladeshi' ")
	private Nationality nationality;

	@Column(name = "resident_status", length = 100, nullable = false, columnDefinition = "varchar(32) default 'Resident' ")
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

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<MonthlyTransactionProfiles> monthlyTransactionProfiles = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<IntroducerDetails> introducerDetails = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<WorkEducationDetails> workEducationDetails = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<LiabilitiesDetails> liabilitiesDetails = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<BusinessDetails> businessDetails = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<AddressDetails> addressDetails = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<BankAccountDetails> bankAccountDetails = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<AttachmentDetails> attachmentDetails = new HashSet<>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user", referencedColumnName = "id")
	private LoanPurposeDetails loanPurposeDetails;

	@Column(name = "minor")
	private String minor;

	@Column(length = 25)
	private String locale;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "auth_info_id", referencedColumnName = "id")
	private AuthInfo authInfo;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "device_info", referencedColumnName = "device_id")
	private DeviceInformations deviceInformation;

	public LoanPurposeDetails getLoanPurposeDetails() {
		return loanPurposeDetails;
	}

	public void setLoanPurposeDetails(LoanPurposeDetails loanPurposeDetails) {
		this.loanPurposeDetails = loanPurposeDetails;
	}

	public Set<IntroducerDetails> getIntroducerDetails() {
		return introducerDetails;
	}

	public void setIntroducerDetails(Set<IntroducerDetails> introducerDetails) {

		for (IntroducerDetails details : introducerDetails) {
			details.setUser(this);
		}
		this.introducerDetails = introducerDetails;
	}

	public Set<WorkEducationDetails> getWorkEducationDetails() {
		return workEducationDetails;
	}

	public void setWorkEducationDetails(Set<WorkEducationDetails> workEducationDetails) {
		for (WorkEducationDetails details : workEducationDetails) {
			details.setUser(this);
		}
		this.workEducationDetails = workEducationDetails;
	}

	public Set<LiabilitiesDetails> getLiabilitiesDetails() {
		return liabilitiesDetails;
	}

	public void setLiabilitiesDetails(Set<LiabilitiesDetails> liabilitiesDetails) {
		for (LiabilitiesDetails details : liabilitiesDetails) {
			details.setUser(this);
		}
		this.liabilitiesDetails = liabilitiesDetails;
	}

	public Set<BusinessDetails> getBusinessDetails() {
		return businessDetails;
	}

	public void setBusinessDetails(Set<BusinessDetails> businessDetails) {
		for (BusinessDetails details : businessDetails) {
			details.setUser(this);
		}
		this.businessDetails = businessDetails;
	}

	public Set<AddressDetails> getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(Set<AddressDetails> addressDetails) {
		for (AddressDetails details : addressDetails) {
			details.setUser(this);
		}
		this.addressDetails = addressDetails;
	}

	public Set<BankAccountDetails> getBankAccountDetails() {
		return bankAccountDetails;
	}

	public void setBankAccountDetails(Set<BankAccountDetails> bankAccountDetails) {
		for (BankAccountDetails details : bankAccountDetails) {
			details.setUser(this);
		}
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

	public Cities getPob() {
		return pob;
	}

	public void setPob(Cities pob) {
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

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public AuthInfo getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(AuthInfo authInfo) {
		this.authInfo = authInfo;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Set<MonthlyTransactionProfiles> getMonthlyTransactionProfiles() {
		return monthlyTransactionProfiles;
	}

	public void setMonthlyTransactionProfiles(Set<MonthlyTransactionProfiles> monthlyTransactionProfilesSet) {
		for (MonthlyTransactionProfiles monthlyTransactionProfile : monthlyTransactionProfilesSet) {
			monthlyTransactionProfile.setUser(this);
		}
		this.monthlyTransactionProfiles = monthlyTransactionProfilesSet;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public DeviceInformations getDeviceInformation() {
		return deviceInformation;
	}

	public void setDeviceInformation(DeviceInformations deviceInformation) {
		this.deviceInformation = deviceInformation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addressDetails == null) ? 0 : addressDetails.hashCode());
		result = prime * result + ((alternateMobileNumber == null) ? 0 : alternateMobileNumber.hashCode());
		result = prime * result + ((attachmentDetails == null) ? 0 : attachmentDetails.hashCode());
		result = prime * result + ((authInfo == null) ? 0 : authInfo.hashCode());
		result = prime * result + ((bankAccountDetails == null) ? 0 : bankAccountDetails.hashCode());
		result = prime * result + ((birthRegistrationNumber == null) ? 0 : birthRegistrationNumber.hashCode());
		result = prime * result + ((businessDetails == null) ? 0 : businessDetails.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((deviceInformation == null) ? 0 : deviceInformation.hashCode());
		result = prime * result + ((dob == null) ? 0 : dob.hashCode());
		result = prime * result + ((drivingLicenseNumber == null) ? 0 : drivingLicenseNumber.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fathersName == null) ? 0 : fathersName.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((introducerDetails == null) ? 0 : introducerDetails.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((liabilitiesDetails == null) ? 0 : liabilitiesDetails.hashCode());
		result = prime * result + ((loanPurposeDetails == null) ? 0 : loanPurposeDetails.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((maritalStatus == null) ? 0 : maritalStatus.hashCode());
		result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
		result = prime * result + ((minor == null) ? 0 : minor.hashCode());
		result = prime * result + ((monthlyTransactionProfiles == null) ? 0 : monthlyTransactionProfiles.hashCode());
		result = prime * result + ((mothersName == null) ? 0 : mothersName.hashCode());
		result = prime * result + ((msisdn == null) ? 0 : msisdn.hashCode());
		result = prime * result + ((nationalIdNumber == null) ? 0 : nationalIdNumber.hashCode());
		result = prime * result + ((nationality == null) ? 0 : nationality.hashCode());
		result = prime * result + ((numberOfDependents == null) ? 0 : numberOfDependents.hashCode());
		result = prime * result + ((passportExpiryDate == null) ? 0 : passportExpiryDate.hashCode());
		result = prime * result + ((passportNumber == null) ? 0 : passportNumber.hashCode());
		result = prime * result + ((pob == null) ? 0 : pob.hashCode());
		result = prime * result + ((residentialStatus == null) ? 0 : residentialStatus.hashCode());
		result = prime * result + ((sisterConcernedOrAllied == null) ? 0 : sisterConcernedOrAllied.hashCode());
		result = prime * result + ((spouseName == null) ? 0 : spouseName.hashCode());
		result = prime * result + ((taxIdentificationNumber == null) ? 0 : taxIdentificationNumber.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result + ((userStatus == null) ? 0 : userStatus.hashCode());
		result = prime * result + ((userType == null) ? 0 : userType.hashCode());
		result = prime * result + ((workEducationDetails == null) ? 0 : workEducationDetails.hashCode());
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
		User other = (User) obj;
		if (addressDetails == null) {
			if (other.addressDetails != null)
				return false;
		} else if (!addressDetails.equals(other.addressDetails))
			return false;
		if (alternateMobileNumber == null) {
			if (other.alternateMobileNumber != null)
				return false;
		} else if (!alternateMobileNumber.equals(other.alternateMobileNumber))
			return false;
		if (attachmentDetails == null) {
			if (other.attachmentDetails != null)
				return false;
		} else if (!attachmentDetails.equals(other.attachmentDetails))
			return false;
		if (authInfo == null) {
			if (other.authInfo != null)
				return false;
		} else if (!authInfo.equals(other.authInfo))
			return false;
		if (bankAccountDetails == null) {
			if (other.bankAccountDetails != null)
				return false;
		} else if (!bankAccountDetails.equals(other.bankAccountDetails))
			return false;
		if (birthRegistrationNumber == null) {
			if (other.birthRegistrationNumber != null)
				return false;
		} else if (!birthRegistrationNumber.equals(other.birthRegistrationNumber))
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
		if (deviceInformation == null) {
			if (other.deviceInformation != null)
				return false;
		} else if (!deviceInformation.equals(other.deviceInformation))
			return false;
		if (dob == null) {
			if (other.dob != null)
				return false;
		} else if (!dob.equals(other.dob))
			return false;
		if (drivingLicenseNumber == null) {
			if (other.drivingLicenseNumber != null)
				return false;
		} else if (!drivingLicenseNumber.equals(other.drivingLicenseNumber))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fathersName == null) {
			if (other.fathersName != null)
				return false;
		} else if (!fathersName.equals(other.fathersName))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender != other.gender)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (introducerDetails == null) {
			if (other.introducerDetails != null)
				return false;
		} else if (!introducerDetails.equals(other.introducerDetails))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (liabilitiesDetails == null) {
			if (other.liabilitiesDetails != null)
				return false;
		} else if (!liabilitiesDetails.equals(other.liabilitiesDetails))
			return false;
		if (loanPurposeDetails == null) {
			if (other.loanPurposeDetails != null)
				return false;
		} else if (!loanPurposeDetails.equals(other.loanPurposeDetails))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (maritalStatus != other.maritalStatus)
			return false;
		if (middleName == null) {
			if (other.middleName != null)
				return false;
		} else if (!middleName.equals(other.middleName))
			return false;
		if (minor == null) {
			if (other.minor != null)
				return false;
		} else if (!minor.equals(other.minor))
			return false;
		if (monthlyTransactionProfiles == null) {
			if (other.monthlyTransactionProfiles != null)
				return false;
		} else if (!monthlyTransactionProfiles.equals(other.monthlyTransactionProfiles))
			return false;
		if (mothersName == null) {
			if (other.mothersName != null)
				return false;
		} else if (!mothersName.equals(other.mothersName))
			return false;
		if (msisdn == null) {
			if (other.msisdn != null)
				return false;
		} else if (!msisdn.equals(other.msisdn))
			return false;
		if (nationalIdNumber == null) {
			if (other.nationalIdNumber != null)
				return false;
		} else if (!nationalIdNumber.equals(other.nationalIdNumber))
			return false;
		if (nationality != other.nationality)
			return false;
		if (numberOfDependents == null) {
			if (other.numberOfDependents != null)
				return false;
		} else if (!numberOfDependents.equals(other.numberOfDependents))
			return false;
		if (passportExpiryDate == null) {
			if (other.passportExpiryDate != null)
				return false;
		} else if (!passportExpiryDate.equals(other.passportExpiryDate))
			return false;
		if (passportNumber == null) {
			if (other.passportNumber != null)
				return false;
		} else if (!passportNumber.equals(other.passportNumber))
			return false;
		if (pob != other.pob)
			return false;
		if (residentialStatus != other.residentialStatus)
			return false;
		if (sisterConcernedOrAllied == null) {
			if (other.sisterConcernedOrAllied != null)
				return false;
		} else if (!sisterConcernedOrAllied.equals(other.sisterConcernedOrAllied))
			return false;
		if (spouseName == null) {
			if (other.spouseName != null)
				return false;
		} else if (!spouseName.equals(other.spouseName))
			return false;
		if (taxIdentificationNumber == null) {
			if (other.taxIdentificationNumber != null)
				return false;
		} else if (!taxIdentificationNumber.equals(other.taxIdentificationNumber))
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
		if (userStatus != other.userStatus)
			return false;
		if (userType == null) {
			if (other.userType != null)
				return false;
		} else if (!userType.equals(other.userType))
			return false;
		if (workEducationDetails == null) {
			if (other.workEducationDetails != null)
				return false;
		} else if (!workEducationDetails.equals(other.workEducationDetails))
			return false;
		return true;
	}

}
