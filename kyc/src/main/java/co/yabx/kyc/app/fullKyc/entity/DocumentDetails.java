package co.yabx.kyc.app.fullKyc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

/**
 * 
 * @author Asad Ali
 *
 */
@Entity
@Table(name = "documents", indexes = { @Index(name = "msisdn", columnList = "msisdn"),
		@Index(name = "user", columnList = "user"), @Index(name = "document_number", columnList = "document_number"),
		@Index(name = "document_type", columnList = "document_type") })
public class DocumentDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "msisdn")
	private String msisdn;

	@Column(name = "user")
	private User user;

	@Column(name = "document_side")
	private String documentSide;

	@Column(name = "document_url")
	private String documentUrl;

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

	@Column(name = "document_type")
	private String documentType;

	@Column(name = "document_issue_date")
	private Long documentIssueDate;

	@Column(name = "issued_at_place")
	private String placeOfIssue;

	@Column(name = "document_expiry_date")
	private Long documentExpiryDate;

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

	public String getDocumentSide() {
		return documentSide;
	}

	public void setDocumentSide(String documentSide) {
		this.documentSide = documentSide;
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

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
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

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getPlaceOfIssue() {
		return placeOfIssue;
	}

	public void setPlaceOfIssue(String placeOfIssue) {
		this.placeOfIssue = placeOfIssue;
	}

	@Override
	public String toString() {
		return "KycDocuments [id=" + id + ", msisdn=" + msisdn + ", documentSide=" + documentSide + ", documentUrl="
				+ documentUrl + ", snapTime=" + snapTime + ", isSelfie=" + isSelfie + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", documentNumber=" + documentNumber + ", documentType=" + documentType
				+ ", documentIssueDate=" + documentIssueDate + ", placeOfIssue=" + placeOfIssue
				+ ", documentExpiryDate=" + documentExpiryDate + "]";
	}

}
