package com.module.service.impl;

import com.module.config.MailConfig;
import com.module.entity.EmailToken;
import com.module.repository.EmailRepository;
import com.module.service.EmailService;
import com.module.utils.lambda.BindParameterSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

@Service("emailService")
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepo;
    private final MailConfig mailConfig;


    @Override
    public Authenticator setAuth() {
        return mailConfig.setAuth();
    }

    @Override
    public void sendEmail(Session session, String toEmail, String subject, String body) {

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("habitManagement@example.com", "NoReply-JD"));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            Transport.send(msg);




        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public com.module.entity.EmailToken save(com.module.entity.EmailToken emailToken) {

        return Optional.
                    ofNullable(
                        emailRepo.save(emailToken)).
                            orElseThrow(BindParameterSupplier.bind(IllegalArgumentException::new, "can not save the email token"));
    }


    public EmailToken findByConfirmationToken(String confirmationToken){

        return Optional.
                    ofNullable(
                        emailRepo.findByConfirmationToken(confirmationToken)).
                            orElseThrow(BindParameterSupplier.bind(EntityNotFoundException::new,"there is no email token with that user input"));
    }

    @Override
    public Properties setSMTPSession() {
            return mailConfig.setSMTP();
    }

    @Override
    public String setTokenString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 16;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }


}
