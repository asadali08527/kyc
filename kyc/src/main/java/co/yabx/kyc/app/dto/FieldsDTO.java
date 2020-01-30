package co.yabx.kyc.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.enums.ControlType;
import co.yabx.kyc.app.enums.DataType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldsDTO implements Serializable {
	private static final long serialVersionUID = 1588883279854358962L;

	private Long id;

	private ControlType type;

	private String fieldId;

	private DataType dataType;

	private String fieldName;

	private String placeHolderText;

	private Object savedData;

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

	private String help;

	private Boolean enableFutureDates;

	private Boolean enablePastDates;

	private Boolean internationalRepresentation;

	private String remark;

	private String localeText;

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

	public Object getSavedData() {
		return savedData;
	}

	public void setSavedData(Object savedData) {
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

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public Boolean getEnableFutureDates() {
		return enableFutureDates;
	}

	public void setEnableFutureDates(Boolean enableFutureDates) {
		this.enableFutureDates = enableFutureDates;
	}

	public Boolean getEnablePastDates() {
		return enablePastDates;
	}

	public void setEnablePastDates(Boolean enablePastDates) {
		this.enablePastDates = enablePastDates;
	}

	public Boolean getInternationalRepresentation() {
		return internationalRepresentation;
	}

	public void setInternationalRepresentation(Boolean internationalRepresentation) {
		this.internationalRepresentation = internationalRepresentation;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLocaleText() {
		return localeText;
	}

	public void setLocaleText(String localeText) {
		this.localeText = localeText;
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
		result = prime * result + ((enableFutureDates == null) ? 0 : enableFutureDates.hashCode());
		result = prime * result + ((enablePastDates == null) ? 0 : enablePastDates.hashCode());
		result = prime * result + ((fieldId == null) ? 0 : fieldId.hashCode());
		result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
		result = prime * result + ((functionality == null) ? 0 : functionality.hashCode());
		result = prime * result + ((help == null) ? 0 : help.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((internationalRepresentation == null) ? 0 : internationalRepresentation.hashCode());
		result = prime * result + ((localeText == null) ? 0 : localeText.hashCode());
		result = prime * result + (mandatory ? 1231 : 1237);
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		result = prime * result + ((placeHolderText == null) ? 0 : placeHolderText.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
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
		if (enableFutureDates == null) {
			if (other.enableFutureDates != null)
				return false;
		} else if (!enableFutureDates.equals(other.enableFutureDates))
			return false;
		if (enablePastDates == null) {
			if (other.enablePastDates != null)
				return false;
		} else if (!enablePastDates.equals(other.enablePastDates))
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
		if (help == null) {
			if (other.help != null)
				return false;
		} else if (!help.equals(other.help))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (internationalRepresentation == null) {
			if (other.internationalRepresentation != null)
				return false;
		} else if (!internationalRepresentation.equals(other.internationalRepresentation))
			return false;
		if (localeText == null) {
			if (other.localeText != null)
				return false;
		} else if (!localeText.equals(other.localeText))
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
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
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

	@Override
	public String toString() {
		return "FieldsDTO [id=" + id + ", response=" + response + "]";
	}

}
