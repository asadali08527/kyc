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
import co.yabx.kyc.app.service.TextTemplateService;
import co.yabx.kyc.app.service.UserService;

/**
 * 
 * @author Asad.ali
 *
 */
@Controller
@CrossOrigin
@RequestMapping(value = "/v1")
public class TextTemplateController {

	@Autowired
	private TextTemplateService textTemplateService;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private UserService userService;

	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private AdminService adminService;

	private static final Logger LOGGER = LoggerFactory.getLogger(TextTemplateController.class);

	@RequestMapping(value = "/upload/text/templates", method = RequestMethod.POST)
	public ResponseEntity<?> uploadImage(@RequestParam("secret_key") String secretKey) throws Exception {
		if (appConfigService.getProperty("CREATE_RM_API_PASSWORD", "magic@yabx").equals(secretKey)) {
			return new ResponseEntity<>(textTemplateService.uploadText(), HttpStatus.OK);
		}
		return new ResponseEntity<>("Invalid secret key", HttpStatus.UNAUTHORIZED);

	}

}
