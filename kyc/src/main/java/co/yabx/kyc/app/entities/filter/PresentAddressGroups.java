package co.yabx.kyc.app.entities.filter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 * @author Asad.ali
 *
 */
@Entity
@DiscriminatorValue("Present Address")
public class PresentAddressGroups extends SubGroups {

	@Column(name = "linked_group")
	private Long linked_group;

	public Long getLinked_group() {
		return linked_group;
	}

	public void setLinked_group(Long linked_group) {
		this.linked_group = linked_group;
	}

}
