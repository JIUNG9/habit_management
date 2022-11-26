package com.module.service;

import com.module.entity.EmailToken;

import javax.mail.Authenticator;
import javax.mail.Session;
import java.util.Properties;

public interface EmailService {
        Authenticator setAuth();
        void sendEmail(Session session, String toEmail, String subject, String body);
        com.module.entity.EmailToken save(com.module.entity.EmailToken emailToken);
        EmailToken findByConfirmationToken(String confirmationToken);
        Properties setSMTPSession();
        String setTokenString();

}
