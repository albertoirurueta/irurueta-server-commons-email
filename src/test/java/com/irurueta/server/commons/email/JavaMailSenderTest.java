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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.*;

public class JavaMailSenderTest {

    private static final long SLEEP = 1000;

    public static final String PROPS_FILE = "./java-mail.properties";

    @BeforeClass
    public static void setUpClass() throws ConfigurationException, IOException {
        final Properties props = new Properties();
        props.load(new FileInputStream(PROPS_FILE));
        props.setProperty(MailConfigurationFactory.MAIL_PROVIDER_PROPERTY,
                EmailProvider.JAVA_MAIL.toString());
        MailConfigurationFactory.getInstance().reconfigure(props);
    }

    @Test
    public void testGetInstance() {
        final JavaMailSender mailSender =
                JavaMailSender.getInstance();
        assertNotNull(mailSender);

        final MailConfiguration cfg = MailConfigurationFactory.getInstance().
                getConfiguration();

        assertEquals(mailSender.getMailHost(), cfg.getMailHost());
        assertEquals(mailSender.getMailPort(), cfg.getMailPort());
        assertEquals(mailSender.getMailId(), cfg.getMailId());
        assertEquals(mailSender.getMailPassword(), cfg.getMailPassword());
        assertEquals(mailSender.getMailFromAddress(), cfg.getMailFromAddress());
        assertTrue(mailSender.isEnabled());
        assertEquals(mailSender.getProvider(), EmailProvider.JAVA_MAIL);

        final JavaMailSender mailSender2 = JavaMailSender.getInstance();
        assertSame(mailSender, mailSender2);

        // reset
        JavaMailSender.reset();

        final JavaMailSender mailSender3 = JavaMailSender.getInstance();

        assertEquals(mailSender3.getMailHost(), cfg.getMailHost());
        assertEquals(mailSender3.getMailPort(), cfg.getMailPort());
        assertEquals(mailSender3.getMailId(), cfg.getMailId());
        assertEquals(mailSender3.getMailPassword(), cfg.getMailPassword());
        assertEquals(mailSender3.getMailFromAddress(), cfg.getMailFromAddress());
        assertTrue(mailSender3.isEnabled());
        assertEquals(mailSender3.getProvider(), EmailProvider.JAVA_MAIL);

        assertNotSame(mailSender, mailSender3);
    }

    @Test
    public void testSendMessage() throws MailNotSentException, NotSupportedException, InterruptedException {
        final JavaMailSender mailSender =
                JavaMailSender.getInstance();

        final String text = "This is some test mail using Java Mail";
        final String subject = "Test";

        final TextEmailMessage message = TextEmailMessage.create(subject, text,
                mailSender);
        message.getTo().add("alberto@irurueta.com");

        assertNull(mailSender.send(message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendMessageToMultipleRecipients()
            throws MailNotSentException, NotSupportedException, InterruptedException {
        final JavaMailSender mailSender =
                JavaMailSender.getInstance();

        final String text = "This is a test mail using Java Mail for multiple recipients";
        final String subject = "Multiple recipients test";

        final TextEmailMessage message = TextEmailMessage.create(subject, text,
                mailSender);
        message.getTo().add("albertoa@irurueta.com");
        message.getTo().add("webmaster@irurueta.com");

        assertNull(mailSender.send(message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendMessageWithNonEnglishCharacters()
            throws MailNotSentException, NotSupportedException, InterruptedException {

        final JavaMailSender mailSender =
                JavaMailSender.getInstance();

        final String text = "Atención. Este mensaje contiene carácteres españoles. Java Mail";
        final String subject = "Prueba de carácteres";

        final TextEmailMessage message = TextEmailMessage.create(subject, text,
                mailSender);
        message.getTo().add("alberto@irurueta.com");

        assertNull(mailSender.send(message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendMessageWithAttachment() throws MailNotSentException,
            NotSupportedException, InterruptedException {
        final JavaMailSender mailSender =
                JavaMailSender.getInstance();

        final String text = "This is some test Java mail with attachment";
        final String subject = "Test with attachment";

        final TextEmailMessageWithAttachments message =
                TextEmailMessageWithAttachments.create(subject, text,
                        mailSender);
        message.getTo().add("alberto@irurueta.com");

        final File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/email/rotate1.jpg");
        final EmailAttachment emailAttachment = new EmailAttachment(attachment,
                "image.jpg", "image/jpg");

        message.getAttachments().add(emailAttachment);

        assertNull(mailSender.send(message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendMessageToMultipleRecipientsWithAttachment()
            throws MailNotSentException, NotSupportedException, InterruptedException {
        final JavaMailSender mailSender =
                JavaMailSender.getInstance();

        final String text = "This is a test Java mail for multiple recipients with attachment";
        final String subject = "Multiple recipients test with attachment";

        final TextEmailMessageWithAttachments message =
                TextEmailMessageWithAttachments.create(subject, text, mailSender);
        message.getTo().add("alberto@irurueta.com");
        message.getTo().add("webmaster@irurueta.com");

        final File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/email/rotate1.jpg");
        final EmailAttachment emailAttachment = new EmailAttachment(attachment,
                "image.jpg", "image/jpg");

        message.getAttachments().add(emailAttachment);

        assertNull(mailSender.send(message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendMessageWithNonEnglishCharactersAndAttachment()
            throws MailNotSentException, NotSupportedException, InterruptedException {

        final JavaMailSender mailSender = JavaMailSender.getInstance();

        final String text = "Atención. Este mensaje contiene carácteres españoles y tiene un archivo adjunto. " +
                "Java mail";
        final String subject = "Prueba de carácteres con archivo adjunto";

        final TextEmailMessageWithAttachments message =
                TextEmailMessageWithAttachments.create(subject, text,
                        mailSender);
        message.getTo().add("alberto@irurueta.com");

        final File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/email/rotate1.jpg");
        final EmailAttachment emailAttachment = new EmailAttachment(attachment,
                "image.jpg", "image/jpg");

        message.getAttachments().add(emailAttachment);

        assertNull(mailSender.send(message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendHtmlMessageWithEmailAndInlineAttachments()
            throws NotSupportedException, MailNotSentException, InterruptedException {
        final JavaMailSender mailSender = JavaMailSender.getInstance();

        final String html = "<h1>HTML email using Java Mail</h1>"
                + "<h2>Inline image with id</h2>"
                + "<img src=\"cid:image\"/>"
                + "<h2>Inline image without id</h2>"
                + "<img src=\"cid:{0}\"/>";
        final String alternativeText = "alternative text";
        final String subject = "HTML email";

        final HtmlEmailMessage message = HtmlEmailMessage.create(subject, html,
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

        assertNull(mailSender.send(message));

        Thread.sleep(SLEEP);
    }

    @Test
    public void testSendDisabled() throws ConfigurationException, IOException,
            NotSupportedException, MailNotSentException {
        Properties props = new Properties();
        MailConfigurationFactory.getInstance().reconfigure(props);

        JavaMailSender.reset();
        final JavaMailSender mailSender = JavaMailSender.getInstance();

        final String text = "Disabled test";

        final TextEmailMessage message = TextEmailMessage.create(null, text,
                mailSender);
        message.getTo().add("alberto@irurueta.com");
        message.getTo().add("webmaster@irurueta.com");

        assertNull(mailSender.send(message));


        // reset configuration
        props = new Properties();
        props.load(new FileInputStream(PROPS_FILE));
        props.setProperty(MailConfigurationFactory.MAIL_PROVIDER_PROPERTY,
                EmailProvider.JAVA_MAIL.toString());
        MailConfigurationFactory.getInstance().reconfigure(props);
        JavaMailSender.reset();
    }

    @Test
    public void testSendWithCcBccAndNoSubject() throws NotSupportedException {
        final JavaMailSender mailSender = JavaMailSender.getInstance();

        final String text = "No subject test";

        final TextEmailMessage message = TextEmailMessage.create(null, text,
                mailSender);
        message.getTo().add("alberto@irurueta.com");
        message.getBCC().add("webmaster@irurueta.com");
        message.getCC().add("alberto@irurueta.com");
    }

}
