package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.enums.DsrProfileStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO implements Serializable {
	private String message;
	private String statusCode;
	private DsrProfileStatus dsrProfileStatus;
	private List<RetailersDTO> retailers;
	private List<AppPagesDTO> retailerInfo;
	private Map<String, String> authInfo;
	private Integer totalCount;
	private QuestionAnswerDTO questionAnswerDTO;
	private List<QuestionAnswerDTO> questionAnswerDTOs;

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

	public DsrProfileStatus getDsrProfileStatus() {
		return dsrProfileStatus;
	}

	public void setDsrProfileStatus(DsrProfileStatus dsrProfileStatus) {
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

	public List<AppPagesDTO> getRetailerInfo() {
		return retailerInfo;
	}

	public void setRetailerInfo(List<AppPagesDTO> retailerInfo) {
		this.retailerInfo = retailerInfo;
	}

}
