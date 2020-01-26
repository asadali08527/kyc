package co.yabx.kyc.app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
	private String name;
	private String msisdn;
	private String username;
	private String email;
	private String secret_key;
	private String password;
	private List<UserDto> retailer;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public List<UserDto> getRetailer() {
		return retailer;
	}

	public void setRetailer(List<UserDto> retailer) {
		this.retailer = retailer;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSecret_key() {
		return secret_key;
	}

	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDto [name=" + name + ", msisdn=" + msisdn + ", username=" + username + ", email=" + email
				+ ", secret_key=" + secret_key + ", password=" + password + ", retailer=" + retailer + "]";
	}

	

}
