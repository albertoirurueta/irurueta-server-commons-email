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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EmailAttachmentTest {

    @Test
    public void testConstructor() {
        final File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/mail/rotate1.jpg");

        // constructor with file
        EmailAttachment emailAttachment = new EmailAttachment(attachment);
        assertEquals(emailAttachment.getAttachment(), attachment);
        assertEquals(emailAttachment.getName(), "rotate1.jpg");
        assertNull(emailAttachment.getContentType());

        // constructor with file and content type
        emailAttachment = new EmailAttachment(attachment, "image/jpg");
        assertEquals(emailAttachment.getAttachment(), attachment);
        assertEquals(emailAttachment.getName(), "rotate1.jpg");
        assertEquals(emailAttachment.getContentType(), "image/jpg");

        // constructor with file, file name and content type
        emailAttachment = new EmailAttachment(attachment, "image.jpg",
                "image/jpg");
        assertEquals(emailAttachment.getAttachment(), attachment);
        assertEquals(emailAttachment.getName(), "image.jpg");
        assertEquals(emailAttachment.getContentType(), "image/jpg");
    }

    @Test
    public void testGetSetAttachment() {
        final File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/mail/rotate1.jpg");
        final File attachment2 = new File(
                "./src/test/java/com/irurueta/server/commons/mail/rotate2.jpg");


        final EmailAttachment emailAttachment = new EmailAttachment(attachment);

        assertEquals(emailAttachment.getAttachment(), attachment);

        // set new attachment
        emailAttachment.setAttachment(attachment2);

        // check correctness
        assertEquals(emailAttachment.getAttachment(), attachment2);
    }

    @Test
    public void testGetSetName() {
        final File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/mail/rotate1.jpg");

        final EmailAttachment emailAttachment = new EmailAttachment(attachment);

        assertEquals(emailAttachment.getName(), "rotate1.jpg");

        // set new name
        emailAttachment.setName("image.jpg");

        // check correctness
        assertEquals(emailAttachment.getName(), "image.jpg");
    }

    @Test
    public void testGetSetContentType() {
        final File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/mail/rotate1.jpg");

        final EmailAttachment emailAttachment = new EmailAttachment(attachment);

        assertNull(emailAttachment.getContentType());

        // set content type
        emailAttachment.setContentType("image/jpg");

        // check correctness
        assertEquals(emailAttachment.getContentType(), "image/jpg");
    }
}
