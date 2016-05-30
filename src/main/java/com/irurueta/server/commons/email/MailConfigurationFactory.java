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

import com.irurueta.server.commons.configuration.BaseConfigurationFactory;
import com.irurueta.server.commons.configuration.ConfigurationException;
import java.util.Properties;

/**
 * Class in charge of loading and configuring the email sending layer.
 */
public class MailConfigurationFactory extends 
        BaseConfigurationFactory<MailConfiguration> {

    /**
     * Property indicating SMTP server to connect to.
     */
    public static final String MAIL_HOST_PROPERTY = 
            "com.irurueta.server.commons.email.MAIL_HOST";
    
    /**
     * Property indicating SMTP server port to connect to.
     */
    public static final String MAIL_PORT_PROPERTY = 
            "com.irurueta.server.commons.email.MAIL_PORT";
    
    /**
     * Property indicating user id to log into SMTP server.
     */
    public static final String MAIL_ID_PROPERTY = 
            "com.irurueta.server.commons.email.MAIL_ID";
    
    /**
     * Property indicating user password to log into SMTP server.
     */
    public static final String MAIL_PASSWORD_PROPERTY = 
            "com.irurueta.server.commons.email.MAIL_PASSWORD";
    
    /**
     * Property indicating email address to send emails from.
     */
    public static final String MAIL_FROM_ADDRESS_PROPERTY = 
            "com.irurueta.server.commons.email.MAIL_FROM_ADDRESS";
    
    /**
     * Property indicating whether email sending is enabled or not.
     * When disabled, email sending will transparently fail.
     */
    public static final String MAIL_SENDING_ENABLED_PROPERTY = 
            "com.irurueta.server.commons.email.MAIL_SENDING_ENABLED";    
    
    /**
     * Property indicating which email provider to use to send emails.
     */
    public static final String MAIL_PROVIDER_PROPERTY =
            "com.irurueta.server.commons.email.MAIL_PROVIDER";    
    
    /**
     * Property indicating Amazon AWS access key to use for Amazon SES.
     */
    public static final String AWS_MAIL_ACCESS_KEY_PROPERTY = 
            "com.irurueta.server.commons.email.AWS_MAIL_ACCESS_KEY";
    
    /**
     * Property indicating Amazon AWS secret key to use for Amazon SES.
     */
    public static final String AWS_MAIL_SECRET_KEY_PROPERTY = 
            "com.irurueta.server.commons.email.AWS_MAIL_SECRET_KEY";
    
    /**
     * Property indicating time in milliseconds to wait to check sending quota.
     * This is used to avoid Amazon SES throttling when sending large amounts
     * of emails.
     */
    public static final String AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS_PROPERTY = 
            "com.irurueta.server.commons.email.AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS";
    
    /**
     * Default amount of time in milliseconds to wait to check email sending
     * quota using AWS SES.
     */
    public static final long DEFAULT_AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS = 
            3600000;           
    
    /**
     * Property indicating whether email sending is enabled by default or not.
     */
    public static final boolean DEFAULT_MAIL_SENDING_ENABLED = false;
    
    /**
     * Property indicating email sending provider if none is given.
     */
    public static final EmailProvider DEFAULT_MAIL_PROVIDER = 
            EmailProvider.JAVA_MAIL;
    
    /**
     * Default port for SSL.
     */
    public static final int DEFAULT_SSL_PORT = 465;
    
    /**
     * Default port for TLS.
     */
    public static final int DEFAULT_TLS_PORT = 587;
    
    /**
     * Reference to factory singleton.
     */
    private static MailConfigurationFactory mSingleton;
    
    /**
     * Constructor.
     */
    private MailConfigurationFactory() { }
    
    /**
     * Factory method to create or return singleton instance.
     * @return factory singleton.
     */
    public synchronized static MailConfigurationFactory getInstance() {
        if (mSingleton == null) {
            mSingleton = new MailConfigurationFactory();
        }
        return mSingleton;
    }

    /**
     * Configures the email sending layer using provided properties.
     * @param properties properties containing email configuration.
     * @return email configuration.
     * @throws ConfigurationException if any property value was invalid.
     */
    @Override
    public MailConfiguration configure(Properties properties) 
            throws ConfigurationException {
        if(mConfiguration != null) return mConfiguration;
        
        if(properties == null){
            //use default configuration
            mConfiguration = new MailConfigurationImpl();
        }else{
            mConfiguration = new MailConfigurationImpl(properties);
        }
        
        //configure email sender.
        EmailSender.getInstance();
        
        return mConfiguration;
    }
    
}
