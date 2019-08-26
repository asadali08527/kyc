package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import co.yabx.kyc.app.entity.KYC;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PhotosDTO implements Serializable {

	private String id;

	private KYC kyc;

	private String photoSide;

	private String photoUrl;

	private Date snapTime;

	private boolean isSelfie;

	private Date createdAt;

	private Date updatedAt;

	private MultipartFile photo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public KYC getKyc() {
		return kyc;
	}

	public void setKyc(KYC kyc) {
		this.kyc = kyc;
	}

	public String getPhotoSide() {
		return photoSide;
	}

	public void setPhotoSide(String photoSide) {
		this.photoSide = photoSide;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Date getSnapTime() {
		return snapTime;
	}

	public void setSnapTime(Date snapTime) {
		this.snapTime = snapTime;
	}

	public boolean isSelfie() {
		return isSelfie;
	}

	public void setSelfie(boolean isSelfie) {
		this.isSelfie = isSelfie;
	}

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

	public MultipartFile getPhoto() {
		return photo;
	}

	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "PhotosDTO [id=" + id + ", kyc=" + kyc + ", photoSide=" + photoSide + ", photoUrl=" + photoUrl
				+ ", snapTime=" + snapTime + ", isSelfie=" + isSelfie + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", photo=" + photo + "]";
	}

}
