package co.yabx.kyc.app.fullKyc.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("suppliers")
public class Suppliers extends User {

}
