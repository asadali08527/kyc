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

}
