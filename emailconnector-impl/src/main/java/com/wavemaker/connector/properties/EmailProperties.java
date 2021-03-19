package com.wavemaker.connector.properties;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Value("${email.server.sslenabled}")
    private boolean sslEnabled;

    private Properties javaMailProperties = new Properties();

    @PostConstruct
    private Properties init() {
        if (StringUtils.isEmpty(username) && StringUtils.isEmpty(password)) {
            javaMailProperties.setProperty("mail.smtp.auth", "false");
        } else {
            javaMailProperties.setProperty("mail.smtp.auth", "true");
        }
        javaMailProperties.setProperty("mail.smtp.starttls.enable", "false");
        javaMailProperties.setProperty("mail.smtp.quitwait", "false");
        if (sslEnabled) {
            javaMailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }
        javaMailProperties.setProperty("mail.smtp.socketFactory.fallback", "false");
        javaMailProperties.setProperty("mail.debug", "false");
        javaMailProperties.setProperty("mail.smtp.timeout", "30000");
        javaMailProperties.setProperty("mail.smtp.connectiontimeout", "30000");
        javaMailProperties.setProperty("mail.transport.protocol", protocol);
        return javaMailProperties;
    }

    public Properties getProperties() {
        return javaMailProperties;
    }

    public void setProperties(Properties properties) {
        javaMailProperties = properties;
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

    public void setJavaMailProperties(Properties javaMailProperties) {
        this.javaMailProperties = javaMailProperties;
    }
}
