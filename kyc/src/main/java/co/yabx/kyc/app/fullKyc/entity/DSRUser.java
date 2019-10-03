package co.yabx.kyc.app.fullKyc.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import co.yabx.kyc.app.enums.DsrProfileStatus;

@Entity
@DiscriminatorValue("dsr_users")
public class DSRUser extends User {
	
	private DsrProfileStatus dsrStatus;

	public DsrProfileStatus getDsrStatus() {
		return dsrStatus;
	}

	public void setDsrStatus(DsrProfileStatus dsrStatus) {
		this.dsrStatus = dsrStatus;
	}

}
