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

import java.util.List;

/**
 * Class containing a text only email message that can contain file attachments
 * and is meant to be sent using Amazon SES.
 */
public class AWSTextEmailMessageWithAttachments extends 
        JavaMailTextEmailMessageWithAttachments {
    
    /**
     * Constructor.
     */
    public AWSTextEmailMessageWithAttachments() {
        super();
    }
    
    /**
     * Constructor with email subject.
     * @param subject subject to be set on email.
     */
    public AWSTextEmailMessageWithAttachments(final String subject) {
        super(subject);
    }
    
    /**
     * Constructor with email subject and content text.
     * @param subject subject to be set on email.
     * @param text textual email content.
     */
    public AWSTextEmailMessageWithAttachments(final String subject, final String text) {
        super(subject, text);
    }
    
    /**
     * Constructor with file attachments.
     * @param attachments list of file attachments to be included in email.
     */
    public AWSTextEmailMessageWithAttachments(
            final List<EmailAttachment> attachments) {
        super(attachments);
    }
    
    /**
     * Constructor with subject and file attachments.
     * @param subject subject to be set on email.
     * @param attachments list of file attachments to be included in email.
     */
    public AWSTextEmailMessageWithAttachments(final String subject,
            final List<EmailAttachment> attachments) {
        super(subject, attachments);
    }
    
    /**
     * Constructor with subject, content text and file attachments.
     * @param subject subject to be set on email.
     * @param text textual email content.
     * @param attachments list of file attachments to be included in email.
     */
    public AWSTextEmailMessageWithAttachments(final String subject, final String text,
            final List<EmailAttachment> attachments) {
        super(subject, text, attachments);
    }    
    
    /**
     * Indicates if CC is supported for this kind of email to send Carbon 
     * Copies of this email. Some email types do not support this feature.
     * @return true if CC feature is supported, false otherwise.
     */
    @Override
    public boolean isCCSupported() {
        return false;
    }
    
    /**
     * Indicates if BCC is supported for this kind of email to send Blind Carbon
     * Copies of this email. Some email types do not support this feature
     * @return true if BCC feature is supported, false otherwise.
     */
    @Override
    public boolean isBCCSupported() {
        return false;
    }
}
