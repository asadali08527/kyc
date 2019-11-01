package co.yabx.kyc.app.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.StorageService;

/**
 * 
 * @author Asad.ali
 *
 */
@Service
public class StorageServiceImpl implements StorageService {

	@Autowired
	private AppConfigService appConfigService;

	private static final Logger LOGGER = LoggerFactory.getLogger(StorageServiceImpl.class);

	@Override
	public String uploadImage(MultipartFile file) throws Exception {
		String fileName = file.getOriginalFilename().replaceAll(" ", "_");
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		String newFileName = UUID.randomUUID().toString() + System.currentTimeMillis() + "." + extension;
		File convFile = new File(fileName);
		String path = appConfigService.getProperty("DOCUMENT_STORAGE_BASE_PATH", "/tmp/") + newFileName;
		convFile.createNewFile();
		try (FileOutputStream fos = new FileOutputStream(convFile)) {
			fos.write(file.getBytes());
		}
		BufferedImage image = null;
		image = ImageIO.read(convFile);
		ImageIO.write(image, extension, new File(path));
		return newFileName;
	}

}
