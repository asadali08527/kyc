package co.yabx.kyc.app.fullKyc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "income_details", indexes = { @Index(name = "total_assets", columnList = "total_assets"),
		@Index(name = "monthly_income", columnList = "monthly_income") })
public class IncomeDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "monthly_income")
	private double monthlyIncome;

	@Column(name = "monthly_income_from_other_source")
	private double monthlyIncomeFromOtherSource;

	@Column(name = "monthly_expenditure")
	private double monthlyExpenditure;

	@Column(name = "monthly_surplus")
	private double monthlySurplus;

	@Column(name = "guarantor_net_assets")
	private double guarantorNetAssets;

	@Column(name = "total_assets")
	private double totalAssets;

	@Column(name = "total_liabilities")
	private double totalLiabilities;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "update_by")
	private String updatedBy;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Guarantors.class)
	Guarantors guarantors;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(double monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	public double getMonthlyExpenditure() {
		return monthlyExpenditure;
	}

	public void setMonthlyExpenditure(double monthlyExpenditure) {
		this.monthlyExpenditure = monthlyExpenditure;
	}

	public double getMonthlySurplus() {
		return monthlySurplus;
	}

	public void setMonthlySurplus(double monthlySurplus) {
		this.monthlySurplus = monthlySurplus;
	}

	public double getGuarantorNetAssets() {
		return guarantorNetAssets;
	}

	public void setGuarantorNetAssets(double guarantorNetAssets) {
		this.guarantorNetAssets = guarantorNetAssets;
	}

	public double getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(double totalAssets) {
		this.totalAssets = totalAssets;
	}

	public double getTotalLiabilities() {
		return totalLiabilities;
	}

	public void setTotalLiabilities(double totalLiabilities) {
		this.totalLiabilities = totalLiabilities;
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

	public double getMonthlyIncomeFromOtherSource() {
		return monthlyIncomeFromOtherSource;
	}

	public void setMonthlyIncomeFromOtherSource(double monthlyIncomeFromOtherSource) {
		this.monthlyIncomeFromOtherSource = monthlyIncomeFromOtherSource;
	}

	public Guarantors getGuarantors() {
		return guarantors;
	}

	public void setGuarantors(Guarantors guarantors) {
		this.guarantors = guarantors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		long temp;
		temp = Double.doubleToLongBits(guarantorNetAssets);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((guarantors == null) ? 0 : guarantors.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		temp = Double.doubleToLongBits(monthlyExpenditure);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(monthlyIncome);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(monthlyIncomeFromOtherSource);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(monthlySurplus);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalAssets);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalLiabilities);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
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
		IncomeDetails other = (IncomeDetails) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (Double.doubleToLongBits(guarantorNetAssets) != Double.doubleToLongBits(other.guarantorNetAssets))
			return false;
		if (guarantors == null) {
			if (other.guarantors != null)
				return false;
		} else if (!guarantors.equals(other.guarantors))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(monthlyExpenditure) != Double.doubleToLongBits(other.monthlyExpenditure))
			return false;
		if (Double.doubleToLongBits(monthlyIncome) != Double.doubleToLongBits(other.monthlyIncome))
			return false;
		if (Double.doubleToLongBits(monthlyIncomeFromOtherSource) != Double
				.doubleToLongBits(other.monthlyIncomeFromOtherSource))
			return false;
		if (Double.doubleToLongBits(monthlySurplus) != Double.doubleToLongBits(other.monthlySurplus))
			return false;
		if (Double.doubleToLongBits(totalAssets) != Double.doubleToLongBits(other.totalAssets))
			return false;
		if (Double.doubleToLongBits(totalLiabilities) != Double.doubleToLongBits(other.totalLiabilities))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		return true;
	}

}
