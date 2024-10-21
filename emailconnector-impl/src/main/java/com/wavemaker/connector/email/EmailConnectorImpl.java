package com.wavemaker.connector.email;

import java.util.Map;
import java.util.Properties;

import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wavemaker.connector.exception.EmailTemplateNotFoundException;
import com.wavemaker.connector.properties.EmailProperties;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 17/2/20
 */
@Service
@Primary
public class EmailConnectorImpl extends AbstractEmailConnector {

    @Autowired
    private EmailProperties emailProperties;

    public void sendSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        getJavaMailSender().send(simpleMailMessage);
    }

    public void sendSimpleMailMessages(SimpleMailMessage... simpleMailMessages) {
        getJavaMailSender().send(simpleMailMessages);
    }

    public void sendMultiPartMail(final SimpleMailMessage simpleMailMessage, final MultipartFile file) {
        sendMultiPartMail(simpleMailMessage, new MultipartFile[]{file});
    }

    public void sendMultiPartMail(final SimpleMailMessage simpleMailMessage, final MultipartFile[] files) {
        sendMultiPartMail(simpleMailMessage, files);
    }

    public void sendMimeMail(final MimeMessagePreparator mimeMessagePreparator) {
        getJavaMailSender().send(mimeMessagePreparator);
    }

    @Override
    public void sendSimpleMailMessageWithTemplate(SimpleMailMessage simpleMailMessage, String templateName, Map<String, String> props) throws EmailTemplateNotFoundException {
        sendMimeMail((MimeMessage mimeMessage) -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(simpleMailMessage.getSubject());
            mimeMessageHelper.setFrom(simpleMailMessage.getFrom());
            mimeMessageHelper.setTo(simpleMailMessage.getTo());
            String resolvedTemplate = getEmailTemplateResolver().replacePlaceHolders(templateName, props);
            mimeMessageHelper.getMimeMessage().setContent(resolvedTemplate, "text/html");
        });
    }

    public EmailProperties getAllEmailProperties() {
        return emailProperties;
    }

    @Override
    public void setEmailProperties(Properties properties) {
        emailProperties.setProperties(properties);
    }

    @Override
    public Properties getEmailProperties() {
        return emailProperties.getProperties();
    }
}
