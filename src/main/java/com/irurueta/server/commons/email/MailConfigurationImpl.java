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
import java.util.Properties;

/**
 * Class containing configuration to send emails, such as email provider,
 * email server (host, port and credentials) and email address to send emails
 * from.
 */
public class MailConfigurationImpl implements MailConfiguration {
    
    /**
     * SMTP server host to connect to send emails.
     */
    private String mMailHost;
    
    /**
     * SMTP server port to connect to send emails.
     */
    private int mMailPort;
    
    /**
     * User id to log into SMTP server to send emails.
     * This value is optional and can be left as null if server does not
     * require credentials.
     */
    private String mailId;
    
    /**
     * User password to log into SMTP server to send emails.
     * This value is optional and can be left as null if server does not require
     * credentials.
     */
    private String mMailPassword;
    
    /**
     * Email address to send emails from.
     */
    private String mMailFromAddress;
    
    /**
     * Indicates if email sending is enabled or not.
     */
    private boolean mMailSendingEnabled;
    
    /**
     * Email provider to use to send emails.
     */
    private EmailProvider mProvider; 
    
    /**
     * Amazon AWS access key to use if Amazon SES is used as a provider.
     */
    private String mAwsMailAccessKey;
    
    /**
     * Amazon AWS secret key to use if Amazon SES is used as a provider.
     */
    private String mAwsMailSecretKey;
    
    /**
     * Milliseconds to wait before checking sending quota to avoid Amazon SES
     * throttling.
     */
    private long mAwsMailCheckQuotaAfterMillis;   
    
    /**
     * Constructor.
     */
    public MailConfigurationImpl() {
        mMailSendingEnabled = 
                MailConfigurationFactory.DEFAULT_MAIL_SENDING_ENABLED;                
        mProvider = MailConfigurationFactory.DEFAULT_MAIL_PROVIDER;
        mAwsMailCheckQuotaAfterMillis = MailConfigurationFactory.
                DEFAULT_AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS;  
        mMailPort = MailConfigurationFactory.DEFAULT_TLS_PORT;
    }
    
    /**
     * Constructor from properties.
     * @param properties properties containing configuration.
     * @throws ConfigurationException if any property value is invalid.
     */
    public MailConfigurationImpl(Properties properties) 
            throws ConfigurationException {
        fromProperties(properties);
    }

    /**
     * SMTP server host to connect to send emails.
     * @return SMTP server host to connect to send emails.
     */
    @Override
    public String getMailHost() {
        return mMailHost;
    }

    /**
     * SMTP server port to connect to send emails.
     * @return SMTP server port to connect to send emails.
     */
    @Override
    public int getMailPort() {
        return mMailPort;
    }

    /**
     * User id to log into SMTP server to send emails.
     * This value is optional and can be left as null if server does not
     * require credentials.
     * @return user id to log into SMTP server to send emails.
     */
    @Override
    public String getMailId() {
        return mailId;
    }

    /**
     * User password to log into SMTP server to send emails.
     * This value is optional and can be left as null if server does not require
     * credentials.
     * @return User password to log into SMTP server to send emails.
     */
    @Override
    public String getMailPassword() {
        return mMailPassword;
    }

    /**
     * Email address to send emails from.
     * @return email address to send emails from.
     */
    @Override
    public String getMailFromAddress() {
        return mMailFromAddress;
    }

    /**
     * Indicates if email sending is enabled or not.
     * @return true if email sending is enabled, false otherwise.
     */
    @Override
    public boolean isMailSendingEnabled() {
        return mMailSendingEnabled;
    }

    /**
     * Amazon AWS access key to use if Amazon SES is used as a provider.
     * @return AWS access key to use Amazon SES.
     */
    @Override
    public String getAWSMailAccessKey() {
        return mAwsMailAccessKey;
    }

    /**
     * Amazon AWS secret key to use if Amazon SES is used as a provider.
     * @return AWS secret key to use Amazon SES.
     */
    @Override
    public String getAWSMailSecretKey() {
        return mAwsMailSecretKey;
    }

    /**
     * Returns structure containing AWS credentials to use Amazon SES.
     * @return AWS credentials.
     */
    @Override
    public AWSEmailSenderCredentials getAWSMailCredentials() {
        return new AWSEmailSenderCredentials(mAwsMailAccessKey, 
                mAwsMailSecretKey);
    }

    /**
     * Milliseconds to wait before checking sending quota to avoid Amazon SES
     * throttling.
     * @return milliseconds to wait before checking sending quota to avoid 
     * Amazon SES throttling.
     */
    @Override
    public long getAWSMailCheckQuotaAfterMillis() {
        return mAwsMailCheckQuotaAfterMillis;
    }
    
    /**
     * Email provider to use to send emails.
     * @return an email provider.
     */
    @Override
    public EmailProvider getProvider() {
        return mProvider;
    }

    /**
     * Loads configuration from provided properties.
     * @param properties properties containing configuration.
     * @throws ConfigurationException if any properties value is invalid.
     */
    @Override
    public final void fromProperties(Properties properties) 
            throws ConfigurationException {
        try {
            //set mail
            mMailHost = properties.getProperty(
                    MailConfigurationFactory.MAIL_HOST_PROPERTY);
            String mailPortProperty = properties.getProperty(
                    MailConfigurationFactory.MAIL_PORT_PROPERTY);
            if (mailPortProperty != null) {
                mMailPort = Integer.parseInt(mailPortProperty);
            }
            mailId = properties.getProperty(
                    MailConfigurationFactory.MAIL_ID_PROPERTY);
            mMailPassword = properties.getProperty(
                    MailConfigurationFactory.MAIL_PASSWORD_PROPERTY);
            mMailFromAddress = properties.getProperty(
                    MailConfigurationFactory.MAIL_FROM_ADDRESS_PROPERTY);
            mMailSendingEnabled = Boolean.valueOf(properties.getProperty(
                    MailConfigurationFactory.MAIL_SENDING_ENABLED_PROPERTY, 
                    String.valueOf(
                    MailConfigurationFactory.DEFAULT_MAIL_SENDING_ENABLED)));
            mProvider = EmailProvider.fromValue(properties.getProperty(
                    MailConfigurationFactory.MAIL_PROVIDER_PROPERTY, 
                    MailConfigurationFactory.DEFAULT_MAIL_PROVIDER.getValue()));
        
            //set aws mail
            mAwsMailAccessKey = properties.getProperty(
                    MailConfigurationFactory.AWS_MAIL_ACCESS_KEY_PROPERTY);
            mAwsMailSecretKey = properties.getProperty(
                    MailConfigurationFactory.AWS_MAIL_SECRET_KEY_PROPERTY);
            mAwsMailCheckQuotaAfterMillis = Long.parseLong(
                    properties.getProperty(MailConfigurationFactory.
                    AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS_PROPERTY, String.valueOf(
                    MailConfigurationFactory.
                    DEFAULT_AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS)));              
        } catch (Throwable t) {
            throw new ConfigurationException(t);
        }
    }

    /**
     * Converts current configuration into properties.
     * @return properties containing configuration.
     */
    @Override
    public Properties toProperties() {
        Properties properties = new Properties();
        if (mMailHost != null) {
            properties.setProperty(MailConfigurationFactory.MAIL_HOST_PROPERTY, 
                    mMailHost);
        }
        if (mMailPort >= 0) {
            properties.setProperty(MailConfigurationFactory.MAIL_PORT_PROPERTY, 
                    String.valueOf(mMailPort));
        }
        if (mailId != null) {
            properties.setProperty(MailConfigurationFactory.MAIL_ID_PROPERTY, 
                    mailId);
        }
        if (mMailPassword != null) {
            properties.setProperty(MailConfigurationFactory.
                    MAIL_PASSWORD_PROPERTY, mMailPassword);
        }
        if (mMailFromAddress != null) {
            properties.setProperty(MailConfigurationFactory.
                    MAIL_FROM_ADDRESS_PROPERTY, mMailFromAddress);
        }
        properties.setProperty(MailConfigurationFactory.
                MAIL_SENDING_ENABLED_PROPERTY, 
                String.valueOf(mMailSendingEnabled));
        if (mProvider != null) {
            properties.setProperty(MailConfigurationFactory.
                    MAIL_PROVIDER_PROPERTY, mProvider.getValue());
            
            if (mProvider == EmailProvider.AWS_MAIL) {
                //AWS mail
                if (mAwsMailAccessKey != null) {
                    properties.setProperty(MailConfigurationFactory.
                            AWS_MAIL_ACCESS_KEY_PROPERTY, mAwsMailAccessKey);
                }
                if (mAwsMailSecretKey != null) {
                    properties.setProperty(MailConfigurationFactory.
                            AWS_MAIL_SECRET_KEY_PROPERTY, mAwsMailSecretKey);
                }                
                properties.setProperty(MailConfigurationFactory.
                        AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS_PROPERTY, 
                        String.valueOf(mAwsMailCheckQuotaAfterMillis));
            }
        }
        return properties;
    }
}
