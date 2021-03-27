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

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;

/**
 * Class to build emails having HTML markup as in webpages using Java Mail.
 * These kind of emails are useful for marketing purposes or simply to get a
 * nicer layout.
 * Files can be attached as in text emails, or they can be sent inline so they
 * can be used within the html content (such as images or css resources to be
 * used within html email body).
 */
public class JavaMailHtmlEmailMessage extends HtmlEmailMessage implements JavaMailEmailMessage {

    /**
     * Constructor.
     */
    public JavaMailHtmlEmailMessage() {
        super();
    }

    /**
     * Constructor with email subject.
     *
     * @param subject subject to be set.
     */
    public JavaMailHtmlEmailMessage(final String subject) {
        super(subject);
    }

    /**
     * Constructor with email subject and textual content.
     *
     * @param subject     subject to be set.
     * @param htmlContent HTML content.
     */
    public JavaMailHtmlEmailMessage(final String subject, final String htmlContent) {
        super(subject, htmlContent);
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
     * Builds internal multipart email content.
     *
     * @return a multipart message.
     * @throws EmailException if something fails.
     */
    private Multipart buildMultipart() throws EmailException {
        try {
            // create multipart. One part will be for alternative text content,
            // another for HTML content and remaining parts will be for inline
            // attachments and other attachments
            final Multipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart;

            if (getAlternativeText() != null || getHtmlContent() != null) {
                // prepare alternative multipart so it can hold both alternative
                // text and html
                final Multipart alternativeMultipart = new MimeMultipart(
                        "alternative");
                if (getAlternativeText() != null) {
                    messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setContent(getAlternativeText(),
                            "text/plain; charset=utf-8");
                    alternativeMultipart.addBodyPart(messageBodyPart);
                }

                // process inline attachments
                final boolean[] generated = processInlineAttachments();

                // set html content in another body part
                if (getHtmlContent() != null) {
                    messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setContent(process(getHtmlContent(), generated),
                            "text/html; charset=utf-8");
                    alternativeMultipart.addBodyPart(messageBodyPart);
                }

                // wrap alternative multipart in a body part to be included in
                // overall message multipart
                messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(alternativeMultipart);
                multipart.addBodyPart(messageBodyPart);
            }

            // all attachments go into general message multipar

            // add inline attachments
            final List<InlineAttachment> inlineAttachments = getInlineAttachments();
            if (inlineAttachments != null) {
                for (final InlineAttachment attachment : inlineAttachments) {
                    // only add attachments with files and content ids
                    if (attachment.getContentId() == null ||
                            attachment.getAttachment() == null) {
                        continue;
                    }

                    messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setDataHandler(new DataHandler(
                            new FileDataSource(attachment.getAttachment())));
                    messageBodyPart.setHeader("Content-ID",
                            "<" + attachment.getContentId() + ">");
                    if (attachment.getContentType() != null) {
                        messageBodyPart.addHeader("Content-Type",
                                attachment.getContentType());
                    }
                    messageBodyPart.setDisposition(Part.INLINE);
                    multipart.addBodyPart(messageBodyPart);
                }
            }

            // add other attachments parts
            final List<EmailAttachment> attachments = getEmailAttachments();
            JavaMailTextEmailMessageWithAttachments.attach(multipart, attachments);
            return multipart;
        } catch (final MessagingException e) {
            throw new EmailException(e);
        }
    }
}
