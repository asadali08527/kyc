package co.yabx.kyc.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import co.yabx.kyc.app.enums.AddressType;

@Entity
@Table(name = "address_details", indexes = { @Index(name = "msisdn", columnList = "msisdn"),
		@Index(name = "address_type", columnList = "address_type") })
public class AddressDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "msisdn", nullable = false)
	private String msisdn;

	@Column(name = "house_number_or_street_name")
	private String houseNumberOrStreetName;

	@Column(name = "area")
	private String area;

	@Column(name = "city")
	private String city;

	@Column(name = "region")
	private String region;

	@Column(name = "zip_code")
	private Integer zipCode;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "update_by")
	private String updatedBy;

	@Column(name = "address_type")
	private AddressType addressType;

}
