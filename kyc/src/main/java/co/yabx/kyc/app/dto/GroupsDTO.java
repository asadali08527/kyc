package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupsDTO implements Serializable {
	private static final long serialVersionUID = 214321L;

	private Long groupId;

	private String groupName;

	private String groupTitle;

	private Date createdAt;

	private Date updatedAt;

	private boolean enable;

	private Integer totalFields;

	private Integer filledFields;

	private List<FieldsDTO> fields;

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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupTitle() {
		return groupTitle;
	}

	public void setGroupTitle(String groupTitle) {
		this.groupTitle = groupTitle;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public List<FieldsDTO> getFields() {
		return fields;
	}

	public void setFields(List<FieldsDTO> fields) {
		this.fields = fields;
	}

	public Integer getTotalFields() {
		return totalFields;
	}

	public void setTotalFields(Integer totalFields) {
		this.totalFields = totalFields;
	}

	public Integer getFilledFields() {
		return filledFields;
	}

	public void setFilledFields(Integer filledFields) {
		this.filledFields = filledFields;
	}

	@Override
	public String toString() {
		return "AppPagesSectionGroupsDTO [groupId=" + groupId + ", groupName=" + groupName + ", groupTitle="
				+ groupTitle + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", enable=" + enable
				+ ", fields=" + fields + "]";
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
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		result = prime * result + ((filledFields == null) ? 0 : filledFields.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
		result = prime * result + ((groupTitle == null) ? 0 : groupTitle.hashCode());
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
		GroupsDTO other = (GroupsDTO) obj;
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
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
			return false;
		if (filledFields == null) {
			if (other.filledFields != null)
				return false;
		} else if (!filledFields.equals(other.filledFields))
			return false;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		if (groupTitle == null) {
			if (other.groupTitle != null)
				return false;
		} else if (!groupTitle.equals(other.groupTitle))
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
