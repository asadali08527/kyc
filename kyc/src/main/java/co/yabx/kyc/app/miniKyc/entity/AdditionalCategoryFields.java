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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((additionalCategory == null) ? 0 : additionalCategory.hashCode());
		result = prime * result + ((additionalCategoryFieldName == null) ? 0 : additionalCategoryFieldName.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
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
		AdditionalCategoryFields other = (AdditionalCategoryFields) obj;
		if (additionalCategory == null) {
			if (other.additionalCategory != null)
				return false;
		} else if (!additionalCategory.equals(other.additionalCategory))
			return false;
		if (additionalCategoryFieldName == null) {
			if (other.additionalCategoryFieldName != null)
				return false;
		} else if (!additionalCategoryFieldName.equals(other.additionalCategoryFieldName))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}

}