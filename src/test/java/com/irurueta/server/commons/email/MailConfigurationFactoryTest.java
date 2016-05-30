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

public class MailConfigurationFactoryTest {
    
    public MailConfigurationFactoryTest() {}
    
    @BeforeClass
    public static void setUpClass() {}
    
    @AfterClass
    public static void tearDownClass() {}
    
    @Before
    public void setUp() {}
    
    @After
    public void tearDown() {}
    
    @Test
    public void testGetInstance(){
        MailConfigurationFactory factory = 
                MailConfigurationFactory.getInstance();
        assertNotNull(factory);
    }
    
    @Test
    public void testConfigure() throws ConfigurationException{
        MailConfigurationFactory factory = 
                MailConfigurationFactory.getInstance();
        
        //test configuration with properties        
        Properties props = new Properties();
        MailConfiguration cfg1 = factory.configure(props);
        assertNotNull(cfg1);
        
        //test default configuration
        MailConfiguration cfg2 = factory.configure();
        assertNotNull(cfg2);
        
        assertSame(cfg1, cfg2);
    }
    
    @Test
    public void testReset() throws ConfigurationException{
        MailConfigurationFactory factory =
                MailConfigurationFactory.getInstance();
        
        MailConfiguration cfg1 = factory.configure();
        MailConfiguration cfg2 = factory.configure();
        
        assertSame(cfg1, cfg2);

        //test reset
        factory.reset();
        MailConfiguration cfg3 = factory.configure();
        assertNotSame(cfg3, cfg1);                
    }
    
    @Test
    public void testReconfigure() throws ConfigurationException{
        MailConfigurationFactory factory =
                MailConfigurationFactory.getInstance();
        
        MailConfiguration cfg1 = factory.reconfigure();
        MailConfiguration cfg2 = factory.reconfigure();
        
        assertNotSame(cfg1, cfg2);
        
        Properties props = new Properties();
        MailConfiguration cfg3 = factory.reconfigure(props);
        
        assertNotSame(cfg2, cfg3);
    }
    
    @Test
    public void testGetConfiguration() throws ConfigurationException{
        MailConfigurationFactory factory =
                MailConfigurationFactory.getInstance();
        
        factory.reset();
        
        assertNull(factory.getConfiguration());
        
        MailConfiguration cfg1 = factory.configure();
        MailConfiguration cfg2 = factory.getConfiguration();
        
        assertNotNull(cfg2);
        assertSame(cfg1, cfg2);
    }
}
