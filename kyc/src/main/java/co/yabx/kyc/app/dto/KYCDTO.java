package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * @author Asad.ali
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class KYCDTO implements Serializable {

	private String firstName;
	private String middleName;
	private String lastName;
	private Boolean isVerified;
	private String address;
	private String area;
	private String city;
	private String zipCode;
	private String idType;
	private String idNumber;
	private String idLink;
	private List<PhotosDTO> photos;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdLink() {
		return idLink;
	}

	public void setIdLink(String idLink) {
		this.idLink = idLink;
	}

	public List<PhotosDTO> getPhotos() {
		return photos;
	}

	public void setPhotos(List<PhotosDTO> photos) {
		this.photos = photos;
	}

	@Override
	public String toString() {
		return "KYCDTO [firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName
				+ ", isVerified=" + isVerified + ", address=" + address + ", area=" + area + ", city=" + city
				+ ", zipCode=" + zipCode + ", idType=" + idType + ", idNumber=" + idNumber + ", idLink=" + idLink
				+ ", photos=" + photos + "]";
	}

}
