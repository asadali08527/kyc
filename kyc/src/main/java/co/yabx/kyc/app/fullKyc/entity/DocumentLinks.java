package co.yabx.kyc.app.fullKyc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import co.yabx.kyc.app.enums.DocumentSide;

/**
 * 
 * @author Asad Ali
 *
 */
@Entity
@Table(name = "document_urls", indexes = { @Index(name = "document_side", columnList = "document_side") })
public class DocumentLinks implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "document_side")
	private DocumentSide documentSide;

	@Column(name = "document_url")
	private String documentUrl;

	@Column(name = "document")
	private DocumentDetails documentDetails;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

}
