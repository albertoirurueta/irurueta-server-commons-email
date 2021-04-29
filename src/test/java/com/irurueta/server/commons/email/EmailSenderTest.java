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
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EmailSenderTest {

    @Test
    public void testGetInstance() throws ConfigurationException {
        EmailSender sender = EmailSender.getInstance();

        assertNotNull(sender);
        assertEquals(MailConfigurationFactory.getInstance().getConfiguration().getProvider(),
                sender.getProvider());

        sender = EmailSender.getInstance(EmailProvider.AWS_MAIL);

        assertNotNull(sender);
        assertEquals(EmailProvider.AWS_MAIL, sender.getProvider());

        sender = EmailSender.getInstance(EmailProvider.APACHE_MAIL);

        assertNotNull(sender);
        assertEquals(EmailProvider.APACHE_MAIL, sender.getProvider());

        sender = EmailSender.getInstance(EmailProvider.JAVA_MAIL);

        assertNotNull(sender);
        assertEquals(EmailProvider.JAVA_MAIL, sender.getProvider());
    }
}
