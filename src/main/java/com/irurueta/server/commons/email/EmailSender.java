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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base class in charge of sending emails.
 * This class is a singleton and an instance must be obtained using getInstance
 * and using current mail configuration.
 */
public abstract class EmailSender {

    /**
     * Regular expression to validate emails.
     */
    private static final String EMAIL_REGEX = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

    /**
     * Factory method to obtain singleton instance using current mail
     * configuration.
     *
     * @return singleton instance.
     * @throws ConfigurationException if mail configuration fails.
     */
    public static EmailSender getInstance()
            throws ConfigurationException {

        return getInstance(MailConfigurationFactory.getInstance().
                configure().getProvider());
    }

    /**
     * Factory method to obtain appropriate subclass for a given email provider.
     *
     * @param provider provider to use.
     * @return an email sender controller singleton.
     */
    protected static EmailSender getInstance(final EmailProvider provider) {
        switch (provider) {
            case AWS_MAIL:
                return AWSMailSender.getInstance();
            case APACHE_MAIL:
                return ApacheMailSender.getInstance();
            case JAVA_MAIL:
            default:
                return JavaMailSender.getInstance();
        }
    }

    /**
     * Determines if provided string corresponds to an email address.
     * This method checks proper format of provided string to match the
     * structure of an email address (i.e. user@host).
     *
     * @param email string containing an email address to be checked.
     * @return true if provided string is a valid email address, false
     * otherwise.
     */
    public static boolean isValidEmailAddress(final String email) {
        /*
        Email format: A valid email address will have following format:
        [\\w\\.-]+: Begins with word characters, (may include periods and hypens).
        @: It must have a '@' symbol after initial characters.
        ([\\w\\.-]+\\.)+: '@' must follow by more alphanumeric characters (may 
        * include hypens or dots to allow subdomains).
        This part must also have a "." to separate domain and subdomain names.
        [A-Z]{2,4}$ : Must end with two to four alphabets.
        (This will allow domain names with 2, 3 and 4 characters e.g pa, com, net, wxyz)

        Examples: Following email addresses will pass validation
        abc@xyz.net; ab.c@tx.gov
        */

        // Initialize reg ex for email.
        // Make the comparison case-insensitive.
        final Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Abstract method to send an email.
     *
     * @param m email message to be sent.
     * @return id of message that has been sent.
     * @throws MailNotSentException if mail couldn't be sent.
     * @throws InterruptedException if thread is interrupted.
     */
    public abstract String send(final EmailMessage m) throws MailNotSentException, InterruptedException;

    /**
     * Returns provider used by this email sender.
     *
     * @return email provider.
     */
    public abstract EmailProvider getProvider();
}
