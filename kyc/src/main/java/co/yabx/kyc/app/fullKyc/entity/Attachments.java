package co.yabx.kyc.app.fullKyc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import co.yabx.kyc.app.enums.DocumentSide;

/**
 * 
 * @author Asad Ali
 *
 */
@Entity
@Table(name = "attachments", indexes = { @Index(name = "document_side", columnList = "document_side") })
public class Attachments implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "document_side")
	private DocumentSide documentSide;

	@Column(name = "document_url")
	private String documentUrl;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = AttachmentDetails.class)
	AttachmentDetails attachmentDetails;

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

	public AttachmentDetails getAttachmentDetails() {
		return attachmentDetails;
	}

	public void setAttachmentDetails(AttachmentDetails attachmentDetails) {
		this.attachmentDetails = attachmentDetails;
	}

	@PrePersist
	protected void insertDates() {
		if (createdAt == null) {
			createdAt = new Date();
		}
		if (updatedAt == null) {
			updatedAt = new Date();
		}
	}

	@PreUpdate
	protected void updateTime() {
		updatedAt = new Date();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attachmentDetails == null) ? 0 : attachmentDetails.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((documentSide == null) ? 0 : documentSide.hashCode());
		result = prime * result + ((documentUrl == null) ? 0 : documentUrl.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
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
		Attachments other = (Attachments) obj;
		if (attachmentDetails == null) {
			if (other.attachmentDetails != null)
				return false;
		} else if (!attachmentDetails.equals(other.attachmentDetails))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (documentSide != other.documentSide)
			return false;
		if (documentUrl == null) {
			if (other.documentUrl != null)
				return false;
		} else if (!documentUrl.equals(other.documentUrl))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}

}
