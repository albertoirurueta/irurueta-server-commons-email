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

import com.irurueta.server.commons.configuration.ConfigurationException;
import java.lang.ref.SoftReference;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;

/**
 * Class to send emails using Apache mail.
 */
public class ApacheMailSender extends EmailSender<Email> {
    
    /**
     * Logger for this class.
     */
    public static final Logger LOGGER = Logger.getLogger(
            ApacheMailSender.class.getName());
    
    /**
     * Minimum allowed port value to connect to SMTP server.
     */
    private static final int MIN_PORT_VALUE = 0;
    
    /**
     * Maximum allowed port value to connect to SMTP server.
     */
    private static final int MAX_PORT_VALUE = 65535;    

    /**
     * Reference to singleton instance of this class.
     */
    private static SoftReference<ApacheMailSender> mReference;
    
    /**
     * SMTP server host to connect to.
     */
    private String mMailHost;
    
    /**
     * SMTP server port to connect to.
     */
    private int mMailPort;
    
    /**
     * User id to log into SMTP server. If none is provided no authentication 
     * will be used.
     */
    private String mMailId;
    
    /**
     * User password to log into SMTP server. If none is provided no 
     * authentication will be used.
     */
    private String mMailPassword;
    
    /**
     * Address to send emails from.
     */
    private String mMailFromAddress;
    
    /**
     * Indicates if mail sending is enabled.
     */
    private boolean mEnabled;
    
    /**
     * Constructor.
     * Loads mail configuration, and if it fails for some reason, mail sending
     * becomes disabled.
     */
    private ApacheMailSender() {
        
        try {
            MailConfiguration cfg = MailConfigurationFactory.getInstance().
                    configure();
            mMailHost = cfg.getMailHost();
            mMailPort = cfg.getMailPort();
            mMailId = cfg.getMailId();
            mMailPassword = cfg.getMailPassword();
            mMailFromAddress = cfg.getMailFromAddress();
        
            mEnabled = isValidConfiguration(mMailHost, mMailPort, 
                    mMailFromAddress) && cfg.isMailSendingEnabled();
        } catch (ConfigurationException e) { 
            mEnabled = false;
        }
        
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
     * Indicates if provided configuration is valid by checking that SMTP server
     * and mail from address are not null, and server port is valid.
     * @param mailHost SMTP server host.
     * @param mailPort SMTP server port.
     * @param mailFromAddress email address to send emails from.
     * @return true if configuration is valid, false otherwise.
     */
    private boolean isValidConfiguration(String mailHost, int mailPort,
            //String mailId, String mailPassword, 
            String mailFromAddress) {
        return mailHost != null && mailPort >= MIN_PORT_VALUE && 
                mailPort <= MAX_PORT_VALUE && mailFromAddress != null && 
                isValidEmailAddress(mailFromAddress);
    }
    
    /**
     * Returns SMTP server host.
     * @return SMTP server host.
     */
    public String getMailHost() {
        return mMailHost;
    }
    
    /**
     * Returns SMTP server port.
     * @return SMTP server port.
     */
    public int getMailPort() {
        return mMailPort;
    }
    
    /**
     * Returns user id to log into SMTP server. If this value is null server
     * authentication will be disabled.
     * @return user id to log into SMTP server.
     */
    public String getMailId() {
        return mMailId;        
    }
    
    /**
     * Returns user password to log into SMTP server. If this value is null
     * server authentication will be disabled.
     * @return user password to log into SMTP server.
     */
    public String getMailPassword() {
        return mMailPassword;
    }
    
    /**
     * Email address to send emails from.
     * @return email address to send emails from.
     */
    public String getMailFromAddress() {
        return mMailFromAddress;
    }
    
    /**
     * Indicates if this mail sender controller is enabled.
     * @return true if enabled, false otherwise.
     */
    public boolean isEnabled() {
        return mEnabled;
    }
    
    /**
     * Method to send an email.
     * @param m email message to be sent.
     * @return id of message that has been sent.
     * @throws MailNotSentException if mail couldn't be sent.
     */        
    @Override
    public String send(EmailMessage<Email> m) throws MailNotSentException {
        //to avoid compilation errors regarding to casting
        EmailMessage m2 = m;
        if (m2 instanceof ApacheMailTextEmailMessage) {
            return sendMultiPartEmail((ApacheMailTextEmailMessage)m2);
        } else if (m2 instanceof ApacheMailTextEmailMessageWithAttachments) {
            return sendMultiPartEmail((ApacheMailTextEmailMessageWithAttachments)m2);
        } else if (m2 instanceof ApacheMailHtmlEmailMessage) {
            return sendHtmlEmail((ApacheMailHtmlEmailMessage)m2);
        } else {
            throw new MailNotSentException("Unsupported email type");
        }
    }   
    
    /**
     * Returns provider used by this email sender.
     * @return email provider.
     */    
    @Override
    public EmailProvider getProvider() {
        return EmailProvider.APACHE_MAIL;
    }    
    
    /**
     * Method to send a multipart email.
     * @param m email message to be sent.
     * @return id of message that has been sent.
     * @throws MailNotSentException if mail couldn't be sent.
     */        
    public String sendMultiPartEmail(EmailMessage<MultiPartEmail> m) 
            throws MailNotSentException {
        try {
            MultiPartEmail email = new MultiPartEmail();
            internalSendApacheEmail(m, email);
        } catch (Throwable t) {
            throw new MailNotSentException(t);
        }
        return null;
    }

    /**
     * Method to send html email.
     * @param m email message to be sent.
     * @return id of message that has been sent.
     * @throws MailNotSentException if mail couldn't be sent.
     */        
    public String sendHtmlEmail(EmailMessage<HtmlEmail> m) 
            throws MailNotSentException {
        try {
            HtmlEmail email = new HtmlEmail();  
            internalSendApacheEmail(m, email);
        } catch (Throwable t) {
            throw new MailNotSentException(t);
        }
        return null;
    }
    
    /**
     * Internal method to send email using Apache Mail.
     * @param m email message.
     * @param email apache email message.
     * @throws NotSupportedException if feature is not supported.
     * @throws EmailException if Apache Mail cannot send email.
     * @throws com.irurueta.server.commons.email.EmailException if sending
     * email fails.
     */
    private void internalSendApacheEmail(EmailMessage m, Email email) 
            throws NotSupportedException, EmailException, 
            com.irurueta.server.commons.email.EmailException {
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
        m.buildContent(email);


        //add destinatoins
        for (String s : (List<String>)m.getTo()) {
            email.addTo(s);
        }

        for (String s : (List<String>)m.getCC()) {
            email.addCc(s);
        }

        for (String s : (List<String>)m.getBCC()) {
            email.addBcc(s);
        }

        email.send();        
    }        
}
