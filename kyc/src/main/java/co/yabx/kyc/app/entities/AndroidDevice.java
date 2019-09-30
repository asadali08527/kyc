package co.yabx.kyc.app.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ANDROID")
public class AndroidDevice extends Device{
    
}
