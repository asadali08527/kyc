package co.yabx.kyc.app.fullKyc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import co.yabx.kyc.app.enums.EducationalQualification;

@Entity
@Table(name = "work_education_details", indexes = { @Index(name = "occupation", columnList = "occupation"),
		@Index(name = "highest_qualification", columnList = "highest_qualification") })
public class WorkEducationDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "occupation", length = 100, columnDefinition = "varchar(32) ")
	private String occupation;

	@Column(name = "designation")
	private String designation;

	@Column(name = "employer")
	private String employer;

	@Column(name = "highest_qualification", length = 100, columnDefinition = "varchar(32) ")
	@Enumerated(value = EnumType.STRING)
	private EducationalQualification educationalQualification;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "update_by")
	private String updatedBy;

	@Column(name = "experience")
	private Integer experience;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	User user;

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	@PrePersist
	private void prePersist() {
		if (createdAt == null) {
			createdAt = new Date();
			updatedAt = new Date();
		}
	}

	@PreUpdate
	private void preUpdate() {
		updatedAt = new Date();

	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public EducationalQualification getEducationalQualification() {
		return educationalQualification;
	}

	public void setEducationalQualification(EducationalQualification educationalQualification) {
		this.educationalQualification = educationalQualification;
	}

	public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	@Override
	public String toString() {
		return "WorkEducationDetails [id=" + id + ", occupation=" + occupation + ", designation=" + designation
				+ ", employer=" + employer + ", educationalQualification=" + educationalQualification + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy
				+ ", experience=" + experience + ", user=" + user + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((designation == null) ? 0 : designation.hashCode());
		result = prime * result + ((educationalQualification == null) ? 0 : educationalQualification.hashCode());
		result = prime * result + ((employer == null) ? 0 : employer.hashCode());
		result = prime * result + ((experience == null) ? 0 : experience.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		WorkEducationDetails other = (WorkEducationDetails) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (designation == null) {
			if (other.designation != null)
				return false;
		} else if (!designation.equals(other.designation))
			return false;
		if (educationalQualification != other.educationalQualification)
			return false;
		if (employer == null) {
			if (other.employer != null)
				return false;
		} else if (!employer.equals(other.employer))
			return false;
		if (experience == null) {
			if (other.experience != null)
				return false;
		} else if (!experience.equals(other.experience))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (occupation == null) {
			if (other.occupation != null)
				return false;
		} else if (!occupation.equals(other.occupation))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
