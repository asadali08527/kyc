package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.enums.KycStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RetailerRequestDTO implements Serializable {
	private String dsrMSISDN;
	private KycStatus kycStatus;
	private Long retailerId;
	private int startIndex;
	private int endIndex;
	private PagesDTO pageResponse;
	private List<MultipartFile> files;
	private Long userId;

	public Long getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(Long retailerId) {
		this.retailerId = retailerId;
	}

	public PagesDTO getPageResponse() {
		return pageResponse;
	}

	public void setPageResponse(PagesDTO pageResponse) {
		this.pageResponse = pageResponse;
	}

	public String getDsrMSISDN() {
		return dsrMSISDN;
	}

	public void setDsrMSISDN(String dsrMSISDN) {
		this.dsrMSISDN = dsrMSISDN;
	}

	public KycStatus getKycStatus() {
		return kycStatus;
	}

	public void setKycStatus(KycStatus kycStatus) {
		this.kycStatus = kycStatus;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "RetailerRequestDTO [dsrMSISDN=" + dsrMSISDN + ", kycStatus=" + kycStatus + ", retailerId=" + retailerId
				+ ", startIndex=" + startIndex + ", endIndex=" + endIndex + ", pageResponse=" + pageResponse + "]";
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
		result = prime * result + ((dsrMSISDN == null) ? 0 : dsrMSISDN.hashCode());
		result = prime * result + endIndex;
		result = prime * result + ((files == null) ? 0 : files.hashCode());
		result = prime * result + ((kycStatus == null) ? 0 : kycStatus.hashCode());
		result = prime * result + ((pageResponse == null) ? 0 : pageResponse.hashCode());
		result = prime * result + ((retailerId == null) ? 0 : retailerId.hashCode());
		result = prime * result + startIndex;
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
		RetailerRequestDTO other = (RetailerRequestDTO) obj;
		if (dsrMSISDN == null) {
			if (other.dsrMSISDN != null)
				return false;
		} else if (!dsrMSISDN.equals(other.dsrMSISDN))
			return false;
		if (endIndex != other.endIndex)
			return false;
		if (files == null) {
			if (other.files != null)
				return false;
		} else if (!files.equals(other.files))
			return false;
		if (kycStatus != other.kycStatus)
			return false;
		if (pageResponse == null) {
			if (other.pageResponse != null)
				return false;
		} else if (!pageResponse.equals(other.pageResponse))
			return false;
		if (retailerId == null) {
			if (other.retailerId != null)
				return false;
		} else if (!retailerId.equals(other.retailerId))
			return false;
		if (startIndex != other.startIndex)
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}
