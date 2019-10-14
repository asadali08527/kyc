package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.fullKyc.dto.AddressDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.WorkEducationDetailsDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DsrProfileDTO implements Serializable {
	private String name;
	private String dateOfBirth;
	private String fatherName;
	private String dsrPhoto;
	private String permanentAddress;
	private String presentAddress;
	private String email;
	private String alternateMobileNumber;
	private String educationalQualification;
	private String dsrMSISDN;
	private List<AddressDetailsDTO> addressDetailsDTO;
	private WorkEducationDetailsDTO workEducationDetailsDTO;
	private AppPagesDTO pageResponse;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getDsrPhoto() {
		return dsrPhoto;
	}

	public void setDsrPhoto(String dsrPhoto) {
		this.dsrPhoto = dsrPhoto;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public String getPresentAddress() {
		return presentAddress;
	}

	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAlternateMobileNumber() {
		return alternateMobileNumber;
	}

	public void setAlternateMobileNumber(String alternateMobileNumber) {
		this.alternateMobileNumber = alternateMobileNumber;
	}

	public String getEducationalQualification() {
		return educationalQualification;
	}

	public void setEducationalQualification(String educationalQualification) {
		this.educationalQualification = educationalQualification;
	}

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

}
