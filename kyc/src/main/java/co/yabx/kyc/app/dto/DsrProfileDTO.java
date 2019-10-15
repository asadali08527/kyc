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
	private AppPagesDTO pageResponse;

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

	public AppPagesDTO getPageResponse() {
		return pageResponse;
	}

	public void setPageResponse(AppPagesDTO pageResponse) {
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

}
