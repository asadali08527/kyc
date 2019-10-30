package co.yabx.kyc.app.fullKyc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import co.yabx.kyc.app.enums.AttachmentType;
import co.yabx.kyc.app.enums.DocumentType;

/**
 * 
 * @author Asad Ali
 *
 */
@Entity
@Table(name = "attachment_details", indexes = { @Index(name = "document_number", columnList = "document_number"),
		@Index(name = "document_type", columnList = "document_type") })
public class AttachmentDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "snap_time")
	private Date snapTime;

	@Column(name = "is_Selfie", nullable = false, columnDefinition = "boolean default false")
	private boolean isSelfie;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "document_number")
	private String documentNumber;

	@Column(name = "document_type", length = 100, columnDefinition = "varchar(32) ")
	@Enumerated(value = EnumType.STRING)
	private DocumentType documentType;

	@Column(name = "attachment_type", length = 100, columnDefinition = "varchar(32) ")
	@Enumerated(value = EnumType.STRING)
	private AttachmentType attachmentType;

	@Column(name = "document_issue_date")
	private Long documentIssueDate;

	@Column(name = "issued_at_place")
	private String placeOfIssue;

	@Column(name = "document_expiry_date")
	private Long documentExpiryDate;

	@OneToMany(mappedBy = "attachmentDetails", cascade = {
			CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Attachments> attachments = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Attachments> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<Attachments> attachments) {
		for (Attachments details : attachments) {
			details.setAttachmentDetails(this);
		}
		this.attachments = attachments;
	}

	public AttachmentType getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(AttachmentType attachmentType) {
		this.attachmentType = attachmentType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attachmentType == null) ? 0 : attachmentType.hashCode());
		result = prime * result + ((attachments == null) ? 0 : attachments.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((documentExpiryDate == null) ? 0 : documentExpiryDate.hashCode());
		result = prime * result + ((documentIssueDate == null) ? 0 : documentIssueDate.hashCode());
		result = prime * result + ((documentNumber == null) ? 0 : documentNumber.hashCode());
		result = prime * result + ((documentType == null) ? 0 : documentType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isSelfie ? 1231 : 1237);
		result = prime * result + ((placeOfIssue == null) ? 0 : placeOfIssue.hashCode());
		result = prime * result + ((snapTime == null) ? 0 : snapTime.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		AttachmentDetails other = (AttachmentDetails) obj;
		if (attachmentType != other.attachmentType)
			return false;
		if (attachments == null) {
			if (other.attachments != null)
				return false;
		} else if (!attachments.equals(other.attachments))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (documentExpiryDate == null) {
			if (other.documentExpiryDate != null)
				return false;
		} else if (!documentExpiryDate.equals(other.documentExpiryDate))
			return false;
		if (documentIssueDate == null) {
			if (other.documentIssueDate != null)
				return false;
		} else if (!documentIssueDate.equals(other.documentIssueDate))
			return false;
		if (documentNumber == null) {
			if (other.documentNumber != null)
				return false;
		} else if (!documentNumber.equals(other.documentNumber))
			return false;
		if (documentType != other.documentType)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isSelfie != other.isSelfie)
			return false;
		if (placeOfIssue == null) {
			if (other.placeOfIssue != null)
				return false;
		} else if (!placeOfIssue.equals(other.placeOfIssue))
			return false;
		if (snapTime == null) {
			if (other.snapTime != null)
				return false;
		} else if (!snapTime.equals(other.snapTime))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
