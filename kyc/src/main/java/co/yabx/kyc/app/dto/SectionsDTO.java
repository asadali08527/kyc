package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SectionsDTO implements Serializable {
	private static final long serialVersionUID = 1547547L;

	private Long sectionId;

	private String sectionName;

	private String sectionTitle;

	private Date createdAt;

	private Date updatedAt;

	private boolean enable;

	private String sectionType;

	private String icon;

	private Integer filledFields;

	private Integer totalFields;

	private Long nomineeId;

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

	private List<GroupsDTO> groups;

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

	public List<GroupsDTO> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupsDTO> groups) {
		this.groups = groups;
	}

	@Override
	public String toString() {
		return "AppPagesSectionsDTO [sectionId=" + sectionId + ", sectionName=" + sectionName + ", sectionTitle="
				+ sectionTitle + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", enable=" + enable
				+ ", groups=" + groups + "]";
	}

	public Long getNomineeId() {
		return nomineeId;
	}

	public void setNomineeId(Long nomineeId) {
		this.nomineeId = nomineeId;
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
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((displayOrder == null) ? 0 : displayOrder.hashCode());
		result = prime * result + (enable ? 1231 : 1237);
		result = prime * result + ((filledFields == null) ? 0 : filledFields.hashCode());
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((nomineeId == null) ? 0 : nomineeId.hashCode());
		result = prime * result + ((sectionId == null) ? 0 : sectionId.hashCode());
		result = prime * result + ((sectionName == null) ? 0 : sectionName.hashCode());
		result = prime * result + ((sectionTitle == null) ? 0 : sectionTitle.hashCode());
		result = prime * result + ((sectionType == null) ? 0 : sectionType.hashCode());
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
		SectionsDTO other = (SectionsDTO) obj;
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
		if (filledFields == null) {
			if (other.filledFields != null)
				return false;
		} else if (!filledFields.equals(other.filledFields))
			return false;
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (nomineeId == null) {
			if (other.nomineeId != null)
				return false;
		} else if (!nomineeId.equals(other.nomineeId))
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
