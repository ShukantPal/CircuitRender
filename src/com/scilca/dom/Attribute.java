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
import org.w3c.dom.Element;
import org.w3c.dom.TypeInfo;

/**
 *
 * @author Sukant Pal
 */
public class Attribute extends Node implements org.w3c.dom.Attr{
    
    protected boolean isId;
    
    protected Attribute(String nodeName, String nodeValue, Document ownerDocument) {
        super(nodeName, Node.ATTRIBUTE_NODE, ownerDocument);
        this.nodeValue = nodeValue;
        nodeAttributes = null;
        childNodes = null;
    }
    
    protected Attribute(String nodeName, Document ownerDocument, String nodeValue, boolean isId){
        this(nodeName, nodeValue, ownerDocument);
        nodeAttributes = null;
        childNodes = null;
    }

    @Override
    public String getName() {
        return nodeName;
    }
    
    @Override
    public String getNamespaceURI(){
        if(getPrefix() != null
                && getPrefix().equals("xmlns"))
            return getNodeValue();
        else if(getNodeName().equals("xmlns"))
            return getNodeValue();
        else return null;
    }
    
    @Override
    public boolean getSpecified() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Node cloneNode(boolean deep) {
        Attribute attrCloned = (Attribute) super.cloneNode(deep);
        attrCloned.nodeName = this.nodeName;
        attrCloned.setNodeValue(this.nodeValue);
        return attrCloned;
    }
    
    @Override
    public String getValue() {
        return nodeValue;
    }

    @Override
    public void setValue(String value) throws DOMException {
        if(value == null)
            throw new DOMException(DOMException.DOMSTRING_SIZE_ERR, "DOMSTRING_NULL_ERR");
        nodeValue = value;
    }

    @Override
    public Element getOwnerElement() {
        return getElementParent();
    }

    @Override
    public TypeInfo getSchemaTypeInfo() {
        return null;
    }

    @Override
    public boolean isId() {
        return isId;
    }
    
    protected void setId(boolean isId){
        this.isId = isId;
    }
    
    @Override
    public org.w3c.dom.Node getPreviousSibling(){
        if(cacheIndex != -1)
            return parentNode.getAttributes().item(cacheIndex - 1);
        for(int siblingIndex = 0; siblingIndex < parentNode.getAttributes().getLength(); siblingIndex++)
            if(parentNode.getAttributes().item(siblingIndex).isSameNode(this)){
                cacheIndex = siblingIndex; // Cache the index for later use
                return parentNode.getAttributes().item(siblingIndex - 1);
            }
        return null;
    }
    
    @Override
    public org.w3c.dom.Node getNextSibling(){
        if(cacheIndex != -1)
            return parentNode.getAttributes().item(cacheIndex - 1);
        for(int siblingIndex = 0; siblingIndex < parentNode.getAttributes().getLength(); siblingIndex++)
            if(parentNode.getAttributes().item(siblingIndex).isSameNode(this)){
                cacheIndex = siblingIndex; // Cache the index for later use
                return parentNode.getAttributes().item(siblingIndex + 1);   
            }
        return null;
    }
    
    @Override
    public String toString(){
        return "Attribute: " + getNodeName() + "=\"" + getNodeValue() + "\"";
    }
    
}
