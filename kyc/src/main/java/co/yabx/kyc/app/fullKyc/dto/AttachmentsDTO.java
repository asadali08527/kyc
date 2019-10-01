package co.yabx.kyc.app.fullKyc.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.enums.DocumentSide;

/**
 * 
 * @author Asad Ali
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachmentsDTO implements Serializable {

	private Long id;

	private DocumentSide documentSide;

	private String documentUrl;

	private Date createdAt;

	private Date updatedAt;

	private AttachmentDetailsDTO attachmentDetails;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DocumentSide getDocumentSide() {
		return documentSide;
	}

	public void setDocumentSide(DocumentSide documentSide) {
		this.documentSide = documentSide;
	}

	public String getDocumentUrl() {
		return documentUrl;
	}

	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
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

	public AttachmentDetailsDTO getAttachmentDetails() {
		return attachmentDetails;
	}

	public void setAttachmentDetails(AttachmentDetailsDTO attachmentDetails) {
		this.attachmentDetails = attachmentDetails;
	}

	@Override
	public String toString() {
		return "AttachmentsDTO [id=" + id + ", documentSide=" + documentSide + ", documentUrl=" + documentUrl
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", attachmentDetails=" + attachmentDetails
				+ "]";
	}

}
