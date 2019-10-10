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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import co.yabx.kyc.app.fullKyc.entity.BankAccountDetails;

/**
 * The persistent class for the Question database table.
 * 
 */
@Entity
@Table(name = "app_pages", indexes = { @Index(name = "page_name", columnList = "page_name") })
@NamedQuery(name = "AppPages.findAll", query = "SELECT ap FROM AppPages ap")
public class AppPages implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int pageId;

	@Column(name = "page_name")
	private String pageName;

	@Column(name = "page_title")
	private String pageTitle;

	@Column(name = "order")
	private Integer order;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "enable", nullable = false, columnDefinition = "boolean default true")
	private boolean enable;

	@OneToMany(mappedBy = "appPages", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<AppPagesSections> appPagesSections;

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

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
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

	public Set<AppPagesSections> getAppPagesSections() {
		return appPagesSections;
	}

	public void setAppPagesSections(Set<AppPagesSections> appPagesSections) {
		this.appPagesSections = appPagesSections;
	}

}
