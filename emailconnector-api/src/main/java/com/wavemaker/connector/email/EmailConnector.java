package com.wavemaker.connector.email;

import java.util.Map;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.multipart.MultipartFile;

import com.wavemaker.connector.exception.EmailTemplateNotFoundException;
import com.wavemaker.runtime.connector.annotation.WMConnector;


@WMConnector(name = "email",
        description = "This connector expose apis to send various types of email")
public interface EmailConnector {

    /**
     * @param simpleMailMessage contains addresses details of mail.
     */
    public void sendSimpleMailMessage(SimpleMailMessage simpleMailMessage);

    /**
     * @param simpleMailMessages contains addresses details of mail.
     */
    public void sendSimpleMailMessages(SimpleMailMessage... simpleMailMessages);

    /**
     * Api to send email with a multipart(attachment)
     *
     * @param simpleMailMessage contains addresses details of mail.
     * @param file              of multiparts
     */
    public void sendMultiPartMail(final SimpleMailMessage simpleMailMessage, final MultipartFile file);

    /**
     * Api to send email with multiple multiparts(attachments).
     *
     * @param simpleMailMessage contains addresses details of mail.
     * @param files             of multiparts
     */
    public void sendMultiPartMail(final SimpleMailMessage simpleMailMessage, final MultipartFile[] files);

    /**
     * @param mimeMessagePreparator to send any mime custom mails
     */
    public void sendMimeMail(final MimeMessagePreparator mimeMessagePreparator);

    /**
     * Api to send email with an attachment of template.
     *
     * @param simpleMailMessage contains addresses details of mail.
     * @param templateName name of the template
     * @param props value to be replaced in template.
     */
    public void sendSimpleMailMessageWithTemplate(final SimpleMailMessage simpleMailMessage, final String templateName, final Map<String,String> props) throws EmailTemplateNotFoundException;

}