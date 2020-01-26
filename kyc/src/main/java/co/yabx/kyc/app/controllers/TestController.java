package co.yabx.kyc.app.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import co.yabx.kyc.app.dto.UserDto;
import co.yabx.kyc.app.fullKyc.entity.AttachmentDetails;
import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.service.AdminService;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.AttachmentService;
import co.yabx.kyc.app.service.StorageService;
import co.yabx.kyc.app.service.UserService;

/**
 * 
 * @author Asad.ali
 *
 */
@Controller
@CrossOrigin
@RequestMapping(value = "/v1")
public class TestController {

	@Autowired
	private StorageService storageService;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private UserService userService;

	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private AdminService adminService;

	private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

	@RequestMapping(value = "/upload/image", method = RequestMethod.POST)
	public ResponseEntity<?> uploadImage(@RequestParam("retailerId") Long retailerId, @RequestParam MultipartFile files,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		User user = userService.getRetailerById(retailerId);
		try {
			String filename = storageService.uploadImage(files, user.getId());
			AttachmentDetails attachmentDetails = attachmentService.persistInDb(user, files, filename);
			if (attachmentDetails != null)
				return new ResponseEntity<>(files, HttpStatus.OK);
			else {
				return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("exception raised while uploading image={},retailer={},error={}", files.getOriginalFilename(),
					retailerId, e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/register/rm", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> registerRM(@RequestBody UserDto userDto) {
		if (userDto != null && appConfigService.getProperty("CREATE_RM_API_PASSWORD", "magic@yabx")
				.equals(userDto.getSecret_key())) {
			LOGGER.info("/register/rm request recieved with body{}", userDto);
			return new ResponseEntity<>(adminService.registerRM(userDto), HttpStatus.OK);

		}
		return new ResponseEntity<>("Invalid secret key", HttpStatus.UNAUTHORIZED);

	}
}
