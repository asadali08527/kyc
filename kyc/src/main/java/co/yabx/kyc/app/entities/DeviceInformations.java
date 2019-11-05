package co.yabx.kyc.app.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "devices_info")
public class DeviceInformations implements Serializable {

	public enum DeviceType {
		IPHONE, ANDROID, WAP, WEB, UNKNOWN
	}

	@Id
	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "device_token", length = 512)
	private String deviceToken;

	@Column(name = "active", columnDefinition = "boolean default false")
	private boolean active;

	@Column(name = "device_type")
	private DeviceType deviceType;

	@Column(name = "instance_id")
	private String instanceId;

	@Column(name = "voip_token")
	private String voipToken;

	@Column(name = "device_model")
	private String deviceModel;

	@Column(name = "device_brand")
	private String deviceBrand;

	@Column(name = "device_manufacturer")
	private String deviceManufacturer;

	public String getVoipToken() {
		return voipToken;
	}

	public void setVoipToken(String voipToken) {
		this.voipToken = voipToken;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

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

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDeviceBrand() {
		return deviceBrand;
	}

	public void setDeviceBrand(String deviceBrand) {
		this.deviceBrand = deviceBrand;
	}

	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((deviceBrand == null) ? 0 : deviceBrand.hashCode());
		result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
		result = prime * result + ((deviceManufacturer == null) ? 0 : deviceManufacturer.hashCode());
		result = prime * result + ((deviceModel == null) ? 0 : deviceModel.hashCode());
		result = prime * result + ((deviceToken == null) ? 0 : deviceToken.hashCode());
		result = prime * result + ((deviceType == null) ? 0 : deviceType.hashCode());
		result = prime * result + ((instanceId == null) ? 0 : instanceId.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((voipToken == null) ? 0 : voipToken.hashCode());
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
		DeviceInformations other = (DeviceInformations) obj;
		if (active != other.active)
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (deviceBrand == null) {
			if (other.deviceBrand != null)
				return false;
		} else if (!deviceBrand.equals(other.deviceBrand))
			return false;
		if (deviceId == null) {
			if (other.deviceId != null)
				return false;
		} else if (!deviceId.equals(other.deviceId))
			return false;
		if (deviceManufacturer == null) {
			if (other.deviceManufacturer != null)
				return false;
		} else if (!deviceManufacturer.equals(other.deviceManufacturer))
			return false;
		if (deviceModel == null) {
			if (other.deviceModel != null)
				return false;
		} else if (!deviceModel.equals(other.deviceModel))
			return false;
		if (deviceToken == null) {
			if (other.deviceToken != null)
				return false;
		} else if (!deviceToken.equals(other.deviceToken))
			return false;
		if (deviceType != other.deviceType)
			return false;
		if (instanceId == null) {
			if (other.instanceId != null)
				return false;
		} else if (!instanceId.equals(other.instanceId))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (voipToken == null) {
			if (other.voipToken != null)
				return false;
		} else if (!voipToken.equals(other.voipToken))
			return false;
		return true;
	}

}
