package com.tesoramobil.auth.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
 * "Cuando alguien necesite un PasswordEncoder, yo le voy a dar una instancia de BCryptPasswordEncoder."
 * */


@Configuration
public class PasswordEncoderBean {

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
