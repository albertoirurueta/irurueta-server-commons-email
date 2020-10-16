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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AWSMailSenderTest {

    private static final long SLEEP = 1000;

    public static final String PROPS_FILE = "./aws-mail.properties";

    public static final long AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS = 3600000;


    public AWSMailSenderTest() {
    }

    @BeforeClass
    public static void setUpClass() throws ConfigurationException, IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(PROPS_FILE));
        props.setProperty(MailConfigurationFactory.
                        AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS_PROPERTY,
                String.valueOf(AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS));
        props.setProperty(MailConfigurationFactory.MAIL_PROVIDER_PROPERTY,
                EmailProvider.AWS_MAIL.toString());
        MailConfigurationFactory.getInstance().reconfigure(props);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetInstanceAndReset() {
        AWSMailSender mailSender =
                AWSMailSender.getInstance();
        assertNotNull(mailSender);

        MailConfiguration cfg = MailConfigurationFactory.getInstance().
                getConfiguration();

        assertNotNull(mailSender.getCredentials());
        assertTrue(mailSender.getCredentials().isReady());
        assertNotNull(mailSender.getCredentials().getAccessKey());
        assertNotNull(mailSender.getCredentials().getSecretKey());
        assertEquals(mailSender.getCredentials().getAccessKey(),
                cfg.getAWSMailAccessKey());
        assertEquals(mailSender.getCredentials().getSecretKey(),
                cfg.getAWSMailSecretKey());
        assertEquals(mailSender.getMailFromAddress(), cfg.getMailFromAddress());
        assertTrue(mailSender.isEnabled());
        assertEquals(mailSender.getCheckQuotaAfterMillis(),
                AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS);
        assertEquals(mailSender.getProvider(), EmailProvider.AWS_MAIL);

        AWSMailSender mailSender2 = AWSMailSender.getInstance();
        assertSame(mailSender, mailSender2);

        //reset
        AWSMailSender.reset();

        AWSMailSender mailSender3 = AWSMailSender.getInstance();

        assertNotNull(mailSender3.getCredentials());
        assertTrue(mailSender3.getCredentials().isReady());
        assertNotNull(mailSender3.getCredentials().getAccessKey());
        assertNotNull(mailSender3.getCredentials().getSecretKey());
        assertEquals(mailSender3.getCredentials().getAccessKey(),
                cfg.getAWSMailAccessKey());
        assertEquals(mailSender3.getCredentials().getSecretKey(),
                cfg.getAWSMailSecretKey());
        assertEquals(mailSender3.getMailFromAddress(), cfg.getMailFromAddress());
        assertTrue(mailSender3.isEnabled());
        assertEquals(mailSender3.getCheckQuotaAfterMillis(),
                AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS);
        assertEquals(mailSender3.getProvider(), EmailProvider.AWS_MAIL);

        assertNotSame(mailSender, mailSender3);
    }

    @Test
    public void testSendMessage() throws MailNotSentException,
            NotSupportedException, InterruptedException {
        AWSMailSender mailSender =
                AWSMailSender.getInstance();

        String text = "This is some test mail using AWS SES";
        String subject = "Test";

        TextEmailMessage message = TextEmailMessage.create(subject, text,
                mailSender);
        message.getTo().add("alberto@irurueta.com");


        assertNotNull(mailSender.send(message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendMessageToMultipleRecipients()
            throws MailNotSentException,
            NotSupportedException, InterruptedException {
        AWSMailSender mailSender =
                AWSMailSender.getInstance();

        String text = "This is a test mail using AWS SES for multiple recipients";
        String subject = "Multiple recipients test";

        TextEmailMessage message = TextEmailMessage.create(subject, text,
                mailSender);
        message.getTo().add("alberto@irurueta.com");
        message.getTo().add("webmaster@irurueta.com");

        assertNotNull(mailSender.send(message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendMessageWithNonEnglishCharacters()
            throws MailNotSentException,
            NotSupportedException, InterruptedException {

        AWSMailSender mailSender =
                AWSMailSender.getInstance();

        String text = "Atención. Este mensaje contiene carácteres españoles. AWS SES";
        String subject = "Prueba de carácteres";

        TextEmailMessage message = TextEmailMessage.create(subject, text,
                mailSender);
        message.getTo().add("alberto@irurueta.com");

        assertNotNull(mailSender.send(message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendMessageWithAttachment() throws MailNotSentException,
            NotSupportedException, InterruptedException {
        AWSMailSender mailSender =
                AWSMailSender.getInstance();

        String text = "This is some test AWS mail with attachment";
        String subject = "Test with attachment";

        TextEmailMessageWithAttachments message =
                TextEmailMessageWithAttachments.create(subject, text,
                        mailSender);
        message.getTo().add("alberto@irurueta.com");

        File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/email/rotate1.jpg");
        EmailAttachment emailAttachment = new EmailAttachment(attachment,
                "image.jpg", "image/jpg");

        message.getAttachments().add(emailAttachment);

        assertNotNull(mailSender.send(message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendMessageToMultipleRecipientsWithAttachment()
            throws MailNotSentException,
            NotSupportedException, InterruptedException {
        AWSMailSender mailSender =
                AWSMailSender.getInstance();

        String text = "This is a test mail for multiple recipients with attachment. AWS SES";
        String subject = "Multiple recipients test with attachment";

        TextEmailMessageWithAttachments message =
                TextEmailMessageWithAttachments.create(subject, text,
                        mailSender);
        message.getTo().add("alberto@irurueta.com");
        message.getTo().add("webmaster@irurueta.com");

        File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/email/rotate1.jpg");
        EmailAttachment emailAttachment = new EmailAttachment(attachment,
                "image.jpg", "image/jpg");

        message.getAttachments().add(emailAttachment);

        assertNotNull(mailSender.send(message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendMessageWithNonEnglishCharactersAndAttachment()
            throws MailNotSentException, NotSupportedException, InterruptedException {

        AWSMailSender mailSender =
                AWSMailSender.getInstance();

        String text = "Atención. Este mensaje contiene carácteres españoles y tiene un archivo adjunto. AWS SES";
        String subject = "Prueba de carácteres con archivo adjunto";

        TextEmailMessageWithAttachments message =
                TextEmailMessageWithAttachments.create(subject, text,
                        mailSender);
        message.getTo().add("alberto@irurueta.com");

        File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/email/rotate1.jpg");
        EmailAttachment emailAttachment = new EmailAttachment(attachment,
                "image.jpg", "image/jpg");

        message.getAttachments().add(emailAttachment);

        assertNotNull(mailSender.send(message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendHtmlMessageWithEmailAndInlineAttachments()
            throws NotSupportedException, MailNotSentException, InterruptedException {
        AWSMailSender mailSender =
                AWSMailSender.getInstance();

        String html = "<h1>HTML email using AWS SES</h1>"
                + "<h2>Inline image with id</h2>"
                + "<img src=\"cid:image\"/>"
                + "<h2>Inline image without id</h2>"
                + "<img src=\"cid:{0}\"/>";
        String alternativeText = "alternative text";
        String subject = "HTML email";

        HtmlEmailMessage message = HtmlEmailMessage.create(subject, html,
                mailSender);
        message.setAlternativeText(alternativeText);
        message.getTo().add("alberto@irurueta.com");

        File f = new File(
                "./src/test/java/com/irurueta/server/commons/email/rotate1.jpg");

        //add inline attachments

        //inline attachment with id "image"
        InlineAttachment inlineAttachment = new InlineAttachment(f, "image",
                "image/jpg");
        message.getInlineAttachments().add(inlineAttachment);
        //inline attachment without id
        inlineAttachment = new InlineAttachment(f, "image/jpg");
        message.getInlineAttachments().add(inlineAttachment);

        //add email attachment
        EmailAttachment emailAttachment = new EmailAttachment(f,
                "image.jpg", "image/jpg");
        message.getEmailAttachments().add(emailAttachment);

        assertNotNull(mailSender.send(message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendDisabled() throws ConfigurationException, IOException,
            NotSupportedException, MailNotSentException {
        Properties props = new Properties();
        MailConfigurationFactory.getInstance().reconfigure(props);

        AWSMailSender.reset();
        AWSMailSender mailSender = AWSMailSender.getInstance();

        String text = "Disabled test";
        String subject = null;

        TextEmailMessage message = TextEmailMessage.create(subject, text,
                mailSender);
        message.getTo().add("alberto@irurueta.com");
        message.getTo().add("webmaster@irurueta.com");

        assertNull(mailSender.send(message));


        //reset configuration
        props = new Properties();
        props.load(new FileInputStream(PROPS_FILE));
        props.setProperty(MailConfigurationFactory.
                        AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS_PROPERTY,
                String.valueOf(AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS));
        props.setProperty(MailConfigurationFactory.MAIL_PROVIDER_PROPERTY,
                EmailProvider.AWS_MAIL.toString());
        MailConfigurationFactory.getInstance().reconfigure(props);
        AWSMailSender.reset();
    }

    @Test
    public void testSendWithCcBccAndNoSubject() throws NotSupportedException {
        AWSMailSender mailSender = AWSMailSender.getInstance();

        String text = "No subject test";
        String subject = null;

        TextEmailMessage message = TextEmailMessage.create(subject, text,
                mailSender);
        message.getTo().add("alberto@irurueta.com");
        message.getBCC().add("webmaster@irurueta.com");
        message.getCC().add("alberto@irurueta.com");
    }
}