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

import java.util.LinkedList;
import java.util.List;

/**
 * Abstract base class defining basic Email message data.
 * @param <E> internal type to be used by email sender implementation.
 */
public abstract class EmailMessage<E> {

    /**
     * List of addresses to send an email to.
     */
    private final List<String> mTo;
    
    /**
     * List of Carbon Copy addresses to send a copy of an email.
     */
    private final List<String> mCc;
    
    /**
     * List of Blind Carbon Copy addresses to send a copy of an email.
     * Other recipients of the email won't be able to see addresses in this 
     * list.
     */
    private final List<String> mBcc;
    
    /**
     * Subject of an email.
     */
    private String mSubject;
    
    /**
     * Constructor.
     * Creates an email with no content, subject and no recipients.
     */
    protected EmailMessage() {
        mTo = new LinkedList<>();
        mCc = new LinkedList<>();
        mBcc = new LinkedList<>();
        mSubject = "";
    }
    
    /**
     * Constructor.
     * Creates an email with subject, no content and no recipients.
     * @param subject subject to be set on the email.
     */
    protected EmailMessage(final String subject) {
        mTo = new LinkedList<>();
        mCc = new LinkedList<>();
        mBcc = new LinkedList<>();
        
        if (subject != null) {
            mSubject = subject;
        } else {
            mSubject = "";
        }        
    }
    
    /**
     * Returns list of addresses to send email to.
     * @return list of addresses to send email to.
     * @throws NotSupportedException if this feature is not supported.
     */
    public List<String> getTo() throws NotSupportedException {
        if (!isToSupported()) {
            throw new NotSupportedException();
        }
        return mTo;
    }
    
    /**
     * Indicates if To is supported to send an email to multiple recipients.
     * @return true if To feature is supported, false otherwise.
     */
    public boolean isToSupported() {
        return true;
    }
    
    /**
     * Returns list of Carbon Copy addresses to send a copy of this email.
     * @return list of Carbon Copy addresses to send a copy of this email.
     * @throws NotSupportedException if this feature is not supported.
     */
    public List<String> getCC() throws NotSupportedException {
        if (!isCCSupported()) {
            throw new NotSupportedException();
        }
        return mCc;
    }
    
    /**
     * Indicates if CC is supported for this kind of email to send Carbon Copies
     * of this email. Some email types do not support this feature.
     * @return true if CC feature is supported, false otherwise.
     */
    public boolean isCCSupported() {
        return true;
    }
    
    /**
     * Returns list of Blind Carbon Copy addresses to send a copy of this email.
     * Other recipients of this email won't be able to see addresses in this 
     * list.
     * @return list of Blind Carbon Copy addresses to send a copy of this email.
     * @throws NotSupportedException if this feature is not supported.
     */
    public List<String> getBCC() throws NotSupportedException {
        if (!isBCCSupported()) {
            throw new NotSupportedException();
        }
        return mBcc;
    }
    
    /**
     * Indicates if BCC is supported for this kind of email to send Blind 
     * Carbon.Copies of this email. Some email types do not support this 
     * feature.
     * @return true if BCC feature is supported, false otherwise.
     */
    public boolean isBCCSupported() {
        return true;
    }
    
    /**
     * Returns subject of this email.
     * @return subject of this email.
     */
    public String getSubject() {
        return mSubject;
    }
    
    /**
     * Sets subject of this email.
     * @param subject subject of this email.
     */
    public void setSubject(final String subject) {
        mSubject = subject;
    }

    /**
     * Method to be overridden so that email content can be built.
     * Depending on type of content of email (text, attachments, HTML) and email 
     * provider (JavaMail, Apache Mail or Amazon SES) this method will be 
     * implemented in different ways.
     * @param content Structure containing email content data to be sent.
     * @throws EmailException if content cannot be properly built.
     */
    protected abstract void buildContent(final E content) throws EmailException;
}
