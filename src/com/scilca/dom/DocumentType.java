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

import org.w3c.dom.NamedNodeMap;

/**
 *
 * @author Sukant Pal
 */
public class DocumentType extends Node implements org.w3c.dom.DocumentType{

    String publicId;
    String sysId;
    
    String internalSubset;
    
    EntityMap entityMap;
    
    protected DocumentType(Document ownerDocument, String nodeName, String publicId, String sysId) {
        super(nodeName, Node.DOCUMENT_TYPE_NODE, ownerDocument);
        this.publicId = publicId;
        this.sysId = sysId;
        entityMap = new EntityMap();
    }

    @Override
    public String getName() {
        return nodeName;
    }

    @Override
    public NamedNodeMap getEntities() {
        return entityMap;
    }

    @Override
    public NamedNodeMap getNotations() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPublicId() {
        return publicId;
    }

    @Override
    public String getSystemId() {
        return sysId;
    }

    @Override
    public String getInternalSubset() {
        return (internalSubset == null) ? "" : internalSubset;
    }
    
}
