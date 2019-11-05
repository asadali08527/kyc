package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The persistent class for the Question database table.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagesDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long pageId;

	private String pageName;

	private String pageTitle;

	private Date createdAt;

	private Date updatedAt;

	private boolean enable;

	private String pageType;

	private String icon;

	private Integer filledFields;

	private Integer totalFields;

	private List<SectionsDTO> sections;

	private String pageCompletion;

	private Long retailerId;

	private Long dsrId;

	private ActionDTO action;

	private Integer displayOrder;

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getFilledFields() {
		return filledFields;
	}

	public void setFilledFields(Integer filledFields) {
		this.filledFields = filledFields;
	}

	public Integer getTotalFields() {
		return totalFields;
	}

	public void setTotalFields(Integer totalFields) {
		this.totalFields = totalFields;
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

	public List<SectionsDTO> getSections() {
		return sections;
	}

	public void setSections(List<SectionsDTO> sections) {
		this.sections = sections;
	}

	public String getPageCompletion() {
		return pageCompletion;
	}

	public void setPageCompletion(String pageCompletion) {
		this.pageCompletion = pageCompletion;
	}

	public Long getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(Long retailerId) {
		this.retailerId = retailerId;
	}

	public Long getDsrId() {
		return dsrId;
	}

	public void setDsrId(Long dsrId) {
		this.dsrId = dsrId;
	}

	public ActionDTO getAction() {
		return action;
	}

	public void setAction(ActionDTO action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "AppPagesDTO [pageId=" + pageId + ", pageName=" + pageName + ", pageTitle=" + pageTitle + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", enable=" + enable + ", pageType=" + pageType + ", icon="
				+ icon + ", filledFields=" + filledFields + ", totalFields=" + totalFields + ", sections=" + sections
				+ ", pageCompletion=" + pageCompletion + ", retailerId=" + retailerId + ", dsrId=" + dsrId + ", action="
				+ action + "]";
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((displayOrder == null) ? 0 : displayOrder.hashCode());
		result = prime * result + ((dsrId == null) ? 0 : dsrId.hashCode());
		result = prime * result + (enable ? 1231 : 1237);
		result = prime * result + ((filledFields == null) ? 0 : filledFields.hashCode());
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((pageCompletion == null) ? 0 : pageCompletion.hashCode());
		result = prime * result + ((pageId == null) ? 0 : pageId.hashCode());
		result = prime * result + ((pageName == null) ? 0 : pageName.hashCode());
		result = prime * result + ((pageTitle == null) ? 0 : pageTitle.hashCode());
		result = prime * result + ((pageType == null) ? 0 : pageType.hashCode());
		result = prime * result + ((retailerId == null) ? 0 : retailerId.hashCode());
		result = prime * result + ((sections == null) ? 0 : sections.hashCode());
		result = prime * result + ((totalFields == null) ? 0 : totalFields.hashCode());
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
		PagesDTO other = (PagesDTO) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
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
		if (dsrId == null) {
			if (other.dsrId != null)
				return false;
		} else if (!dsrId.equals(other.dsrId))
			return false;
		if (enable != other.enable)
			return false;
		if (filledFields == null) {
			if (other.filledFields != null)
				return false;
		} else if (!filledFields.equals(other.filledFields))
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (pageCompletion == null) {
			if (other.pageCompletion != null)
				return false;
		} else if (!pageCompletion.equals(other.pageCompletion))
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
		if (pageType == null) {
			if (other.pageType != null)
				return false;
		} else if (!pageType.equals(other.pageType))
			return false;
		if (retailerId == null) {
			if (other.retailerId != null)
				return false;
		} else if (!retailerId.equals(other.retailerId))
			return false;
		if (sections == null) {
			if (other.sections != null)
				return false;
		} else if (!sections.equals(other.sections))
			return false;
		if (totalFields == null) {
			if (other.totalFields != null)
				return false;
		} else if (!totalFields.equals(other.totalFields))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}

}
