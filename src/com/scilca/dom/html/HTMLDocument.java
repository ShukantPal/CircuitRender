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
package com.scilca.dom.html;

import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLCollection;
import org.w3c.dom.html.HTMLElement;

import com.scilca.dom.AttributesMap;
import org.w3c.dom.Node;

/**
 *
 * @author Sukant Pal
 */
public class HTMLDocument extends com.scilca.dom.Document implements org.w3c.dom.html.HTMLDocument{

    com.scilca.dom.html.HTMLHtmlElement htmlElement;
    
    com.scilca.dom.html.HTMLHeadElement headElement;
    com.scilca.dom.html.HTMLElement bodyElement;
    
    double HtmlVersion;
    
    AttributesMap htmlAttrs;
    
    protected HTMLDocument(com.scilca.dom.DocumentType doctype, String URI, com.scilca.dom.DOMImplementation domImpl){
        super(null, doctype, URI, domImpl);
        htmlAttrs = new AttributesMap(this, this);
        htmlElement = new com.scilca.dom.html.HTMLHtmlElement(this);
        HtmlVersion = 5;
    }
    
    public static HTMLDocument getDocument(String URI){
        return new HTMLDocument(null, URI, null);
    }
    
    public com.scilca.dom.html.HTMLElement getElement(String HTMLTag){   
        switch(HTMLTag){
            case "a" : return new HTMLAnchorElement(this);
            case "b" : return new HTMLFormattingElement
        (HTMLFormattingElement.FORMATTING_TYPE.BOLD, this);
            case "base": return new HTMLBaseElement(this);
            case "basefont": return new HTMLBaseFontElement(this);
            case "br" : return new HTMLBRElement(this);
            case "button" : return new HTMLButtonElement(this);
            case "body" : return new HTMLBodyElement(this);
            case "div" : return new HTMLDivElement(this);
            case "font" : return new HTMLFontElement(this);
            case "form" : return new HTMLFormElement(this);
            case "hr" : return new HTMLHRElement(this);
            case "head" : return new HTMLHeadElement(this);
            case "html" : return htmlElement;
            case "i": return new HTMLFormattingElement
        (HTMLFormattingElement.FORMATTING_TYPE.ITALICS, this);
            // case "img" : return new HTMLImageELement(this);
            case "link": return new HTMLLinkElement(this);
            case "p": return new HTMLParagraphElement(this);
            case "title" : return new HTMLTitleElement(this);
            default: 
                if(HTMLTag.charAt(0) == 'h')
                    return new HTMLHeadingElement(Integer.parseInt(Character.toString(HTMLTag.charAt(1))), this);
                return null;
        }
    }
    
    @Override
    public String getTitle() {
        return htmlAttrs.getNamedItem("title") != null ?
                htmlAttrs.getNamedItem("title").getNodeValue() : "";
    }

    @Override
    public void setTitle(String title) {
        if(htmlAttrs.getNamedItem("title") != null)
            htmlAttrs.getNamedItem("title").setNodeValue(title);
        else htmlAttrs.setNamedItem(super.createAttribute("title", title));
    }

    @Override
    public String getReferrer() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getDomain() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getURL() {
        return super.getDocumentURI();
    }

    @Override
    public HTMLElement getBody() {
        return bodyElement;
    }

    @Override
    public void setBody(HTMLElement body) {
        bodyElement = (com.scilca.dom.html.HTMLElement) body;
    }
    
    public HTMLElement getHTMLElement(){
        return htmlElement;
    }

    HTMLCollection findType(com.scilca.dom.html.HTMLElement sourceOfType, String tagName){
        com.scilca.dom.html.HTMLCollection typeResults 
                = new com.scilca.dom.html.HTMLCollection();
        
        if(sourceOfType.getNodeName().equals(tagName))
            typeResults.add(sourceOfType);
            
        com.scilca.dom.NodeList childNodes = sourceOfType.getChildNodes();
        if(childNodes != null)
            for(int childIndex = 0; childIndex < childNodes.getLength(); childIndex++){
                Node childNode = childNodes.item(childIndex);
                if(childNode.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                
                com.scilca.dom.html.HTMLElement childElement;
                try {
                    childElement = (com.scilca.dom.html.HTMLElement) childNode;
                } catch(ClassCastException e){
                    continue;
                }
                
                if(childElement.getNodeName().equals(tagName))
                    typeResults.add(childElement);
            }
        
        
        return typeResults;
    }
    
    @Override
    public HTMLCollection getImages() {
        return bodyElement != null ?
                findType(bodyElement, "img") : new com.scilca.dom.html.HTMLCollection();
    }

    @Override
    public HTMLCollection getApplets() {
        return bodyElement != null ?
                findType(bodyElement, "applet") : new com.scilca.dom.html.HTMLCollection();
    }

    @Override
    public HTMLCollection getLinks() {
        return headElement != null ?
                findType(headElement, "link") : new com.scilca.dom.html.HTMLCollection();
    }

    @Override
    public HTMLCollection getForms() {
        return bodyElement != null ?
                findType(bodyElement, "img") : new com.scilca.dom.html.HTMLCollection();
    }

    @Override
    public HTMLCollection getAnchors() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getCookie() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCookie(String cookie) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void open() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void write(String text) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeln(String text) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NodeList getElementsByName(String elementName) {
        HTMLCollection searchMatches = findType(htmlElement, elementName);
        com.scilca.dom.NodeList nodes = new com.scilca.dom.NodeList();
        
        for(int sM = 1; sM < searchMatches.getLength(); sM++){
            nodes.add(searchMatches.item(sM));
        }
        
        return nodes;
    }
    
    public HTMLCollection getElementByTagName(String tagName){
        return findType(htmlElement, tagName);
    }
    
    @Override
    public String toString(){
        return htmlElement.toString();
    }
    
}
