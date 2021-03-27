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

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;

import java.lang.ref.SoftReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to send emails using Apache mail.
 */
public class ApacheMailSender extends CommonEmailSender {

    /**
     * Logger for this class.
     */
    public static final Logger LOGGER = Logger.getLogger(
            ApacheMailSender.class.getName());

    /**
     * Reference to singleton instance of this class.
     */
    private static SoftReference<ApacheMailSender> mReference;

    /**
     * Constructor.
     * Loads mail configuration, and if it fails for some reason, mail sending
     * becomes disabled.
     */
    private ApacheMailSender() {
        super();

        if (mEnabled) {
            LOGGER.log(Level.INFO, "Apache Email Sender enabled.");
        } else {
            LOGGER.log(Level.INFO, "Apache Email Sender disabled. Any " +
                    "attempt to send emails using Apache mail will be " +
                    "silently ignored");
        }
    }

    /**
     * Returns or creates singleton instance of this class.
     *
     * @return singleton of this class.
     */
    public static synchronized ApacheMailSender getInstance() {
        ApacheMailSender sender;
        if (mReference == null || (sender = mReference.get()) == null) {
            sender = new ApacheMailSender();
            mReference = new SoftReference<>(sender);
        }
        return sender;
    }

    /**
     * Resets ApacheMailSender singleton.
     */
    public static synchronized void reset() {
        mReference = null;
    }

    /**
     * Method to send an email.
     *
     * @param m email message to be sent.
     * @return id of message that has been sent.
     * @throws MailNotSentException if mail couldn't be sent.
     */
    @Override
    public String send(final EmailMessage m) throws MailNotSentException {
        if (!mEnabled) {
            //don't send message if not enabled
            return null;
        }

        //to avoid compilation errors regarding to casting
        if (m instanceof ApacheMailTextEmailMessage) {
            return sendMultiPartEmail(m);
        } else if (m instanceof ApacheMailTextEmailMessageWithAttachments) {
            return sendMultiPartEmail(m);
        } else if (m instanceof ApacheMailHtmlEmailMessage) {
            return sendHtmlEmail(m);
        } else {
            throw new MailNotSentException("Unsupported email type");
        }
    }

    /**
     * Returns provider used by this email sender.
     *
     * @return email provider.
     */
    @Override
    public EmailProvider getProvider() {
        return EmailProvider.APACHE_MAIL;
    }

    /**
     * Method to send a multipart email.
     *
     * @param m email message to be sent.
     * @return id of message that has been sent.
     * @throws MailNotSentException if mail couldn't be sent.
     */
    public String sendMultiPartEmail(final EmailMessage m)
            throws MailNotSentException {
        try {
            final MultiPartEmail email = new MultiPartEmail();
            internalSendApacheEmail(m, email);
        } catch (final Exception e) {
            throw new MailNotSentException(e);
        }
        return null;
    }

    /**
     * Method to send html email.
     *
     * @param m email message to be sent.
     * @return id of message that has been sent.
     * @throws MailNotSentException if mail couldn't be sent.
     */
    public String sendHtmlEmail(final EmailMessage m)
            throws MailNotSentException {
        try {
            final HtmlEmail email = new HtmlEmail();
            internalSendApacheEmail(m, email);
        } catch (final Exception e) {
            throw new MailNotSentException(e);
        }
        return null;
    }

    /**
     * Internal method to send email using Apache Mail.
     *
     * @param m     email message.
     * @param email apache email message.
     * @throws NotSupportedException                            if feature is not supported.
     * @throws EmailException                                   if Apache Mail cannot send email.
     * @throws com.irurueta.server.commons.email.EmailException if sending
     *                                                          email fails.
     */
    private <T extends Email> void internalSendApacheEmail(final EmailMessage m, T email)
            throws NotSupportedException, EmailException,
            com.irurueta.server.commons.email.EmailException {

        ApacheEmailMessage apacheEmailMessage = null;
        ApacheMultipartEmailMessage apacheMultipartEmailMessage = null;
        if (m instanceof ApacheEmailMessage) {
            apacheEmailMessage = (ApacheEmailMessage) m;
        }
        if (m instanceof ApacheMultipartEmailMessage) {
            apacheMultipartEmailMessage = (ApacheMultipartEmailMessage) m;
        }
        if (apacheEmailMessage == null && apacheMultipartEmailMessage == null) {
            throw new MailNotSentException("Wrong provider");
        }

        email.setHostName(mMailHost);
        email.setSmtpPort(mMailPort);
        if (mMailId != null && !mMailId.isEmpty() && mMailPassword != null &&
                !mMailPassword.isEmpty()) {
            email.setAuthenticator(new DefaultAuthenticator(mMailId,
                    mMailPassword));
        }
        email.setStartTLSEnabled(true);
        email.setFrom(mMailFromAddress);
        if (m.getSubject() != null) {
            email.setSubject(m.getSubject());
        }
        if (apacheEmailMessage != null) {
            apacheEmailMessage.buildContent((HtmlEmail) email);
        }
        if (apacheMultipartEmailMessage != null) {
            apacheMultipartEmailMessage.buildContent((MultiPartEmail) email);
        }

        // add destinations
        for (final String s : m.getTo()) {
            email.addTo(s);
        }

        for (final String s : m.getCC()) {
            email.addCc(s);
        }

        for (final String s : m.getBCC()) {
            email.addBcc(s);
        }

        email.send();
    }
}
