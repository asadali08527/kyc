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

/**
 * The persistent class for the Question database table.
 * 
 */
@Entity
@Table(name = "groups", indexes = { @Index(name = "group_name", columnList = "group_name") })
public class AppPagesSectionGroups implements Serializable {
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

	@OneToMany(mappedBy = "groups", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<AppDynamicFields> appDynamicFields;

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

	public Set<AppDynamicFields> getAppDynamicFields() {
		return appDynamicFields;
	}

	public void setAppDynamicFields(Set<AppDynamicFields> appDynamicFields) {
		this.appDynamicFields = appDynamicFields;
	}

	@Override
	public String toString() {
		return "AppPagesSectionGroups [groupId=" + groupId + ", groupName=" + groupName + ", groupTitle=" + groupTitle
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", enable=" + enable + ", appDynamicFields="
				+ appDynamicFields + "]";
	}

}
