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

import com.irurueta.server.commons.configuration.ConfigurationException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class TextEmailMessageWithAttachmentsTest {

    public static final String PROPS_FILE = "./java-mail.properties";

    @BeforeClass
    public static void setUpClass() throws IOException, ConfigurationException {
        final Properties props = new Properties();
        props.load(new FileInputStream(PROPS_FILE));
        props.setProperty(MailConfigurationFactory.MAIL_PROVIDER_PROPERTY,
                EmailProvider.JAVA_MAIL.toString());
        MailConfigurationFactory.getInstance().reconfigure(props);
    }

    @Test
    public void testCreate() {
        final JavaMailSender mailSender = JavaMailSender.getInstance();

        // test with mail sender
        TextEmailMessageWithAttachments<?> email =
                TextEmailMessageWithAttachments.create(mailSender);
        assertTrue(email instanceof JavaMailTextEmailMessageWithAttachments);

        // test with provider
        email = TextEmailMessageWithAttachments.create(EmailProvider.AWS_MAIL);
        assertTrue(email instanceof AWSTextEmailMessageWithAttachments);
        email = TextEmailMessageWithAttachments.create(
                EmailProvider.APACHE_MAIL);
        assertTrue(email instanceof ApacheMailTextEmailMessageWithAttachments);
        email = TextEmailMessageWithAttachments.create(EmailProvider.JAVA_MAIL);
        assertTrue(email instanceof JavaMailTextEmailMessageWithAttachments);

        // test with mail sender and subject
        email = TextEmailMessageWithAttachments.create("subject", mailSender);
        assertTrue(email instanceof JavaMailTextEmailMessageWithAttachments);
        assertEquals(email.getSubject(), "subject");

        // test with provider and subject
        email = TextEmailMessageWithAttachments.create("subject",
                EmailProvider.AWS_MAIL);
        assertTrue(email instanceof AWSTextEmailMessageWithAttachments);
        assertEquals(email.getSubject(), "subject");
        email = TextEmailMessageWithAttachments.create("subject",
                EmailProvider.APACHE_MAIL);
        assertTrue(email instanceof ApacheMailTextEmailMessageWithAttachments);
        assertEquals(email.getSubject(), "subject");
        email = TextEmailMessageWithAttachments.create("subject",
                EmailProvider.JAVA_MAIL);
        assertTrue(email instanceof JavaMailTextEmailMessageWithAttachments);
        assertEquals(email.getSubject(), "subject");

        // test with mail sender, subject and text
        email = TextEmailMessageWithAttachments.create("subject", "text",
                mailSender);
        assertTrue(email instanceof JavaMailTextEmailMessageWithAttachments);
        assertEquals(email.getSubject(), "subject");
        assertEquals(email.getText(), "text");

        // test with provider, subject and text
        email = TextEmailMessageWithAttachments.create("subject", "text",
                EmailProvider.AWS_MAIL);
        assertTrue(email instanceof AWSTextEmailMessageWithAttachments);
        assertEquals(email.getSubject(), "subject");
        assertEquals(email.getText(), "text");
        email = TextEmailMessageWithAttachments.create("subject", "text",
                EmailProvider.APACHE_MAIL);
        assertTrue(email instanceof ApacheMailTextEmailMessageWithAttachments);
        assertEquals(email.getSubject(), "subject");
        assertEquals(email.getText(), "text");
        email = TextEmailMessageWithAttachments.create("subject", "text",
                EmailProvider.JAVA_MAIL);
        assertTrue(email instanceof JavaMailTextEmailMessageWithAttachments);
        assertEquals(email.getSubject(), "subject");
        assertEquals(email.getText(), "text");

        // test with attachments and sender
        final List<EmailAttachment> attachments = new ArrayList<>();
        email = TextEmailMessageWithAttachments.create(attachments, mailSender);
        assertTrue(email instanceof JavaMailTextEmailMessageWithAttachments);
        assertSame(email.getAttachments(), attachments);

        // test with attachments and provider
        email = TextEmailMessageWithAttachments.create(attachments,
                EmailProvider.AWS_MAIL);
        assertTrue(email instanceof AWSTextEmailMessageWithAttachments);
        assertSame(email.getAttachments(), attachments);
        email = TextEmailMessageWithAttachments.create(attachments,
                EmailProvider.APACHE_MAIL);
        assertTrue(email instanceof ApacheMailTextEmailMessageWithAttachments);
        assertSame(email.getAttachments(), attachments);
        email = TextEmailMessageWithAttachments.create(attachments,
                EmailProvider.JAVA_MAIL);
        assertTrue(email instanceof JavaMailTextEmailMessageWithAttachments);
        assertSame(email.getAttachments(), attachments);

        // test with subject, attachments and sender
        email = TextEmailMessageWithAttachments.create("subject", attachments,
                mailSender);
        assertTrue(email instanceof JavaMailTextEmailMessageWithAttachments);
        assertEquals(email.getSubject(), "subject");
        assertSame(email.getAttachments(), attachments);

        // test with subject, attachments and provider
        email = TextEmailMessageWithAttachments.create("subject", attachments,
                EmailProvider.AWS_MAIL);
        assertTrue(email instanceof AWSTextEmailMessageWithAttachments);
        assertEquals(email.getSubject(), "subject");
        assertSame(email.getAttachments(), attachments);
        email = TextEmailMessageWithAttachments.create("subject", attachments,
                EmailProvider.APACHE_MAIL);
        assertTrue(email instanceof ApacheMailTextEmailMessageWithAttachments);
        assertEquals(email.getSubject(), "subject");
        assertSame(email.getAttachments(), attachments);
        email = TextEmailMessageWithAttachments.create("subject", attachments,
                EmailProvider.JAVA_MAIL);
        assertTrue(email instanceof JavaMailTextEmailMessageWithAttachments);
        assertEquals(email.getSubject(), "subject");
        assertSame(email.getAttachments(), attachments);

        // test with subject, text, attachments and sender
        email = TextEmailMessageWithAttachments.create("subject", "text",
                attachments, mailSender);
        assertTrue(email instanceof JavaMailTextEmailMessageWithAttachments);
        assertEquals(email.getSubject(), "subject");
        assertEquals(email.getText(), "text");
        assertSame(email.getAttachments(), attachments);

        // test with subject, text, attachments and provider
        email = TextEmailMessageWithAttachments.create("subject", "text",
                attachments, EmailProvider.AWS_MAIL);
        assertTrue(email instanceof AWSTextEmailMessageWithAttachments);
        assertEquals(email.getSubject(), "subject");
        assertEquals(email.getText(), "text");
        assertSame(email.getAttachments(), attachments);
        email = TextEmailMessageWithAttachments.create("subject", "text",
                attachments, EmailProvider.APACHE_MAIL);
        assertTrue(email instanceof ApacheMailTextEmailMessageWithAttachments);
        assertEquals(email.getSubject(), "subject");
        assertEquals(email.getText(), "text");
        assertSame(email.getAttachments(), attachments);
        email = TextEmailMessageWithAttachments.create("subject", "text",
                attachments, EmailProvider.JAVA_MAIL);
        assertTrue(email instanceof JavaMailTextEmailMessageWithAttachments);
        assertEquals(email.getSubject(), "subject");
        assertEquals(email.getText(), "text");
        assertSame(email.getAttachments(), attachments);
    }

    @Test
    public void testGetSetAttachments() {
        final TextEmailMessageWithAttachments<?> email =
                TextEmailMessageWithAttachments.create(EmailProvider.AWS_MAIL);

        assertNotNull(email.getAttachments());

        // set new value
        final List<EmailAttachment> attachments = new ArrayList<>();
        email.setAttachments(attachments);

        // check correctness
        assertSame(email.getAttachments(), attachments);
    }
}
