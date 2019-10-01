package co.yabx.kyc.app.fullKyc.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("retailers")
public class Retailers extends User {

	@Column(name = "retailer_id", unique = true)
	private String retailerId;

	public String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(String retailerId) {
		this.retailerId = retailerId;
	}

}
