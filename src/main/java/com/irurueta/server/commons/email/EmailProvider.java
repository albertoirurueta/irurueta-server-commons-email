/**
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
 * Enumerator indicating supported email providers to send emails.
 */
public enum EmailProvider {
    /**
     * JavaMail. This can be used in most Java server containers if a supported
     * SMTP email server is available.
     */
    JAVA_MAIL("java_mail"),
    
    /**
     * Apache Mail. Alternative implementation to JavaMail. Provides similar
     * features and also requires a supported SMTP email server.
     */
    APACHE_MAIL("apache_mail"),
    
    /**
     * Uses Amazon SES to send emails. Requires an Amazon AWS account and SES
     * to be setup, but no SMTP email server is required.
     */
    AWS_MAIL("aws_mail");
        
    /**
     * String representation of this enumerator.
     */
    private String mValue;
    
    /**
     * Constructor.
     * @param value string representation.
     */
    EmailProvider(String value) {
        mValue = value;
    }
    
    /**
     * Returns string representation.
     * @return string representation.
     */
    public String getValue() {
        return mValue;
    }
    
    /**
     * Factory method to create an enumerator value from its string 
     * representation.
     * @param value string representation.
     * @return enumerator value.
     */
    public static EmailProvider fromValue(String value) {
        if (value != null) {
            if (value.equalsIgnoreCase("java_mail")) {
                return JAVA_MAIL;
            }
            if (value.equalsIgnoreCase("apache_mail")) {
                return APACHE_MAIL;
            }
            if (value.equalsIgnoreCase("aws_mail")) {
                return AWS_MAIL;
            }
        }
        return null;
    }
}
