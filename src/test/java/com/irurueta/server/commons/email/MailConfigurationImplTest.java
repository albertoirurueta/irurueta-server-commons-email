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
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MailConfigurationImplTest {
    
    public static final String MAIL_HOST = "smtp.desigual.com";
    public static final int MAIL_PORT = 25;
    public static final String MAIL_ID = "mailId";
    public static final String MAIL_PASSWORD = "password";
    public static final String MAIL_FROM_ADDRESS = "alberto@irurueta.com";
    public static final boolean MAIL_SENDING_ENABLED = true;
    
    public static final String AWS_MAIL_ACCESS_KEY = "key";
    public static final String AWS_MAIL_SECRET_KEY = "secret";
    public static final String AWS_MAIL_FROM_ADDRESS = "alberto@irurueta.com";    
    public static final long AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS = 1000;
    
    public static final EmailProvider MAIL_PROVIDER = EmailProvider.AWS_MAIL;
    
    public MailConfigurationImplTest() {}
    
    @BeforeClass
    public static void setUpClass() {}
    
    @AfterClass
    public static void tearDownClass() {}
    
    @Before
    public void setUp() {}
    
    @After
    public void tearDown() {}
    
    @Test
    public void testConstructorsAndGetters() throws ConfigurationException{
        
        //empty constructor
        MailConfigurationImpl cfg = new MailConfigurationImpl();
        
        //check correctness
        assertNull(cfg.getMailHost());
        assertEquals(cfg.getMailPort(), 587);
        assertNull(cfg.getMailId());
        assertNull(cfg.getMailPassword());
        assertNull(cfg.getMailFromAddress());
        assertEquals(cfg.isMailSendingEnabled(), 
                MailConfigurationFactory.DEFAULT_MAIL_SENDING_ENABLED);
        assertNull(cfg.getAWSMailAccessKey());
        assertNull(cfg.getAWSMailSecretKey());
        assertNotNull(cfg.getAWSMailCredentials());
        assertEquals(cfg.getAWSMailCheckQuotaAfterMillis(), 
                MailConfigurationFactory.
                DEFAULT_AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS);
        assertEquals(cfg.getProvider(), 
                MailConfigurationFactory.DEFAULT_MAIL_PROVIDER);
        
        //test constructor with properties
        Properties props = buildProperties();
        
        cfg = new MailConfigurationImpl(props);
        
        //check correctness
        assertEquals(cfg.getMailHost(), MAIL_HOST);
        assertEquals(cfg.getMailPort(), MAIL_PORT);
        assertEquals(cfg.getMailId(), MAIL_ID);
        assertEquals(cfg.getMailPassword(), MAIL_PASSWORD);
        assertEquals(cfg.getMailFromAddress(), MAIL_FROM_ADDRESS);
        assertEquals(cfg.isMailSendingEnabled(), MAIL_SENDING_ENABLED);
        assertEquals(cfg.getAWSMailAccessKey(), AWS_MAIL_ACCESS_KEY);
        assertEquals(cfg.getAWSMailSecretKey(), AWS_MAIL_SECRET_KEY);
        AWSEmailSenderCredentials credentials = cfg.getAWSMailCredentials();
        assertNotNull(credentials);
        assertEquals(credentials.getAccessKey(), AWS_MAIL_ACCESS_KEY);
        assertEquals(credentials.getSecretKey(), AWS_MAIL_SECRET_KEY);
        assertEquals(cfg.getAWSMailCheckQuotaAfterMillis(), 
                AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS);
        assertEquals(cfg.getProvider(), MAIL_PROVIDER);
        
        //Force ConfigurationException
        props = new Properties();
        props.setProperty(MailConfigurationFactory.MAIL_PORT_PROPERTY, "wrong");        
        cfg = null;        
        try{
            cfg = new MailConfigurationImpl(props);
            fail("ConfigurationException expected but not thrown");
        }catch(ConfigurationException e){}        
                
        props = new Properties();
        props.setProperty(MailConfigurationFactory.
                AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS_PROPERTY, "wrong");
        try{
            cfg = new MailConfigurationImpl(props);
            fail("ConfigurationException expected but not thrown");
        }catch(ConfigurationException e){}        
        assertNull(cfg);
    }
    
    @Test
    public void testFromProperties() throws ConfigurationException{
        //test constructor with properties
        Properties props = buildProperties();
        
        MailConfiguration cfg = new MailConfigurationImpl();
        
        //load configuration from properties
        cfg.fromProperties(props);
        
        //check correctness
        assertEquals(cfg.getMailHost(), MAIL_HOST);
        assertEquals(cfg.getMailPort(), MAIL_PORT);
        assertEquals(cfg.getMailId(), MAIL_ID);
        assertEquals(cfg.getMailPassword(), MAIL_PASSWORD);
        assertEquals(cfg.getMailFromAddress(), MAIL_FROM_ADDRESS);
        assertEquals(cfg.isMailSendingEnabled(), MAIL_SENDING_ENABLED);
        assertEquals(cfg.getAWSMailAccessKey(), AWS_MAIL_ACCESS_KEY);
        assertEquals(cfg.getAWSMailSecretKey(), AWS_MAIL_SECRET_KEY);
        AWSEmailSenderCredentials credentials = cfg.getAWSMailCredentials();
        assertNotNull(credentials);
        assertEquals(credentials.getAccessKey(), AWS_MAIL_ACCESS_KEY);
        assertEquals(credentials.getSecretKey(), AWS_MAIL_SECRET_KEY);
        assertEquals(cfg.getAWSMailCheckQuotaAfterMillis(), 
                AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS);
        assertEquals(cfg.getProvider(), MAIL_PROVIDER);
        
        //Force ConfigurationException
        props = new Properties();
        props.setProperty(MailConfigurationFactory.MAIL_PORT_PROPERTY, "wrong");                
        try{
            cfg.fromProperties(props);
            fail("ConfigurationException expected but not thrown");
        }catch(ConfigurationException e){}        
                
        props = new Properties();
        props.setProperty(MailConfigurationFactory.
                AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS_PROPERTY, "wrong");
        try{
            cfg.fromProperties(props);
            fail("ConfigurationException expected but not thrown");
        }catch(ConfigurationException e){}        
    }
    
    @Test
    public void testToProperties() throws ConfigurationException{
        Properties props = buildProperties();
        
        MailConfiguration cfg = new MailConfigurationImpl(props);
        Properties props2 = cfg.toProperties();
        
        assertEquals(props.getProperty(
                MailConfigurationFactory.MAIL_HOST_PROPERTY),
                props2.getProperty(
                MailConfigurationFactory.MAIL_HOST_PROPERTY));
        assertEquals(props.getProperty(
                MailConfigurationFactory.MAIL_PORT_PROPERTY),
                props2.getProperty(
                MailConfigurationFactory.MAIL_PORT_PROPERTY));
        assertEquals(props.getProperty(
                MailConfigurationFactory.MAIL_ID_PROPERTY),
                props2.getProperty(MailConfigurationFactory.MAIL_ID_PROPERTY));
        assertEquals(props.getProperty(
                MailConfigurationFactory.MAIL_PASSWORD_PROPERTY),
                props2.getProperty(
                MailConfigurationFactory.MAIL_PASSWORD_PROPERTY));
        assertEquals(props.getProperty(
                MailConfigurationFactory.MAIL_FROM_ADDRESS_PROPERTY),
                props2.getProperty(
                MailConfigurationFactory.MAIL_FROM_ADDRESS_PROPERTY));
        assertEquals(props.getProperty(
                MailConfigurationFactory.MAIL_SENDING_ENABLED_PROPERTY),
                props2.getProperty(
                MailConfigurationFactory.MAIL_SENDING_ENABLED_PROPERTY));
        assertEquals(props.getProperty(
                MailConfigurationFactory.AWS_MAIL_ACCESS_KEY_PROPERTY),
                props2.getProperty(
                MailConfigurationFactory.AWS_MAIL_ACCESS_KEY_PROPERTY));
        assertEquals(props.getProperty(
                MailConfigurationFactory.AWS_MAIL_SECRET_KEY_PROPERTY),
                props2.getProperty(
                MailConfigurationFactory.AWS_MAIL_SECRET_KEY_PROPERTY));
        assertEquals(props.getProperty(MailConfigurationFactory.
                AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS_PROPERTY),
                props2.getProperty(MailConfigurationFactory.
                AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS_PROPERTY));
        assertEquals(props.getProperty(
                MailConfigurationFactory.MAIL_PROVIDER_PROPERTY),
                props2.getProperty(
                MailConfigurationFactory.MAIL_PROVIDER_PROPERTY));
    }
    
    private Properties buildProperties(){
        Properties props = new Properties();
        props.setProperty(MailConfigurationFactory.MAIL_HOST_PROPERTY, 
                MAIL_HOST);
        props.setProperty(MailConfigurationFactory.MAIL_PORT_PROPERTY, 
                String.valueOf(MAIL_PORT));
        props.setProperty(MailConfigurationFactory.MAIL_ID_PROPERTY, MAIL_ID);
        props.setProperty(MailConfigurationFactory.MAIL_PASSWORD_PROPERTY, 
                MAIL_PASSWORD);
        props.setProperty(MailConfigurationFactory.MAIL_FROM_ADDRESS_PROPERTY,
                MAIL_FROM_ADDRESS);
        props.setProperty(MailConfigurationFactory.
                MAIL_SENDING_ENABLED_PROPERTY, String.valueOf(
                MAIL_SENDING_ENABLED));
        props.setProperty(MailConfigurationFactory.AWS_MAIL_ACCESS_KEY_PROPERTY,
                AWS_MAIL_ACCESS_KEY);
        props.setProperty(MailConfigurationFactory.AWS_MAIL_SECRET_KEY_PROPERTY,
                AWS_MAIL_SECRET_KEY);
        props.setProperty(MailConfigurationFactory.
                AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS_PROPERTY,
                String.valueOf(AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS));
        props.setProperty(MailConfigurationFactory.MAIL_PROVIDER_PROPERTY, 
                MAIL_PROVIDER.getValue());
        return props;
    }
}
