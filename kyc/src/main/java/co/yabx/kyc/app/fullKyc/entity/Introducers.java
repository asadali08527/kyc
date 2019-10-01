package co.yabx.kyc.app.fullKyc.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("introducers")
public class Introducers extends User {

}
