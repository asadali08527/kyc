package co.yabx.kyc.app.dto;

import java.io.Serializable;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.enums.FunctionalityType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Functionality implements Serializable {
	private Long id;
	private FunctionalityType type;
	private Long fromGroup;
	private Long toGroup;
	private Integer fieldToCompare;
	private Integer minThreshold;
	private Integer maxThreshold;

	public FunctionalityType getType() {
		return type;
	}

	public void setType(FunctionalityType type) {
		this.type = type;
	}

	public Long getFromGroup() {
		return fromGroup;
	}

	public void setFromGroup(Long fromGroup) {
		this.fromGroup = fromGroup;
	}

	public Long getToGroup() {
		return toGroup;
	}

	public void setToGroup(Long toGroup) {
		this.toGroup = toGroup;
	}

	public Integer getFieldToCompare() {
		return fieldToCompare;
	}

	public void setFieldToCompare(Integer fieldToCompare) {
		this.fieldToCompare = fieldToCompare;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMinThreshold() {
		return minThreshold;
	}

	public void setMinThreshold(Integer minThreshold) {
		this.minThreshold = minThreshold;
	}

	public Integer getMaxThreshold() {
		return maxThreshold;
	}

	public void setMaxThreshold(Integer maxThreshold) {
		this.maxThreshold = maxThreshold;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldToCompare == null) ? 0 : fieldToCompare.hashCode());
		result = prime * result + ((fromGroup == null) ? 0 : fromGroup.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((maxThreshold == null) ? 0 : maxThreshold.hashCode());
		result = prime * result + ((minThreshold == null) ? 0 : minThreshold.hashCode());
		result = prime * result + ((toGroup == null) ? 0 : toGroup.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Functionality other = (Functionality) obj;
		if (fieldToCompare == null) {
			if (other.fieldToCompare != null)
				return false;
		} else if (!fieldToCompare.equals(other.fieldToCompare))
			return false;
		if (fromGroup == null) {
			if (other.fromGroup != null)
				return false;
		} else if (!fromGroup.equals(other.fromGroup))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (maxThreshold == null) {
			if (other.maxThreshold != null)
				return false;
		} else if (!maxThreshold.equals(other.maxThreshold))
			return false;
		if (minThreshold == null) {
			if (other.minThreshold != null)
				return false;
		} else if (!minThreshold.equals(other.minThreshold))
			return false;
		if (toGroup == null) {
			if (other.toGroup != null)
				return false;
		} else if (!toGroup.equals(other.toGroup))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
