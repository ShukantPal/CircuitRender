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
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 *
 * @author Sukant Pal
 */
public class Document extends Node implements org.w3c.dom.Document{

    org.w3c.dom.DocumentType docType;
    com.scilca.dom.Element rootNode;
    
    String documentURI = null;
    
    double XmlVersion;
    String charSet;
    boolean isStandalone;
    
    DOMImplementation domImpl;
    
    Document() {
        super(null, Node.DOCUMENT_NODE, null);
        docType = null;
        XmlVersion = 1.0;
        charSet = "utf-8";
        isStandalone = false;
    }
    
    protected Document(String nodeName, org.w3c.dom.DocumentType docType, DOMImplementation domImpl){
        this();
        this.docType = docType;
        this.domImpl = domImpl;
    }
    
    protected Document(String nodeName, org.w3c.dom.DocumentType docType, String namespaceURI, DOMImplementation domImpl){
        this(nodeName, docType, domImpl);
        documentURI = namespaceURI;
    }

    @Override
    public DocumentType getDoctype() {
        return docType;
    }

    @Override
    public DOMImplementation getImplementation() {
        return domImpl;
    }

    @Override
    public Element getDocumentElement() {
        return rootNode;
    }
    
    public com.scilca.dom.Element getRootElement(){
        return rootNode;
    }

    @Override
    public Element createElement(String tagName) throws DOMException {
        return new com.scilca.dom.Element(tagName, this);
    }

    @Override
    public DocumentFragment createDocumentFragment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Text createTextNode(String data) {
        return new TextNode(null, data, this);
    }
    
    public Text createTextNode(String name, String data){
        return new TextNode(name, data, this);
    }

    @Override
    public Comment createComment(String data) {
        return new com.scilca.dom.Comment(data, this);
    }

    @Override
    public CDATASection createCDATASection(String data) throws DOMException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ProcessingInstruction createProcessingInstruction(String target, String data) throws DOMException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Attr createAttribute(String name) throws DOMException {
        return new Attribute(name, "", this);
    }

    public Attr createAttribute(String name, String value) throws DOMException{
        if(!isValidDOMString(name))
            throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "INVALID_CHARACTER_ERR");
        return new Attribute(name, value, this);
    }
    
    @Override
    public EntityReference createEntityReference(String name) throws DOMException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    ArrayList<Element> findElementsByTagName(com.scilca.dom.Element elementToFind, String tagName){
        ArrayList<Element> nodesSatisfying = new ArrayList<>();
        if(elementToFind.getTagName().equals(tagName))
            nodesSatisfying.add(elementToFind);
        
        for(int childIndex = 0; childIndex < elementToFind.getChildNodes().getLength(); childIndex++){
            Node childNode = (com.scilca.dom.Node) elementToFind.getChildNodes().item(childIndex);
            if(childNode.getNodeType() != Node.ELEMENT_NODE)
                continue;
            
            com.scilca.dom.Element childElement = (com.scilca.dom.Element) childNode;
            if(childElement.getTagName().equals(tagName))
                nodesSatisfying.add(childElement);
            
            nodesSatisfying.addAll(findElementsByTagName(childElement, tagName));
        }
        
        return nodesSatisfying;
    }
    
    @Override
    public NodeList getElementsByTagName(String tagname) {
        ArrayList<Element> elements = findElementsByTagName(rootNode, tagname);
        ArrayList<org.w3c.dom.Node> finallist = new ArrayList<>();
        
        for(Element e : elements)
            finallist.add((org.w3c.dom.Node) e);
        
        return new com.scilca.dom.NodeList(finallist);
    }

    @Override
    public org.w3c.dom.Node importNode(org.w3c.dom.Node importedNode, boolean deep) throws DOMException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException {
        if(!qualifiedName.contains(":") || !qualifiedName.contains("xmlns"))
            throw new DOMException(DOMException.INVALID_STATE_ERR, "INVALID_STATE_ERR");
        return new Attribute(qualifiedName, namespaceURI, this);
    }

    @Override
    public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    com.scilca.dom.Element findElementById(com.scilca.dom.Element elementToFind, String elementId){
        if(elementToFind.getIdAttr() != null && elementToFind.getIdAttr().getNodeValue().equals(elementId))
            return elementToFind;

        for(int childIndex = 0; childIndex < elementToFind.getChildNodes().getLength(); childIndex++){
            Node childNode = (com.scilca.dom.Node) elementToFind.getChildNodes().item(childIndex);
            if(childNode.getNodeType() != Node.ELEMENT_NODE)
                continue;
            
            com.scilca.dom.Element childElement = (com.scilca.dom.Element) childNode;
            if(childElement.getIdAttr() != null && childElement.getIdAttr().getNodeValue().equals(elementId))
                return childElement;
            
            com.scilca.dom.Element resultElement = findElementById(childElement, elementId);
            if(resultElement != null)
                return resultElement;
        }
        return null;
    }

    @Override
    public Element getElementById(String elementId) {
        return findElementById(rootNode, elementId);
    }

    @Override
    public String getInputEncoding() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getXmlEncoding() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getXmlStandalone() {
        return isStandalone;
    }

    @Override
    public void setXmlStandalone(boolean xmlStandalone) throws DOMException {
        isStandalone = xmlStandalone;
    }

    @Override
    public String getXmlVersion() {
        return Double.toString(XmlVersion);
    }

    @Override
    public void setXmlVersion(String xmlVersion) throws DOMException {
        @SuppressWarnings("LocalVariableHidesMemberVariable")
        double XmlVersion;
        try{
            XmlVersion = Double.parseDouble(xmlVersion);
        } catch(NumberFormatException e){
            throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "INVALID_CHARACTER_ERR - Version cannot be parsed");
        }
        
        if(XmlVersion != 1.0 && XmlVersion != 1.1)
            throw new DOMException(DOMException.SYNTAX_ERR, "SYNTAX_ERR");
        else this.XmlVersion = XmlVersion;
    }

    @Override
    public boolean getStrictErrorChecking() {
        return true;
    }

    @Override
    public void setStrictErrorChecking(boolean strictErrorChecking) {
        
    }

    @Override
    public String getDocumentURI() {
        return documentURI;
    }

    @Override
    public void setDocumentURI(String documentURI) {
        this.documentURI = documentURI;
    }

    void adoptChildren(com.scilca.dom.Node source) throws DOMException{
        for(org.w3c.dom.Node childNode : source.getChildNodes()){
            com.scilca.dom.Node nodeSource;
            try{
                nodeSource = (com.scilca.dom.Node) childNode;
            } catch(ClassCastException e){
                throw new DOMException(DOMException.INVALID_ACCESS_ERR, "INVALID_ACCESS_ERR");
            }
            
            nodeSource.ownerDocument = this;
            adoptChildren(nodeSource);
        }
    }
    
    @Override
    public org.w3c.dom.Node adoptNode(org.w3c.dom.Node source) throws DOMException {
        com.scilca.dom.Node nodeSource;
        try{
            nodeSource = (com.scilca.dom.Node) source;
        } catch(ClassCastException e){
            throw new DOMException(DOMException.INVALID_ACCESS_ERR, "INVALID_ACCESS_ERR");
        }
        
        nodeSource.ownerDocument = this;
        adoptChildren(nodeSource);
        return nodeSource;
    }

    @Override
    public DOMConfiguration getDomConfig() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void normalizeDocument() {
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
        
        rootNode.normalize();
    }

    @Override
    public org.w3c.dom.Node renameNode(org.w3c.dom.Node n, String namespaceURI, String qualifiedName) throws DOMException {
        String newPrefix = n.lookupPrefix(namespaceURI);
        com.scilca.dom.Node convertedNode;
        try{
            convertedNode = (com.scilca.dom.Node) n;
        } catch(ClassCastException e){
            throw new DOMException(DOMException.INVALID_ACCESS_ERR, "INVALID_ACCESS_ERR");
        }
        
        convertedNode.nodeName = newPrefix + qualifiedName;
        return n;
    }
    
    @Override
    public org.w3c.dom.Node insertBefore(org.w3c.dom.Node newChild, org.w3c.dom.Node refChild) throws DOMException{
        if(newChild.getNodeType() == Node.ELEMENT_NODE && rootNode != null)
            throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "HIERARCHY_REQUEST_ERR");
        else if(newChild.getNodeType() == Node.DOCUMENT_FRAGMENT_NODE)
            throw new UnsupportedOperationException();
       
        if(newChild.getNodeType() == Node.ELEMENT_NODE)
            rootNode = (com.scilca.dom.Element) newChild;
        
        if(childNodes == null){
            childNodes = new com.scilca.dom.NodeList();
            if(refChild != null)
                throw new DOMException(DOMException.NOT_FOUND_ERR, "NOT_FOUND_ERR");
            childNodes.add(newChild);
        } else {
            if(refChild == null)
                childNodes.add(newChild);
            else {
                for(int childIndex = 0; childIndex < childNodes.getLength(); childIndex++){
                    if(childNodes.item(childIndex) == refChild){
                        childNodes.add(rootNode, childIndex);
                        break;
                    }
                }
            }
        }
        
        return newChild;
    }
    
    @Override
    public org.w3c.dom.Node replaceChild(org.w3c.dom.Node newChild, org.w3c.dom.Node oldChild){
        if(newChild.getNodeType() == Node.ELEMENT_NODE && (rootNode != null && oldChild != rootNode))
            throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "HIERARCHY_REQUEST_ERR");
        else if(newChild.getNodeType() == Node.DOCUMENT_FRAGMENT_NODE)
            throw new UnsupportedOperationException();
        else if(newChild == null || oldChild == null)
            throw new DOMException(DOMException.INVALID_STATE_ERR, "INVALID_STATE_ERR");
        
        if(oldChild == rootNode && newChild.getNodeType() != Node.ELEMENT_NODE)
            rootNode = null;
        
        for(int childIndex = 0; childIndex < getChildNodes().getLength(); childIndex++){
            if(getChildNodes().item(childIndex).isSameNode(oldChild)){
                getChildNodes().set(newChild, childIndex);
                break;
            }
        }
        return newChild;
    }
    
    @Override
    public org.w3c.dom.Node removeChild(org.w3c.dom.Node oldChild){
        getChildNodes().remove(oldChild);
        return oldChild;
    }
    
    @Override
    public NamedNodeMap getAttributes(){
        return null;
    }
    
}
