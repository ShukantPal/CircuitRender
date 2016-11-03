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

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/**
 *
 * @author Sukant Pal
 */
public class HTMLHeadElement extends HTMLElement implements org.w3c.dom.html.HTMLHeadElement{

    HTMLHtmlElement parentElement;
    
    protected HTMLHeadElement(HTMLDocument ownerDocument) {
        super("head", ownerDocument);
    }
    
    @Override
    public boolean isValidAttribute(String attrName){
        return false; // Head elements don't have attributes
    }
    
    @Override
    public boolean isValidChild(String tagName){
        return tagName.equals("link")
                || tagName.equals("meta")
                || tagName.equals("base")
                || tagName.equals("basefont")
                || tagName.equals("bgsound")
                || tagName.equals("comand")
                || tagName.equals("title");
    }
    
    @Override
    public com.scilca.dom.Node getParentNode(){
        return parentElement;
    }
    
    public HTMLHtmlElement getHtmlElement(){
        return parentElement;
    }

    @Override
    public String getProfile() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setProfile(String profile) {
        
    }
    
    @Override
    public String toString(){
        return "HTMLHeadElement";
    }
    
}
