package com.wavemaker.connector.resolver;

import com.wavemaker.connector.exception.EmailTemplateNotFoundException;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 27/2/20
 */
public class EmailTemplateResolver {

    private static Pattern pattern = Pattern.compile("@[a-z A-Z0-9]*@");

    public String replacePlaceHolders(String templateName, Map<String, String> props) throws EmailTemplateNotFoundException {
        String templateText = readFile(templateName);
        String replacedText = templateText;
        Matcher matcher = pattern.matcher(replacedText);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            String param = templateText.substring(start, end);
            String placeholder = param.replaceAll("@", "");
            replacedText = replacedText.replaceAll(param, props.get(placeholder));
        }
        return replacedText;

    }

    private String readFile(String fileName) throws EmailTemplateNotFoundException {
        URL classPathResource = getClassPathResource(fileName);
        try {
            return IOUtils.toString(new BufferedReader(new FileReader(classPathResource.getFile())));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read content from the given file", e);
        }
    }

    private URL getClassPathResource(String templateName) throws EmailTemplateNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(templateName);
        if (resource == null) {
            throw new EmailTemplateNotFoundException("Template not found in the class path");
        }
        return resource;
    }
}
