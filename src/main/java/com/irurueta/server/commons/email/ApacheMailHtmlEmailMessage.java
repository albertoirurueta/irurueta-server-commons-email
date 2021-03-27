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

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.List;

/**
 * Class to build emails having HTML markup as in webpages using Apache Mail.
 * These kind of emails are useful for marketing purposes or simply to get a
 * nicer layout.
 * Files can be attached as in text emails, or they can be sent inline so they
 * can be used within the html content (such as images or css resources to be
 * used within html email body).
 */
public class ApacheMailHtmlEmailMessage extends HtmlEmailMessage implements ApacheEmailMessage {

    /**
     * Constructor.
     */
    public ApacheMailHtmlEmailMessage() {
        super();
    }

    /**
     * Constructor with email subject.
     *
     * @param subject subject to be set.
     */
    public ApacheMailHtmlEmailMessage(final String subject) {
        super(subject);
    }

    /**
     * Constructor with email subject and textual content.
     *
     * @param subject     subject to be set.
     * @param htmlContent HTML content.
     */
    public ApacheMailHtmlEmailMessage(final String subject, final String htmlContent) {
        super(subject, htmlContent);
    }

    /**
     * Builds email content to be sent using an email sender.
     *
     * @param content instance where content must be set.
     * @throws com.irurueta.server.commons.email.EmailException if setting mail
     *                                                          content fails.
     */
    @Override
    public void buildContent(final HtmlEmail content)
            throws com.irurueta.server.commons.email.EmailException {
        try {
            if (getAlternativeText() != null) {
                content.setTextMsg(getAlternativeText());
            }

            // process inline attachments
            final boolean[] generated = processInlineAttachments();

            if (getHtmlContent() != null) {
                content.setHtmlMsg(process(getHtmlContent(), generated));
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

                    content.embed(attachment.getAttachment(),
                            attachment.getContentId());
                }
            }

            // add other attachments parts
            final List<EmailAttachment> attachments = getEmailAttachments();
            ApacheMailTextEmailMessageWithAttachments.attach(content, attachments);
        } catch (final EmailException e) {
            throw new com.irurueta.server.commons.email.EmailException(e);
        }
    }
}
