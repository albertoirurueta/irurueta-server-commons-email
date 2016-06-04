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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TextEmailMessageTest {
    
    public static final String PROPS_FILE = "./java-mail.properties";
    
    public TextEmailMessageTest() {}
    
    @BeforeClass
    public static void setUpClass() throws IOException, ConfigurationException {
        Properties props = new Properties();
        props.load(new FileInputStream(PROPS_FILE));
        props.setProperty(MailConfigurationFactory.MAIL_PROVIDER_PROPERTY, 
                EmailProvider.JAVA_MAIL.toString());                
        MailConfigurationFactory.getInstance().reconfigure(props);                        
    }
    
    @AfterClass
    public static void tearDownClass() {}
    
    @Before
    public void setUp() {}
    
    @After
    public void tearDown() {}
    
    @Test
    public void testCreate() {
        JavaMailSender mailSender = JavaMailSender.getInstance();
        
        //test with mail sender
        TextEmailMessage email = TextEmailMessage.create(mailSender);
        assertTrue(email instanceof JavaMailTextEmailMessage);
        
        //test with provider
        email = TextEmailMessage.create(EmailProvider.AWS_MAIL);
        assertTrue(email instanceof AWSTextEmailMessage);
        email = TextEmailMessage.create(EmailProvider.APACHE_MAIL);
        assertTrue(email instanceof ApacheMailTextEmailMessage);
        email = TextEmailMessage.create(EmailProvider.JAVA_MAIL);
        assertTrue(email instanceof JavaMailTextEmailMessage);
        
        //test with mail sender and subject
        email = TextEmailMessage.create("subject", mailSender);
        assertTrue(email instanceof JavaMailTextEmailMessage); 
        assertEquals(email.getSubject(), "subject");
        
        //test with provider and subject
        email = TextEmailMessage.create("subject", EmailProvider.AWS_MAIL);
        assertTrue(email instanceof AWSTextEmailMessage);
        assertEquals(email.getSubject(), "subject");
        email = TextEmailMessage.create("subject", EmailProvider.APACHE_MAIL);
        assertTrue(email instanceof ApacheMailTextEmailMessage);
        assertEquals(email.getSubject(), "subject");
        email = TextEmailMessage.create("subject", EmailProvider.JAVA_MAIL);
        assertTrue(email instanceof JavaMailTextEmailMessage);
        assertEquals(email.getSubject(), "subject");
        
        //test with mail sender, subject and text
        email = TextEmailMessage.create("subject", "text", mailSender);
        assertTrue(email instanceof JavaMailTextEmailMessage); 
        assertEquals(email.getSubject(), "subject");
        assertEquals(email.getText(), "text");
        
        //test with provider, subject and text
        email = TextEmailMessage.create("subject", "text", 
                EmailProvider.AWS_MAIL);
        assertTrue(email instanceof AWSTextEmailMessage);
        assertEquals(email.getSubject(), "subject");
        assertEquals(email.getText(), "text");
        email = TextEmailMessage.create("subject", "text", 
                EmailProvider.APACHE_MAIL);
        assertTrue(email instanceof ApacheMailTextEmailMessage);
        assertEquals(email.getSubject(), "subject");
        assertEquals(email.getText(), "text");
        email = TextEmailMessage.create("subject", "text", 
                EmailProvider.JAVA_MAIL);
        assertTrue(email instanceof JavaMailTextEmailMessage);
        assertEquals(email.getSubject(), "subject");        
        assertEquals(email.getText(), "text");
    }
}
