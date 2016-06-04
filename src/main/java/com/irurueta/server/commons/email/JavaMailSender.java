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
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Class to send emails using JavaMail.
 */
public class JavaMailSender extends EmailSender<MimeMessage> {
    
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
    private static SoftReference<JavaMailSender> mReference;
    
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
    private JavaMailSender() {
        
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
        } catch (ConfigurationException e) { }
        
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
     * @return singleton of this class.
     */
    public static synchronized JavaMailSender getInstance() {
        JavaMailSender sender;
        if(mReference == null || (sender = mReference.get()) == null) {
            sender = new JavaMailSender();
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
        return (mailHost != null && mailPort >= MIN_PORT_VALUE && 
                mailPort <= MAX_PORT_VALUE && mailFromAddress != null && 
                isValidEmailAddress(mailFromAddress));
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
    public String send(EmailMessage<MimeMessage> m) throws MailNotSentException{
        
        if (mEnabled) {
            Properties props = new Properties();
            if (mMailId == null || mMailId.isEmpty() || mMailPassword == null || 
                    mMailPassword.isEmpty()) {
                //disable authentication
                props.put("mail.smtp.auth", "false");
            } else {
                props.put("mail.smtp.auth", "true");            
                props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");            
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.socketFactory.port", mMailPort);
            }            
        
            Session session = Session.getInstance(props, null);
        
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(mMailFromAddress));
            
                for (String s : m.getTo()) {
                    message.addRecipients(Message.RecipientType.TO, s);
                }
            
                for (String s : m.getCC()) {
                    message.addRecipients(Message.RecipientType.CC, s);
                }
            
                for (String s : m.getBCC()) {
                    message.addRecipients(Message.RecipientType.BCC, s);
                }
            
                if (m.getSubject() != null) {
                    message.setSubject(m.getSubject(), "utf-8");
                }
                //sets content of message
                m.buildContent(message);
                //set date when mail was sent
                message.setSentDate(new Date());
            
                Transport transport = session.getTransport("smtp");
                transport.connect(mMailHost, mMailPort, mMailId, mMailPassword);
            
                transport.sendMessage(message, message.getAllRecipients());
            
                transport.close();
            
            } catch (MessagingException ex) {
                throw new MailNotSentException(ex);
            } catch (Throwable t) {
                throw new MailNotSentException(t);
            }
        }
        return null;
    }  
    
    /**
     * Returns provider used by this email sender.
     * @return email provider.
     */
    @Override
    public EmailProvider getProvider() {
        return EmailProvider.JAVA_MAIL;
    }        
}
