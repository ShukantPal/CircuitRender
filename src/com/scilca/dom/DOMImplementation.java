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
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

/**
 *
 * @author Sukant Pal
 */
public class DOMImplementation implements org.w3c.dom.DOMImplementation{

    com.scilca.dom.Document ownerDocument;
    
    public DOMImplementation(com.scilca.dom.Document ownerDocument) {
        this.ownerDocument = ownerDocument;
    }

    @Override
    public boolean hasFeature(String feature, String version) {
        return false;
    }

    @Override
    public DocumentType createDocumentType(String qualifiedName, String publicId, String systemId) throws DOMException {
        return new com.scilca.dom.DocumentType(ownerDocument, qualifiedName, publicId, systemId);
    }

    @Override
    public Document createDocument(String namespaceURI, String qualifiedName, DocumentType doctype) throws DOMException {
        return new com.scilca.dom.Document(qualifiedName, doctype, namespaceURI, this);
    }

    @Override
    public Object getFeature(String feature, String version) {
        return null;
    }
    
}
