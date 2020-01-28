package co.yabx.kyc.app.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.entities.TextTemplates;
import co.yabx.kyc.app.repositories.TextTemplatesRepository;
import co.yabx.kyc.app.service.AppConfigService;
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

	@Autowired
	private AppConfigService appConfigService;

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
		if (textTemplatesList != null && !textTemplatesList.isEmpty()) {
			Optional<TextTemplates> optional = textTemplatesList.stream()
					.filter(f -> f != null && f.getTemplateName().equalsIgnoreCase(templateName)).findFirst();
			return optional.isPresent() ? optional.get().getTemplate() : null;
		}
		return templateName;
	}

	@Override
	public Map<String, List<TextTemplates>> uploadText() {
		Map<String, List<TextTemplates>> status = new HashMap<String, List<TextTemplates>>();
		List<TextTemplates> saved = new ArrayList<TextTemplates>();
		List<TextTemplates> unsaved = new ArrayList<TextTemplates>();

		try {
			FileInputStream file = new FileInputStream(new File(appConfigService.getProperty(
					"TEXT_TRANSLATION_FILE_LOCATION", "/var/lib/kyc/text_translation/textTemplates.xlsx")));
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				TextTemplates textTemplates = new TextTemplates();
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				int i = 0;
				String key = null;
				String value = null;
				String locale = null;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					// Check the cell type and format accordingly
					if (i == 0) {
						key = cell.getStringCellValue();
					} else if (i == 1) {
						value = cell.getStringCellValue();
					} else if (i == 2) {
						locale = cell.getStringCellValue();
					}
					i++;
				}
				textTemplates.setTemplateName(key);
				textTemplates.setTemplate(value);
				textTemplates.setLocale(locale);
				try {
					textTemplates = textTemplatesRepository.save(textTemplates);
					saved.add(textTemplates);
				} catch (Exception e) {
					e.printStackTrace();
					unsaved.add(textTemplates);
				}
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		status.put("Saved List", saved);
		status.put("Unsaved List", unsaved);
		return status;
	}

	@Override
	public List<TextTemplates> getTemplates() {
		return textTemplatesRepository.findAll();
	}

}
