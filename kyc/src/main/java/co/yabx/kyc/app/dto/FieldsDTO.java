package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.enums.ControlType;
import co.yabx.kyc.app.enums.DataType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldsDTO implements Serializable {
	private static final long serialVersionUID = 1588962L;

	private Long id;

	private ControlType type;

	private String fieldId;

	private DataType dataType;

	private String fieldName;

	private String placeHolderText;

	private String savedData;

	private String validation;

	private Date createdAt;

	private Date updatedAt;

	private boolean camera;

	private boolean editable;

	private boolean mandatory;

	private String response;

	private String defaultValue;

	private List<String> options;

	private List<SubFieldsDTO> subFields;

	private Functionality functionality;

	private Integer displayOrder;

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

	public ControlType getType() {
		return type;
	}

	public void setType(ControlType type) {
		this.type = type;
	}

	public String getPlaceHolderText() {
		return placeHolderText;
	}

	public void setPlaceHolderText(String placeHolderText) {
		this.placeHolderText = placeHolderText;
	}

	public String getSavedData() {
		return savedData;
	}

	public void setSavedData(String savedData) {
		this.savedData = savedData;
	}

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public boolean isCamera() {
		return camera;
	}

	public void setCamera(boolean camera) {
		this.camera = camera;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public List<SubFieldsDTO> getSubFields() {
		return subFields;
	}

	public void setSubFields(List<SubFieldsDTO> subFields) {
		this.subFields = subFields;
	}

	public Functionality getFunctionality() {
		return functionality;
	}

	public void setFunctionality(Functionality functionality) {
		this.functionality = functionality;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Override
	public String toString() {
		return "FieldsDTO [id=" + id + ", type=" + type + ", fieldId=" + fieldId + ", dataType=" + dataType
				+ ", fieldName=" + fieldName + ", placeHolderText=" + placeHolderText + ", savedData=" + savedData
				+ ", validation=" + validation + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", camera="
				+ camera + ", editable=" + editable + ", mandatory=" + mandatory + ", response=" + response
				+ ", defaultValue=" + defaultValue + ", options=" + options + ", subFields=" + subFields
				+ ", functionality=" + functionality + ", displayOrder=" + displayOrder + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (camera ? 1231 : 1237);
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result + ((defaultValue == null) ? 0 : defaultValue.hashCode());
		result = prime * result + ((displayOrder == null) ? 0 : displayOrder.hashCode());
		result = prime * result + (editable ? 1231 : 1237);
		result = prime * result + ((fieldId == null) ? 0 : fieldId.hashCode());
		result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
		result = prime * result + ((functionality == null) ? 0 : functionality.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (mandatory ? 1231 : 1237);
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		result = prime * result + ((placeHolderText == null) ? 0 : placeHolderText.hashCode());
		result = prime * result + ((response == null) ? 0 : response.hashCode());
		result = prime * result + ((savedData == null) ? 0 : savedData.hashCode());
		result = prime * result + ((subFields == null) ? 0 : subFields.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((validation == null) ? 0 : validation.hashCode());
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
		FieldsDTO other = (FieldsDTO) obj;
		if (camera != other.camera)
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (dataType != other.dataType)
			return false;
		if (defaultValue == null) {
			if (other.defaultValue != null)
				return false;
		} else if (!defaultValue.equals(other.defaultValue))
			return false;
		if (displayOrder == null) {
			if (other.displayOrder != null)
				return false;
		} else if (!displayOrder.equals(other.displayOrder))
			return false;
		if (editable != other.editable)
			return false;
		if (fieldId == null) {
			if (other.fieldId != null)
				return false;
		} else if (!fieldId.equals(other.fieldId))
			return false;
		if (fieldName == null) {
			if (other.fieldName != null)
				return false;
		} else if (!fieldName.equals(other.fieldName))
			return false;
		if (functionality == null) {
			if (other.functionality != null)
				return false;
		} else if (!functionality.equals(other.functionality))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mandatory != other.mandatory)
			return false;
		if (options == null) {
			if (other.options != null)
				return false;
		} else if (!options.equals(other.options))
			return false;
		if (placeHolderText == null) {
			if (other.placeHolderText != null)
				return false;
		} else if (!placeHolderText.equals(other.placeHolderText))
			return false;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		if (savedData == null) {
			if (other.savedData != null)
				return false;
		} else if (!savedData.equals(other.savedData))
			return false;
		if (subFields == null) {
			if (other.subFields != null)
				return false;
		} else if (!subFields.equals(other.subFields))
			return false;
		if (type != other.type)
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (validation == null) {
			if (other.validation != null)
				return false;
		} else if (!validation.equals(other.validation))
			return false;
		return true;
	}

}
