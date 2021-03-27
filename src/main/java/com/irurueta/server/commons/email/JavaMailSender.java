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

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.lang.ref.SoftReference;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to send emails using JavaMail.
 */
public class JavaMailSender extends CommonEmailSender {

    /**
     * Reference to singleton instance of this class.
     */
    private static SoftReference<JavaMailSender> mReference;

    /**
     * Constructor.
     * Loads mail configuration, and if it fails for some reason, mail sending
     * becomes disabled.
     */
    private JavaMailSender() {
        super();

        if (mEnabled) {
            Logger.getLogger(JavaMailSender.class.getName()).log(
                    Level.INFO, "Java Email Sender enabled.");
        } else {
            Logger.getLogger(JavaMailSender.class.getName()).log(
                    Level.INFO, "Java Email Sender disabled. Any " +
                            "attempt to send emails using Java mail will be silently " +
                            "ignored");
        }
    }

    /**
     * Returns or creates singleton instance of this class.
     *
     * @return singleton of this class.
     */
    public static synchronized JavaMailSender getInstance() {
        JavaMailSender sender;
        if (mReference == null || (sender = mReference.get()) == null) {
            sender = new JavaMailSender();
            mReference = new SoftReference<>(sender);
        }
        return sender;
    }

    /**
     * Resets JavaMailSingleton.
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
    public String send(final EmailMessage m)
            throws MailNotSentException {

        if (mEnabled) {
            final Properties props = new Properties();
            if (mMailId == null || mMailId.isEmpty() || mMailPassword == null ||
                    mMailPassword.isEmpty()) {
                // disable authentication
                props.put("mail.smtp.auth", "false");
            } else {
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.socketFactory.class",
                        "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.ssl.checkserveridentity", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.socketFactory.port", mMailPort);
            }

            if (!(m instanceof JavaMailEmailMessage)) {
                throw new MailNotSentException("Wrong provider");
            }

            final JavaMailEmailMessage javaEmailMessage = (JavaMailEmailMessage) m;

            final Session session = Session.getInstance(props, null);

            try {
                final MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(mMailFromAddress));

                for (final String s : m.getTo()) {
                    message.addRecipients(Message.RecipientType.TO, s);
                }

                for (final String s : m.getCC()) {
                    message.addRecipients(Message.RecipientType.CC, s);
                }

                for (final String s : m.getBCC()) {
                    message.addRecipients(Message.RecipientType.BCC, s);
                }

                if (m.getSubject() != null) {
                    message.setSubject(m.getSubject(), "utf-8");
                }
                // sets content of message
                javaEmailMessage.buildContent(message);
                // set date when mail was sent
                message.setSentDate(new Date());

                final Transport transport = session.getTransport("smtp");
                transport.connect(mMailHost, mMailPort, mMailId, mMailPassword);

                transport.sendMessage(message, message.getAllRecipients());

                transport.close();

            } catch (final Exception ex) {
                throw new MailNotSentException(ex);
            }
        }
        return null;
    }

    /**
     * Returns provider used by this email sender.
     *
     * @return email provider.
     */
    @Override
    public EmailProvider getProvider() {
        return EmailProvider.JAVA_MAIL;
    }
}
