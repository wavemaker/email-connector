/*Copyright (c) 2016-2017 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
 package ${packageName};

 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

 import org.springframework.beans.factory.annotation.Autowired;

 import com.wavemaker.runtime.service.annotations.ExposeToClient;
 import com.wavemaker.runtime.service.annotations.HideFromClient;

 import java.util.HashMap;
 import java.util.Map;
 import com.wavemaker.connector.exception.EmailTemplateNotFoundException;
 import com.wavemaker.connector.email.EmailConnector;
 import org.springframework.mail.SimpleMailMessage;
 import org.springframework.beans.factory.annotation.Value;
 import java.text.SimpleDateFormat;

 import javax.mail.internet.MimeBodyPart;
 import javax.mail.internet.MimeMessage;
 import javax.mail.internet.MimeMultipart;
 import org.springframework.mail.javamail.MimeMessageHelper;
 import org.springframework.mail.javamail.MimeMessagePreparator;
 import org.springframework.core.io.ClassPathResource;

 //import ${packageName}.model.*;

 /**
  * This is a singleton class with all its public methods exposed as REST APIs via generated controller class.
  * To avoid exposing an API for a particular public method, annotate it with @HideFromClient.
  *
  * Method names will play a major role in defining the Http Method for the generated APIs. For example, a method name
  * that starts with delete/remove, will make the API exposed as Http Method "DELETE".
  *
  * Method Parameters of type primitives (including java.lang.String) will be exposed as Query Parameters &
  * Complex Types/Objects will become part of the Request body in the generated API.
  *
  * NOTE: We do not recommend using method overloading on client exposed methods.
  */
 @ExposeToClient
 public class ${className} {

     private static final Logger logger = LoggerFactory.getLogger(${className}.class);

	 @Value("${r"${email.server.username}"}")
     private String fromEmailAddress;

     @Autowired
     private EmailConnector emailConnector;

	 //Simple Email text
     public void sendMailWithSimpleMessage(String toEmailAddress, String emailSubject, String emailMessage) {
         logger.info("Sending the email to {} with subject {} and message {}", toEmailAddress, emailSubject, emailMessage);

         String[] recipientList = toEmailAddress.split(",");
         SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
         simpleMailMessage.setFrom(fromEmailAddress);
         simpleMailMessage.setTo(recipientList);
         simpleMailMessage.setSubject(emailSubject);
         simpleMailMessage.setText(emailMessage);
         emailConnector.sendSimpleMailMessage(simpleMailMessage);
     }

	 //Email text with template
     public void sendEmailWithTemplate(String toEmailAddress, String emailSubject, String templateFilePath,
     								   Map<String, String> props) {
         logger.info("Sending the email to {} with subject {} ", toEmailAddress, emailSubject);
         String[] recipientList = toEmailAddress.split(",");
         SimpleMailMessage message = new SimpleMailMessage();
         message.setSubject(emailSubject);
         message.setTo(recipientList);
         message.setFrom(fromEmailAddress);
         try {
              emailConnector.sendSimpleMailMessageWithTemplate(message, templateFilePath, props);
         } catch (EmailTemplateNotFoundException e) {
              throw new RuntimeException("Email template not found", e);
         }
     }

	 //Send Email with Attachment
     public void sendMailWithMessagePreparator(String toEmailAddress, String emailSubject, String emailMessage,
     											String filePath) {
         logger.info("Sending email to {}, with subject : {}, message : {} and mimetype content", toEmailAddress, emailSubject, emailMessage);
         emailConnector.sendMimeMail(new MimeMessagePreparator() {
         	@Override
         	public void prepare(final MimeMessage mimeMessage) throws Exception {
             	MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
             	mimeMessageHelper.addTo(toEmailAddress);
             	mimeMessageHelper.setFrom(fromEmailAddress);
             	mimeMessageHelper.setSubject(emailSubject);
             	mimeMessageHelper.setText(emailMessage);
             	mimeMessageHelper.addAttachment("file", new ClassPathResource(filePath));
         	}
         });
     }

	 //Send MIME Email.
     public void sendMimeMail(String toEmailAddress, String emailSubject, String htmlContent) {
         logger.info("Sending email to {}, with subject {} and mimetype content", toEmailAddress, emailSubject);
         emailConnector.sendMimeMail(new MimeMessagePreparator() {
             @Override
             public void prepare(final MimeMessage mimeMessage) throws Exception {
                 MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                 mimeMessageHelper.addTo(toEmailAddress);
                 mimeMessageHelper.setFrom(fromEmailAddress);
                 mimeMessageHelper.setSubject(emailSubject);
                 MimeBodyPart mimeBodyPart = new MimeBodyPart();
                 MimeMultipart mimeMultipart = new MimeMultipart();
                 mimeBodyPart.setContent(htmlContent, "text/html");
                 mimeMultipart.addBodyPart(mimeBodyPart);
                 mimeMessageHelper.getMimeMessage().setContent(mimeMultipart);
             }
         });
     }

     //Send Inline Mail
     public void sendInlineMail(String toEmailAddress, String emailSubject, String htmlMessage, String filePath) {
         emailConnector.sendMimeMail(new MimeMessagePreparator() {
             @Override
             public void prepare(final MimeMessage mimeMessage) throws Exception {
                 MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                 mimeMessageHelper.addTo(toEmailAddress);
                 mimeMessageHelper.setFrom(fromEmailAddress);
                 mimeMessageHelper.setSubject(emailSubject);

                 MimeMultipart mimeMultipart = new MimeMultipart();

                 MimeBodyPart htmlpart = new MimeBodyPart();
                 htmlpart.setContent(htmlMessage, "text/html");

                 MimeBodyPart imagePart = new MimeBodyPart();
                 imagePart.setHeader("Content-ID", "file");
                 imagePart.setDisposition(MimeBodyPart.INLINE);
                 imagePart.attachFile(new ClassPathResource(filePath).getFile());

                 mimeMultipart.addBodyPart(htmlpart);
                 mimeMultipart.addBodyPart(imagePart);
                 mimeMessageHelper.getMimeMessage().setContent(mimeMultipart);
             }
         });
     }
 }