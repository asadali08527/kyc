package co.yabx.kyc.app.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.enums.FunctionalityType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Functionality implements Serializable {
	private Long id;
	private FunctionalityType type;
	private Long fromGroup;
	private Long toGroup;
	private Integer fieldToCompare;

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

}
