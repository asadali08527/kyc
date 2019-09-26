package co.yabx.kyc.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class KYCApplication {

	public static void main(String[] args) {
		SpringApplication.run(KYCApplication.class, args);
	}
}
