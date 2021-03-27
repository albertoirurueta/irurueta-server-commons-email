/*
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

import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;

/**
 * Class containing a text only email message to be sent using Amazon SES.
 */
public class AWSTextEmailMessage extends TextEmailMessage implements AWSEmailMessage {
    
    /**
     * Constructor.
     */
    public AWSTextEmailMessage() {
        super();
    }
    
    /**
     * Constructor with email subject.
     * @param subject subject to be set on email.
     */
    public AWSTextEmailMessage(final String subject) {
        super(subject);
    }
    
    /**
     * Constructor with email subject and content text.
     * @param subject subject to be set on email.
     * @param text textual email content.
     */
    public AWSTextEmailMessage(final String subject, final String text) {
        super(subject, text);
    }
    
    /**
     * Builds email content to be sent using an email sender.
     * @param message instance where content must be set.
     * @throws EmailException if setting mail content fails.
     */
    @Override
    public void buildContent(final Message message) throws EmailException {
        final Destination destination = new Destination(getTo());
        if (getBCC() != null && !getBCC().isEmpty()) {
            destination.setBccAddresses(getBCC());
        }
        if (getCC() != null && !getCC().isEmpty()) {
            destination.setCcAddresses(getCC());
        }
                
        if (getSubject() != null) {
            final Content subject = new Content(getSubject());
            // set utf-8 encoding to support all languages
            subject.setCharset("UTF-8"); 
            message.setSubject(subject);
        }
                
        if (getText() != null) {
            final Body body = new Body();
            final Content content = new Content(getText());
            //set utf-8 encoding to support all languages
            content.setCharset("UTF-8"); 
        
            body.setText(content);
            message.setBody(body);
        }
    }    
}
