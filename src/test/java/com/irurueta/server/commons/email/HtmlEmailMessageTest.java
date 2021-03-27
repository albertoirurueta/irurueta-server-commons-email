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

public class HtmlEmailMessageTest {

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
        HtmlEmailMessage email = HtmlEmailMessage.create(mailSender);
        assertTrue(email instanceof JavaMailHtmlEmailMessage);

        // test with provider
        email = HtmlEmailMessage.create(EmailProvider.AWS_MAIL);
        assertTrue(email instanceof AWSHtmlEmailMessage);
        email = HtmlEmailMessage.create(EmailProvider.APACHE_MAIL);
        assertTrue(email instanceof ApacheMailHtmlEmailMessage);
        email = HtmlEmailMessage.create(EmailProvider.JAVA_MAIL);
        assertTrue(email instanceof JavaMailHtmlEmailMessage);

        // test with subject and sender
        email = HtmlEmailMessage.create("subject", mailSender);
        assertTrue(email instanceof JavaMailHtmlEmailMessage);
        assertEquals(email.getSubject(), "subject");

        // test with subject and provider
        email = HtmlEmailMessage.create("subject", EmailProvider.AWS_MAIL);
        assertTrue(email instanceof AWSHtmlEmailMessage);
        assertEquals(email.getSubject(), "subject");
        email = HtmlEmailMessage.create("subject", EmailProvider.APACHE_MAIL);
        assertTrue(email instanceof ApacheMailHtmlEmailMessage);
        assertEquals(email.getSubject(), "subject");
        email = HtmlEmailMessage.create("subject", EmailProvider.JAVA_MAIL);
        assertTrue(email instanceof JavaMailHtmlEmailMessage);
        assertEquals(email.getSubject(), "subject");


        // test with subject, html and sender
        final String html = "<p>html</p>";
        email = HtmlEmailMessage.create("subject", html, mailSender);
        assertTrue(email instanceof JavaMailHtmlEmailMessage);
        assertEquals(email.getSubject(), "subject");
        assertEquals(email.getHtmlContent(), html);

        // test with subject, html and provider
        email = HtmlEmailMessage.create("subject", html,
                EmailProvider.AWS_MAIL);
        assertTrue(email instanceof AWSHtmlEmailMessage);
        assertEquals(email.getSubject(), "subject");
        assertEquals(email.getHtmlContent(), html);
        email = HtmlEmailMessage.create("subject", html,
                EmailProvider.APACHE_MAIL);
        assertTrue(email instanceof ApacheMailHtmlEmailMessage);
        assertEquals(email.getSubject(), "subject");
        assertEquals(email.getHtmlContent(), html);
        email = HtmlEmailMessage.create("subject", html,
                EmailProvider.JAVA_MAIL);
        assertTrue(email instanceof JavaMailHtmlEmailMessage);
        assertEquals(email.getSubject(), "subject");
        assertEquals(email.getHtmlContent(), html);
    }

    @Test
    public void testGetSetEmailAttachments() {
        final HtmlEmailMessage email = HtmlEmailMessage.create(
                EmailProvider.JAVA_MAIL);

        assertNotNull(email.getEmailAttachments());

        // set new value
        final List<EmailAttachment> attachments = new ArrayList<>();
        email.setEmailAttachments(attachments);

        // check correctness
        assertSame(email.getEmailAttachments(), attachments);
    }

    @Test
    public void testGetSetInlineAttachments() {
        final HtmlEmailMessage email = HtmlEmailMessage.create(
                EmailProvider.JAVA_MAIL);

        assertNotNull(email.getInlineAttachments());

        // set new value
        final List<InlineAttachment> attachments = new ArrayList<>();
        email.setInlineAttachments(attachments);

        // check correctness
        assertSame(email.getInlineAttachments(), attachments);
    }
}
