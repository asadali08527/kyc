package co.yabx.kyc.app.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WEB")
public class WebDevice extends Device{
    
}
