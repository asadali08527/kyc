package co.yabx.kyc.app.fullKyc.entity;

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

@Entity
@Table(name = "dsr_retailers", indexes = { @Index(name = "dsr_msisdn", columnList = "dsr_msisdn") })
public class DsrRetailersRelationships {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "dsr_msisdn")
	private String dsrMsisdn;

	@Column(name = "ratailer")
	private Retailers retailers;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDsrMsisdn() {
		return dsrMsisdn;
	}

	public void setDsrMsisdn(String dsrMsisdn) {
		this.dsrMsisdn = dsrMsisdn;
	}

	public Retailers getRetailers() {
		return retailers;
	}

	public void setRetailers(Retailers retailers) {
		this.retailers = retailers;
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

}
