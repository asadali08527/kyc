package co.yabx.kyc.app.fullKyc.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("retailers")
public class Retailers extends User implements Serializable {

	@Column(name = "retailer_id", unique = true)
	private String retailerId;

	@OneToMany(mappedBy = "retailers", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<DsrRetailersRelationships> retailers = new HashSet<>();

	public String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(String retailerId) {
		this.retailerId = retailerId;
	}

	public Set<DsrRetailersRelationships> getRetailers() {
		return retailers;
	}

	public void setRetailers(Set<DsrRetailersRelationships> retailers) {
		this.retailers = retailers;
	}

}
