package co.yabx.kyc.app.fullKyc.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.enums.DocumentType;

/**
 * 
 * @author Asad Ali
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachmentDetailsDTO implements Serializable {

	private Long id;

	private String documentUrl;

	private Date snapTime;

	private boolean isSelfie;

	private Date createdAt;

	private Date updatedAt;

	private String documentNumber;

	private DocumentType documentType;

	private Long documentIssueDate;

	private String placeOfIssue;

	private Long documentExpiryDate;

	private Set<AttachmentsDTO> attachments;

	private UserDTO user;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocumentUrl() {
		return documentUrl;
	}

	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Long getDocumentIssueDate() {
		return documentIssueDate;
	}

	public void setDocumentIssueDate(Long documentIssueDate) {
		this.documentIssueDate = documentIssueDate;
	}

	public Long getDocumentExpiryDate() {
		return documentExpiryDate;
	}

	public void setDocumentExpiryDate(Long documentExpiryDate) {
		this.documentExpiryDate = documentExpiryDate;
	}

	public String getPlaceOfIssue() {
		return placeOfIssue;
	}

	public void setPlaceOfIssue(String placeOfIssue) {
		this.placeOfIssue = placeOfIssue;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Set<AttachmentsDTO> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<AttachmentsDTO> attachments) {
		this.attachments = attachments;
	}

	@Override
	public String toString() {
		return "AttachmentDetailsDTO [id=" + id + ", documentUrl=" + documentUrl + ", snapTime=" + snapTime
				+ ", isSelfie=" + isSelfie + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", documentNumber=" + documentNumber + ", documentType=" + documentType + ", documentIssueDate="
				+ documentIssueDate + ", placeOfIssue=" + placeOfIssue + ", documentExpiryDate=" + documentExpiryDate
				+ ", attachments=" + attachments + ", user=" + user + "]";
	}

}
