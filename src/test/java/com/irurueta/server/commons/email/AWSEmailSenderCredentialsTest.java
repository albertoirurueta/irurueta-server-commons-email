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

public class AWSEmailSenderCredentialsTest {
    
    public AWSEmailSenderCredentialsTest() {}
    
    @BeforeClass
    public static void setUpClass() {}
    
    @AfterClass
    public static void tearDownClass() {}
    
    @Before
    public void setUp() {}
    
    @After
    public void tearDown() {}
    
    @Test
    public void testConstructor(){
        String accessKey = "access";
        String secretKey = "secret";
        AWSEmailSenderCredentials credentials = new AWSEmailSenderCredentials(
                accessKey, secretKey);
        
        assertEquals(credentials.getAccessKey(), accessKey);
        assertEquals(credentials.getSecretKey(), secretKey);
        assertTrue(credentials.isAccessKeyAvailable());
        assertTrue(credentials.isSecretKeyAvailable());
        assertTrue(credentials.isReady());
    }
    
    @Test
    public void testGetSetAccesseyAndIsAccessKeyAvailable(){
        String accessKey = "access";
        String secretKey = "secret";
        AWSEmailSenderCredentials credentials = new AWSEmailSenderCredentials(
                accessKey, secretKey);

        assertEquals(credentials.getAccessKey(), accessKey);
        assertEquals(credentials.getSecretKey(), secretKey);
        assertTrue(credentials.isReady());
        
        //set new value
        String accessKey2 = "access2";
        credentials.setAccessKey(accessKey2);
        
        //check correctness
        assertEquals(credentials.getAccessKey(), accessKey2);
        assertTrue(credentials.isAccessKeyAvailable());
        assertTrue(credentials.isReady());
        
        //set to null
        credentials.setAccessKey(null);
        
        //check correctness        
        assertNull(credentials.getAccessKey());
        assertFalse(credentials.isAccessKeyAvailable());
        assertFalse(credentials.isReady());
    }
    
    @Test
    public void testGetSetSecretKeyAndIsSecretKeyAvailable(){
        String accessKey = "access";
        String secretKey = "secret";
        AWSEmailSenderCredentials credentials = new AWSEmailSenderCredentials(
                accessKey, secretKey);

        assertEquals(credentials.getAccessKey(), accessKey);
        assertEquals(credentials.getSecretKey(), secretKey);
        assertTrue(credentials.isReady());

        //set new value
        String secretKey2 = "secret2";
        credentials.setSecretKey(secretKey2);
        
        //check correctness
        assertEquals(credentials.getSecretKey(), secretKey2);
        assertTrue(credentials.isSecretKeyAvailable());
        assertTrue(credentials.isReady());
        
        //set to null
        credentials.setSecretKey(null);
        
        //check correctness
        assertNull(credentials.getSecretKey());
        assertFalse(credentials.isSecretKeyAvailable());
        assertFalse(credentials.isReady());
    }
}
