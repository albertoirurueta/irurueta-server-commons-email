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

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.GetSendQuotaResult;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.amazonaws.services.simpleemail.model.SendRawEmailResult;
import com.irurueta.server.commons.configuration.ConfigurationException;

import java.io.ByteArrayOutputStream;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

/**
 * Class to send emails using Amazon Web Services (AWS) SES.
 */
public class AWSMailSender extends EmailSender {

    /**
     * Logger for this class.
     */
    public static final Logger LOGGER = Logger.getLogger(
            AWSMailSender.class.getName());

    /**
     * Milliseconds to wait to check quota.
     */
    public static final long DEFAULT_CHECK_QUOTA_AFTER_MILLIS = 3600000;

    /**
     * Number of sent mails to take into account before checking quota.
     */
    public static final long MIN_CHECK_QUOTA_AFTER_MILLIS = 0;

    /**
     * Number of milliseconds in a second.
     */
    private static final double MILLISECONDS_PER_SECOND = 1000.0;

    /**
     * Reference to singleton instance of this class.
     */
    private static SoftReference<AWSMailSender> mReference;

    /**
     * AWS SES credentials.
     */
    private AWSEmailSenderCredentials mCredentials;

    /**
     * Email address to be used as sender.
     */
    private String mMailFromAddress;

    /**
     * Indicates whether email sender is enabled or not.
     */
    private boolean mEnabled;

    /**
     * Internal client to be used for mail sending.
     */
    private AmazonSimpleEmailServiceClient mClient;

    /**
     * Indicates amount of milliseconds to check quota of mail sending returned
     * by Amazon. This is used to prevent Amazon SES throttling.
     */
    private volatile long mCheckQuotaAfterMillis;

    /**
     * Timestamps of last sent email.
     */
    private volatile long mLastSentMailTimestamp;

    /**
     * Amount of milliseconds to wait when next email must be sent if quota is
     * exceeded.
     */
    private volatile long mWaitIntervalMillis;

    /**
     * Constructor.
     * Loads mail configuration, and if it fails for some reason, mail sending
     * becomes disabled.
     */
    private AWSMailSender() {

        try {
            final MailConfiguration cfg = MailConfigurationFactory.getInstance().
                    configure();
            mCredentials = cfg.getAWSMailCredentials();
            mMailFromAddress = cfg.getMailFromAddress();

            mEnabled = mMailFromAddress != null &&
                    isValidEmailAddress(mMailFromAddress) &&
                    cfg.isMailSendingEnabled();
            mCheckQuotaAfterMillis = cfg.getAWSMailCheckQuotaAfterMillis();
            mLastSentMailTimestamp = 0;
            mWaitIntervalMillis = 0;
        } catch (final ConfigurationException e) {
            mEnabled = false;
        }

        if (mEnabled) {
            LOGGER.log(Level.INFO, "AWS Email Sender enabled.");
        } else {
            LOGGER.log(Level.INFO, "AWS Email Sender disabled. Any " +
                    "attempt to send emails using AWS SES will be " +
                    "silently ignored");
        }
    }

    /**
     * Returns or creates singleton instance of this class.
     *
     * @return singleton of this class.
     */
    public static synchronized AWSMailSender getInstance() {
        AWSMailSender sender;
        if (mReference == null || (sender = mReference.get()) == null) {
            sender = new AWSMailSender();
            mReference = new SoftReference<>(sender);
        }
        return sender;
    }

    /**
     * Resets AWSMailSender singleton.
     */
    public static synchronized void reset() {
        mReference = null;
    }

    /**
     * Returns AWS SES credentials.
     *
     * @return Amazon Web Services Simple Email Service credentials.
     */
    public AWSEmailSenderCredentials getCredentials() {
        return mCredentials;
    }

    /**
     * Email address to send emails from.
     *
     * @return email address to send emails from.
     */
    public String getMailFromAddress() {
        return mMailFromAddress;
    }

    /**
     * Indicates if this mail sender controller is enabled.
     *
     * @return true if enabled, false otherwise
     */
    public boolean isEnabled() {
        return mEnabled;
    }

    /**
     * Amount of milliseconds to wait before checking sending quota.
     *
     * @return amount of milliseconds to wait before checking sending quota.
     */
    public long getCheckQuotaAfterMillis() {
        return mCheckQuotaAfterMillis;
    }

    /**
     * Method to send an email.
     *
     * @param m email message to be sent.
     * @return id of message that has been sent.
     * @throws MailNotSentException if mail couldn't be sent.
     * @throws InterruptedException if thread is interrupted.
     */
    @Override
    public String send(final EmailMessage m) throws MailNotSentException, InterruptedException {
        // to avoid compilation errors because of casting
        if (m instanceof AWSTextEmailMessage) {
            return sendTextEmail((AWSTextEmailMessage) m);
        } else if (m instanceof AWSTextEmailMessageWithAttachments) {
            return sendRawEmail(m);
        } else if (m instanceof AWSHtmlEmailMessage) {
            return sendRawEmail(m);
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
        return EmailProvider.AWS_MAIL;
    }

    /**
     * Method to send a text email.
     *
     * @param m email message to be sent.
     * @return id of message that has been sent.
     * @throws MailNotSentException if mail couldn't be sent.
     * @throws InterruptedException if thread is interrupted.
     */
    private String sendTextEmail(final AWSTextEmailMessage m)
            throws MailNotSentException, InterruptedException {
        final String messageId;
        final long currentTimestamp = System.currentTimeMillis();
        prepareClient();
        if (!mEnabled) {
            // don't send message if not enabled
            return null;
        }

        try {
            synchronized (this) {
                // prevents throttling
                checkQuota(currentTimestamp);

                final Destination destination = new Destination(m.getTo());
                if (m.getBCC() != null && !m.getBCC().isEmpty()) {
                    destination.setBccAddresses(m.getBCC());
                }
                if (m.getCC() != null && !m.getCC().isEmpty()) {
                    destination.setCcAddresses(m.getCC());
                }

                // if no subject, set to empty string to avoid errors
                if (m.getSubject() == null) {
                    m.setSubject("");
                }

                final Message message = new Message();
                m.buildContent(message);

                final SendEmailResult result = mClient.sendEmail(new SendEmailRequest(mMailFromAddress, destination,
                        message));
                messageId = result.getMessageId();

                waitIfNeeded(currentTimestamp);
            }
        } catch (final InterruptedException e) {
            throw e;
        } catch (final Exception e) {
            throw new MailNotSentException(e);
        }
        return messageId;
    }

    /**
     * Method to send a text email with attachments or HTML emails with
     * attachments (inline or not).
     *
     * @param m email message to be sent.
     * @return id of message that has been sent.
     * @throws MailNotSentException if mail couldn't be sent.
     * @throws InterruptedException if thread is interrupted.
     */
    private String sendRawEmail(final EmailMessage m)
            throws MailNotSentException, InterruptedException {

        final String messageId;
        final long currentTimestamp = System.currentTimeMillis();
        prepareClient();
        if (!mEnabled) {
            // don't send message if not enabled
            return null;
        }

        if (!(m instanceof JavaMailEmailMessage)) {
            throw new MailNotSentException("Wrong provider");
        }

        final JavaMailEmailMessage javaEmailMessage = (JavaMailEmailMessage) m;

        try {
            synchronized (this) {
                // prevents throttling and excessive memory usage
                checkQuota(currentTimestamp);

                // if no subject, set to empty string to avoid errors
                if (m.getSubject() == null) {
                    m.setSubject("");
                }

                final ByteArrayOutputStream outputStream =
                        new ByteArrayOutputStream();

                // convert message into mime multi part and write it to output
                // stream into memory
                final Session session = Session.getInstance(new Properties());
                final MimeMessage mimeMessage = new MimeMessage(session);
                if (m.getSubject() != null) {
                    mimeMessage.setSubject(m.getSubject(), "utf-8");
                }
                javaEmailMessage.buildContent(mimeMessage);
                mimeMessage.writeTo(outputStream);

                final RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(
                        outputStream.toByteArray()));

                final SendRawEmailRequest rawRequest = new SendRawEmailRequest(
                        rawMessage);
                rawRequest.setDestinations(m.getTo());
                rawRequest.setSource(mMailFromAddress);
                SendRawEmailResult result = mClient.sendRawEmail(rawRequest);
                messageId = result.getMessageId();

                waitIfNeeded(currentTimestamp);
            }
        } catch (final InterruptedException e) {
            throw e;
        } catch (final Exception e) {
            throw new MailNotSentException(e);
        }

        return messageId;
    }

    /**
     * Waits for next message to be sent depending on available AWS quota.
     *
     * @param currentTimestamp current timestamp before sending last email.
     * @throws InterruptedException if thread is interrupted.
     */
    private void waitIfNeeded(final long currentTimestamp) throws InterruptedException {
        // update timestamp of last sent email
        mLastSentMailTimestamp = System.currentTimeMillis();

        // wait to avoid throttling exceptions to avoid making any
        // further requests
        final long endTimestamp = currentTimestamp + mWaitIntervalMillis;
        while (System.currentTimeMillis() < endTimestamp) {
            wait(mWaitIntervalMillis);
        }
    }

    /**
     * Checks quota of sent emails to prevent AWS SES throttling.
     *
     * @param currentTimestamp current timestamp.
     */
    private synchronized void checkQuota(final long currentTimestamp) {
        if ((currentTimestamp - mLastSentMailTimestamp) >
                mCheckQuotaAfterMillis) {
            // check quota to determine the number of messages per second to
            // avoid throttling exceptions
            final GetSendQuotaResult quota = mClient.getSendQuota();
            // update interval that we must wait between send requests
            mWaitIntervalMillis = (long) Math.ceil(
                    MILLISECONDS_PER_SECOND / quota.getMaxSendRate());
        }
    }

    /**
     * Prepares client by setting proper credentials.
     */
    private synchronized void prepareClient() {
        if (mClient == null) {
            // instantiate new client if needed
            if (areValidCredentials()) {
                mClient = new AmazonSimpleEmailServiceClient(
                        new BasicAWSCredentials(mCredentials.getAccessKey(),
                                mCredentials.getSecretKey()));
            } else {
                // disabling mail sending
                mEnabled = false;
                Logger.getLogger(AWSMailSender.class.getName()).log(
                        Level.INFO, "AWS Email Sender disabled. " +
                                "Invalid credentials");
            }
        }
    }

    /**
     * Indicates whether provided credentials are valid.
     *
     * @return true if credentials are valid, false otherwise.
     */
    private boolean areValidCredentials() {
        if (mCredentials != null) {
            return mCredentials.isReady();
        } else {
            return false;
        }
    }
}
