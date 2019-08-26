package co.yabx.kyc.app.entity.entityListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.impl.AppConfigServiceImpl;
import co.yabx.kyc.app.util.SpringUtil;

public abstract class Listener {
	private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);

	protected boolean checkEnableDisable() {
		AppConfigService dbConfigService = SpringUtil.bean(AppConfigServiceImpl.class);
		return dbConfigService.getBooleanProperty("ENTITY_LISTENER_ENABLE", false);
	}

	protected void publishKycId(Long id) {
		// TODO Auto-generated method stub

	}

}
