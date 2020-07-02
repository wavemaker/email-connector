package com.wavemaker.connector.exception;

import java.io.FileNotFoundException;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 28/2/20
 */
public class EmailTemplateNotFoundException extends FileNotFoundException {

    public EmailTemplateNotFoundException(String s) {
        super(s);
    }

}

