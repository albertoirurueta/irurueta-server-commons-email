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

import com.amazonaws.services.simpleemail.model.Message;
import com.irurueta.server.commons.configuration.ConfigurationException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.*;

public class AWSMailSenderTest {

    private static final long SLEEP = 1000;

    public static final String PROPS_FILE = "./aws-mail.properties";

    public static final long AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS = 3600000;


    @BeforeClass
    public static void setUpClass() throws ConfigurationException, IOException {
        final Properties props = new Properties();
        props.load(new FileInputStream(PROPS_FILE));
        props.setProperty(MailConfigurationFactory.
                        AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS_PROPERTY,
                String.valueOf(AWS_MAIL_CHECK_QUOTA_AFTER_MILLIS));
        props.setProperty(MailConfigurationFactory.MAIL_PROVIDER_PROPERTY,
                EmailProvider.AWS_MAIL.toString());
        MailConfigurationFactory.getInstance().reconfigure(props);
    }

    @Test
    public void testConstants() {
        assertEquals(3600000, AWSMailSender.DEFAULT_CHECK_QUOTA_AFTER_MILLIS);
        assertEquals(0, AWSMailSender.MIN_CHECK_QUOTA_AFTER_MILLIS);
    }

    @Test
    public void testGetInstanceAndReset() {
        final AWSMailSender mailSender = AWSMailSender.getInstance();
        assertNotNull(mailSender);

        final MailConfiguration cfg = MailConfigurationFactory.getInstance().
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

        final AWSMailSender mailSender2 = AWSMailSender.getInstance();
        assertSame(mailSender, mailSender2);

        // reset
        AWSMailSender.reset();

        final AWSMailSender mailSender3 = AWSMailSender.getInstance();

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
        final AWSMailSender mailSender = AWSMailSender.getInstance();

        final String text = "This is some test mail using AWS SES";
        final String subject = "Test";

        TextEmailMessage<?> message = TextEmailMessage.create(subject, text,
                mailSender);
        message.getTo().add("alberto@irurueta.com");


        //noinspection unchecked
        assertNotNull(mailSender.send((EmailMessage<Message>) message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendMessageToMultipleRecipients()
            throws MailNotSentException,
            NotSupportedException, InterruptedException {
        final AWSMailSender mailSender = AWSMailSender.getInstance();

        final String text = "This is a test mail using AWS SES for multiple recipients";
        final String subject = "Multiple recipients test";

        final TextEmailMessage<?> message = TextEmailMessage.create(subject, text,
                mailSender);
        message.getTo().add("alberto@irurueta.com");
        message.getTo().add("webmaster@irurueta.com");

        //noinspection unchecked
        assertNotNull(mailSender.send((EmailMessage<Message>) message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendMessageWithNonEnglishCharacters()
            throws MailNotSentException,
            NotSupportedException, InterruptedException {

        final AWSMailSender mailSender = AWSMailSender.getInstance();

        final String text = "Atención. Este mensaje contiene carácteres españoles. AWS SES";
        final String subject = "Prueba de carácteres";

        final TextEmailMessage<?> message = TextEmailMessage.create(subject, text,
                mailSender);
        message.getTo().add("alberto@irurueta.com");

        //noinspection unchecked
        assertNotNull(mailSender.send((EmailMessage<Message>) message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendMessageWithAttachment() throws MailNotSentException,
            NotSupportedException, InterruptedException {
        final AWSMailSender mailSender = AWSMailSender.getInstance();

        final String text = "This is some test AWS mail with attachment";
        final String subject = "Test with attachment";

        final TextEmailMessageWithAttachments<?> message = TextEmailMessageWithAttachments.create(subject, text,
                mailSender);
        message.getTo().add("alberto@irurueta.com");

        final File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/email/rotate1.jpg");
        final EmailAttachment emailAttachment = new EmailAttachment(attachment,
                "image.jpg", "image/jpg");

        message.getAttachments().add(emailAttachment);

        //noinspection unchecked
        assertNotNull(mailSender.send((EmailMessage<Message>) message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendMessageToMultipleRecipientsWithAttachment()
            throws MailNotSentException,
            NotSupportedException, InterruptedException {
        final AWSMailSender mailSender = AWSMailSender.getInstance();

        final String text = "This is a test mail for multiple recipients with attachment. AWS SES";
        final String subject = "Multiple recipients test with attachment";

        final TextEmailMessageWithAttachments<?> message = TextEmailMessageWithAttachments.create(subject, text,
                mailSender);
        message.getTo().add("alberto@irurueta.com");
        message.getTo().add("webmaster@irurueta.com");

        final File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/email/rotate1.jpg");
        final EmailAttachment emailAttachment = new EmailAttachment(attachment,
                "image.jpg", "image/jpg");

        message.getAttachments().add(emailAttachment);

        //noinspection unchecked
        assertNotNull(mailSender.send((EmailMessage<Message>) message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendMessageWithNonEnglishCharactersAndAttachment()
            throws MailNotSentException, NotSupportedException, InterruptedException {

        final AWSMailSender mailSender = AWSMailSender.getInstance();

        final String text = "Atención. Este mensaje contiene carácteres españoles y tiene un archivo adjunto. AWS SES";
        final String subject = "Prueba de carácteres con archivo adjunto";

        final TextEmailMessageWithAttachments<?> message = TextEmailMessageWithAttachments.create(subject, text,
                mailSender);
        message.getTo().add("alberto@irurueta.com");

        final File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/email/rotate1.jpg");
        final EmailAttachment emailAttachment = new EmailAttachment(attachment,
                "image.jpg", "image/jpg");

        message.getAttachments().add(emailAttachment);

        //noinspection unchecked
        assertNotNull(mailSender.send((EmailMessage<Message>) message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendHtmlMessageWithEmailAndInlineAttachments()
            throws NotSupportedException, MailNotSentException, InterruptedException {
        final AWSMailSender mailSender = AWSMailSender.getInstance();

        final String html = "<h1>HTML email using AWS SES</h1>"
                + "<h2>Inline image with id</h2>"
                + "<img src=\"cid:image\"/>"
                + "<h2>Inline image without id</h2>"
                + "<img src=\"cid:{0}\"/>";
        final String alternativeText = "alternative text";
        final String subject = "HTML email";

        final HtmlEmailMessage<?> message = HtmlEmailMessage.create(subject, html,
                mailSender);
        message.setAlternativeText(alternativeText);
        message.getTo().add("alberto@irurueta.com");

        final File f = new File(
                "./src/test/java/com/irurueta/server/commons/email/rotate1.jpg");

        // add inline attachments

        // inline attachment with id "image"
        InlineAttachment inlineAttachment = new InlineAttachment(f, "image",
                "image/jpg");
        message.getInlineAttachments().add(inlineAttachment);
        // inline attachment without id
        inlineAttachment = new InlineAttachment(f, "image/jpg");
        message.getInlineAttachments().add(inlineAttachment);

        // add email attachment
        final EmailAttachment emailAttachment = new EmailAttachment(f,
                "image.jpg", "image/jpg");
        message.getEmailAttachments().add(emailAttachment);

        //noinspection unchecked
        assertNotNull(mailSender.send((EmailMessage<Message>) message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendDisabled() throws ConfigurationException, IOException,
            NotSupportedException, MailNotSentException {
        Properties props = new Properties();
        MailConfigurationFactory.getInstance().reconfigure(props);

        AWSMailSender.reset();
        final AWSMailSender mailSender = AWSMailSender.getInstance();

        final String text = "Disabled test";
        final String subject = null;

        final TextEmailMessage<?> message = TextEmailMessage.create(subject, text,
                mailSender);
        message.getTo().add("alberto@irurueta.com");
        message.getTo().add("webmaster@irurueta.com");

        //noinspection unchecked
        assertNull(mailSender.send((EmailMessage<Message>) message));


        // reset configuration
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
        final AWSMailSender mailSender = AWSMailSender.getInstance();

        final String text = "No subject test";
        final String subject = null;

        final TextEmailMessage<?> message = TextEmailMessage.create(subject, text,
                mailSender);
        message.getTo().add("alberto@irurueta.com");
        message.getBCC().add("webmaster@irurueta.com");
        message.getCC().add("alberto@irurueta.com");
    }
}