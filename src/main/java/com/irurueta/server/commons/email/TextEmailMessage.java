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

/**
 * Base class for text only email messages.
 *
 * @param <E> internal representation of email using by internal client.
 */
public abstract class TextEmailMessage<E> extends EmailMessage<E> {

    /**
     * Text content of email.
     */
    private String mText;

    /**
     * Constructor.
     */
    protected TextEmailMessage() {
        super();
        mText = "";
    }

    /**
     * Constructor with email subject.
     *
     * @param subject subject to be set.
     */
    protected TextEmailMessage(final String subject) {
        super(subject);
        mText = "";
    }

    /**
     * Constructor with email subject and textual content.
     *
     * @param subject subject to be set.
     * @param text    textual content.
     */
    protected TextEmailMessage(final String subject, final String text) {
        super(subject);
        mText = text;
    }

    /**
     * Returns email text content.
     *
     * @return email text content.
     */
    public String getText() {
        return mText;
    }

    /**
     * Sets email text content.
     *
     * @param text email text content.
     */
    public void setText(final String text) {
        mText = text;
    }

    /**
     * Creates a textual email message using provided email sender.
     *
     * @param sender class in charge of sending this email.
     * @return a textual email message.
     */
    public static TextEmailMessage<?> create(final EmailSender<?> sender) {
        return create(sender.getProvider());
    }

    /**
     * Creates a textual email message using given email provider.
     *
     * @param provider email provider.
     * @return a textual email message.
     */
    protected static TextEmailMessage<?> create(final EmailProvider provider) {
        return create("", provider);
    }

    /**
     * Creates a textual email message having provided subject and using
     * provided email sender.
     *
     * @param subject email subject to be set.
     * @param sender  class in charge of sending this email.
     * @return a textual email message.
     */
    public static TextEmailMessage<?> create(final String subject,
                                             final EmailSender<?> sender) {
        return create(subject, sender.getProvider());
    }

    /**
     * Creates a textual email message having provided subject and given email
     * provider.
     *
     * @param subject  email subject to be set.
     * @param provider email provider.
     * @return a textual email message.
     */
    protected static TextEmailMessage<?> create(final String subject,
                                                final EmailProvider provider) {
        return create(subject, "", provider);
    }

    /**
     * Creates a textual email message having provided subject, text content and
     * using provided email sender.
     *
     * @param subject email subject to be set.
     * @param text    text content of email.
     * @param sender  class in charge of sending this email.
     * @return a textual email message.
     */
    public static TextEmailMessage<?> create(final String subject, final String text,
                                             final EmailSender<?> sender) {
        return create(subject, text, sender.getProvider());
    }

    /**
     * Creates a textual email message having provided subject, text content and
     * using given email provider.
     *
     * @param subject  email subject to be set.
     * @param text     text content of email.
     * @param provider email provider.
     * @return a textual email message.
     */
    protected static TextEmailMessage<?> create(final String subject, final String text,
                                                final EmailProvider provider) {

        switch (provider) {
            case AWS_MAIL:
                return new AWSTextEmailMessage(subject, text);
            case APACHE_MAIL:
                return new ApacheMailTextEmailMessage(subject, text);
            case JAVA_MAIL:
            default:
                return new JavaMailTextEmailMessage(subject, text);
        }
    }
}
