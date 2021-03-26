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

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for text only email messages containing file attachments.
 *
 * @param <E> internal representation of email using by internal client.
 */
public abstract class TextEmailMessageWithAttachments<E> extends
        TextEmailMessage<E> {

    /**
     * List of attachments to be included in email.
     */
    private List<EmailAttachment> mAttachments;

    /**
     * Constructor.
     */
    protected TextEmailMessageWithAttachments() {
        super();
        mAttachments = new ArrayList<>();
    }

    /**
     * Constructor with email subject.
     *
     * @param subject subject to be set.
     */
    protected TextEmailMessageWithAttachments(final String subject) {
        super(subject);
        mAttachments = new ArrayList<>();
    }

    /**
     * Constructor with email subject and textual content.
     *
     * @param subject subject to be set.
     * @param text    textual content.
     */
    protected TextEmailMessageWithAttachments(final String subject, final String text) {
        super(subject, text);
        mAttachments = new ArrayList<>();
    }

    /**
     * Constructor with list of files to be attached.
     *
     * @param attachments list of files to be attached.
     */
    protected TextEmailMessageWithAttachments(final List<EmailAttachment> attachments) {
        super();
        mAttachments = attachments;
    }

    /**
     * Constructor with email subject and list of files to be attached.
     *
     * @param subject     subject to be set.
     * @param attachments list of files to be attached.
     */
    protected TextEmailMessageWithAttachments(final String subject,
                                              final List<EmailAttachment> attachments) {
        super(subject);
        mAttachments = attachments;
    }

    /**
     * Constructor with email subject, textual content and list of files to be
     * attached.
     *
     * @param subject     subject to be set.
     * @param text        textual content.
     * @param attachments list of files to be attached.
     */
    protected TextEmailMessageWithAttachments(final String subject, final String text,
                                              final List<EmailAttachment> attachments) {
        super(subject, text);
        mAttachments = attachments;
    }

    /**
     * Get list of files to be attached.
     *
     * @return list of files to be attached.
     */
    public List<EmailAttachment> getAttachments() {
        return mAttachments;
    }

    /**
     * Sets list of files to be attached.
     *
     * @param attachments list of files to be attached.
     */
    public void setAttachments(final List<EmailAttachment> attachments) {
        mAttachments = attachments;
    }

    /**
     * Creates a textual email message with attachments using provided email
     * sender.
     *
     * @param sender class in charge of sending this email.
     * @return a textual email message with attachments.
     */
    public static TextEmailMessageWithAttachments<?> create(
            final EmailSender sender) {
        return create("", sender);
    }

    /**
     * Creates a textual email message with attachments using given email
     * provider.
     *
     * @param provider email provider.
     * @return a textual email message with attachments.
     */
    protected static TextEmailMessageWithAttachments<?> create(
            final EmailProvider provider) {
        return create("", provider);
    }

    /**
     * Creates a textual email message with attachments having provided subject
     * and using provided email sender.
     *
     * @param subject email subject to be set.
     * @param sender  class in charge of sending this email.
     * @return a textual email message with attachments.
     */
    public static TextEmailMessageWithAttachments<?> create(
            final String subject, final EmailSender sender) {
        return create(subject, "", sender);
    }

    /**
     * Creates a textual email message with attachments having provided subject
     * and given email provider.
     *
     * @param subject  email subject to be set.
     * @param provider email provider.
     * @return a textual email message with attachments.
     */
    protected static TextEmailMessageWithAttachments<?> create(
            final String subject, final EmailProvider provider) {
        return create(subject, "", provider);
    }

    /**
     * Creates a textual email message with attachments having provided subject,
     * text content and using provided email sender.
     *
     * @param subject email subject to be set.
     * @param text    text content of email.
     * @param sender  class in charge of sending this email.
     * @return a textual email message with attachments.
     */
    public static TextEmailMessageWithAttachments<?> create(
            final String subject, final String text, final EmailSender sender) {
        return create(subject, text, sender.getProvider());
    }

    /**
     * Creates a textual email message with attachments having provided subject,
     * text content and using given email provider.
     *
     * @param subject  email subject to be set.
     * @param text     text content of email.
     * @param provider email provider.
     * @return a textual email message with attachments.
     */
    protected static TextEmailMessageWithAttachments<?> create(
            final String subject, final String text, final EmailProvider provider) {
        return create(subject, text, new ArrayList<EmailAttachment>(),
                provider);
    }

    /**
     * Creates a textual email message with provided attachments and using
     * provided email sender.
     *
     * @param attachments list of files to be attached.
     * @param sender      class in charge of sending this email.
     * @return a textual email message with attachments.
     */
    public static TextEmailMessageWithAttachments<?> create(
            final List<EmailAttachment> attachments, final EmailSender sender) {
        return create("", attachments, sender);
    }

    /**
     * Creates a textual email message with provided attachments and using given
     * email provider.
     *
     * @param attachments list of files to be attached.
     * @param provider    email provider.
     * @return a textual email message with attachments.
     */
    protected static TextEmailMessageWithAttachments<?> create(
            final List<EmailAttachment> attachments, final EmailProvider provider) {
        return create("", attachments, provider);
    }

    /**
     * Creates a textual email message with provided attachments, subject and
     * using provided email sender.
     *
     * @param subject     subject to be set.
     * @param attachments list of files to be attached.
     * @param sender      class in charge of sending this email.
     * @return a textual email message with attachments.
     */
    public static TextEmailMessageWithAttachments<?> create(
            final String subject, final List<EmailAttachment> attachments, final EmailSender sender) {
        return create(subject, "", attachments, sender);
    }

    /**
     * Creates a textual email message with provided attachments, subject and
     * using given email provider.
     *
     * @param subject     subject to be set.
     * @param attachments list of files to be attached.
     * @param provider    email provider.
     * @return a textual email message with attachments.
     */
    protected static TextEmailMessageWithAttachments<?> create(
            final String subject, final List<EmailAttachment> attachments, final EmailProvider provider) {
        return create(subject, "", attachments, provider);
    }

    /**
     * Creates a textual email message with provided attachments, subject,
     * text content and using provided email sender.
     *
     * @param subject     subject to be set.
     * @param text        email text content.
     * @param attachments list of files to be attached.
     * @param sender      class in charge of sending this email.
     * @return a textual email message with attachments.
     */
    public static TextEmailMessageWithAttachments<?> create(
            final String subject, final String text, final List<EmailAttachment> attachments,
            final EmailSender sender) {
        return create(subject, text, attachments, sender.getProvider());
    }

    /**
     * Creates a textual email message with provided attachments, subject, text
     * content and using given email provider.
     *
     * @param subject     subject to be set.
     * @param text        email text content.
     * @param attachments list of files to be attached.
     * @param provider    email provider.
     * @return a textual email message with attachments.
     */
    protected static TextEmailMessageWithAttachments<?> create(
            final String subject, final String text, final List<EmailAttachment> attachments,
            final EmailProvider provider) {

        switch (provider) {
            case AWS_MAIL:
                return new AWSTextEmailMessageWithAttachments(subject, text,
                        attachments);
            case APACHE_MAIL:
                return new ApacheMailTextEmailMessageWithAttachments(subject,
                        text, attachments);
            case JAVA_MAIL:
            default:
                return new JavaMailTextEmailMessageWithAttachments(subject,
                        text, attachments);
        }
    }
}
