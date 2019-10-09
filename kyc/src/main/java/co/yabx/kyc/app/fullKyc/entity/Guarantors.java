package co.yabx.kyc.app.fullKyc.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import co.yabx.kyc.app.enums.GuarantorType;

@Entity
@DiscriminatorValue("guarantors")
public class Guarantors extends User {

	@Column(name = "guarantor_type")
	private GuarantorType guarantorType;
	
	


}
