package co.yabx.kyc.app.fullKyc.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("dsr_users")
public class DSRUser extends User {

}
