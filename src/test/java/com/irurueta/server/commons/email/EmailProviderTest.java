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

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmailProviderTest {
    
    public EmailProviderTest() {}
    
    @BeforeClass
    public static void setUpClass() {}
    
    @AfterClass
    public static void tearDownClass() {}
    
    @Before
    public void setUp() {}
    
    @After
    public void tearDown() {}
    
    @Test
    public void testFromValue(){
        EmailProvider provider = EmailProvider.fromValue("java_mail");
        assertEquals(provider, EmailProvider.JAVA_MAIL);
        
        provider = EmailProvider.fromValue("apache_mail");
        assertEquals(provider, EmailProvider.APACHE_MAIL);
        
        provider = EmailProvider.fromValue("aws_mail");
        assertEquals(provider, EmailProvider.AWS_MAIL);
        
        provider = EmailProvider.fromValue("none");
        assertNull(provider);
        
        provider = EmailProvider.fromValue("other");
        assertNull(provider);
    }
    
    @Test
    public void testGetValue(){
        assertEquals(EmailProvider.JAVA_MAIL.getValue(), "java_mail");
        assertEquals(EmailProvider.APACHE_MAIL.getValue(), "apache_mail");
        assertEquals(EmailProvider.AWS_MAIL.getValue(), "aws_mail");
    }
}
