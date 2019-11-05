package co.yabx.kyc.app.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import co.yabx.kyc.app.entities.filter.Operations;
import co.yabx.kyc.app.entities.filter.SubFields;
import co.yabx.kyc.app.entities.filter.SubGroups;
import co.yabx.kyc.app.enums.ControlType;
import co.yabx.kyc.app.enums.DataType;

/**
 * The persistent class for the Question database table.
 * 
 */
@Entity
@Table(name = "fields", indexes = { @Index(name = "field_id", columnList = "field_id"),
		@Index(name = "field_name", columnList = "field_name") })
@NamedQuery(name = "Fields.findAll", query = "SELECT f FROM Fields f")
public class Fields implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "control_type", length = 100, nullable = false, columnDefinition = "varchar(32) ")
	@Enumerated(value = EnumType.STRING)
	private ControlType type;

	@Column(name = "field_id")
	private String fieldId;

	@Column(name = "data_type", length = 100, nullable = false, columnDefinition = "varchar(32) ")
	@Enumerated(value = EnumType.STRING)
	private DataType dataType;

	@Column(name = "field_name")
	private String fieldName;

	@Column(name = "placeholder_text")
	private String placeHolderText;

	@Transient
	private String savedData;

	@Column(name = "validation")
	private String validation;

	@Column(name = "default_value")
	private String defaultValue;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "is_camera", nullable = false, columnDefinition = "boolean default false")
	private boolean camera;

	@Column(name = "is_editable", nullable = false, columnDefinition = "boolean default true")
	private boolean editable;

	@Column(name = "mandaory", nullable = false, columnDefinition = "boolean default true")
	private boolean mandatory;

	private Integer hash;

	@Column(name = "display_order")
	private Integer displayOrder;

	/**
	 * options field is non persistent field, and will be used only while returning
	 * options of a radio or check box input types.
	 */
	@Transient
	private List<String> options;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Groups.class)
	private Groups groups;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<SubFields> subFields;

	@OneToMany(mappedBy = "fields", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<SubGroups> subGroups;

	@OneToMany(mappedBy = "fields", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Operations> operations;

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

	public Integer getHash() {
		return hash;
	}

	public void setHash(Integer hash) {
		this.hash = hash;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public Groups getGroups() {
		return groups;
	}

	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public String toString() {
		return "AppDynamicFields [id=" + id + ", type=" + type + ", fieldId=" + fieldId + ", dataType=" + dataType
				+ ", fieldName=" + fieldName + ", placeHolderText=" + placeHolderText + ", savedData=" + savedData
				+ ", validation=" + validation + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", camera="
				+ camera + ", editable=" + editable + ", mandatory=" + mandatory + ", hash=" + hash + ", displayOrder="
				+ displayOrder + ", options=" + options + ", groups=" + groups + "]";
	}

	public Set<SubFields> getSubFields() {
		return subFields;
	}

	public void setSubFields(Set<SubFields> subFields) {
		this.subFields = subFields;
	}

	public Set<SubGroups> getSubGroups() {
		return subGroups;
	}

	public void setSubGroups(Set<SubGroups> subGroups) {
		this.subGroups = subGroups;
	}

	public Set<Operations> getOperations() {
		return operations;
	}

	public void setOperations(Set<Operations> operations) {
		this.operations = operations;
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
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (mandatory ? 1231 : 1237);
		result = prime * result + ((operations == null) ? 0 : operations.hashCode());
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		result = prime * result + ((placeHolderText == null) ? 0 : placeHolderText.hashCode());
		result = prime * result + ((savedData == null) ? 0 : savedData.hashCode());
		result = prime * result + ((subFields == null) ? 0 : subFields.hashCode());
		result = prime * result + ((subGroups == null) ? 0 : subGroups.hashCode());
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
		Fields other = (Fields) obj;
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
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!hash.equals(other.hash))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mandatory != other.mandatory)
			return false;
		if (operations == null) {
			if (other.operations != null)
				return false;
		} else if (!operations.equals(other.operations))
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
		if (subGroups == null) {
			if (other.subGroups != null)
				return false;
		} else if (!subGroups.equals(other.subGroups))
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
