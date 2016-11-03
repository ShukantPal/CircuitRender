/*
 * Copyright (C) 2016 Sukant Pal
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.scilca.dom;

import org.w3c.dom.DOMException;

/**
 *
 * @author Sukant Pal
 */
public class Entity extends Node implements org.w3c.dom.Entity{

    String publicId;
    String systemId;
    
    String notationName;
    
    public Entity(String nodeName, Document ownerDocument,
            String notationName, String publicId, String systemId) throws DOMException{
        super(nodeName, Node.ENTITY_NODE, ownerDocument);
        if(publicId == null || systemId == null || notationName == null)
            throw new DOMException(DOMException.INVALID_STATE_ERR, "INVALID_STATE(NULL)_ERR");
        this.publicId = publicId;
        this.systemId = systemId;
        this.notationName = notationName;
    }
    
    @Override
    public String getPublicId() {
        return publicId;
    }

    @Override
    public String getSystemId() {
        return systemId;
    }

    @Override
    public String getNotationName() {
        return notationName;
    }

    @Override
    public String getInputEncoding() {
        return getOwnerDocument().getInputEncoding();
    }

    @Override
    public String getXmlEncoding() {
        return getOwnerDocument().getXmlEncoding();
    }

    @Override
    public String getXmlVersion() {
        return getOwnerDocument().getXmlVersion();
    }
    
}
