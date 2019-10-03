package co.yabx.kyc.app.service;

import java.util.Map;

public interface AdminService {

	Map<String, String> getAuthToken(Long yabxId);

	Map<String, String> prepareTokenAndKey(Long yabxId, String msisdn);

}
