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

/**
 * Class to build emails having HTML markup as in webpages using Amazon SES.
 * These kind of emails are useful for marketing purposes or simply to get a
 * nicer layout.
 * Files can be attached as in text emails, or they can be sent inline so they
 * can be used within the html content (such as images or css resources to be
 * used within html email body).
 */
public class AWSHtmlEmailMessage extends JavaMailHtmlEmailMessage {
    /**
     * Constructor.
     */
    public AWSHtmlEmailMessage() {
        super();
    }
    
    /**
     * Constructor with email subject.
     * @param subject subject to be set.
     */    
    public AWSHtmlEmailMessage(String subject) {
        super(subject);
    }
    
    /**
     * Constructor with email subject and textual content.
     * @param subject subject to be set.
     * @param htmlContent HTML content.
     */    
    public AWSHtmlEmailMessage(String subject, String htmlContent) {
        super(subject, htmlContent);
    }    
    
    /**
     * Indicates if CC is supported for this kind of email to send Carbon Copies
     * of this email. Some email types do not support this feature.
     * @return true if CC feature is supported, false otherwise.
     */
    @Override
    public boolean isCCSupported() {
        return false;
    }
    
    /**
     * Indicates if BCC is supported for this kind of email to send Blind Carbon
     * Copies of this email. Some email types do not support this feature.
     * @return true if BCC feature is supported, false otherwise.
     */
    @Override
    public boolean isBCCSupported() {
        return false;
    }    
}
