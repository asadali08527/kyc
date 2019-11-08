package co.yabx.kyc.app.entities.filter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 * @author Asad.ali
 *
 */
@Entity
@DiscriminatorValue("Trade License")
public class TradeLicenseGroup extends SubGroups {

}
