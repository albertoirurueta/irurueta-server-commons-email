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
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Class containing a text only email message that can contain file attachments
 * and is meant to be sent using JavaMail.
 */
public class JavaMailTextEmailMessageWithAttachments extends
        TextEmailMessageWithAttachments implements JavaMailEmailMessage {

    /**
     * Constructor.
     */
    public JavaMailTextEmailMessageWithAttachments() {
        super();
    }

    /**
     * Constructor with email subject.
     *
     * @param subject subject to be set on email.
     */
    public JavaMailTextEmailMessageWithAttachments(final String subject) {
        super(subject);
    }

    /**
     * Constructor with email subject and content text.
     *
     * @param subject subject to be set on email.
     * @param text    textual email content.
     */
    public JavaMailTextEmailMessageWithAttachments(final String subject,
                                                   final String text) {
        super(subject, text);
    }

    /**
     * Constructor with file attachments.
     *
     * @param attachments list of file attachments to be included in email.
     */
    public JavaMailTextEmailMessageWithAttachments(
            final List<EmailAttachment> attachments) {
        super(attachments);
    }

    /**
     * Constructor with subject and file attachments.
     *
     * @param subject     subject to be set on email.
     * @param attachments list of file attachments to be included in email.
     */
    public JavaMailTextEmailMessageWithAttachments(final String subject,
                                                   final List<EmailAttachment> attachments) {
        super(subject, attachments);
    }

    /**
     * Constructor with subject, content text and file attachments.
     *
     * @param subject     subject to be set on email.
     * @param text        textual email content.
     * @param attachments list of file attachments to be included in email.
     */
    public JavaMailTextEmailMessageWithAttachments(final String subject, String text,
                                                   final List<EmailAttachment> attachments) {
        super(subject, text, attachments);
    }

    /**
     * Builds email content to be sent using an email sender.
     *
     * @param message instance where content must be set.
     * @throws EmailException if setting mail content fails.
     */
    @Override
    public void buildContent(final MimeMessage message) throws EmailException {
        try {
            // finally add multipart contents to mail message
            message.setContent(buildMultipart());

        } catch (final MessagingException e) {
            throw new EmailException(e);
        }
    }

    /**
     * Builds multipart content of email.
     * Multipart is used when a text email needs to have files attached.
     *
     * @return a multipart.
     * @throws EmailException if building multipart fails.
     */
    private Multipart buildMultipart() throws EmailException {
        try {
            // create multipart. One part will be for text content, and remaining
            // parts will be for attachments
            final Multipart multipart = new MimeMultipart();
            // set text content in body part
            BodyPart messageBodyPart;
            if (getText() != null) {
                messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(getText(),
                        "text/plain; charset=utf-8");
                multipart.addBodyPart(messageBodyPart);
            }

            // add attachments parts
            final List<EmailAttachment> attachments = getAttachments();
            attach(multipart, attachments);
            return multipart;
        } catch (final MessagingException e) {
            throw new EmailException(e);
        }
    }

    /**
     * Adds attachments to multipart email.
     *
     * @param multipart   a multipart within an email.
     * @param attachments attachments to be added.
     * @throws MessagingException if an error occurs.
     */
    protected static void attach(
            final Multipart multipart, final List<EmailAttachment> attachments)
            throws MessagingException {
        if (attachments != null) {
            for (final EmailAttachment attachment : attachments) {
                // only add attachments with files
                if (attachment.getAttachment() == null) {
                    continue;
                }

                final BodyPart messageBodyPart = new MimeBodyPart();
                if (attachment.getName() != null) {
                    messageBodyPart.setFileName(attachment.getName());
                }
                messageBodyPart.setDisposition(Part.ATTACHMENT);
                if (attachment.getContentType() != null) {
                    messageBodyPart.addHeader("Content-Type",
                            attachment.getContentType());
                }
                messageBodyPart.setDataHandler(new DataHandler(
                        new FileDataSource(attachment.getAttachment())));
                multipart.addBodyPart(messageBodyPart);
            }
        }
    }
}
