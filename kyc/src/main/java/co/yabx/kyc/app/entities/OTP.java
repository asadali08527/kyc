package co.yabx.kyc.app.entities;

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
import javax.persistence.Temporal;

import co.yabx.kyc.app.enums.OtpGroup;
import co.yabx.kyc.app.enums.OtpType;

@Entity
@Table(name = "otps")
public class OTP implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "user")
	private Long user;

	@Column(name = "otp")
	private String otp;

	@Column(name = "otp_type")
	private OtpType otpType;

	@Column(name = "otp_group")
	private OtpGroup otpGroup;

	@Column(name = "expiry_time")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date expiryTime;

	@Column(name = "error_log")
	private String errorLog = "";

	@Column(name = "success")
	private Boolean success = true;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "update_by")
	private String updatedBy;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	public String getErrorLog() {
		return errorLog;
	}

	public void setErrorLog(String errorLog) {
		this.errorLog = errorLog;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public OtpType getOtpType() {
		return otpType;
	}

	public void setOtpType(OtpType otpType) {
		this.otpType = otpType;
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

	@PrePersist
	private void prePersist() {
		if (createdAt == null) {
			createdAt = new Date();
			updatedAt = new Date();
		}
	}

	@PreUpdate
	private void preUpdate() {
		updatedAt = new Date();

	}

	public OtpGroup getOtpGroup() {
		return otpGroup;
	}

	public void setOtpGroup(OtpGroup otpGroup) {
		this.otpGroup = otpGroup;
	}

	@Override
	public String toString() {
		return "OTP [id=" + id + ", user=" + user + ", otp=" + otp + ", otpType=" + otpType + ", otpGroup=" + otpGroup
				+ ", expiryTime=" + expiryTime + ", errorLog=" + errorLog + ", success=" + success + ", createdBy="
				+ createdBy + ", updatedBy=" + updatedBy + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((errorLog == null) ? 0 : errorLog.hashCode());
		result = prime * result + ((expiryTime == null) ? 0 : expiryTime.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((otp == null) ? 0 : otp.hashCode());
		result = prime * result + ((otpGroup == null) ? 0 : otpGroup.hashCode());
		result = prime * result + ((otpType == null) ? 0 : otpType.hashCode());
		result = prime * result + ((success == null) ? 0 : success.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
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
		OTP other = (OTP) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (errorLog == null) {
			if (other.errorLog != null)
				return false;
		} else if (!errorLog.equals(other.errorLog))
			return false;
		if (expiryTime == null) {
			if (other.expiryTime != null)
				return false;
		} else if (!expiryTime.equals(other.expiryTime))
			return false;
		if (id != other.id)
			return false;
		if (otp == null) {
			if (other.otp != null)
				return false;
		} else if (!otp.equals(other.otp))
			return false;
		if (otpGroup != other.otpGroup)
			return false;
		if (otpType != other.otpType)
			return false;
		if (success == null) {
			if (other.success != null)
				return false;
		} else if (!success.equals(other.success))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
