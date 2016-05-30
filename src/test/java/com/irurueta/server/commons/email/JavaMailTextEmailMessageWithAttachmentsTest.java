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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class JavaMailTextEmailMessageWithAttachmentsTest {
    
    public JavaMailTextEmailMessageWithAttachmentsTest() {}
    
    @BeforeClass
    public static void setUpClass() {}
    
    @AfterClass
    public static void tearDownClass() {}
    
    @Before
    public void setUp() {}
    
    @After
    public void tearDown() {}
    
    @Test
    public void testConstructor() throws NotSupportedException{
        //test empty constructor
        JavaMailTextEmailMessageWithAttachments message = 
                new JavaMailTextEmailMessageWithAttachments();
        
        //check correctness
        assertTrue(message.getText().isEmpty());
        assertTrue(message.getTo().isEmpty());
        assertTrue(message.isToSupported());
        assertTrue(message.getCC().isEmpty());
        assertTrue(message.isCCSupported());
        assertTrue(message.getBCC().isEmpty());
        assertTrue(message.isBCCSupported());
        assertTrue(message.getSubject().isEmpty());
        assertTrue(message.getAttachments().isEmpty());
        
        //test constructor with subject
        message = new JavaMailTextEmailMessageWithAttachments("subject");
        
        //check correctness
        assertTrue(message.getText().isEmpty());
        assertTrue(message.getTo().isEmpty());
        assertTrue(message.isToSupported());
        assertTrue(message.getCC().isEmpty());
        assertTrue(message.isCCSupported());
        assertTrue(message.getBCC().isEmpty());
        assertTrue(message.isBCCSupported());
        assertEquals(message.getSubject(), "subject");
        assertTrue(message.getAttachments().isEmpty());
        
        //test constructor with subject and text
        message = new JavaMailTextEmailMessageWithAttachments("subject", 
                "text");
        
        //check correctness
        assertEquals(message.getText(), "text");
        assertTrue(message.getTo().isEmpty());
        assertTrue(message.isToSupported());
        assertTrue(message.getCC().isEmpty());
        assertTrue(message.isCCSupported());
        assertTrue(message.getBCC().isEmpty());
        assertTrue(message.isBCCSupported());
        assertEquals(message.getSubject(), "subject");   
        assertTrue(message.getAttachments().isEmpty());

        File f = new File(
                "./src/test/java/com/irurueta/server/commons/mail/rotate1.jpg");
        List<EmailAttachment> attachments = new ArrayList<EmailAttachment>();
        attachments.add(new EmailAttachment(f));        
        
        //test constructor with attachment
        message = new JavaMailTextEmailMessageWithAttachments(attachments);
        
        //check correctness
        assertTrue(message.getText().isEmpty());
        assertTrue(message.getTo().isEmpty());
        assertTrue(message.isToSupported());
        assertTrue(message.getCC().isEmpty());
        assertTrue(message.isCCSupported());
        assertTrue(message.getBCC().isEmpty());
        assertTrue(message.isBCCSupported());
        assertTrue(message.getSubject().isEmpty());
        assertEquals(message.getAttachments(), attachments);
        
        //test constructor with subject
        message = new JavaMailTextEmailMessageWithAttachments("subject", 
                attachments);
        
        //check correctness
        assertTrue(message.getText().isEmpty());
        assertTrue(message.getTo().isEmpty());
        assertTrue(message.isToSupported());
        assertTrue(message.getCC().isEmpty());
        assertTrue(message.isCCSupported());
        assertTrue(message.getBCC().isEmpty());
        assertTrue(message.isBCCSupported());
        assertEquals(message.getSubject(), "subject");
        assertEquals(message.getAttachments(), attachments);
        
        //test constructor with subject and text
        message = new JavaMailTextEmailMessageWithAttachments("subject", 
                "text", attachments);
        
        //check correctness
        assertEquals(message.getText(), "text");
        assertTrue(message.getTo().isEmpty());
        assertTrue(message.isToSupported());
        assertTrue(message.getCC().isEmpty());
        assertTrue(message.isCCSupported());
        assertTrue(message.getBCC().isEmpty());
        assertTrue(message.isBCCSupported());
        assertEquals(message.getSubject(), "subject");                
        assertEquals(message.getAttachments(), attachments);
    }
    
    @Test
    public void testGetSetText(){
        JavaMailTextEmailMessageWithAttachments message =
                new JavaMailTextEmailMessageWithAttachments();
        
        //check correctness
        assertTrue(message.getText().isEmpty());
        
        //set new text
        String text = "text";
        message.setText(text);
        
        //check correctness
        assertEquals(message.getText(), text);
    }
    
    @Test
    public void testGetTo() throws NotSupportedException{
        JavaMailTextEmailMessageWithAttachments message =
                new JavaMailTextEmailMessageWithAttachments();
        
        //check correctness
        assertTrue(message.getTo().isEmpty());
        
        //add destination
        message.getTo().add("alberto@irurueta.com");
        
        //check correctness
        assertEquals(message.getTo().size(), 1);
        assertTrue(message.getTo().contains("alberto@irurueta.com"));
    }
    
    @Test
    public void testGetCC() throws NotSupportedException{
        JavaMailTextEmailMessageWithAttachments message =
                new JavaMailTextEmailMessageWithAttachments();
        
        //check correctness
        assertTrue(message.getCC().isEmpty());
        
        //add destination
        message.getCC().add("alberto@irurueta.com");
        
        //check correctness
        assertEquals(message.getCC().size(), 1);
        assertTrue(message.getCC().contains("alberto@irurueta.com"));
    }
    
    @Test
    public void testGetBCC() throws NotSupportedException{
        JavaMailTextEmailMessageWithAttachments message =
                new JavaMailTextEmailMessageWithAttachments();
        
        //check correctness
        assertTrue(message.getBCC().isEmpty());
        
        //add destination
        message.getBCC().add("alberto@irurueta.com");
        
        //check correctness
        assertEquals(message.getBCC().size(), 1);
        assertTrue(message.getBCC().contains("alberto@irurueta.com"));
    }
    
    @Test
    public void testGetSetSubject(){
        JavaMailTextEmailMessageWithAttachments message =
                new JavaMailTextEmailMessageWithAttachments();
        
        //check correctness
        assertTrue(message.getSubject().isEmpty());
        
        //set subject
        String subject = "subject";
        message.setSubject(subject);
        
        //check correctness
        assertEquals(message.getSubject(), subject);
    }
    
    @Test
    public void testGetSetAttachments(){
        File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/mail/rotate1.jpg");
        
        JavaMailTextEmailMessageWithAttachments message =
                new JavaMailTextEmailMessageWithAttachments();
        
        //check correctness
        assertTrue(message.getAttachments().isEmpty());
        
        EmailAttachment emailAttachment = new EmailAttachment(attachment);
        
        message.getAttachments().add(emailAttachment);
        
        //check correctness
        assertTrue(message.getAttachments().contains(emailAttachment));
        assertEquals(message.getAttachments().size(), 1);
    }
}
