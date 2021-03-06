/*
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

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class JavaMailHtmlEmailMessageTest {

    @Test
    public void testConstructor() throws NotSupportedException {
        // test empty constructor
        JavaMailHtmlEmailMessage message = new JavaMailHtmlEmailMessage();

        // check correctness
        assertNull(message.getHtmlContent());
        assertNull(message.getAlternativeText());
        assertTrue(message.getTo().isEmpty());
        assertTrue(message.isToSupported());
        assertTrue(message.getCC().isEmpty());
        assertTrue(message.isCCSupported());
        assertTrue(message.getBCC().isEmpty());
        assertTrue(message.isBCCSupported());
        assertTrue(message.getSubject().isEmpty());
        assertTrue(message.getEmailAttachments().isEmpty());
        assertTrue(message.getInlineAttachments().isEmpty());

        // test constructor with subject
        message = new JavaMailHtmlEmailMessage("subject");

        // check correctness
        assertNull(message.getHtmlContent());
        assertNull(message.getAlternativeText());
        assertTrue(message.getTo().isEmpty());
        assertTrue(message.isToSupported());
        assertTrue(message.getCC().isEmpty());
        assertTrue(message.isCCSupported());
        assertTrue(message.getBCC().isEmpty());
        assertTrue(message.isBCCSupported());
        assertEquals(message.getSubject(), "subject");
        assertTrue(message.getEmailAttachments().isEmpty());
        assertTrue(message.getInlineAttachments().isEmpty());

        // test constructor with subject and text
        message = new JavaMailHtmlEmailMessage("subject", "<p>content</p>");

        // check correctness
        assertEquals(message.getHtmlContent(), "<p>content</p>");
        assertNull(message.getAlternativeText());
        assertTrue(message.getTo().isEmpty());
        assertTrue(message.isToSupported());
        assertTrue(message.getCC().isEmpty());
        assertTrue(message.isCCSupported());
        assertTrue(message.getBCC().isEmpty());
        assertTrue(message.isBCCSupported());
        assertEquals(message.getSubject(), "subject");
        assertTrue(message.getEmailAttachments().isEmpty());
        assertTrue(message.getInlineAttachments().isEmpty());
    }

    @Test
    public void testGetSetHtmlContent() {
        final JavaMailHtmlEmailMessage message = new JavaMailHtmlEmailMessage();

        // check correctness
        assertNull(message.getHtmlContent());

        // set new text
        final String text = "<p>content</p>";
        message.setHtmlContent(text);

        // check correctness
        assertEquals(message.getHtmlContent(), text);
    }

    @Test
    public void testGetSetAlternativeText() {
        final JavaMailHtmlEmailMessage message = new JavaMailHtmlEmailMessage();

        // check correctness
        assertNull(message.getAlternativeText());

        // set new text
        final String text = "text";
        message.setAlternativeText(text);

        // check correctness
        assertEquals(message.getAlternativeText(), text);
    }

    @Test
    public void testGetTo() throws NotSupportedException {
        final JavaMailHtmlEmailMessage message = new JavaMailHtmlEmailMessage();

        // check correctness
        assertTrue(message.getTo().isEmpty());

        // add destination
        message.getTo().add("alberto@irurueta.com");

        // check correctness
        assertEquals(message.getTo().size(), 1);
        assertTrue(message.getTo().contains("alberto@irurueta.com"));
    }

    @Test
    public void testGetCC() throws NotSupportedException {
        final JavaMailHtmlEmailMessage message = new JavaMailHtmlEmailMessage();

        // check correctness
        assertTrue(message.getCC().isEmpty());

        // add destination
        message.getCC().add("alberto@irurueta.com");

        // check correctness
        assertEquals(message.getCC().size(), 1);
        assertTrue(message.getCC().contains("alberto@irurueta.com"));
    }

    @Test
    public void testGetBCC() throws NotSupportedException {
        final JavaMailHtmlEmailMessage message = new JavaMailHtmlEmailMessage();

        // check correctness
        assertTrue(message.getBCC().isEmpty());

        // add destination
        message.getBCC().add("alberto@irurueta.com");

        // check correctness
        assertEquals(message.getBCC().size(), 1);
        assertTrue(message.getBCC().contains("alberto@irurueta.com"));
    }

    @Test
    public void testGetSetSubject() {
        final JavaMailHtmlEmailMessage message = new JavaMailHtmlEmailMessage();

        // check correctness
        assertTrue(message.getSubject().isEmpty());

        // set subject
        final String subject = "subject";
        message.setSubject(subject);

        // check correctness
        assertEquals(message.getSubject(), subject);
    }

    @Test
    public void testGetSetEmailAttachments() {
        final File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/mail/rotate1.jpg");

        final JavaMailHtmlEmailMessage message =
                new JavaMailHtmlEmailMessage();

        // check correctness
        assertTrue(message.getEmailAttachments().isEmpty());

        final EmailAttachment emailAttachment = new EmailAttachment(attachment);

        message.getEmailAttachments().add(emailAttachment);

        // check correctness
        assertTrue(message.getEmailAttachments().contains(emailAttachment));
        assertEquals(message.getEmailAttachments().size(), 1);
    }

    @Test
    public void testGetSetInlineAttachments() {
        final File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/mail/rotate1.jpg");

        final JavaMailHtmlEmailMessage message =
                new JavaMailHtmlEmailMessage();

        // check correctness
        assertTrue(message.getInlineAttachments().isEmpty());

        final InlineAttachment inlineAttachment = new InlineAttachment(attachment);

        message.getInlineAttachments().add(inlineAttachment);

        // check correctness
        assertTrue(message.getInlineAttachments().contains(inlineAttachment));
        assertEquals(message.getInlineAttachments().size(), 1);
    }
}