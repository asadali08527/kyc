package co.yabx.kyc.app.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import co.yabx.kyc.app.entities.filter.SubGroups;

/**
 * The persistent class for the Question database table.
 * 
 */
@Entity
@Table(name = "groups", indexes = { @Index(name = "group_name", columnList = "group_name") })
public class Groups implements Serializable {
	private static final long serialVersionUID = 214321L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long groupId;

	@Column(name = "group_name")
	private String groupName;

	@Column(name = "group_title")
	private String groupTitle;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "enable", columnDefinition = "boolean default true")
	private boolean enable;

	@Column(name = "total_fields")
	private Integer totalFields;

	@OneToMany(mappedBy = "groups", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Fields> appDynamicFields;

	@Column(name = "display_order")
	private Integer displayOrder;

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
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

	public Set<Fields> getAppDynamicFields() {
		return appDynamicFields;
	}

	public void setAppDynamicFields(Set<Fields> appDynamicFields) {
		this.appDynamicFields = appDynamicFields;
	}

	public Integer getTotalFields() {
		return totalFields;
	}

	public void setTotalFields(Integer totalFields) {
		this.totalFields = totalFields;
	}

	@Override
	public String toString() {
		return "AppPagesSectionGroups [groupId=" + groupId + ", groupName=" + groupName + ", groupTitle=" + groupTitle
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", enable=" + enable + ", appDynamicFields="
				+ appDynamicFields + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appDynamicFields == null) ? 0 : appDynamicFields.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((displayOrder == null) ? 0 : displayOrder.hashCode());
		result = prime * result + (enable ? 1231 : 1237);
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
		Groups other = (Groups) obj;
		if (appDynamicFields == null) {
			if (other.appDynamicFields != null)
				return false;
		} else if (!appDynamicFields.equals(other.appDynamicFields))
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
		if (enable != other.enable)
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
