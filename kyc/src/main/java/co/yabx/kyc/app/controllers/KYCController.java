package co.yabx.kyc.app.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import co.yabx.kyc.app.dto.KYCDTO;
import co.yabx.kyc.app.dto.PhotosDTO;
import co.yabx.kyc.app.entity.Photos;
import co.yabx.kyc.app.service.KYCService;
import co.yabx.kyc.app.service.impl.AppConfigServiceImpl;

/**
 * 
 * @author Asad.ali
 *
 */
@Controller
@RequestMapping(value = "/v1")
public class KYCController {
	@Autowired
	private KYCService kycService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KYCController.class);


	@RequestMapping(value = "/create/kyc", method = RequestMethod.POST)
	public ResponseEntity<?> createAccount(@RequestParam(name = "kyc") KYCDTO kycdto) {
		KYCDTO customerDto = new KYCDTO();
		customerDto.setFirstName("Asad");
		customerDto.setLastName("Ali");
		customerDto.setAddress("Gelhi NCR");
		customerDto.setArea("Sector 51");
		customerDto.setCity("Gurgaon");
		customerDto.setIdNumber("APPPA7499M");
		customerDto.setIdType("PAN CARD");
		customerDto.setZipCode("122001");
		customerDto.setPhotos(preparePhotos());
		return new ResponseEntity<>(customerDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/upload/photo", method = RequestMethod.POST)
	public ResponseEntity<?> createAccount(@RequestParam(name = "file") MultipartFile file) {
		File convFile = new File(file.getOriginalFilename());
		try {
			convFile.createNewFile();
			try (FileOutputStream fos = new FileOutputStream(convFile)) {
				fos.write(file.getBytes());
			}
			BufferedImage image = null;
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			image = ImageIO.read(convFile);
			ImageIO.write(image, extension, new File("D://" + file.getOriginalFilename() ));
		} catch (Exception e) {

		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private List<PhotosDTO> preparePhotos() {
		PhotosDTO photosDTO = new PhotosDTO();
		return null;
	}

}
