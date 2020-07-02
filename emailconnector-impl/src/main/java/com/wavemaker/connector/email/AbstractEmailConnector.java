package com.wavemaker.connector.email;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.wavemaker.connector.properties.EmailProperties;
import com.wavemaker.connector.resolver.EmailTemplateResolver;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 17/2/20
 */
public abstract class AbstractEmailConnector implements EmailConnector {

    private JavaMailSender mailSender;

    private EmailTemplateResolver emailTemplateResolver;


    public JavaMailSender getJavaMailSender() {
        if (mailSender == null) {
            mailSender = new JavaMailSenderImpl();
            ((JavaMailSenderImpl) mailSender).setHost(getEmailProperties().getHost());
            ((JavaMailSenderImpl) mailSender).setPort(getEmailProperties().getPort());
            ((JavaMailSenderImpl) mailSender).setUsername(getEmailProperties().getUsername());
            ((JavaMailSenderImpl) mailSender).setPassword(getEmailProperties().getPassword());
            ((JavaMailSenderImpl) mailSender).setJavaMailProperties(getEmailProperties().getJavaMailProperties());
        }
        return mailSender;
    }

    protected abstract EmailProperties getEmailProperties();


    public EmailTemplateResolver getEmailTemplateResolver() {
        if (emailTemplateResolver == null) {
            emailTemplateResolver = new EmailTemplateResolver();
        }
        return emailTemplateResolver;
    }
}
