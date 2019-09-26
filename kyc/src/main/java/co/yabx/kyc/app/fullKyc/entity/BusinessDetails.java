package co.yabx.kyc.app.fullKyc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "business_details", indexes = { @Index(name = "msisdn", columnList = "msisdn"),
		@Index(name = "account_number", columnList = "account_number") })
public class BusinessDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "msisdn")
	private String msisdn;

	@Column(name = "business_name")
	private String businessName;

	/**
	 * NAME OF THE PROPRIETOR/PARTNERS/MD/DIRECTOR
	 */
	@Column(name = "partner_or_director_name")
	private String directorOrPartnerName;

	@Column(name = "facility_details")
	private String facilityDetails;

	@Column(name = "facility_type")
	private String facilityType;

	@Column(name = "fixed_asset_purchase")
	private String fixedAssetPurchase;

	@Column(name = "fixed_asset_name")
	private String fixedAssetName;

	@Column(name = "price")
	private double price;

	@Column(name = "origin")
	private String origin;

	@Column(name = "proposed_collateral")
	private String proposedCollateral;

	@Column(name = "business_type")
	private String businessType;

	@Column(name = "sector")
	private String sector;

	@Column(name = "detail_of_busness")
	private String detailOfBusness;

	@Column(name = "initial_capital")
	private double initialCapital;

	@Column(name = "fund_source")
	private String fundSource;

	@Column(name = "vat_registration_number")
	private String vatRegistrationNumber;

	@Column(name = "business_start_date")
	private Long businessStartDate;

	@Column(name = "business_tin")
	private String businessTin;

	@Column(name = "annual_sales")
	private double annualSales;

	@Column(name = "annual_gross_profit")
	private double annualGrossProfit;

	@Column(name = "annual_expenses")
	private double annualExpenses;

	@Column(name = "fixed_assets_value")
	private double valueOfFixedAssets;

	@Column(name = "employees")
	private short numberOfEmployees;

	@Column(name = "stock_value")
	private double stockValue;

	@Column(name = "monthly_turn_over")
	private double monthlyTurnOver;

	@Column(name = "deposits")
	private double deposits;

	@Column(name = "withdrawls")
	private double withdrawls;

	@Column(name = "initial_deposits")
	private double initialDeposit;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "license_details", referencedColumnName = "id")
	private LicenseDetails licenseDetails;

	@OneToMany(mappedBy = "businessDetails", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<AddressDetails> addressDetails;

	@OneToMany(mappedBy = "businessDetails", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<BankAccountDetails> bankAccountDetails;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "update_by")
	private String updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

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

}
