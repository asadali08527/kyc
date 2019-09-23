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

@Entity
@Table(name = "income_details", indexes = { @Index(name = "msisdn", columnList = "msisdn") })
public class IncomeDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "msisdn", nullable = false)
	private String msisdn;

	@Column(name = "monthly_income")
	private double monthlyIncome;

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

}
