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

import java.io.File;

/**
 * Contains information related to an inline email attachment.
 * Inline attachments can be used in HTML mails to include additional resources
 * such as images or css files which will be embedded in the same email.
 * This can be useful in situations where some clients block loading external
 * URLs in HTML mails to avoid malicious activities such as determining if an
 * email address exists or if an email was actually read.
 */
public class InlineAttachment extends BaseAttachment {
    
    /**
     * Id to be assigned to part created to send the inline attachment.
     */
    private String mContentId;
        
    /**
     * Constructor with file attachment.
     * @param attachment file being attached.
     */
    public InlineAttachment(File attachment) {
        super(attachment);
        mContentId = null;
    }
    
    /**
     * Constructor with file attachment and content type.
     * @param attachment file being attached.
     * @param contentType MIME type of the file being attached.
     */
    public InlineAttachment(File attachment, String contentType) {
        super(attachment, contentType);
        mContentId = null;
    }
    
    /**
     * Constructor with file attachment, file name shown to the receiver and
     * content type.
     * @param attachment file being attached.
     * @param contentId id to be assigned to inline part containing file data.
     * @param contentType MIME type of the file being attached.
     */
    public InlineAttachment(File attachment, String contentId, 
            String contentType) {
        super(attachment, contentType);
        mContentId = contentId;
    }
        
    /**
     * Returns Id to be assigned to part created to send the inline attachment.
     * @return id to be assigned to part created to send the inline attachment.
     */
    public String getContentId() {
        return mContentId;
    }
    
    /**
     * Sets id to be assigned to part created to send the inline attachment.
     * @param contentId id to be assigned to part created to send the inline
     * attachment.
     */
    public void setContentId(String contentId) {
        mContentId = contentId;
    }        
}
