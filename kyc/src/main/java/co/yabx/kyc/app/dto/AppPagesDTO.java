package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The persistent class for the Question database table.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppPagesDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long pageId;

	private String pageName;

	private String pageTitle;

	private Date createdAt;

	private Date updatedAt;

	private boolean enable;

	private List<AppPagesSectionsDTO> sections;

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

	public List<AppPagesSectionsDTO> getSections() {
		return sections;
	}

	public void setSections(List<AppPagesSectionsDTO> sections) {
		this.sections = sections;
	}

	@Override
	public String toString() {
		return "AppPagesDTO [pageId=" + pageId + ", pageName=" + pageName + ", pageTitle=" + pageTitle + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", enable=" + enable + ", sections=" + sections + "]";
	}

}
