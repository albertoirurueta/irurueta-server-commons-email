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

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

/**
 * Class containing a text only email message to be sent using Apache Mail.
 */
public class ApacheMailTextEmailMessage extends 
        TextEmailMessage<MultiPartEmail> {
    /**
     * Constructor.
     */
    public ApacheMailTextEmailMessage() {
        super();
    }
    
    /**
     * Constructor with email subject.
     * @param subject subject to be set on email.
     */
    public ApacheMailTextEmailMessage(String subject) {
        super(subject);
    }
    
    /**
     * Constructor with email subject and content text.
     * @param subject subject to be set on email.
     * @param text textual email content.
     */
    public ApacheMailTextEmailMessage(String subject, String text) {
        super(subject, text);
    }
    
    /**
     * Builds email content to be sent using an email sender.
     * @param content instance where content must be set.
     * @throws com.irurueta.server.commons.email.EmailException if setting mail 
     * content fails.
     */
    @Override
    protected void buildContent(MultiPartEmail content) 
            throws com.irurueta.server.commons.email.EmailException {
        try {            
            content.setMsg(getText());
        } catch(EmailException e) {
            throw new com.irurueta.server.commons.email.EmailException(e);
        }
    }    
}
