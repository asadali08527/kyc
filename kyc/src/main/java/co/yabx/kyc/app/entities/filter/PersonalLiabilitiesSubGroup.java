package co.yabx.kyc.app.entities.filter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 * @author Asad.ali
 *
 */
@Entity
@DiscriminatorValue("Personal Liabilities")
public class PersonalLiabilitiesSubGroup extends SubGroups {

}
