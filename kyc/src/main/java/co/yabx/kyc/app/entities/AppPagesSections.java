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
@Table(name = "app_pages_sections", indexes = { @Index(name = "section_name", columnList = "section_name") })
@NamedQuery(name = "AppPagesSections.findAll", query = "SELECT aps FROM AppPagesSections aps")
public class AppPagesSections implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int sectionId;

	@Column(name = "section_name")
	private String sectionName;

	@Column(name = "section_title")
	private String sectionTitle;

	@Column(name = "order")
	private Integer order;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "enable", nullable = false, columnDefinition = "boolean default true")
	private boolean enable;

	@OneToMany(mappedBy = "appPagesSections", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<AppPagesSectionGroups> appPagesSectionGroups;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = AppPages.class)
	private AppPages appPages;

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

	public int getSectionId() {
		return sectionId;
	}

	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
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

	public Set<AppPagesSectionGroups> getAppPagesSectionGroups() {
		return appPagesSectionGroups;
	}

	public void setAppPagesSectionGroups(Set<AppPagesSectionGroups> appPagesSectionGroups) {
		this.appPagesSectionGroups = appPagesSectionGroups;
	}

	public AppPages getAppPages() {
		return appPages;
	}

	public void setAppPages(AppPages appPages) {
		this.appPages = appPages;
	}

}
