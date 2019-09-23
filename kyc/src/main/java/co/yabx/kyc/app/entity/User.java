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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@Column(name = "house_number_or_street_name")
	private String houseNumberOrStreetName;

	@Column(name = "area")
	private String area;

	@Column(name = "city")
	private String city;

	@Column(name = "region")
	private String region;

	@Column(name = "zip_code")
	private Integer zipCode;

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

	@Column(name = "occupation")
	private String occupation;

	@Column(name = "designation")
	private String designation;

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

	@Column(name = "employer")
	private String employer;

	@Column(name = "business_name")
	private String businessName;

	@Column(name = "experience")
	private String experience;

	@Column(name = "business_address")
	private String businessAddress;

	@Column(name = "income_details")
	private IncomeDetails incomeDetails;

	@Column(name = "minor")
	private String minor;

}
