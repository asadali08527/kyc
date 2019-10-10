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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the Question database table.
 * 
 */
@Entity
@Table(name = "app_pages_section_groups", indexes = { @Index(name = "group_name", columnList = "group_name") })
@NamedQuery(name = "AppPagesSectionGroups.findAll", query = "SELECT apsg FROM AppPagesSectionGroups apsg")
public class AppPagesSectionGroups implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int groupId;

	@Column(name = "group_name")
	private String groupName;

	@Column(name = "group_title")
	private String groupTitle;

	@Column(name = "order")
	private Integer order;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "enable", nullable = false, columnDefinition = "boolean default true")
	private boolean enable;

	@OneToMany(mappedBy = "appPagesSectionGroups", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<AppDynamicFields> appDynamicFields;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = AppPagesSections.class)
	private AppPagesSections appPagesSections;

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

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
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

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Set<AppDynamicFields> getAppDynamicFields() {
		return appDynamicFields;
	}

	public void setAppDynamicFields(Set<AppDynamicFields> appDynamicFields) {
		this.appDynamicFields = appDynamicFields;
	}

	public AppPagesSections getAppPagesSections() {
		return appPagesSections;
	}

	public void setAppPagesSections(AppPagesSections appPagesSections) {
		this.appPagesSections = appPagesSections;
	}

}
