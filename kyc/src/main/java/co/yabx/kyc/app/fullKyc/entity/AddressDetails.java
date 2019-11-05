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

import co.yabx.kyc.app.enums.AddressType;
import co.yabx.kyc.app.enums.Cities;
import co.yabx.kyc.app.enums.Countries;
import co.yabx.kyc.app.enums.Divisions;

@Entity
@Table(name = "address_details", indexes = { @Index(name = "division", columnList = "division"),
		@Index(name = "address_type", columnList = "address_type"),
		@Index(name = "city_district", columnList = "city_district") })
public class AddressDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "mobile_no")
	private String mobileNumber;

	@Column(name = "phone_no")
	private String phoneNumber;

	@Column(name = "address")
	private String address;

	@Column(name = "upazila_thana")
	private String upazilaThana;

	@Column(name = "city_district", length = 100, columnDefinition = "varchar(32) ")
	@Enumerated(value = EnumType.STRING)
	private Cities cityDsitrict;

	@Column(name = "division", length = 100, columnDefinition = "varchar(32) ")
	@Enumerated(value = EnumType.STRING)
	private Divisions division;

	@Column(name = "postal_code")
	private Integer zipCode;

	@Column(name = "landmark")
	private String landmark;

	@Column(name = "email")
	private String email;

	@Column(name = "country", length = 100, columnDefinition = "varchar(32) default 'Bangladesh'")
	@Enumerated(value = EnumType.STRING)
	private Countries country;

	@Column(name = "area")
	private String area;

	@Column(name = "territory")
	private String territory;

	@Column(name = "address_type")
	private AddressType addressType;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	User user;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = BusinessDetails.class)
	BusinessDetails businessDetails;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = CustomerInformations.class)
	CustomerInformations customerInformations;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
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

	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUpazilaThana() {
		return upazilaThana;
	}

	public void setUpazilaThana(String upazilaThana) {
		this.upazilaThana = upazilaThana;
	}

	public Cities getCityDsitrict() {
		return cityDsitrict;
	}

	public void setCityDsitrict(Cities cityDsitrict) {
		this.cityDsitrict = cityDsitrict;
	}

	public Divisions getDivision() {
		return division;
	}

	public void setDivision(Divisions division) {
		this.division = division;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Countries getCountry() {
		return country;
	}

	public void setCountry(Countries country) {
		this.country = country;
	}

	public CustomerInformations getCustomerInformations() {
		return customerInformations;
	}

	public void setCustomerInformations(CustomerInformations customerInformations) {
		this.customerInformations = customerInformations;
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

	@Override
	public String toString() {
		return "AddressDetails [id=" + id + ", mobileNumber=" + mobileNumber + ", phoneNumber=" + phoneNumber
				+ ", address=" + address + ", upazilaThana=" + upazilaThana + ", cityDsitrict=" + cityDsitrict
				+ ", division=" + division + ", zipCode=" + zipCode + ", landmark=" + landmark + ", email=" + email
				+ ", country=" + country + ", addressType=" + addressType + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", user=" + user + ", businessDetails=" + businessDetails + ", customerInformations="
				+ customerInformations + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((addressType == null) ? 0 : addressType.hashCode());
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((businessDetails == null) ? 0 : businessDetails.hashCode());
		result = prime * result + ((cityDsitrict == null) ? 0 : cityDsitrict.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((customerInformations == null) ? 0 : customerInformations.hashCode());
		result = prime * result + ((division == null) ? 0 : division.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((landmark == null) ? 0 : landmark.hashCode());
		result = prime * result + ((mobileNumber == null) ? 0 : mobileNumber.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((territory == null) ? 0 : territory.hashCode());
		result = prime * result + ((upazilaThana == null) ? 0 : upazilaThana.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
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
		AddressDetails other = (AddressDetails) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (addressType != other.addressType)
			return false;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (businessDetails == null) {
			if (other.businessDetails != null)
				return false;
		} else if (!businessDetails.equals(other.businessDetails))
			return false;
		if (cityDsitrict != other.cityDsitrict)
			return false;
		if (country != other.country)
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (customerInformations == null) {
			if (other.customerInformations != null)
				return false;
		} else if (!customerInformations.equals(other.customerInformations))
			return false;
		if (division != other.division)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (landmark == null) {
			if (other.landmark != null)
				return false;
		} else if (!landmark.equals(other.landmark))
			return false;
		if (mobileNumber == null) {
			if (other.mobileNumber != null)
				return false;
		} else if (!mobileNumber.equals(other.mobileNumber))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (territory == null) {
			if (other.territory != null)
				return false;
		} else if (!territory.equals(other.territory))
			return false;
		if (upazilaThana == null) {
			if (other.upazilaThana != null)
				return false;
		} else if (!upazilaThana.equals(other.upazilaThana))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}

}
