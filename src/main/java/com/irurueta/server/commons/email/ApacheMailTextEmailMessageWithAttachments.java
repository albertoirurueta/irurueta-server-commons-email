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

import java.util.List;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

/**
 * Class containing a text only email message that can contain file attachments
 * and is meant to be sent using Apache Mail.
 */
public class ApacheMailTextEmailMessageWithAttachments extends 
        TextEmailMessageWithAttachments<MultiPartEmail> {
    
    /**
     * Constructor.
     */
    public ApacheMailTextEmailMessageWithAttachments() {
        super();
    }
    
    /**
     * Constructor with email subject.
     * @param subject subject to be set on email.
     */
    public ApacheMailTextEmailMessageWithAttachments(String subject) {
        super(subject);
    }
    
    /**
     * Constructor with email subject and content text.
     * @param subject subject to be set on email.
     * @param text textual email content.
     */
    public ApacheMailTextEmailMessageWithAttachments(String subject, 
            String text) {
        super(subject, text);
    }
    
    /**
     * Constructor with file attachments.
     * @param attachments list of file attachments to be included in email.
     */
    public ApacheMailTextEmailMessageWithAttachments(
            List<EmailAttachment> attachments) {
        super(attachments);
    }
    
    /**
     * Constructor with subject and file attachments.
     * @param subject subject to be set on email.
     * @param attachments list of file attachments to be included in email.
     */
    public ApacheMailTextEmailMessageWithAttachments(String subject, 
            List<EmailAttachment> attachments) {
        super(subject, attachments);
    }
    
    /**
     * Constructor with subject, content text and file attachments.
     * @param subject subject to be set on email.
     * @param text textual email content.
     * @param attachments list of file attachments to be included in email.
     */
    public ApacheMailTextEmailMessageWithAttachments(String subject, 
            String text, List<EmailAttachment> attachments) {
        super(subject, text, attachments);
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
            if (getText() != null) {
                content.setMsg(getText());
            }
                        
            //add attachments
            List<EmailAttachment> attachments = getAttachments();
            org.apache.commons.mail.EmailAttachment apacheAttachment;
            if (attachments != null) {
                for (EmailAttachment attachment : attachments) {
                    //only add attachments with files
                    if (attachment.getAttachment() == null) {
                        continue;
                    }
                    
                    apacheAttachment = new  org.apache.commons.mail.
                            EmailAttachment();
                    apacheAttachment.setPath(attachment.getAttachment().getAbsolutePath());
                    apacheAttachment.setDisposition(
                            org.apache.commons.mail.EmailAttachment.ATTACHMENT);
                    if (attachment.getName() != null) {
                        apacheAttachment.setName(attachment.getName());
                    }
                    
                    content.attach(apacheAttachment);
                }
            }
        } catch (EmailException e) {
            throw new com.irurueta.server.commons.email.EmailException(e);
        }
    }  
}
