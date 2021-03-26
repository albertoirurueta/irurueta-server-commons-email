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

import com.irurueta.server.commons.configuration.Configuration;

/**
 * Interface defining parameters to configure email sending.
 */
public interface MailConfiguration extends Configuration {
    /**
     * SMTP server host to connect to send emails.
     *
     * @return SMTP server host to connect to send emails.
     */
    String getMailHost();

    /**
     * SMTP server port to connect to send emails.
     *
     * @return SMTP server port to connect to send emails.
     */
    int getMailPort();

    /**
     * User id to log into SMTP server to send emails.
     * This value is optional and can be left as null if server does not
     * require credentials.
     *
     * @return user id to log into SMTP server to send emails.
     */
    String getMailId();

    /**
     * User password to log into SMTP server to send emails.
     * This value is optional and can be left as null if server does not require
     * credentials.
     *
     * @return User password to log into SMTP server to send emails.
     */
    String getMailPassword();

    /**
     * Email address to send emails from.
     *
     * @return email address to send emails from.
     */
    String getMailFromAddress();

    /**
     * Indicates if email sending is enabled or not.
     *
     * @return true if email sending is enabled, false otherwise.
     */
    boolean isMailSendingEnabled();

    // AWS Mail Configuration

    /**
     * Amazon AWS access key to use if Amazon SES is used as a provider.
     *
     * @return AWS access key to use Amazon SES.
     */
    String getAWSMailAccessKey();

    /**
     * Amazon AWS secret key to use if Amazon SES is used as a provider.
     *
     * @return AWS secret key to use Amazon SES.
     */
    String getAWSMailSecretKey();

    /**
     * Returns structure containing AWS credentials to use Amazon SES.
     *
     * @return AWS credentials.
     */
    AWSEmailSenderCredentials getAWSMailCredentials();

    /**
     * Milliseconds to wait before checking sending quota to avoid Amazon SES
     * throttling.
     *
     * @return milliseconds to wait before checking sending quota to avoid .
     * Amazon SES throttling.
     */
    long getAWSMailCheckQuotaAfterMillis();

    /**
     * Email provider to use to send emails.
     *
     * @return an email provider.
     */
    EmailProvider getProvider();
}
