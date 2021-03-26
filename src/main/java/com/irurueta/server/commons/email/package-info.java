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

/**
 * This package contains classes related to sending emails.
 * The main classes of this package are:
 * - EmailSender: to send emails
 * - EmailMessage: which contains the email data: text, subject,
 * attached files, etc.
 * Different implementations of these classes exist providing different
 * behaviors (i.e. text emails only, emails with attachments, HTML emails etc).
 * Currently JavaMail, Amazon SES and Apache Mail are supported but further
 * implementations can be added to support other providers.
 * MailConfigurationFactory contains all the properties to be used during
 * configuration so that the system knows how to send emails (i.e. server,
 * credentials, etc).
 */
package com.irurueta.server.commons.email;
