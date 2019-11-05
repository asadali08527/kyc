package co.yabx.kyc.app.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import co.yabx.kyc.app.entities.Fields;

/**
 * 
 * @author Asad.ali
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubFieldsDTO implements Serializable {

	private Long id;

	private String fieldType;

	private FieldsDTO parent;

	private FieldsDTO fields;

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FieldsDTO getParent() {
		return parent;
	}

	public void setParent(FieldsDTO parent) {
		this.parent = parent;
	}

	public String getFieldType() {
		return fieldType;
	}

	public FieldsDTO getFields() {
		return fields;
	}

	public void setFields(FieldsDTO fields) {
		this.fields = fields;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldType == null) ? 0 : fieldType.hashCode());
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
		SubFieldsDTO other = (SubFieldsDTO) obj;
		if (fieldType == null) {
			if (other.fieldType != null)
				return false;
		} else if (!fieldType.equals(other.fieldType))
			return false;
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}

}
