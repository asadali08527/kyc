package co.yabx.kyc.app.service;

import java.util.List;

import co.yabx.kyc.app.miniKyc.entity.AppConfigurations;

public interface AppConfigService {

	String getProperty(String propertyName, String defaultValue);

	String getPropertyWithEmptyCheck(String propertyName, String defaultValue);

	String getProperty(String propertyName);

	Boolean getBooleanProperty(String propertyName);

	Boolean getBooleanProperty(String propertyName, Boolean defaultValue);

	Integer getIntProperty(String propertyName, Integer defaultValue);

	Long getLongProperty(String propertyName);

	Long getLongProperty(String propertyName, Long defaultValue);

	Integer getIntProperty(String propertyName);

	Double getDoubleProperty(String propertyName, Double defaultValue);

	Double getDoubleProperty(String propertyName);

	Float getFloatProperty(String propertyName, Float defaultValue);

	Float getFloatProperty(String propertyName);

	void refresh();

//	void update(AppConfigurations config);

	/*
	 * List<String> getPropertyValues(String propertyName, List<String> asList);
	 */
	/*
	 * void updateTag(String tag, String value);
	 */
}
