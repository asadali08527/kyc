package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.enums.KycStatus;
import co.yabx.kyc.app.fullKyc.dto.AddressDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.WorkEducationDetailsDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DsrProfileDTO implements Serializable {
	private KycStatus kycStatus;
	private Long retailerId;
	private int startIndex;
	private int endIndex;
	private String dsrMSISDN;
	private List<AddressDetailsDTO> addressDetailsDTO;
	private WorkEducationDetailsDTO workEducationDetailsDTO;
	private PagesDTO pageResponse;
	private Long userId;

	public String getDsrMSISDN() {
		return dsrMSISDN;
	}

	public void setDsrMSISDN(String dsrMSISDN) {
		this.dsrMSISDN = dsrMSISDN;
	}

	public List<AddressDetailsDTO> getAddressDetailsDTO() {
		return addressDetailsDTO;
	}

	public void setAddressDetailsDTO(List<AddressDetailsDTO> addressDetailsDTO) {
		this.addressDetailsDTO = addressDetailsDTO;
	}

	public WorkEducationDetailsDTO getWorkEducationDetailsDTO() {
		return workEducationDetailsDTO;
	}

	public void setWorkEducationDetailsDTO(WorkEducationDetailsDTO workEducationDetailsDTO) {
		this.workEducationDetailsDTO = workEducationDetailsDTO;
	}

	public PagesDTO getPageResponse() {
		return pageResponse;
	}

	public void setPageResponse(PagesDTO pageResponse) {
		this.pageResponse = pageResponse;
	}

	public KycStatus getKycStatus() {
		return kycStatus;
	}

	public void setKycStatus(KycStatus kycStatus) {
		this.kycStatus = kycStatus;
	}

	public Long getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(Long retailerId) {
		this.retailerId = retailerId;
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

	@Override
	public String toString() {
		return "DsrProfileDTO [kycStatus=" + kycStatus + ", retailerId=" + retailerId + ", startIndex=" + startIndex
				+ ", endIndex=" + endIndex + ", dsrMSISDN=" + dsrMSISDN + ", addressDetailsDTO=" + addressDetailsDTO
				+ ", workEducationDetailsDTO=" + workEducationDetailsDTO + ", pageResponse=" + pageResponse + "]";
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
		result = prime * result + ((addressDetailsDTO == null) ? 0 : addressDetailsDTO.hashCode());
		result = prime * result + ((dsrMSISDN == null) ? 0 : dsrMSISDN.hashCode());
		result = prime * result + endIndex;
		result = prime * result + ((kycStatus == null) ? 0 : kycStatus.hashCode());
		result = prime * result + ((pageResponse == null) ? 0 : pageResponse.hashCode());
		result = prime * result + ((retailerId == null) ? 0 : retailerId.hashCode());
		result = prime * result + startIndex;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((workEducationDetailsDTO == null) ? 0 : workEducationDetailsDTO.hashCode());
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
		DsrProfileDTO other = (DsrProfileDTO) obj;
		if (addressDetailsDTO == null) {
			if (other.addressDetailsDTO != null)
				return false;
		} else if (!addressDetailsDTO.equals(other.addressDetailsDTO))
			return false;
		if (dsrMSISDN == null) {
			if (other.dsrMSISDN != null)
				return false;
		} else if (!dsrMSISDN.equals(other.dsrMSISDN))
			return false;
		if (endIndex != other.endIndex)
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
		if (workEducationDetailsDTO == null) {
			if (other.workEducationDetailsDTO != null)
				return false;
		} else if (!workEducationDetailsDTO.equals(other.workEducationDetailsDTO))
			return false;
		return true;
	}

}
