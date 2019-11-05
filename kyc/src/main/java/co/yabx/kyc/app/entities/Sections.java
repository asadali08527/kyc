package co.yabx.kyc.app.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the Question database table.
 * 
 */
@Entity
@Table(name = "sections", indexes = { @Index(name = "section_name", columnList = "section_name") })
public class Sections implements Serializable {
	private static final long serialVersionUID = 1547547L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long sectionId;

	@Column(name = "section_name")
	private String sectionName;

	@Column(name = "section_title")
	private String sectionTitle;

	@Column(name = "section_type")
	private String sectionType;

	@Column(name = "icon")
	private String icon;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "enable", columnDefinition = "boolean default true")
	private boolean enable;

	@Column(name = "display_order")
	private Integer displayOrder;

	public String getSectionType() {
		return sectionType;
	}

	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Pages.class)
	Pages pages;

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

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getSectionTitle() {
		return sectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public Pages getPages() {
		return pages;
	}

	public void setPages(Pages pages) {
		this.pages = pages;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((displayOrder == null) ? 0 : displayOrder.hashCode());
		result = prime * result + (enable ? 1231 : 1237);
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((pages == null) ? 0 : pages.hashCode());
		result = prime * result + ((sectionId == null) ? 0 : sectionId.hashCode());
		result = prime * result + ((sectionName == null) ? 0 : sectionName.hashCode());
		result = prime * result + ((sectionTitle == null) ? 0 : sectionTitle.hashCode());
		result = prime * result + ((sectionType == null) ? 0 : sectionType.hashCode());
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
		Sections other = (Sections) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (displayOrder == null) {
			if (other.displayOrder != null)
				return false;
		} else if (!displayOrder.equals(other.displayOrder))
			return false;
		if (enable != other.enable)
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (pages == null) {
			if (other.pages != null)
				return false;
		} else if (!pages.equals(other.pages))
			return false;
		if (sectionId == null) {
			if (other.sectionId != null)
				return false;
		} else if (!sectionId.equals(other.sectionId))
			return false;
		if (sectionName == null) {
			if (other.sectionName != null)
				return false;
		} else if (!sectionName.equals(other.sectionName))
			return false;
		if (sectionTitle == null) {
			if (other.sectionTitle != null)
				return false;
		} else if (!sectionTitle.equals(other.sectionTitle))
			return false;
		if (sectionType == null) {
			if (other.sectionType != null)
				return false;
		} else if (!sectionType.equals(other.sectionType))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}

}
