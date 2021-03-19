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
            ((JavaMailSenderImpl) mailSender).setHost(getAllEmailProperties().getHost());
            ((JavaMailSenderImpl) mailSender).setPort(getAllEmailProperties().getPort());
            ((JavaMailSenderImpl) mailSender).setUsername(getAllEmailProperties().getUsername());
            ((JavaMailSenderImpl) mailSender).setPassword(getAllEmailProperties().getPassword());
            ((JavaMailSenderImpl) mailSender).setJavaMailProperties(getAllEmailProperties().getProperties());
        }
        return mailSender;
    }

    protected abstract EmailProperties getAllEmailProperties();

    public EmailTemplateResolver getEmailTemplateResolver() {
        if (emailTemplateResolver == null) {
            emailTemplateResolver = new EmailTemplateResolver();
        }
        return emailTemplateResolver;
    }
}
