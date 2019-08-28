package co.yabx.kyc.app.entity;

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
public class AdditionalFieldsValue {
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

}
