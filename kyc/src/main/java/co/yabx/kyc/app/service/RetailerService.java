package co.yabx.kyc.app.service;

import co.yabx.kyc.app.dto.QuestionAnswerDTO;
import co.yabx.kyc.app.dto.ResponseDTO;
import co.yabx.kyc.app.dto.RetailerRequestDTO;
import co.yabx.kyc.app.fullKyc.dto.BusinessDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.LiabilitiesDetailsDTO;
import co.yabx.kyc.app.fullKyc.dto.UserDTO;

public interface RetailerService {

	ResponseDTO retailerDetails(String dsrMsisdn, Long retailerId);

	ResponseDTO submitRetailerProfile(RetailerRequestDTO retailerRequestDTO) throws Exception;

	ResponseDTO submitRetailerNomineeProfile(UserDTO userDTO);

	ResponseDTO submitRetailerBusinessInfo(BusinessDetailsDTO businessDetailsDTO);

	ResponseDTO submitLiabilitiesInfo(LiabilitiesDetailsDTO liabilitiesDetailsDTO);

	ResponseDTO getRetailerQuestion(Integer questionId);

	ResponseDTO getRetailerAllQuestions(String retailerId);

	ResponseDTO persistRetailerAnswer(QuestionAnswerDTO questionAnswerDTO);

	ResponseDTO searchRetailerByDsr(String dsrMsisdn, String retailerId);

	ResponseDTO submitRetailerProfile(UserDTO userDTO);

	ResponseDTO getSummaries(String dsrMSISDN, Integer startIndex, Integer endIndex);

	ResponseDTO submitKyc(String dsrMsisdn, Long retailerId);

}
