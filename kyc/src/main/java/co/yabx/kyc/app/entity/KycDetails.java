package co.yabx.kyc.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "kyc_details")
public class KycDetails implements Serializable {

	@Id
	@Column(name = "msisdn", unique = true, nullable = false)
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

	@Column(name = "zip_code")
	private Integer zipCode;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "dob")
	private Long dob;

	@Column(name = "gender")
	private String gender;

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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
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

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getHouseNumberOrStreetName() {
		return houseNumberOrStreetName;
	}

	public void setHouseNumberOrStreetName(String houseNumberOrStreetName) {
		this.houseNumberOrStreetName = houseNumberOrStreetName;
	}

	public Long getDob() {
		return dob;
	}

	public void setDob(Long dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "KycDetails [msisdn=" + msisdn + ", firstName=" + firstName + ", middleName=" + middleName
				+ ", lastName=" + lastName + ", houseNumberOrStreetName=" + houseNumberOrStreetName + ", area=" + area
				+ ", city=" + city + ", zipCode=" + zipCode + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", dob=" + dob + ", gender=" + gender + "]";
	}

}
