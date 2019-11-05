package co.yabx.kyc.app.miniKyc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "additional_category")
public class AdditionalCategory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "category_name", unique = true)
	private String categoryName;

	@Column(name = "category_max_count")
	private int categoryMaxCount;

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

	@Column(name = "color_code")
	private String colorCode;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = AdditionalCategoryFields.class)
	private Set<AdditionalCategoryFields> additionalCategoryFields;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getCategoryMaxCount() {
		return categoryMaxCount;
	}

	public void setCategoryMaxCount(int categoryMaxCount) {
		this.categoryMaxCount = categoryMaxCount;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public Set<AdditionalCategoryFields> getAdditionalCategoryFields() {
		return additionalCategoryFields;
	}

	public void setAdditionalCategoryFields(Set<AdditionalCategoryFields> additionalCategoryFields) {
		this.additionalCategoryFields = additionalCategoryFields;
	}

	@Override
	public String toString() {
		return "AdditionalCategory [id=" + id + ", categoryName=" + categoryName + ", categoryMaxCount="
				+ categoryMaxCount + ", colorCode=" + colorCode + ", additionalCategoryFields="
				+ additionalCategoryFields + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((additionalCategoryFields == null) ? 0 : additionalCategoryFields.hashCode());
		result = prime * result + categoryMaxCount;
		result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
		result = prime * result + ((colorCode == null) ? 0 : colorCode.hashCode());
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
		AdditionalCategory other = (AdditionalCategory) obj;
		if (additionalCategoryFields == null) {
			if (other.additionalCategoryFields != null)
				return false;
		} else if (!additionalCategoryFields.equals(other.additionalCategoryFields))
			return false;
		if (categoryMaxCount != other.categoryMaxCount)
			return false;
		if (categoryName == null) {
			if (other.categoryName != null)
				return false;
		} else if (!categoryName.equals(other.categoryName))
			return false;
		if (colorCode == null) {
			if (other.colorCode != null)
				return false;
		} else if (!colorCode.equals(other.colorCode))
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
