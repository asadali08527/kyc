package co.yabx.kyc.app.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import co.yabx.kyc.app.enums.PageType;

/**
 * The persistent class for the Question database table.
 * 
 */
@Entity
@Table(name = "pages", indexes = { @Index(name = "page_name", columnList = "page_name"),
		@Index(name = "page_type", columnList = "page_type") })
public class Pages implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long pageId;

	@Column(name = "page_name")
	private String pageName;

	@Column(name = "page_title")
	private String pageTitle;

	@Column(name = "page_type", length = 100, nullable = false, columnDefinition = "varchar(32) default 'RETAILERS' ")
	@Enumerated(value = EnumType.STRING)
	private PageType pageType;

	@Column(name = "icon")
	private String icon;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "enable", nullable = false, columnDefinition = "boolean default true")
	private boolean enable;

	@OneToMany(mappedBy = "pages", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Sections> sections;

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Column(name = "display_order")
	private Integer displayOrder;

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

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	public PageType getPageType() {
		return pageType;
	}

	public void setPageType(PageType pageType) {
		this.pageType = pageType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Set<Sections> getSections() {
		return sections;
	}

	public void setSections(Set<Sections> sections) {
		this.sections = sections;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((displayOrder == null) ? 0 : displayOrder.hashCode());
		result = prime * result + (enable ? 1231 : 1237);
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((pageId == null) ? 0 : pageId.hashCode());
		result = prime * result + ((pageName == null) ? 0 : pageName.hashCode());
		result = prime * result + ((pageTitle == null) ? 0 : pageTitle.hashCode());
		result = prime * result + ((pageType == null) ? 0 : pageType.hashCode());
		result = prime * result + ((sections == null) ? 0 : sections.hashCode());
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
		Pages other = (Pages) obj;
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
		if (pageId == null) {
			if (other.pageId != null)
				return false;
		} else if (!pageId.equals(other.pageId))
			return false;
		if (pageName == null) {
			if (other.pageName != null)
				return false;
		} else if (!pageName.equals(other.pageName))
			return false;
		if (pageTitle == null) {
			if (other.pageTitle != null)
				return false;
		} else if (!pageTitle.equals(other.pageTitle))
			return false;
		if (pageType != other.pageType)
			return false;
		if (sections == null) {
			if (other.sections != null)
				return false;
		} else if (!sections.equals(other.sections))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}

}
