package co.yabx.kyc.app.fullKyc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import co.yabx.kyc.app.enums.LicenseType;

@Entity
@Table(name = "license_details", indexes = { @Index(name = "license_type", columnList = "license_type"),
		@Index(name = "license_number", columnList = "license_number") })
public class LicenseDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "license_number")
	private String licenseNumber;

	@Column(name = "license_expiry_date")
	private String licenseExpiryDate;

	@Column(name = "license_issuing_authority")
	private String licenseIssuingAuthority;

	@Column(name = "license_type")
	private LicenseType licenseType;

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

	public void setId(Long id) {
		this.id = id;
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

	@PreUpdate
	private void preUpdate() {
		updatedAt = new Date();

	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getLicenseExpiryDate() {
		return licenseExpiryDate;
	}

	public void setLicenseExpiryDate(String licenseExpiryDate) {
		this.licenseExpiryDate = licenseExpiryDate;
	}

	public String getLicenseIssuingAuthority() {
		return licenseIssuingAuthority;
	}

	public void setLicenseIssuingAuthority(String licenseIssuingAuthority) {
		this.licenseIssuingAuthority = licenseIssuingAuthority;
	}

	public LicenseType getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(LicenseType licenseType) {
		this.licenseType = licenseType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((licenseExpiryDate == null) ? 0 : licenseExpiryDate.hashCode());
		result = prime * result + ((licenseIssuingAuthority == null) ? 0 : licenseIssuingAuthority.hashCode());
		result = prime * result + ((licenseNumber == null) ? 0 : licenseNumber.hashCode());
		result = prime * result + ((licenseType == null) ? 0 : licenseType.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
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
		LicenseDetails other = (LicenseDetails) obj;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (licenseExpiryDate == null) {
			if (other.licenseExpiryDate != null)
				return false;
		} else if (!licenseExpiryDate.equals(other.licenseExpiryDate))
			return false;
		if (licenseIssuingAuthority == null) {
			if (other.licenseIssuingAuthority != null)
				return false;
		} else if (!licenseIssuingAuthority.equals(other.licenseIssuingAuthority))
			return false;
		if (licenseNumber == null) {
			if (other.licenseNumber != null)
				return false;
		} else if (!licenseNumber.equals(other.licenseNumber))
			return false;
		if (licenseType != other.licenseType)
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
		return true;
	}

}
