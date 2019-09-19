package co.yabx.kyc.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "account_statuses_trackers")
public class AccountStatusesTrackers implements Serializable {

	private static final long serialVersionUID = -6441043639437893962L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "msisdn", nullable = false)
	private String msisdn;

	@Column(name = "from_status", columnDefinition = "varchar(32) default 'NEW'")
	@Enumerated(value = EnumType.STRING)
	private AccountStatus from;

	@Column(name = "to_status", columnDefinition = "varchar(32) default 'NEW'")
	@Enumerated(value = EnumType.STRING)
	private AccountStatus to;

	@Column(name = "reason")
	private String reason;

	@Column(name = "changed_at")
	private Date changedAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "update_by")
	private String updatedBy;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@PrePersist
	protected void insertDates() {
		if (changedAt == null) {
			changedAt = new Date();
		}

	}

	@PreUpdate
	protected void updateTime() {
		changedAt = new Date();
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public AccountStatus getFrom() {
		return from;
	}

	public void setFrom(AccountStatus from) {
		this.from = from;
	}

	public AccountStatus getTo() {
		return to;
	}

	public void setTo(AccountStatus to) {
		this.to = to;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getChangedAt() {
		return changedAt;
	}

	public void setChangedAt(Date changedAt) {
		this.changedAt = changedAt;
	}

}
