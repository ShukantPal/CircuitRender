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

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/**
 *
 * @author Sukant Pal
 */
public class HTMLElement extends com.scilca.dom.Element implements org.w3c.dom.html.HTMLElement{

    HTMLDocument ownerHTMLDocument;
    
    HTMLElement(String elementName, HTMLDocument ownerDocument){
        super(elementName, ownerDocument);
        ownerHTMLDocument = ownerDocument;
    }
    
    String getAttr(String AttrName, String defaultValue){
        Attr attr = getAttributeNode(AttrName);
        if(attr == null)
            return defaultValue;
        else return attr.getValue();
    }
    
    void setAttr(String AttrName, String AttrValue){
        Attr attr = getAttributeNode(AttrName);
        if(attr == null)
            setAttribute(AttrName, AttrValue);
        else attr.setValue(AttrValue);
    }
    
    /**
     * This method is used to figure out if the given attribute 
     * is valid for the HTMLElement in use.
     * @param attrName
     * @return 
     */
    public boolean isValidAttribute(String attrName){
        return true; // Inherited by child classes
    }
    
    /**
     * This method is used to figure out if the given tag name 
     * can be the name of a child element for the given HTMLElement 
     * in use.
     * @param tagName
     * @return 
     */ 
    public boolean isValidChild(String tagName){
        return true; // Inherited by child classes
    }
    
    @Override
    public String getId() {
        return getAttr("id", "");
    }

    @Override
    public void setId(String id) {
        super.setAttribute(getIdAttr().getNodeName(), id);
    }

    @Override
    public String getTitle() {
        return getAttr("title", "");
    }

    @Override
    public void setTitle(String title) {
        setAttr("title", title);
    }

    @Override
    public String getLang() {
        Attr langAttr = (Attr) getAttributes().getNamedItem("lang");
        if(langAttr != null)
            return langAttr.getNodeValue();
        else return getParentNode() != null 
                ? ((HTMLElement) getParentNode()).getLang() : "";
    }

    @Override
    public void setLang(String lang) {
        setAttr("lang", lang);
    }

    @Override
    public String getDir() {
        return getAttr("dir", "ltr");
    }

    @Override
    public void setDir(String dir) {
        if(!dir.equals("ltf") && !dir.equals("rtl"))
            throw new DOMException(DOMException.INVALID_MODIFICATION_ERR, "INVALID_MODIFICATION_ERR");
        
        setAttr("dir", dir);
    }

    @Override
    public String getClassName() {
        return getAttr("class", "");
    }

    @Override
    public void setClassName(String className) {
        setAttr("class", className);
    }
    
    public String getButtonText(){
        String buttonText = new String();
        for(Node childNode : (com.scilca.dom.NodeList) getChildNodes()){
            if(childNode.getNodeType() == Node.TEXT_NODE){
                buttonText += childNode.getNodeValue();
            }
        }
        return buttonText;
    }
    
    @Override
    public String toString(){
        return "HTMLElement " + getNodeName();
    }
    
}
