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
import org.w3c.dom.Node;
import java.util.HashMap;
import org.w3c.dom.Attr;
/**
 *
 * @author Sukant Pal
 */
public final class AttributesMap implements NodeCircuit{

    HashMap<String, org.w3c.dom.Attr> attributesMap = new HashMap<>();
    org.w3c.dom.Document ownerDocument;
    org.w3c.dom.Node parentNode;
    
    public AttributesMap(org.w3c.dom.Document ownerDocument, org.w3c.dom.Node parentNode){
        this.ownerDocument = ownerDocument;
        this.parentNode = parentNode;
    }
    
    @Override
    public Node getNamedItem(String name) {
        return attributesMap.get(name);
    }

    @Override
    public Node setNamedItem(Node arg) throws DOMException {
        if(arg.getNodeType() != Node.ATTRIBUTE_NODE)
            throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "HIERARCHY_REQUEST_ERR");
        else if(arg.getOwnerDocument() != ownerDocument)
            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "WRONG_DOCUMENT_ERR");
        Node oldValue = attributesMap.get(arg.getNodeName());
        attributesMap.put(arg.getNodeName(), (Attr) arg);
        return oldValue;
    }

    @Override
    public Node removeNamedItem(String name) throws DOMException {
        return attributesMap.remove(name);
    }

    @Override
    public Node item(int index) {
        return attributesMap.get((String) attributesMap.keySet().toArray()[index]);
    }

    @Override
    public int getLength() {
        return attributesMap.size();
    }

    @Override
    public Node getNamedItemNS(String namespaceURI, String localName) throws DOMException {
        Object[] keys = attributesMap.keySet().toArray();
        for(int attrIndex = 0; attrIndex < attributesMap.size(); attrIndex++){
            Attr attr = (Attr) attributesMap.get((String) keys[attrIndex]);
            if(attr.getNamespaceURI() != null 
                    && attr.getNamespaceURI().equals(namespaceURI) 
                    && attr.getLocalName().equals(localName))
                return attr;
        }
        return null;
    }

    @Override
    public Node setNamedItemNS(Node arg) throws DOMException {
        if(arg.getNodeType() != Node.ATTRIBUTE_NODE)
            throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "HIERARCHY_REQUEST_ERR");
        Node oldValue = getNamedItemNS(arg.getNamespaceURI(), arg.getLocalName());
        attributesMap.put(arg.getNodeName(), (Attr) arg);
        return oldValue;
    }

    @Override
    public Node removeNamedItemNS(String namespaceURI, String localName) throws DOMException {
        Object[] keys = attributesMap.keySet().toArray();
        for(int attrIndex = 0; attrIndex < attributesMap.size(); attrIndex++){
            Attr attr = attributesMap.get((String) keys[attrIndex]);
            if(attr.getNamespaceURI() != null && attr.getNamespaceURI().equals(namespaceURI)
                    && attr.getLocalName().equals(localName)){
                return attributesMap.remove((String) keys[attrIndex]);
            }
        }
        return null;
    }
    
    /**
     *
     * @return
     */
    @Override
    public AttributesMap cloneMap(){
        AttributesMap clone = new AttributesMap(null, null);
        clone.attributesMap = (HashMap) this.attributesMap.clone();
        return clone;
    }
    
}
