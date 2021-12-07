package com.laonstory.bbom.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Bean
    public static JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("guemjiwoon@gmail.com");
        mailSender.setPassword("wwseoturuustcyxq");
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.debug", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.EnableSSL.enable", "true");
        mailSender.setJavaMailProperties(prop);
        return mailSender;
    }


}
