package co.yabx.kyc.app.entity.entityListener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import co.yabx.kyc.app.entity.AdditionalFieldsValue;
import co.yabx.kyc.app.entity.KycDetails;

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
