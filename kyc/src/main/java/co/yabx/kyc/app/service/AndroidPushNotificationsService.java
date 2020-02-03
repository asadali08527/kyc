package co.yabx.kyc.app.service;

import org.springframework.http.ResponseEntity;

/**
 * 
 * @author Asad.ali
 *
 */
public interface AndroidPushNotificationsService {

	ResponseEntity<String> sendNotificationToDevice(String deviceId, String title, String message, String dataToSend);

	void prepareAndSendNotification(String msisdn, String title, String message, String data);

	ResponseEntity<String> sendNotificationToUser(String msisdn, String title, String message, String data);

}
