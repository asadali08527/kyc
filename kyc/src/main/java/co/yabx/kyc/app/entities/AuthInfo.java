package co.yabx.kyc.app.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * @author Asad.ali
 *
 */
@Entity
@Table(name = "auth_infos", indexes = { @Index(name = "msisdn", columnList = "msisdn"),
		@Index(name = "yabx_token", columnList = "yabx_token"), @Index(name = "user_name", columnList = "user_name") })
public class AuthInfo implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	public enum AUTH_TYPE {
		OTP, PASSWORD
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "current_auth_type")
	private AUTH_TYPE currentAuthType;

	@Column(name = "origin_auth_type")
	private AUTH_TYPE originAuthType;

	@Column(name = "yabx_token")
	private String yabxToken;

	@Column(unique = true)
	private String msisdn;

	@Column(name = "user_name", unique = true)
	private String username;

	@Column(length = 512)
	private String password;

	@Column(name = "enabled", columnDefinition = "tinyint(1) default 1")
	private boolean enabled;

	@Column(name = "credentials_non_expired", columnDefinition = "tinyint(1) default 1")
	private boolean credentialsNonExpired;

	@Column(name = "account_non_expired", columnDefinition = "tinyint(1) default 1")
	private boolean accountNonExpired;

	@Column(name = "account_non_locked", columnDefinition = "tinyint(1) default 1")
	private boolean accountNonLocked;

	@Column(name = "secret_key")
	private String secretKey;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@PrePersist
	public void prePersist() {
		if (createdAt == null) {
			createdAt = new Date();
		}
		if (updatedAt == null) {
			updatedAt = new Date();
		}
	}

	@PreUpdate
	public void update() {
		updatedAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public AUTH_TYPE getCurrentAuthType() {
		return currentAuthType;
	}

	public void setCurrentAuthType(AUTH_TYPE currentAuthType) {
		this.currentAuthType = currentAuthType;
	}

	public AUTH_TYPE getOriginAuthType() {
		return originAuthType;
	}

	public void setOriginAuthType(AUTH_TYPE originAuthType) {
		this.originAuthType = originAuthType;
	}

	public String getYabxToken() {
		return yabxToken;
	}

	public void setYabxToken(String yabxToken) {
		this.yabxToken = yabxToken;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
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

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (accountNonExpired ? 1231 : 1237);
		result = prime * result + (accountNonLocked ? 1231 : 1237);
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + (credentialsNonExpired ? 1231 : 1237);
		result = prime * result + ((currentAuthType == null) ? 0 : currentAuthType.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((msisdn == null) ? 0 : msisdn.hashCode());
		result = prime * result + ((originAuthType == null) ? 0 : originAuthType.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((secretKey == null) ? 0 : secretKey.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((yabxToken == null) ? 0 : yabxToken.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthInfo other = (AuthInfo) obj;
		if (accountNonExpired != other.accountNonExpired)
			return false;
		if (accountNonLocked != other.accountNonLocked)
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (credentialsNonExpired != other.credentialsNonExpired)
			return false;
		if (currentAuthType != other.currentAuthType)
			return false;
		if (enabled != other.enabled)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (msisdn == null) {
			if (other.msisdn != null)
				return false;
		} else if (!msisdn.equals(other.msisdn))
			return false;
		if (originAuthType != other.originAuthType)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (secretKey == null) {
			if (other.secretKey != null)
				return false;
		} else if (!secretKey.equals(other.secretKey))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (yabxToken == null) {
			if (other.yabxToken != null)
				return false;
		} else if (!yabxToken.equals(other.yabxToken))
			return false;
		return true;
	}

}
