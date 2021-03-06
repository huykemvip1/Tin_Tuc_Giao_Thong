package com.tintucgiaothong.data.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class ConfigApp {
	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl javaMailSenderImpl=new JavaMailSenderImpl();
		javaMailSenderImpl.setPort(587);
		javaMailSenderImpl.setHost("smtp.gmail.com");
		javaMailSenderImpl.setUsername("huyjavacode@gmail.com");
		javaMailSenderImpl.setPassword("huykemvip1");
		
		Properties props=javaMailSenderImpl.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return javaMailSenderImpl;
	}
}
