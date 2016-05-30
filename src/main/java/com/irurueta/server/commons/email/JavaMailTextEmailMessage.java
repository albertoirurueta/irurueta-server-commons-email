/**
 * Copyright (C) 2016 Alberto Irurueta Carro (alberto@irurueta.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.irurueta.server.commons.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Class containing a text only email message to be sent using JavaMail.
 */
public class JavaMailTextEmailMessage extends TextEmailMessage<MimeMessage> {

    /**
     * Constructor.
     */
    public JavaMailTextEmailMessage() {
        super();
    }
    
    /**
     * Constructor with email subject.
     * @param subject subject to be set on email.
     */
    public JavaMailTextEmailMessage(String subject) {
        super(subject);
    }
    
    /**
     * Constructor with email subject and content text.
     * @param subject subject to be set on email.
     * @param text textual email content.
     */
    public JavaMailTextEmailMessage(String subject, String text) {
        super(subject, text);
    }
    
    /**
     * Builds email content to be sent using an email sender.
     * @param content instance where content must be set.
     * @throws EmailException if setting mail content fails.
     */
    @Override
    protected void buildContent(MimeMessage content) throws EmailException{
        try {            
            content.setText(getText(), "utf-8");
        } catch (MessagingException e) {
            throw new EmailException(e);
        }
    }    
}
