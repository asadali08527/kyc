package co.yabx.kyc.app.miniKyc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "additional_fields_value", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "kyc_details_id", "additional_category_field_id" }) })
public class AdditionalFieldsValue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "field_value")
	private String fieldValue;

	@Column(name = "additional_category_field_id")
	private AdditionalCategoryFields additionalCategoryFields;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = KycDetails.class)
	@JoinColumn(name = "kyc_details_id")
	private KycDetails kycDetails;

	@Column(name = "created_at")
	private Date createdTime;

	@Column(name = "updated_at")
	private Date updatedTime;

	@PrePersist
	protected void insertDates() {
		if (createdTime == null) {
			createdTime = new Date();
		}
		if (updatedTime == null) {
			updatedTime = new Date();
		}
	}

	@PreUpdate
	protected void updateTime() {
		updatedTime = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public AdditionalCategoryFields getAdditionalCategoryFields() {
		return additionalCategoryFields;
	}

	public void setAdditionalCategoryFields(AdditionalCategoryFields additionalCategoryFields) {
		this.additionalCategoryFields = additionalCategoryFields;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public KycDetails getKycDetails() {
		return kycDetails;
	}

	public void setKycDetails(KycDetails kycDetails) {
		this.kycDetails = kycDetails;
	}

	@Override
	public String toString() {
		return "AdditionalFieldsValue [id=" + id + ", fieldValue=" + fieldValue + ", additionalCategoryFields="
				+ additionalCategoryFields + ", kycDetails=" + kycDetails + ", createdTime=" + createdTime
				+ ", updatedTime=" + updatedTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((additionalCategoryFields == null) ? 0 : additionalCategoryFields.hashCode());
		result = prime * result + ((createdTime == null) ? 0 : createdTime.hashCode());
		result = prime * result + ((fieldValue == null) ? 0 : fieldValue.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((kycDetails == null) ? 0 : kycDetails.hashCode());
		result = prime * result + ((updatedTime == null) ? 0 : updatedTime.hashCode());
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
		AdditionalFieldsValue other = (AdditionalFieldsValue) obj;
		if (additionalCategoryFields == null) {
			if (other.additionalCategoryFields != null)
				return false;
		} else if (!additionalCategoryFields.equals(other.additionalCategoryFields))
			return false;
		if (createdTime == null) {
			if (other.createdTime != null)
				return false;
		} else if (!createdTime.equals(other.createdTime))
			return false;
		if (fieldValue == null) {
			if (other.fieldValue != null)
				return false;
		} else if (!fieldValue.equals(other.fieldValue))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kycDetails == null) {
			if (other.kycDetails != null)
				return false;
		} else if (!kycDetails.equals(other.kycDetails))
			return false;
		if (updatedTime == null) {
			if (other.updatedTime != null)
				return false;
		} else if (!updatedTime.equals(other.updatedTime))
			return false;
		return true;
	}

}
