package co.yabx.kyc.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.yabx.kyc.app.dto.KYCDTO;
import co.yabx.kyc.app.repository.KYCRepository;
import co.yabx.kyc.app.repository.PhotosRepository;
import co.yabx.kyc.app.service.KYCService;

@Service
public class KYCServiceImpl implements KYCService {
	@Autowired
	private KYCRepository kycRepository;

	@Autowired
	private PhotosRepository photosRepository;

	@Override
	public void persistKYC(KYCDTO kycdto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void persistPhoto(MultipartFile multipartFile) {
		// TODO Auto-generated method stub
		
	}

}
