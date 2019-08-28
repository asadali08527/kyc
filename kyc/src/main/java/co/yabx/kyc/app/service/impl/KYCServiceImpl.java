package co.yabx.kyc.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.yabx.kyc.app.dto.KycDetailsDTO;
import co.yabx.kyc.app.repository.KycDetailsRepository;
import co.yabx.kyc.app.repository.KycDocumentsRepository;
import co.yabx.kyc.app.service.KYCService;

@Service
public class KYCServiceImpl implements KYCService {
	@Autowired
	private KycDetailsRepository kycDetailsRepository;

	@Autowired
	private KycDocumentsRepository kycDocumentsRepository;

	@Override
	public void persistKYC(KycDetailsDTO kycdto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void persistPhoto(MultipartFile multipartFile) {
		// TODO Auto-generated method stub

	}

}
