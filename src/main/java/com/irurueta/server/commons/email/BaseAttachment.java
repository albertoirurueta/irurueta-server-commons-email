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
 * Base class to be used for attachments.
 */
public abstract class BaseAttachment {
    /**
     * File being attached.
     */
    private File mAttachment;

    /**
     * MIME type of the file being attached.
     */
    private String mContentType;

    /**
     * Constructor with file attachment.
     *
     * @param attachment file being attached.
     */
    protected BaseAttachment(final File attachment) {
        mAttachment = attachment;
        mContentType = null;
    }

    /**
     * Constructor with file attachment and content type.
     *
     * @param attachment  file being attached.
     * @param contentType MIME type of the file being attached.
     */
    protected BaseAttachment(final File attachment, final String contentType) {
        mAttachment = attachment;
        mContentType = contentType;
    }

    /**
     * Returns file being attached.
     *
     * @return file being attached.
     */
    public File getAttachment() {
        return mAttachment;
    }

    /**
     * Sets file being attached.
     *
     * @param attachment file being attached.
     */
    public void setAttachment(final File attachment) {
        mAttachment = attachment;
    }

    /**
     * Returns MIME type of the file being attached.
     *
     * @return MIME type of the file being attached.
     */
    public String getContentType() {
        return mContentType;
    }

    /**
     * Sets MIME type of the file being attached.
     *
     * @param contentType MIME type of the file being attached.
     */
    public void setContentType(final String contentType) {
        mContentType = contentType;
    }
}
