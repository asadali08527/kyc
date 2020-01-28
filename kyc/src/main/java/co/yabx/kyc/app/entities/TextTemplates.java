package co.yabx.kyc.app.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import co.yabx.kyc.app.util.EncoderDecoderUtil;

@Entity
@Table(name = "text_templates", uniqueConstraints = { @UniqueConstraint(columnNames = { "templateName", "locale" }) })
public class TextTemplates implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String templateName;

	private String template;

	private Date updatedTime;

	private String locale;

	@PrePersist
	@PreUpdate
	public void persistDate() {
		updatedTime = new Date();
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplate() {
		return EncoderDecoderUtil.base64Decode(template);
	}

	public void setTemplate(String template) {
		this.template = EncoderDecoderUtil.base64Encode(template);
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}
}
