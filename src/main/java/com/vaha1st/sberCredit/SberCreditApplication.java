package com.vaha1st.sberCredit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SberCreditApplication {

	public static void main(String[] args) {
		SpringApplication.run(SberCreditApplication.class, args);
	}

}
