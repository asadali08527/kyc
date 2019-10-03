package co.yabx.kyc.app.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.dto.DsrProfileDTO;
import co.yabx.kyc.app.dto.ResponseDTO;
import co.yabx.kyc.app.dto.VerifyOtpDTO;
import co.yabx.kyc.app.dto.dtoHelper.DsrDtoHelper;
import co.yabx.kyc.app.enums.DsrProfileStatus;
import co.yabx.kyc.app.fullKyc.dto.AddressDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.WorkEducationDetailsDTO;
import co.yabx.kyc.app.fullKyc.entity.AddressDetails;
import co.yabx.kyc.app.fullKyc.entity.DSRUser;
import co.yabx.kyc.app.fullKyc.entity.WorkEducationDetails;
import co.yabx.kyc.app.fullKyc.repository.DSRUserRepository;
import co.yabx.kyc.app.service.AdminService;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.DSRService;

/**
 * 
 * @author Asad.ali
 *
 */
@Service
public class DSRServiceImpl implements DSRService {

	@Autowired
	private AdminService adminService;

	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private DSRUserRepository dsrUserRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(DSRServiceImpl.class);

	@Override
	public ResponseDTO login(String msisdn) {
		// userRepository.findByMsisdn
		return DsrDtoHelper.getLoginDTO(msisdn, "SUCCESS", "200", null);
	}

	@Override
	public ResponseDTO verifyOTP(VerifyOtpDTO verifyOtpDTO) {
		// TODO Auto-generated method stub
		ResponseDTO responseDTO = DsrDtoHelper.getLoginDTO("", "SUCCESS", "200", DsrProfileStatus.NEW);
		responseDTO.setAuthInfo(adminService.prepareTokenAndKey(null, verifyOtpDTO.getDsrMSISDN()));
		return responseDTO;
	}

	@Override
	public ResponseDTO submitDsrProfile(DsrProfileDTO dsrProfileDTO) {
		if (dsrProfileDTO != null && dsrProfileDTO.getDsrMSISDN() != null) {
			try {
				DSRUser dsrUser = dsrUserRepository.findBymsisdn(dsrProfileDTO.getDsrMSISDN());
				if (dsrUser == null) {
					dsrUser = persistDSRUser(dsrProfileDTO, DsrProfileStatus.NEW);
					return DsrDtoHelper.getLoginDTO(null, "SUCCESS", "200", DsrProfileStatus.NEW);
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("Exception raised while persisting DSR profile={}, error={} ",
						dsrProfileDTO.getDsrMSISDN(), e.getMessage());
				return DsrDtoHelper.getLoginDTO(null, "Internal Server Error", "500", null);

			}
		}
		return DsrDtoHelper.getLoginDTO(null, "Invalid Feilds", "403", null);
	}

	@Transactional
	private DSRUser persistDSRUser(DsrProfileDTO dsrProfileDTO, DsrProfileStatus dsrStatus) {
		DSRUser dsrUser = new DSRUser();
		dsrUser.setDsrStatus(dsrStatus);
		dsrUser.setFirstName(dsrProfileDTO.getName());
		dsrUser.setDob(dsrProfileDTO.getDateOfBirth());
		dsrUser.setFathersName(dsrProfileDTO.getFatherName());
		dsrUser.setEmail(dsrProfileDTO.getEmail());
		dsrUser.setAlternateMobileNumber(dsrProfileDTO.getAlternateMobileNumber());
		dsrUser.setMsisdn(dsrProfileDTO.getDsrMSISDN());
		dsrUser.setWorkEducationDetails(prepareWorkEducationdetails(dsrProfileDTO));
		dsrUser.setAddressDetails(prepareAddress(dsrProfileDTO));
		dsrUser = dsrUserRepository.save(dsrUser);
		return dsrUser;
	}

	private Set<AddressDetails> prepareAddress(DsrProfileDTO dsrProfileDTO) {
		if (dsrProfileDTO != null && dsrProfileDTO.getAddressDetailsDTO() != null
				&& !dsrProfileDTO.getAddressDetailsDTO().isEmpty()) {
			Set<AddressDetails> addressDetails = new HashSet<AddressDetails>();
			List<AddressDetailsDTO> addressDetailsDTO = dsrProfileDTO.getAddressDetailsDTO();
			for (AddressDetailsDTO detailsDTO : addressDetailsDTO) {
				AddressDetails addressDetail = new AddressDetails();
				addressDetail.setAddressType(detailsDTO.getAddressType());
				addressDetail.setArea(detailsDTO.getArea());
				addressDetail.setCity(detailsDTO.getCity());
				addressDetail.setHouseNumberOrStreetName(detailsDTO.getHouseNumberOrStreetName());
				addressDetail.setRegion(detailsDTO.getRegion());
				addressDetail.setZipCode(detailsDTO.getZipCode());
				addressDetails.add(addressDetail);
			}
			return addressDetails;
		}
		return null;
	}

	private Set<WorkEducationDetails> prepareWorkEducationdetails(DsrProfileDTO dsrProfileDTO) {
		if (dsrProfileDTO != null && dsrProfileDTO.getWorkEducationDetailsDTO() != null) {
			WorkEducationDetailsDTO workEducationDetailsDTO = dsrProfileDTO.getWorkEducationDetailsDTO();
			Set<WorkEducationDetails> workEducationDetails = new HashSet<WorkEducationDetails>();
			WorkEducationDetails details = new WorkEducationDetails();
			details.setDesignation(workEducationDetailsDTO.getDesignation());
			details.setEducationalQualification(workEducationDetailsDTO.getEducationalQualification());
			details.setEmployer(workEducationDetailsDTO.getEmployer());
			details.setOccupation(workEducationDetailsDTO.getOccupation());
			workEducationDetails.add(details);
			return workEducationDetails;
		}
		return null;
	}

}
