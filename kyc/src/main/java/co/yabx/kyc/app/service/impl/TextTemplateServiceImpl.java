package co.yabx.kyc.app.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.entities.TextTemplates;
import co.yabx.kyc.app.repositories.TextTemplatesRepository;
import co.yabx.kyc.app.service.TextTemplateService;

/**
 * 
 * @author Asad.ali
 *
 */
@Service
public class TextTemplateServiceImpl implements TextTemplateService {

	@Autowired
	private TextTemplatesRepository textTemplatesRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(TextTemplateServiceImpl.class);

	@Override
	public List<TextTemplates> getTextTemplatesByLocale(String locale) {
		if (locale != null && !locale.isEmpty()) {
			return textTemplatesRepository.findByLocale(locale);
		}
		return Collections.EMPTY_LIST;
	}

	@Override
	public String getTemplate(List<TextTemplates> textTemplatesList, String templateName) {
		Optional<TextTemplates> optional = textTemplatesList.stream()
				.filter(f -> f != null && f.getTemplateName().equalsIgnoreCase(templateName)).findFirst();
		return optional.isPresent() ? optional.get().getTemplate() : null;
	}

}
