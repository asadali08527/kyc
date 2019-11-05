package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.enums.UserStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO implements Serializable {
	private String message;
	private String statusCode;
	private UserStatus dsrProfileStatus;
	private List<RetailersDTO> retailers;
	private List<PagesDTO> retailerInfo;
	private List<PagesDTO> dsrInfo;
	private Map<String, String> authInfo;
	private Integer totalCount;
	private QuestionAnswerDTO questionAnswerDTO;
	private List<QuestionAnswerDTO> questionAnswerDTOs;
	private String kycRejectedMessage;
	private String profileCompeltion;
	private String name;
	private String email;
	private Long userId;

	public List<QuestionAnswerDTO> getQuestionAnswerDTOs() {
		return questionAnswerDTOs;
	}

	public void setQuestionAnswerDTOs(List<QuestionAnswerDTO> questionAnswerDTOs) {
		this.questionAnswerDTOs = questionAnswerDTOs;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public UserStatus getDsrProfileStatus() {
		return dsrProfileStatus;
	}

	public void setDsrProfileStatus(UserStatus dsrProfileStatus) {
		this.dsrProfileStatus = dsrProfileStatus;
	}

	public List<RetailersDTO> getRetailers() {
		return retailers;
	}

	public void setRetailers(List<RetailersDTO> retailers) {
		this.retailers = retailers;
	}

	@Override
	public String toString() {
		return "ResponseDTO [message=" + message + ", statusCode=" + statusCode + ", dsrProfileStatus="
				+ dsrProfileStatus + ", retailers=" + retailers + ", retailerInfo=" + retailerInfo + "]";
	}

	public Map<String, String> getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(Map<String, String> authInfo) {
		this.authInfo = authInfo;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public QuestionAnswerDTO getQuestionAnswerDTO() {
		return questionAnswerDTO;
	}

	public void setQuestionAnswerDTO(QuestionAnswerDTO questionAnswerDTO) {
		this.questionAnswerDTO = questionAnswerDTO;
	}

	public List<PagesDTO> getRetailerInfo() {
		return retailerInfo;
	}

	public void setRetailerInfo(List<PagesDTO> retailerInfo) {
		this.retailerInfo = retailerInfo;
	}

	public List<PagesDTO> getDsrInfo() {
		return dsrInfo;
	}

	public void setDsrInfo(List<PagesDTO> dsrInfo) {
		this.dsrInfo = dsrInfo;
	}

	public String getKycRejectedMessage() {
		return kycRejectedMessage;
	}

	public void setKycRejectedMessage(String kycRejectedMessage) {
		this.kycRejectedMessage = kycRejectedMessage;
	}

	public String getProfileCompeltion() {
		return profileCompeltion;
	}

	public void setProfileCompeltion(String profileCompeltion) {
		this.profileCompeltion = profileCompeltion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authInfo == null) ? 0 : authInfo.hashCode());
		result = prime * result + ((dsrInfo == null) ? 0 : dsrInfo.hashCode());
		result = prime * result + ((dsrProfileStatus == null) ? 0 : dsrProfileStatus.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((kycRejectedMessage == null) ? 0 : kycRejectedMessage.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((profileCompeltion == null) ? 0 : profileCompeltion.hashCode());
		result = prime * result + ((questionAnswerDTO == null) ? 0 : questionAnswerDTO.hashCode());
		result = prime * result + ((questionAnswerDTOs == null) ? 0 : questionAnswerDTOs.hashCode());
		result = prime * result + ((retailerInfo == null) ? 0 : retailerInfo.hashCode());
		result = prime * result + ((retailers == null) ? 0 : retailers.hashCode());
		result = prime * result + ((statusCode == null) ? 0 : statusCode.hashCode());
		result = prime * result + ((totalCount == null) ? 0 : totalCount.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		ResponseDTO other = (ResponseDTO) obj;
		if (authInfo == null) {
			if (other.authInfo != null)
				return false;
		} else if (!authInfo.equals(other.authInfo))
			return false;
		if (dsrInfo == null) {
			if (other.dsrInfo != null)
				return false;
		} else if (!dsrInfo.equals(other.dsrInfo))
			return false;
		if (dsrProfileStatus != other.dsrProfileStatus)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (kycRejectedMessage == null) {
			if (other.kycRejectedMessage != null)
				return false;
		} else if (!kycRejectedMessage.equals(other.kycRejectedMessage))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (profileCompeltion == null) {
			if (other.profileCompeltion != null)
				return false;
		} else if (!profileCompeltion.equals(other.profileCompeltion))
			return false;
		if (questionAnswerDTO == null) {
			if (other.questionAnswerDTO != null)
				return false;
		} else if (!questionAnswerDTO.equals(other.questionAnswerDTO))
			return false;
		if (questionAnswerDTOs == null) {
			if (other.questionAnswerDTOs != null)
				return false;
		} else if (!questionAnswerDTOs.equals(other.questionAnswerDTOs))
			return false;
		if (retailerInfo == null) {
			if (other.retailerInfo != null)
				return false;
		} else if (!retailerInfo.equals(other.retailerInfo))
			return false;
		if (retailers == null) {
			if (other.retailers != null)
				return false;
		} else if (!retailers.equals(other.retailers))
			return false;
		if (statusCode == null) {
			if (other.statusCode != null)
				return false;
		} else if (!statusCode.equals(other.statusCode))
			return false;
		if (totalCount == null) {
			if (other.totalCount != null)
				return false;
		} else if (!totalCount.equals(other.totalCount))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}
