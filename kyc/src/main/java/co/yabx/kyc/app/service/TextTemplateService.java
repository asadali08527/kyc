package co.yabx.kyc.app.service;

import java.util.List;

import co.yabx.kyc.app.entities.TextTemplates;

/**
 * 
 * @author Asad.ali
 *
 */
public interface TextTemplateService {

	List<TextTemplates> getTextTemplatesByLocale(String locale);

	String getTemplate(List<TextTemplates> textTemplatesList, String pageTitle);
}
