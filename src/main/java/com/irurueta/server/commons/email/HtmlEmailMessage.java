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
 * Abstract class to build emails having HTML markup as in webpages.
 * These kind of emails are useful for marketing purposes or simply to get a
 * nicer layout.
 * Files can be attached as in text emails, or they can be sent inline so they
 * can be used within the html content (such as images or css resources to be
 * used within html email body).
 *
 * @param <E> internal type to be used by email sender implementation.
 */
public abstract class HtmlEmailMessage<E> extends EmailMessage<E> {

    /**
     * HTML content of email to be rendered as a web page.
     */
    private String mHtmlContent;

    /**
     * Alternative text to be shown on clients not supporting HTML content.
     */
    private String mAlternativeText;

    /**
     * List of attachments to be included in email. Attachments are shown in
     * mail clients as files that can be downloaded from the email.
     */
    private List<EmailAttachment> mEmailAttachments;

    /**
     * List of files to be embedded inline within the email so they are shown
     * as part of the html content. These files can be used within the HTML
     * body as images, css resources, etc.
     * In order to use this resources within the HTML markup, and id must be
     * provided on each inline attachment and the same id must be used within
     * the HTML markup. If no id is generated, one will be randomly created, and
     * placeholders of the form {0}, {1}, etc will be filled within the HTML
     * markup in the same order as the inline attachments are provided.
     */
    private List<InlineAttachment> mInlineAttachments;

    /**
     * Constructor.
     */
    protected HtmlEmailMessage() {
        super();
        mHtmlContent = null;
        mAlternativeText = null;
        mEmailAttachments = new ArrayList<>();
        mInlineAttachments = new ArrayList<>();
    }

    /**
     * Constructor with email subject.
     *
     * @param subject subject to be set.
     */
    protected HtmlEmailMessage(final String subject) {
        super(subject);
        mHtmlContent = null;
        mAlternativeText = null;
        mEmailAttachments = new ArrayList<>();
        mInlineAttachments = new ArrayList<>();
    }

    /**
     * Constructor with email subject and textual content.
     *
     * @param subject     subject to be set.
     * @param htmlContent HTML content.
     */
    protected HtmlEmailMessage(final String subject, final String htmlContent) {
        super(subject);
        mHtmlContent = htmlContent;
        mAlternativeText = null;
        mEmailAttachments = new ArrayList<>();
        mInlineAttachments = new ArrayList<>();
    }

    /**
     * Returns HTML content to be rendered in this email as a webpage.
     * HTML content can have placeholders in the form {0}, {1}, {2}, etc where
     * id's of resources can be automatically embedded inline for those
     * attachments where no id is provided.
     * Notice that HTML content can also use regular external links, but some
     * email clients might block those links for security reasons.
     *
     * @return HTML content to be rendered as a webpage.
     */
    public String getHtmlContent() {
        return mHtmlContent;
    }

    /**
     * Sets HTML content to be rendered in this email as a webpage.
     * HTML content can have placeholders in the form {0}, {1}, {2}, etc where
     * id's of resources can be automatically embedded inline for those
     * attachments where no id is provided.
     * Notice that HTML content can also use regular external links, but some
     * email clients might block those links for security reasons.
     *
     * @param htmlContent html content of email to be sent.
     */
    public void setHtmlContent(final String htmlContent) {
        mHtmlContent = htmlContent;
    }

    /**
     * Returns alternative text to be shown on those clients not supporting HTML
     * content.
     *
     * @return alternative text.
     */
    public String getAlternativeText() {
        return mAlternativeText;
    }

    /**
     * Sets alternative text to be shown on those clients not supporting HTML
     * content.
     *
     * @param alternativeText alternative text.
     */
    public void setAlternativeText(final String alternativeText) {
        this.mAlternativeText = alternativeText;
    }

    /**
     * Get list of files to be attached. Attachments are shown in mail clients
     * as files that can be downloaded from the email.
     *
     * @return list of files to be attached.
     */
    public List<EmailAttachment> getEmailAttachments() {
        return mEmailAttachments;
    }

    /**
     * Sets list of files to be attached. Attachments are shown in mail clients
     * as files that can be downloaded from the email.
     *
     * @param emailAttachments list of files to be attached.
     */
    public void setEmailAttachments(final List<EmailAttachment> emailAttachments) {
        mEmailAttachments = emailAttachments;
    }

    /**
     * Returns list of files to be embedded inline within the email so they are
     * shown as part of the html content. These files can be used within the
     * HTML body as images, css resources, etc.
     * In order to use this resources within the HTML markup, and id must be
     * provided on each inline attachment and the same id must be used within
     * the HTML markup. If no id is generated, one will be randomly created, and
     * placeholders of the form {0}, {1}, etc will be filled within the HTML
     * markup in the same order as the inline attachments are provided. For
     * instance an image in the HTML can be defined either as
     * &lt;img src="cid:some-id"/&gt;, or instead a placeholder can be used such
     * as &lt;img src="cid:{0}"/&gt;.
     *
     * @return list of files to be embedded inline.
     */
    public List<InlineAttachment> getInlineAttachments() {
        return mInlineAttachments;
    }

    /**
     * Sets list of files to be embedded inline within the email so they are
     * shown as part of the html content. These files can be used within the
     * HTML body as images, css resources, etc.
     * In order to use this resources within the HTML markup, and id must be
     * provided on each inline attachment and the same id must be used within
     * the HTML markup. If no id is generated, one will be randomly created, and
     * placeholders of the form {0}, {1}, etc will be filled within the HTML
     * markup in the same order as the inline attachments are provided. For
     * instance an image in the HTML can be defined either as
     * &lt;img src="cid:some-id"/&gt;, or instead a placeholder can be used such
     * as &lt;img src="cid:{0}"/&gt;.
     *
     * @param inlineAttachments list of files to be embedded inline.
     */
    public void setInlineAttachments(final List<InlineAttachment> inlineAttachments) {
        mInlineAttachments = inlineAttachments;
    }

    /**
     * Creates an HTML email message using provided email sender.
     *
     * @param sender class in charge of sending this email.
     * @return an HTML email message.
     */
    public static HtmlEmailMessage<?> create(final EmailSender sender) {
        return create(sender.getProvider());
    }

    /**
     * Creates an HTML email message using given email provider.
     *
     * @param provider email provider.
     * @return an HTML email message.
     */
    protected static HtmlEmailMessage<?> create(final EmailProvider provider) {
        return create("", provider);
    }

    /**
     * Creates an HTML email message having provided subject and using
     * provided email sender.
     *
     * @param subject email subject to be set.
     * @param sender  class in charge of sending this email.
     * @return an HTML email message.
     */
    public static HtmlEmailMessage<?> create(final String subject,
                                             final EmailSender sender) {
        return create(subject, sender.getProvider());
    }

    /**
     * Creates an HTML email message having provided subject and given email
     * provider.
     *
     * @param subject  email subject to be set.
     * @param provider email provider.
     * @return an HTML email message.
     */
    protected static HtmlEmailMessage<?> create(final String subject,
                                                final EmailProvider provider) {
        return create(subject, "", provider);
    }

    /**
     * Creates an HTML email message having provided subject, HTML content and
     * using provided email sender.
     *
     * @param subject     email subject to be set.
     * @param htmlContent email HTML content.
     * @param sender      class in charge of sending this email.
     * @return an HTML email message.
     */
    public static HtmlEmailMessage<?> create(final String subject, final String htmlContent,
                                             final EmailSender sender) {
        return create(subject, htmlContent, sender.getProvider());
    }

    /**
     * Creates an HTML email message having provided subject, HTML content and
     * given email provider.
     *
     * @param subject     email subject to be set.
     * @param htmlContent email HTML content.
     * @param provider    email provider.
     * @return an HTML email message.
     */
    protected static HtmlEmailMessage<?> create(final String subject, final String htmlContent,
                                                final EmailProvider provider) {
        switch (provider) {
            case AWS_MAIL:
                return new AWSHtmlEmailMessage(subject, htmlContent);
            case APACHE_MAIL:
                return new ApacheMailHtmlEmailMessage(subject, htmlContent);
            case JAVA_MAIL:
            default:
                return new JavaMailHtmlEmailMessage(subject, htmlContent);
        }
    }
}
