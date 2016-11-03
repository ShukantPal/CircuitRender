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

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;

/**
 *
 * @author Sukant Pal
 */
public class Element extends Node implements org.w3c.dom.Element{
    static final long serialVersionUID = 4521345896052233378L;
    
    TypeInfo schemaTypeInfo;
    Attr idAttribute = null;
    
    protected Element(String nodeName, Document ownerDocument){
        super(nodeName, Node.ELEMENT_NODE, ownerDocument);
        nodeAttributes = new AttributesMap(ownerDocument, this);
        childNodes = new com.scilca.dom.NodeList();
    }
    
    void setParentNode(Node n){
        parentNode = n;
    }
    
    @Override
    public String getTagName() {
        return nodeName;
    }
    
    @Override
    public NamedNodeMap getAttributes(){
        return nodeAttributes; 
    }
    
    @Override
    public String getAttribute(String name) {
        return getAttributes().getNamedItem(name).getNodeValue();
    }

    @Override
    public void setAttribute(String name, String value) throws DOMException {
        Attribute setter = new Attribute(name, value, ownerDocument);
        setter.parentNode = this;
        getAttributes().setNamedItem(setter);
    }

    @Override
    public void removeAttribute(String name) throws DOMException {
        Attr removedAttr = (Attr) getAttributes().removeNamedItem(name);
        if(removedAttr == idAttribute)
            idAttribute = null;
    }

    @Override
    public Attr getAttributeNode(String name) {
        return (Attr) getAttributes().getNamedItem(name);
    }

    @Override
    public Attr setAttributeNode(Attr newAttr) throws DOMException {
        Attr oldValue = (Attr) getAttributeNode(newAttr.getNodeName());
        getAttributes().setNamedItem(newAttr);
        if(idAttribute == oldValue)
            idAttribute = getAttributeNode(newAttr.getNodeName());
        return oldValue;
    }

    @Override
    public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
         Attr oldValue = (Attr) getAttributeNode(oldAttr.getNodeName());
         getAttributes().removeNamedItem(oldAttr.getNodeName());
         if(oldValue == idAttribute)
             idAttribute = null;
         return oldValue;
    }

    @Override
    public Node cloneNode(boolean deep){
        Node clone = (Node) super.cloneNode(deep);
        clone.nodeAttributes = ((AttributesMap) this.nodeAttributes).cloneMap();
        if(deep)
            clone.childNodes = this.childNodes.cloneList();
        return clone;
    }
    
    @Override
    public NodeList getElementsByTagName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
        return nodeAttributes.getNamedItemNS(namespaceURI, localName).getNodeValue();
    }

    @Override
    public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws DOMException {
        Attribute setter = new Attribute(qualifiedName, null, ownerDocument);
        setter.parentNode = this;
        nodeAttributes.setNamedItemNS(setter);
    }

    @Override
    public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {
        nodeAttributes.removeNamedItemNS(namespaceURI, localName);
    }

    @Override
    public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException {
        return (Attr) nodeAttributes.getNamedItemNS(namespaceURI, localName);
    }

    @Override
    public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
        Attr oldValue = getAttributeNodeNS(newAttr.getNamespaceURI(), newAttr.getLocalName());
        if(newAttr.getOwnerDocument() != getOwnerDocument())
            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "WRONG_DOCUMENT_ERR");
        else if(newAttr.getParentNode() != null)
            throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "HIERARCH_REQUEST_ERR");
        
        Attribute setter;
        try{
            setter = (Attribute) newAttr;
        } catch(ClassCastException e){
            throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "NO_MODIFICATION_ALLOWED_ERR");
        }
        
        setter.parentNode = this;
        
        nodeAttributes.setNamedItemNS(setter);
        return oldValue;
    }

    @Override
    public NodeList getElementsByTagNameNS(String namespaceURI, String localName) throws DOMException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasAttribute(String name) {
        return this.getAttribute(name) != null;
    }

    @Override
    public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException {
        return this.getAttributeNS(namespaceURI, localName) != null;
    }

    @Override
    public TypeInfo getSchemaTypeInfo() {
        return schemaTypeInfo;
    }

    
    @Override
    public void setIdAttribute(String name, boolean isId) throws DOMException{
        Attribute setId = ((Attribute) getAttributeNode(name));
        setId.isId = true; 
        if(this.idAttribute != null)
            try {
                ((Attribute) idAttribute).setId(false);
            } catch(ClassCastException e){
                throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "NOT_SUPPORTED_ERR");
            }
        idAttribute = setId;
    }

    @Override
    public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
        Attribute setId = ((Attribute) getAttributes().getNamedItemNS(namespaceURI, localName));
        setId.isId = true;
        if(this.idAttribute != null)
            try{
                ((Attribute) idAttribute).setId(false);
            } catch(ClassCastException e){
                throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "NOT_SUPPORTED_ERR");
            }
        idAttribute = setId;
    }

    @Override
    public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
        if(!((AttributesMap) getAttributes()).attributesMap.containsValue(idAttr)){
            setAttributeNode(idAttr);
            if(idAttribute != null)
                ((Attribute) idAttribute).isId = false;
            idAttribute = idAttr;
        } else {
            if(idAttribute != null)
                ((Attribute) idAttribute).isId = false;
            idAttribute = idAttr;
        }
    }
    
    @Override
    public void normalize(){
        if(hasChildNodes()){
            // Only child nodes are normalized because 
            // attributes don't have children.
            for(int childIndex = 0; childIndex < childNodes.getLength(); childIndex++){
                org.w3c.dom.Node childNode = childNodes.item(childIndex);
                org.w3c.dom.Node nextChild = childIndex != childNodes.getLength() - 1
                        ? childNodes.item(childIndex + 1) : null;
                switch(childNode.getNodeType()){
                    case Node.TEXT_NODE:
                        if(childNode.getNodeValue().equals(""))
                            removeChild(childNode);
                        else if(nextChild != null && 
                                nextChild.getNodeType() == Node.TEXT_NODE){
                            childNode.setNodeValue(childNode.getNodeValue() + nextChild.getNodeValue());
                            removeChild(nextChild);
                        }
                        break;
                    case Node.ELEMENT_NODE:
                        childNode.normalize();
                        break;
                }
            }
        }
    }
    
    // Is the node able to become my child node???
    private void ableChildNode(org.w3c.dom.Node newChild) throws DOMException{
        if(newChild.getNodeType() != Node.ELEMENT_NODE
                && newChild.getNodeType() != Node.TEXT_NODE
                && newChild.getNodeType() != Node.COMMENT_NODE){
            throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "HIERARCHY_REQUEST_ERR");
        } else if(newChild.getParentNode() != null){
            throw new DOMException(DOMException.INVALID_STATE_ERR, "INVALID_STATE_ERR");
        }
    }
    
    @Override
    public org.w3c.dom.Node insertBefore(org.w3c.dom.Node newChild, org.w3c.dom.Node refChild) throws DOMException{
        ableChildNode(newChild);
        
        if(refChild != null)
            for(int childIndex = 0; childIndex < childNodes.getLength(); childIndex++){
                if(childNodes.item(childIndex) == refChild){
                    childNodes.add(newChild, childIndex);
                    break;
                }
            }
        else childNodes.add(newChild);
        return null;
    }
    
    @Override
    public org.w3c.dom.Node replaceChild(org.w3c.dom.Node newChild, org.w3c.dom.Node oldChild) throws DOMException{
        ableChildNode(newChild);
        
        if(childNodes.nodeArray.contains(oldChild)){
            for(int childIndex = 0; childIndex < childNodes.getLength(); childIndex++){
                if(childNodes.item(childIndex) == oldChild){
                    childNodes.nodeArray.set(childIndex, oldChild);
                    break;
                }
            }
        } else throw new DOMException(DOMException.NOT_FOUND_ERR, "NOT_FOUND_ERR");
        
        return newChild;
    }
    
    @Override
    public org.w3c.dom.Node removeChild(org.w3c.dom.Node oldChild) throws DOMException{
        if(!childNodes.nodeArray.contains(oldChild))
            throw new DOMException(DOMException.NOT_FOUND_ERR, "NOT_FOUND_ERR");
        
        childNodes.nodeArray.remove(oldChild);
        return oldChild;
    }

    public Attr getIdAttr(){
        return idAttribute;
    }
    
    @Override
    public String toString(){
        return "Element: " + getNodeName();
    }
    
}
