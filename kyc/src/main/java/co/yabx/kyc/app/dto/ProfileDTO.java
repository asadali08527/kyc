package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The persistent class for the Question database table.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO implements Serializable {
	private static final long serialVersionUID = 178783276L;

	private List<PagesDTO> pagesDTOs;

	private Integer profileCount;

	public List<PagesDTO> getPagesDTOs() {
		return pagesDTOs;
	}

	public void setPagesDTOs(List<PagesDTO> pagesDTOs) {
		this.pagesDTOs = pagesDTOs;
	}

	public Integer getProfileCount() {
		return profileCount;
	}

	public void setProfileCount(Integer profileCount) {
		this.profileCount = profileCount;
	}

}
