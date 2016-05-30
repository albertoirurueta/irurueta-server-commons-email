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

import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;

/**
 * Class containing a text only email message to be sent using Amazon SES.
 */
public class AWSTextEmailMessage extends TextEmailMessage<Message> {
    
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
    public AWSTextEmailMessage(String subject) {
        super(subject);
    }
    
    /**
     * Constructor with email subject and content text.
     * @param subject subject to be set on email.
     * @param text textual email content.
     */
    public AWSTextEmailMessage(String subject, String text) {
        super(subject, text);
    }
    
    /**
     * Builds email content to be sent using an email sender.
     * @param message instance where content must be set.
     * @throws EmailException if setting mail content fails.
     */
    @Override
    protected void buildContent(Message message) throws EmailException {
        Destination destination = new Destination(getTo());
        if (getBCC() != null && !getBCC().isEmpty()) {
            destination.setBccAddresses(getBCC());
        }
        if (getCC() != null && !getCC().isEmpty()) {
            destination.setCcAddresses(getCC());
        }
                
        if (getSubject() != null) {
            Content subject = new Content(getSubject());
            subject.setCharset("UTF-8"); //set utf-8 enconding to support 
                                         //all languages
            message.setSubject(subject);
        }
                
        if (getText() != null) {
            Body body = new Body();
            Content content = new Content(getText());
            content.setCharset("UTF-8"); //set utf-8 enconding to support all 
                                        //languages
        
            body.setText(content);
            message.setBody(body);
        }
    }    
}
