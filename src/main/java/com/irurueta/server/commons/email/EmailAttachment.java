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

import java.io.File;

/**
 * Contains information related to an email file attachment.
 */
public class EmailAttachment extends BaseAttachment {
    /**
     * File name shown to the receiver on their clients, this doesn't have to
     * be the real attachment file name on the server.
     */
    private String mName;
    
    /**
     * Constructor with file attachment.
     * @param attachment file being attached.
     */
    public EmailAttachment(final File attachment) {
        super(attachment);
        mName = attachment.getName();
    }
    
    /**
     * Constructor with file attachment and content type.
     * @param attachment file being attached.
     * @param contentType MIME type of the file being attached.
     */
    public EmailAttachment(final File attachment, final String contentType) {
        super(attachment, contentType);
        mName = attachment.getName();
    }
    
    /**
     * Constructor with file attachment, file name shown to the receiver and
     * content type.
     * @param attachment file being attached.
     * @param name file name shown to the receiver.
     * @param contentType MIME type of the file being attached.
     */
    public EmailAttachment(final File attachment, final String name, final String contentType) {
        super(attachment, contentType);
        mName = name;
    }
    
    /**
     * Returns file name shown to the receiver on their clients, this doesn't 
     * have to be the real attachment file name on the server.
     * @return file name shown to the receiver.
     */
    public String getName() {
        return mName;
    }
    
    /**
     * Sets file name shown to the receiver on their clients. This doesn't have
     * to be the real attachment file name on the server.
     * @param name file name shown to the receiver.
     */
    public void setName(final String name) {
        this.mName = name;
    }    
}
