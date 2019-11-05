package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.enums.AccountStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountStatusTrackerDTO implements Serializable {

	private String msisdn;

	private AccountStatus fromStatus;

	private AccountStatus toStatus;

	private String reason;

	private Date changedAt;

	private String createdBy;

	private String updatedBy;

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public AccountStatus getFromStatus() {
		return fromStatus;
	}

	public void setFromStatus(AccountStatus fromStatus) {
		this.fromStatus = fromStatus;
	}

	public AccountStatus getToStatus() {
		return toStatus;
	}

	public void setToStatus(AccountStatus toStatus) {
		this.toStatus = toStatus;
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

	@Override
	public String toString() {
		return "AccountStatusTrackerDTO [msisdn=" + msisdn + ", fromStatus=" + fromStatus + ", toStatus=" + toStatus
				+ ", reason=" + reason + ", changedAt=" + changedAt + ", createdBy=" + createdBy + ", updatedBy="
				+ updatedBy + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((changedAt == null) ? 0 : changedAt.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((fromStatus == null) ? 0 : fromStatus.hashCode());
		result = prime * result + ((msisdn == null) ? 0 : msisdn.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((toStatus == null) ? 0 : toStatus.hashCode());
		result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
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
		AccountStatusTrackerDTO other = (AccountStatusTrackerDTO) obj;
		if (changedAt == null) {
			if (other.changedAt != null)
				return false;
		} else if (!changedAt.equals(other.changedAt))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (fromStatus != other.fromStatus)
			return false;
		if (msisdn == null) {
			if (other.msisdn != null)
				return false;
		} else if (!msisdn.equals(other.msisdn))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (toStatus != other.toStatus)
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		return true;
	}

}
