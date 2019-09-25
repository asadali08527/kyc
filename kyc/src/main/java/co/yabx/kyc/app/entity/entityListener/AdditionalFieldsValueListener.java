package co.yabx.kyc.app.entity.entityListener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import co.yabx.kyc.app.miniKyc.entity.AdditionalFieldsValue;
import co.yabx.kyc.app.miniKyc.entity.KycDetails;

public class AdditionalFieldsValueListener extends Listener {
	@PrePersist
	@PreUpdate
	public void persist(AdditionalFieldsValue additionalFieldsValue) {

		if (checkEnableDisable()) {
			if (additionalFieldsValue != null) {
				KycDetails kyc = additionalFieldsValue.getKycDetails();
				publishKycId(kyc.getMsisdn());
			}
		}
	}

}
