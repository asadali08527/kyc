package co.yabx.kyc.app.entities.filter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 * @author Asad.ali
 *
 */
@Entity
@DiscriminatorValue("Office Address")
public class OfficeAddressGroup extends SubGroups {

}
