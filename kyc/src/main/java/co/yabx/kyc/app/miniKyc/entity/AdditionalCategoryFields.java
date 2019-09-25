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

@Entity
@Table(name = "additional_category_fields")
public class AdditionalCategoryFields implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "field_name", unique = true)
	private String additionalCategoryFieldName;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = AdditionalCategory.class)
	@JoinColumn(name = "additional_category_id")
	private AdditionalCategory additionalCategory;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	public String getAdditionalCategoryFieldName() {
		return additionalCategoryFieldName;
	}

	public void setAdditionalCategoryFieldName(String additionalCategoryFieldName) {
		this.additionalCategoryFieldName = additionalCategoryFieldName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AdditionalCategory getAdditionalCategory() {
		return additionalCategory;
	}

	public void setAdditionalCategory(AdditionalCategory additionalCategory) {
		this.additionalCategory = additionalCategory;
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

	@Override
	public String toString() {
		return "AdditionalCategoryFields [id=" + id + ", additionalCategoryFieldName=" + additionalCategoryFieldName
				+ ", additionalCategory=" + additionalCategory + "]";
	}

}