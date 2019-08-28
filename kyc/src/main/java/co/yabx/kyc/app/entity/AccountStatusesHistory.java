package co.yabx.kyc.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "account_statuses_history")
public class AccountStatusesHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "msisdn", unique = true, nullable = false)
	private String msisdn;

	@Column(name = "from_status")
	private String from;

	@Column(name = "to_status")
	private String to;

	@Column(name = "reason")
	private String reason;

	@Column(name = "changed_at")
	private Date changedAt;

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

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getReason() {
		return reason;
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

	@Override
	public String toString() {
		return "AccountStatusesHistory [id=" + id + ", msisdn=" + msisdn + ", from=" + from + ", to=" + to + ", reason="
				+ reason + ", changedAt=" + changedAt + "]";
	}

}
