package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppPagesSectionsDTO implements Serializable {
	private static final long serialVersionUID = 1547547L;

	private Long sectionId;

	private String sectionName;

	private String sectionTitle;

	private Date createdAt;

	private Date updatedAt;

	private boolean enable;

	private List<AppPagesSectionGroupsDTO> groups;

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

	public List<AppPagesSectionGroupsDTO> getGroups() {
		return groups;
	}

	public void setGroups(List<AppPagesSectionGroupsDTO> groups) {
		this.groups = groups;
	}

	@Override
	public String toString() {
		return "AppPagesSectionsDTO [sectionId=" + sectionId + ", sectionName=" + sectionName + ", sectionTitle="
				+ sectionTitle + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", enable=" + enable
				+ ", groups=" + groups + "]";
	}

}
