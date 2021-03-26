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

public class InlineAttachmentTest {

    @Test
    public void testConstructor() {
        final File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/mail/rotate1.jpg");

        // constructor with file
        InlineAttachment inlineAttachment = new InlineAttachment(attachment);
        assertEquals(inlineAttachment.getAttachment(), attachment);
        assertNull(inlineAttachment.getContentId());
        assertNull(inlineAttachment.getContentType());

        // constructor with file and content type
        inlineAttachment = new InlineAttachment(attachment, "image/jpg");
        assertEquals(inlineAttachment.getAttachment(), attachment);
        assertNull(inlineAttachment.getContentId());
        assertEquals(inlineAttachment.getContentType(), "image/jpg");

        // constructor with file, part id and content type
        inlineAttachment = new InlineAttachment(attachment, "part1",
                "image/jpg");
        assertEquals(inlineAttachment.getAttachment(), attachment);
        assertEquals(inlineAttachment.getContentId(), "part1");
        assertEquals(inlineAttachment.getContentType(), "image/jpg");
    }

    @Test
    public void testGetSetAttachment() {
        final File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/mail/rotate1.jpg");
        final File attachment2 = new File(
                "./src/test/java/com/irurueta/server/commons/mail/rotate2.jpg");


        final InlineAttachment inlineAttachment = new InlineAttachment(attachment);

        assertEquals(inlineAttachment.getAttachment(), attachment);

        // set new attachment
        inlineAttachment.setAttachment(attachment2);

        // check correctness
        assertEquals(inlineAttachment.getAttachment(), attachment2);
    }

    @Test
    public void testGetSetContentId() {
        final File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/mail/rotate1.jpg");

        final InlineAttachment inlineAttachment = new InlineAttachment(attachment);

        assertNull(inlineAttachment.getContentId());

        // set new value
        inlineAttachment.setContentId("part1");

        // check correctness
        assertEquals(inlineAttachment.getContentId(), "part1");
    }

    @Test
    public void testGetSetContentType() {
        final File attachment = new File(
                "./src/test/java/com/irurueta/server/commons/mail/rotate1.jpg");

        final InlineAttachment inlineAttachment = new InlineAttachment(attachment);

        assertNull(inlineAttachment.getContentType());

        // set content type
        inlineAttachment.setContentType("image/jpg");

        // check correctness
        assertEquals(inlineAttachment.getContentType(), "image/jpg");
    }
}