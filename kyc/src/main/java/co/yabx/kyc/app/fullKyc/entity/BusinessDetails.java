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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import co.yabx.kyc.app.enums.BusinessSector;
import co.yabx.kyc.app.enums.BusinessType;
import co.yabx.kyc.app.enums.FacilityDetails;
import co.yabx.kyc.app.enums.FacilityType;

@Entity
@Table(name = "business_details", indexes = { @Index(name = "business_type", columnList = "business_type"),
		@Index(name = "business_name", columnList = "business_name"),
		@Index(name = "license_details", columnList = "license_details") })
public class BusinessDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "business_phone_number")
	private String businessPhone;

	@Column(name = "business_name")
	private String businessName;

	/**
	 * NAME OF THE PROPRIETOR/PARTNERS/MD/DIRECTOR
	 */
	@Column(name = "partner_or_director_name")
	private String directorOrPartnerName;

	@Column(name = "facility_details", columnDefinition = "varchar(32) ")
	private FacilityDetails facilityDetails;

	@Column(name = "facility_type", columnDefinition = "varchar(32) ")
	private FacilityType facilityType;

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

	@Column(name = "business_type", length = 100, nullable = false, columnDefinition = "varchar(32) default 'Proprietorship'")
	private BusinessType businessType;

	@Column(name = "sector", length = 100, columnDefinition = "varchar(32) ")
	private BusinessSector sector;

	@Column(name = "detail_of_busness", length = 100, columnDefinition = "varchar(32) default 'Telecom Retailer'")
	private String detailOfBusness;

	@Column(name = "initial_capital")
	private double initialCapital;

	@Column(name = "fund_source", length = 100, columnDefinition = "varchar(32) default 'Business'")
	private String fundSource;

	@Column(name = "vat_registration_number")
	private String vatRegistrationNumber;

	@Column(name = "business_start_date")
	private String businessStartDate;

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
	private int numberOfEmployees;

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

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	User user;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "receivables")
	private String receivables;

	@Column(name = "payables")
	private String payables;

	public Long getId() {
		return id;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getDirectorOrPartnerName() {
		return directorOrPartnerName;
	}

	public void setDirectorOrPartnerName(String directorOrPartnerName) {
		this.directorOrPartnerName = directorOrPartnerName;
	}

	public FacilityDetails getFacilityDetails() {
		return facilityDetails;
	}

	public void setFacilityDetails(FacilityDetails facilityDetails) {
		this.facilityDetails = facilityDetails;
	}

	public FacilityType getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(FacilityType facilityType) {
		this.facilityType = facilityType;
	}

	public String getFixedAssetPurchase() {
		return fixedAssetPurchase;
	}

	public void setFixedAssetPurchase(String fixedAssetPurchase) {
		this.fixedAssetPurchase = fixedAssetPurchase;
	}

	public String getFixedAssetName() {
		return fixedAssetName;
	}

	public void setFixedAssetName(String fixedAssetName) {
		this.fixedAssetName = fixedAssetName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getProposedCollateral() {
		return proposedCollateral;
	}

	public void setProposedCollateral(String proposedCollateral) {
		this.proposedCollateral = proposedCollateral;
	}

	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	public BusinessSector getSector() {
		return sector;
	}

	public void setSector(BusinessSector sector) {
		this.sector = sector;
	}

	public String getDetailOfBusness() {
		return detailOfBusness;
	}

	public void setDetailOfBusness(String detailOfBusness) {
		this.detailOfBusness = detailOfBusness;
	}

	public double getInitialCapital() {
		return initialCapital;
	}

	public void setInitialCapital(double initialCapital) {
		this.initialCapital = initialCapital;
	}

	public String getFundSource() {
		return fundSource;
	}

	public void setFundSource(String fundSource) {
		this.fundSource = fundSource;
	}

	public String getVatRegistrationNumber() {
		return vatRegistrationNumber;
	}

	public void setVatRegistrationNumber(String vatRegistrationNumber) {
		this.vatRegistrationNumber = vatRegistrationNumber;
	}

	public String getBusinessTin() {
		return businessTin;
	}

	public void setBusinessTin(String businessTin) {
		this.businessTin = businessTin;
	}

	public double getAnnualSales() {
		return annualSales;
	}

	public void setAnnualSales(double annualSales) {
		this.annualSales = annualSales;
	}

	public double getAnnualGrossProfit() {
		return annualGrossProfit;
	}

	public void setAnnualGrossProfit(double annualGrossProfit) {
		this.annualGrossProfit = annualGrossProfit;
	}

	public double getAnnualExpenses() {
		return annualExpenses;
	}

	public void setAnnualExpenses(double annualExpenses) {
		this.annualExpenses = annualExpenses;
	}

	public double getValueOfFixedAssets() {
		return valueOfFixedAssets;
	}

	public void setValueOfFixedAssets(double valueOfFixedAssets) {
		this.valueOfFixedAssets = valueOfFixedAssets;
	}

	public int getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(short numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

	public double getStockValue() {
		return stockValue;
	}

	public void setStockValue(double stockValue) {
		this.stockValue = stockValue;
	}

	public double getMonthlyTurnOver() {
		return monthlyTurnOver;
	}

	public void setMonthlyTurnOver(double monthlyTurnOver) {
		this.monthlyTurnOver = monthlyTurnOver;
	}

	public double getDeposits() {
		return deposits;
	}

	public void setDeposits(double deposits) {
		this.deposits = deposits;
	}

	public double getWithdrawls() {
		return withdrawls;
	}

	public void setWithdrawls(double withdrawls) {
		this.withdrawls = withdrawls;
	}

	public double getInitialDeposit() {
		return initialDeposit;
	}

	public void setInitialDeposit(double initialDeposit) {
		this.initialDeposit = initialDeposit;
	}

	public LicenseDetails getLicenseDetails() {
		return licenseDetails;
	}

	public void setLicenseDetails(LicenseDetails licenseDetails) {
		this.licenseDetails = licenseDetails;
	}

	public Set<AddressDetails> getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(Set<AddressDetails> addressDetails) {
		for (AddressDetails details : addressDetails) {
			details.setBusinessDetails(this);
		}
		this.addressDetails = addressDetails;
	}

	public Set<BankAccountDetails> getBankAccountDetails() {
		return bankAccountDetails;
	}

	public void setBankAccountDetails(Set<BankAccountDetails> bankAccountDetails) {
		for (BankAccountDetails details : bankAccountDetails) {
			details.setBusinessDetails(this);
		}
		this.bankAccountDetails = bankAccountDetails;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setId(Long id) {
		this.id = id;
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

	@PrePersist
	private void prePersist() {
		if (createdAt == null) {
			createdAt = new Date();
			updatedAt = new Date();
		}
	}

	public String getBusinessPhone() {
		return businessPhone;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	@PreUpdate
	private void preUpdate() {
		updatedAt = new Date();

	}

	public void setNumberOfEmployees(int numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

	public String getBusinessStartDate() {
		return businessStartDate;
	}

	public void setBusinessStartDate(String businessStartDate) {
		this.businessStartDate = businessStartDate;
	}

	public String getReceivables() {
		return receivables;
	}

	public void setReceivables(String receivables) {
		this.receivables = receivables;
	}

	public String getPayables() {
		return payables;
	}

	public void setPayables(String payables) {
		this.payables = payables;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addressDetails == null) ? 0 : addressDetails.hashCode());
		long temp;
		temp = Double.doubleToLongBits(annualExpenses);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(annualGrossProfit);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(annualSales);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((bankAccountDetails == null) ? 0 : bankAccountDetails.hashCode());
		result = prime * result + ((businessName == null) ? 0 : businessName.hashCode());
		result = prime * result + ((businessPhone == null) ? 0 : businessPhone.hashCode());
		result = prime * result + ((businessStartDate == null) ? 0 : businessStartDate.hashCode());
		result = prime * result + ((businessTin == null) ? 0 : businessTin.hashCode());
		result = prime * result + ((businessType == null) ? 0 : businessType.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		temp = Double.doubleToLongBits(deposits);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((detailOfBusness == null) ? 0 : detailOfBusness.hashCode());
		result = prime * result + ((directorOrPartnerName == null) ? 0 : directorOrPartnerName.hashCode());
		result = prime * result + ((facilityDetails == null) ? 0 : facilityDetails.hashCode());
		result = prime * result + ((facilityType == null) ? 0 : facilityType.hashCode());
		result = prime * result + ((fixedAssetName == null) ? 0 : fixedAssetName.hashCode());
		result = prime * result + ((fixedAssetPurchase == null) ? 0 : fixedAssetPurchase.hashCode());
		result = prime * result + ((fundSource == null) ? 0 : fundSource.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		temp = Double.doubleToLongBits(initialCapital);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(initialDeposit);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((licenseDetails == null) ? 0 : licenseDetails.hashCode());
		temp = Double.doubleToLongBits(monthlyTurnOver);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + numberOfEmployees;
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + ((payables == null) ? 0 : payables.hashCode());
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((proposedCollateral == null) ? 0 : proposedCollateral.hashCode());
		result = prime * result + ((receivables == null) ? 0 : receivables.hashCode());
		result = prime * result + ((sector == null) ? 0 : sector.hashCode());
		temp = Double.doubleToLongBits(stockValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		temp = Double.doubleToLongBits(valueOfFixedAssets);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((vatRegistrationNumber == null) ? 0 : vatRegistrationNumber.hashCode());
		temp = Double.doubleToLongBits(withdrawls);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		BusinessDetails other = (BusinessDetails) obj;
		if (addressDetails == null) {
			if (other.addressDetails != null)
				return false;
		} else if (!addressDetails.equals(other.addressDetails))
			return false;
		if (Double.doubleToLongBits(annualExpenses) != Double.doubleToLongBits(other.annualExpenses))
			return false;
		if (Double.doubleToLongBits(annualGrossProfit) != Double.doubleToLongBits(other.annualGrossProfit))
			return false;
		if (Double.doubleToLongBits(annualSales) != Double.doubleToLongBits(other.annualSales))
			return false;
		if (bankAccountDetails == null) {
			if (other.bankAccountDetails != null)
				return false;
		} else if (!bankAccountDetails.equals(other.bankAccountDetails))
			return false;
		if (businessName == null) {
			if (other.businessName != null)
				return false;
		} else if (!businessName.equals(other.businessName))
			return false;
		if (businessPhone == null) {
			if (other.businessPhone != null)
				return false;
		} else if (!businessPhone.equals(other.businessPhone))
			return false;
		if (businessStartDate == null) {
			if (other.businessStartDate != null)
				return false;
		} else if (!businessStartDate.equals(other.businessStartDate))
			return false;
		if (businessTin == null) {
			if (other.businessTin != null)
				return false;
		} else if (!businessTin.equals(other.businessTin))
			return false;
		if (businessType != other.businessType)
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (Double.doubleToLongBits(deposits) != Double.doubleToLongBits(other.deposits))
			return false;
		if (detailOfBusness == null) {
			if (other.detailOfBusness != null)
				return false;
		} else if (!detailOfBusness.equals(other.detailOfBusness))
			return false;
		if (directorOrPartnerName == null) {
			if (other.directorOrPartnerName != null)
				return false;
		} else if (!directorOrPartnerName.equals(other.directorOrPartnerName))
			return false;
		if (facilityDetails != other.facilityDetails)
			return false;
		if (facilityType != other.facilityType)
			return false;
		if (fixedAssetName == null) {
			if (other.fixedAssetName != null)
				return false;
		} else if (!fixedAssetName.equals(other.fixedAssetName))
			return false;
		if (fixedAssetPurchase == null) {
			if (other.fixedAssetPurchase != null)
				return false;
		} else if (!fixedAssetPurchase.equals(other.fixedAssetPurchase))
			return false;
		if (fundSource == null) {
			if (other.fundSource != null)
				return false;
		} else if (!fundSource.equals(other.fundSource))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(initialCapital) != Double.doubleToLongBits(other.initialCapital))
			return false;
		if (Double.doubleToLongBits(initialDeposit) != Double.doubleToLongBits(other.initialDeposit))
			return false;
		if (licenseDetails == null) {
			if (other.licenseDetails != null)
				return false;
		} else if (!licenseDetails.equals(other.licenseDetails))
			return false;
		if (Double.doubleToLongBits(monthlyTurnOver) != Double.doubleToLongBits(other.monthlyTurnOver))
			return false;
		if (numberOfEmployees != other.numberOfEmployees)
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (payables == null) {
			if (other.payables != null)
				return false;
		} else if (!payables.equals(other.payables))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (proposedCollateral == null) {
			if (other.proposedCollateral != null)
				return false;
		} else if (!proposedCollateral.equals(other.proposedCollateral))
			return false;
		if (receivables == null) {
			if (other.receivables != null)
				return false;
		} else if (!receivables.equals(other.receivables))
			return false;
		if (sector != other.sector)
			return false;
		if (Double.doubleToLongBits(stockValue) != Double.doubleToLongBits(other.stockValue))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (Double.doubleToLongBits(valueOfFixedAssets) != Double.doubleToLongBits(other.valueOfFixedAssets))
			return false;
		if (vatRegistrationNumber == null) {
			if (other.vatRegistrationNumber != null)
				return false;
		} else if (!vatRegistrationNumber.equals(other.vatRegistrationNumber))
			return false;
		if (Double.doubleToLongBits(withdrawls) != Double.doubleToLongBits(other.withdrawls))
			return false;
		return true;
	}

}
