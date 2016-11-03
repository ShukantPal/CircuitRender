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

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.*;

/**
 * 
 * @author Sukant Pal
 */
public abstract class Node extends Object implements org.w3c.dom.Node, Cloneable{

    String nodeName;
    String nodeValue;
    
    Document ownerDocument;
    short nodeType;
    
    Node parentNode;
    NodeList childNodes;
    
    int cacheIndex = -1;
    
    NamedNodeMap nodeAttributes;
    
    boolean readOnly = false;
    
    public static final short TREE_POSITION_PRECEDING = -1;
    
    public static final short TREE_POSITION_FOLLOWING = 1;
    
    public static final short TREE_POSITION_ANCESTOR = -10;
    
    public static final short TREE_POSITION_DESENDANT = 10;
    
    public static final short TREE_POSITION_EQUIVALENT = 0;
    
    public static final short TREE_POSITION_SAME_NODE  = -20;
    
    public static final short TREE_POSITION_DISCONNECTED = 100;
    
    Node(String nodeName, short nodeType, Document ownerDocument){
        this.nodeName = nodeName;
        this.nodeType = nodeType;
        this.ownerDocument = ownerDocument;
    }
    
    @Override
    public String getNodeName() {
        return nodeName;
    }

    @Override
    public String getNodeValue() throws DOMException {
        return nodeValue;
    }

    boolean isValidDOMString(String domString){
        return !(domString.contains("<")
                || domString.contains(">"));
    }
    
    @Override
    public void setNodeValue(String nodeValue) throws DOMException {
        if(!isValidDOMString(nodeValue))
            throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The node value is not a valid DOMString");
        this.nodeValue = nodeValue;
    }

    @Override
    public short getNodeType() {
        return nodeType;
    }

    @Override
    public Node getParentNode() {
        return parentNode;
    }

    @Override
    public NodeList getChildNodes() {
        return childNodes;
    }

    @Override
    public org.w3c.dom.Node getFirstChild() {
        return childNodes.item(0);
    }

    @Override
    public org.w3c.dom.Node getLastChild() {
        return childNodes.item(childNodes.getLength() - 1);
    }

    @Override
    public org.w3c.dom.Node getPreviousSibling() {
        if(cacheIndex != -1)
            return parentNode.getChildNodes().item(cacheIndex - 1);
        for(int siblingIndex = 0; siblingIndex < parentNode.getChildNodes().getLength(); siblingIndex++)
            if(parentNode.getChildNodes().item(siblingIndex).isSameNode(this)){
                cacheIndex = siblingIndex; // Cache the index for later use
                return parentNode.getChildNodes().item(siblingIndex - 1);
            }
        return null;
    }
    
    @Override
    public org.w3c.dom.Node getNextSibling() {
        if(cacheIndex != -1)
            return parentNode.getChildNodes().item(cacheIndex + 1);
        for(int siblingIndex = 0; siblingIndex < parentNode.getChildNodes().getLength(); siblingIndex++)
            if(parentNode.getChildNodes().item(siblingIndex).isSameNode(this)){
                cacheIndex = siblingIndex; // Cache the index for later use
                return parentNode.getChildNodes().item(siblingIndex + 1);   
            }
        return null;
    }

    @Override
    public NamedNodeMap getAttributes() {
        return nodeAttributes;
    }

    @Override
    public Document getOwnerDocument() {
        return ownerDocument;
    }

    @Override
    public org.w3c.dom.Node insertBefore(org.w3c.dom.Node newChild, org.w3c.dom.Node refChild) throws DOMException{
        throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "HIERARCHY_REQUEST_ERR");
    }
    
    @Override
    public org.w3c.dom.Node replaceChild(org.w3c.dom.Node newChild, org.w3c.dom.Node oldChild) throws DOMException {
        throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "HIERARCHY_REQUEST_ERR");
    }

    @Override
    public org.w3c.dom.Node removeChild(org.w3c.dom.Node oldChild) throws DOMException {
        throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "HIERARCHY_REQUEST_ERR");
    }

    @Override
    public org.w3c.dom.Node appendChild(org.w3c.dom.Node newChild) throws DOMException {
        return insertBefore(newChild, null);
    }

    @Override
    public boolean hasChildNodes() {
        return childNodes.getLength() != 0;
    }

    @Override
    public Node cloneNode(boolean deep) {
        Node clonedNode;
        try {
            clonedNode = (Node) clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
            throw new Error();
        }
        
        clonedNode.ownerDocument = this.ownerDocument;
        
        callUserDataHandlers(
                UserDataHandler.NODE_CLONED,
                clonedNode
        );
        
        return clonedNode;
    }

    @Override
    public void normalize(){
        
    }

    @Override
    public boolean isSupported(String feature, String version) {
        return ownerDocument.getImplementation().hasFeature(feature, version);
    }

    @Override
    public String getNamespaceURI() {
        return getParentNode() != null 
                ? getParentNode().lookupNamespaceURI(getPrefix()) : null;
    }

    String cachePrefix;
    @Override
    public String getPrefix() {
        if(cachePrefix != null)
            return cachePrefix;
        else if(nodeName.contains(":")){
            String prefixStr = new String();
            for(int charIndex = 0; charIndex < nodeName.length(); charIndex++){
                if(nodeName.charAt(charIndex) == ':')
                    break;
                prefixStr += nodeName.charAt(charIndex);
            }
            cachePrefix = prefixStr;
            return prefixStr;
        }
        
        return "";
    }

    @Override
    public void setPrefix(String prefix) throws DOMException {
        throw new DOMException(DOMException.NAMESPACE_ERR, "NAMESPACE_ERR");
    }

    String cacheLocalName = null;
    @Override
    @SuppressWarnings("empty-statement")
    public String getLocalName() {
        if(cacheLocalName != null)
            return cacheLocalName;
        else if(nodeName.contains(":")){
            int startIndex;
            for(startIndex = 0; nodeName.charAt(startIndex) != ':'; startIndex++);
            String localName = new String();
            for(int charIndex = startIndex + 1; charIndex < nodeName.length(); charIndex++){
                localName += nodeName.charAt(charIndex);
            }
            cacheLocalName = localName;
            return localName;
        }
        return getNodeName();
    }

    @Override
    public boolean hasAttributes() {
        return nodeAttributes.getLength() != 0;
    }

    @Override
    public String getBaseURI() {
        return null;
    }

    @Override
    public short compareDocumentPosition(org.w3c.dom.Node other) throws DOMException {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "NOT_SUPPORT_ERR"); // Not supported in Scilca DOM
    }

    @Override
    public String getTextContent() throws DOMException {
        return null;
    }

    @Override
    public void setTextContent(String textContent) throws DOMException {
        throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "INVALID_CHARACTER_ERR");
    }

    /**
     * This method tests for the equality of two nodes, <br>
     * i.e., if they are the references to the same node.
     * @param other
     * @return 
     */
    @Override
    public boolean isSameNode(org.w3c.dom.Node other) {
        return other == this;
    }

    @Override
    public String lookupPrefix(String namespaceURI) {
        if(namespaceURI == null)
            return null;
        
        switch(getNodeType()){
            case Node.ELEMENT_NODE:
                for(int attrIndex = 0; attrIndex < getAttributes().getLength(); attrIndex++){
                    if(getAttributes().item(attrIndex).getNamespaceURI().equals(namespaceURI))
                        return getAttributes().item(attrIndex).getLocalName();
                }
                return null;
            case Node.ENTITY_NODE :
            case Node.NOTATION_NODE:
            case Node.DOCUMENT_FRAGMENT_NODE:
            case Node.DOCUMENT_TYPE_NODE:
            case Node.DOCUMENT_NODE:
                return null;
            case Node.ATTRIBUTE_NODE:
                return (getParentNode().getNodeType() == Node.ELEMENT_NODE)
                        ? getParentNode().lookupPrefix(namespaceURI) : null;
            default: return (getElementParent() != null) 
                    ? ((Node) getElementParent()).lookupPrefix(namespaceURI) : null;
        }
    }

    String cacheDefaultNamespace = null; // Cache the default namespace URI if found
    @Override
    public boolean isDefaultNamespace(String namespaceURI) {
        if(cacheDefaultNamespace != null)
            return cacheDefaultNamespace.equals(namespaceURI);
        switch(getNodeType()){
            case Node.ELEMENT_NODE:
                if(getPrefix() == null || getPrefix().isEmpty()){
                    return (getNamespaceURI() == null ? namespaceURI == null
                            : getNamespaceURI().equals(namespaceURI));
                } 
                
                org.w3c.dom.Element selfElement = (org.w3c.dom.Element) this;
                org.w3c.dom.Node namespaceAttr = selfElement.getAttributeNodeNS(namespaceURI, "xmlns");
                
                cacheDefaultNamespace = namespaceAttr != null 
                        ? namespaceAttr.getNodeValue() : null; // Cache the default namespace
                
                if(namespaceAttr != null){
                    return true;
                }
                
                return getParentNode() != null ? getParentNode().isDefaultNamespace(namespaceURI) : false;
            case Node.DOCUMENT_NODE:
            case Node.ENTITY_NODE:
            case Node.NOTATION_NODE:
            case Node.CDATA_SECTION_NODE:
            case Node.COMMENT_NODE:
                return false;
            case Node.ATTRIBUTE_NODE:
                if(getParentNode() != null
                        && getParentNode().getNodeType() == Node.ELEMENT_NODE
                        && getParentNode().isDefaultNamespace(namespaceURI)){
                    cacheDefaultNamespace = namespaceURI;
                    return true;
                }
                
                return false;
            default: 
                return getParentNode() != null
                        ? getParentNode().isDefaultNamespace(namespaceURI) : false;
        }
    }
    
    org.w3c.dom.Element cacheElementParent;
    org.w3c.dom.Element getElementParent(){
        if(cacheElementParent != null)
            return cacheElementParent;
        else if(getParentNode() != null 
                && getParentNode().getNodeType() == Node.ELEMENT_NODE){
            cacheElementParent = (org.w3c.dom.Element) getParentNode();
            return (org.w3c.dom.Element) getParentNode();
        }
        else { 
            cacheElementParent = (getParentNode() != null) ? getParentNode().getElementParent() : null;
            return cacheElementParent;
        }
    }

    @Override
    public String lookupNamespaceURI(String prefix) {
        switch(getNodeType()){
            case Node.ELEMENT_NODE:
                if(getNamespaceURI() == null){
                    if(prefix == null && getPrefix() == null)
                        return getNamespaceURI();
                    else if(prefix != null && getPrefix().equals(prefix))
                        return getNamespaceURI();
                }
                
                if(hasAttributes()){
                    for(int attrIndex = 0; attrIndex < nodeAttributes.getLength(); attrIndex++){
                        if(prefix == null && nodeAttributes.item(attrIndex).getLocalName().equals("xmlns"))
                            return nodeAttributes.item(attrIndex).getNodeValue();
                        else if(nodeAttributes.item(attrIndex) !=null &&
                                   nodeAttributes.item(attrIndex).getPrefix().equals("xmlns") &&
                                   nodeAttributes.item(attrIndex).getLocalName().equals(prefix))
                            return nodeAttributes.item(attrIndex).getNodeValue();
                    }
                }
                
                return (getElementParent() != null) 
                        ? getElementParent().lookupNamespaceURI(prefix) : null;
            case Node.ENTITY_NODE :
            case Node.NOTATION_NODE:
            case Node.DOCUMENT_FRAGMENT_NODE:
            case Node.DOCUMENT_TYPE_NODE:
            case Node.DOCUMENT_NODE:
                return null;
            case Node.ATTRIBUTE_NODE:
                return (getParentNode() != null && 
                        getParentNode().getNodeType() == Node.ELEMENT_NODE) 
                        ? getParentNode().lookupNamespaceURI(prefix) : null;
            default: return (getElementParent() != null) 
                    ? getElementParent().lookupNamespaceURI(prefix) : null;
        }
    }
    
    @Override
    public boolean isEqualNode(org.w3c.dom.Node arg) {
        if(this == arg)
            return true;
       
        if (arg.getNodeType() != getNodeType()) {
            return false;
        }
        
        // in theory nodeName can't be null but better be careful
        // who knows what other implementations may be doing?...
        if (getNodeName() == null
                && arg.getNodeName() != null) {
            return false;
        } else if (!getNodeName().equals(arg.getNodeName())) {
            return false;
        }

        if (getLocalName() == null
                && arg.getLocalName() != null) {
            return false;
        } else if (!getLocalName().equals(arg.getLocalName())) {
            return false;
        }

        if (getNamespaceURI() == null
                && arg.getNamespaceURI() != null) {
            return false;
        } else if (!getNamespaceURI().equals(arg.getNamespaceURI())) {
            return false;
        }

        if (getPrefix() == null
                && arg.getPrefix() != null) {
            return false;
        } else if (!getPrefix().equals(arg.getPrefix())) {
            return false;
        } else if (getNodeValue() == null
                && arg.getNodeValue() != null) {
            return false;
        } else if (!getNodeValue().equals(arg.getNodeValue())) {
            return false;
        }

        return true;
    }

    @Override
    public Object getFeature(String feature, String version) {
        return isSupported(feature, version) ? this : null;
    }
    
    class UserDataRecord{
        public final Object data;
        public final UserDataHandler handler;
    
        public UserDataRecord(Object data, UserDataHandler handler){
            this.data = data;
            this.handler = handler;
        }
    }
    
    HashMap<String, UserDataRecord> dataRecord = new HashMap<>();
    
    @Override
    public Object setUserData(String key, Object data, UserDataHandler handler) {
        Object oldData = dataRecord.get(key);
        dataRecord.put(key, new UserDataRecord(data, handler));
        return oldData;
    }

    @Override
    public Object getUserData(String key) {
        return dataRecord.get(key);
    }
    
    void callUserDataHandlers(short actionTaken, Node dst){
        Object[] keySet = dataRecord.keySet().toArray();
        for(int dataIndex = 0; dataIndex < dataRecord.size(); dataIndex++){
            String key = (String) keySet[dataIndex];
            UserDataRecord Udr = dataRecord.get(key);
            Udr.handler.handle(actionTaken, key, Udr.data, this, dst);
        }
    }
    
    
    public void clearCache(){
        this.cacheIndex = -1;
        this.cacheElementParent = null;
        this.cacheDefaultNamespace = null;
    }
    
}
