package co.yabx.kyc.app.fullKyc.dto;

import java.io.Serializable;
import java.util.Date;

import co.yabx.kyc.app.fullKyc.entity.User;

public class LiabilitiesDetailsDTO implements Serializable {

	private Long id;

	private double loanAmount;

	private String bankOrNbfiName;

	private Date createdAt;

	private Date updatedAt;

	private String createdBy;

	private String updatedBy;

	private UserDTO user;

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getBankOrNbfiName() {
		return bankOrNbfiName;
	}

	public void setBankOrNbfiName(String bankOrNbfiName) {
		this.bankOrNbfiName = bankOrNbfiName;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
