/*
 * Copyright (C) 2021 Alberto Irurueta Carro (alberto@irurueta.com)
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

/**
 * Base class for Apache and JavaMail senders.
 */
public abstract class CommonEmailSender extends EmailSender {
    /**
     * Minimum allowed port value to connect to SMTP server.
     */
    private static final int MIN_PORT_VALUE = 0;

    /**
     * Maximum allowed port value to connect to SMTP server.
     */
    private static final int MAX_PORT_VALUE = 65535;

    /**
     * SMTP server host to connect to.
     */
    protected String mMailHost;

    /**
     * SMTP server port to connect to.
     */
    protected int mMailPort;

    /**
     * User id to log into SMTP server. If none is provided no authentication
     * will be used.
     */
    protected String mMailId;

    /**
     * User password to log into SMTP server. If none is provided no
     * authentication will be used.
     */
    protected String mMailPassword;

    /**
     * Address to send emails from.
     */
    protected String mMailFromAddress;

    /**
     * Indicates if mail sending is enabled.
     */
    protected boolean mEnabled;

    /**
     * Constructors.
     * Loads mail configuration, and if it fails for some reason, mail sending
     * becomes disabled.
     */
    protected CommonEmailSender() {
        try {
            final MailConfiguration cfg = MailConfigurationFactory.getInstance().
                    configure();
            mMailHost = cfg.getMailHost();
            mMailPort = cfg.getMailPort();
            mMailId = cfg.getMailId();
            mMailPassword = cfg.getMailPassword();
            mMailFromAddress = cfg.getMailFromAddress();

            mEnabled = isValidConfiguration(mMailHost, mMailPort,
                    mMailFromAddress) && cfg.isMailSendingEnabled();
        } catch (final ConfigurationException e) {
            mEnabled = false;
        }
    }

    /**
     * Indicates if provided configuration is valid by checking that SMTP server
     * and mail from address are not null, and server port is valid.
     *
     * @param mailHost        SMTP server host.
     * @param mailPort        SMTP server port.
     * @param mailFromAddress email address to send emails from.
     * @return true if configuration is valid, false otherwise.
     */
    private boolean isValidConfiguration(final String mailHost, final int mailPort,
                                         final String mailFromAddress) {
        return mailHost != null && mailPort >= MIN_PORT_VALUE &&
                mailPort <= MAX_PORT_VALUE && mailFromAddress != null &&
                isValidEmailAddress(mailFromAddress);
    }
}
