package com.wavemaker.connector.properties;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 9/6/20
 */
@Service
public class EmailProperties {

    @Value("${email.server.host}")
    private String host;

    @Value("${email.server.port}")
    private Integer port;

    @Value("${email.server.username}")
    private String username;

    @Value("${email.server.password}")
    private String password;

    @Value("${email.transport.protocol}")
    private String protocol;

    private Properties javaMailProperties = new Properties();

    @PostConstruct
    public Properties setProperties() {
        javaMailProperties.setProperty("mail.smtp.auth", "true");
        javaMailProperties.setProperty("mail.smtp.starttls.enable", "false");
        javaMailProperties.setProperty("mail.smtp.quitwait", "false");
        javaMailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.setProperty("mail.smtp.socketFactory.fallback", "false");
        javaMailProperties.setProperty("mail.debug", "false");
        javaMailProperties.setProperty("mail.transport.protocol", protocol);
        return javaMailProperties;
    }

    public String getHost() {
        return host;
    }


    public Integer getPort() {
        return port;
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public EmailProperties addJavaMailProperty(String key, String value) {
        javaMailProperties.setProperty(key, value);
        return this;
    }

    public Properties getJavaMailProperties() {
        return javaMailProperties;
    }

    public void setJavaMailProperties(Properties javaMailProperties) {
        this.javaMailProperties = javaMailProperties;
    }
}
