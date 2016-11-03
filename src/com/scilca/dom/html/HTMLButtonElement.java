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
import org.w3c.dom.html.HTMLFormElement;

/**
 *
 * @author Sukant Pal
 */
public class HTMLButtonElement extends HTMLElement implements org.w3c.dom.html.HTMLButtonElement{
    
    public static final String DEFAULT_TYPE = "submit";
    
    protected HTMLButtonElement(HTMLDocument ownerDocument){
        super("button", ownerDocument);
    }
    
    @Override
    public HTMLFormElement getForm() {
        Node parentNode = getParentNode();
        if(parentNode == null)
            return null;
        else {
            try{
                return (HTMLFormElement) parentNode;
            } catch(ClassCastException e){
                return null;
            }
        }
    }

    @Override
    public String getAccessKey() {
        return getAttr("accesskey", null);
    }

    @Override
    public void setAccessKey(String accessKey) {
        if(accessKey.length() != 1)
            throw new DOMException(DOMException.DOMSTRING_SIZE_ERR, "DOMSTRING_SIZE(NOT 1)_ERR");
        
        setAttr("accesskey", accessKey);
    }

    @Override
    public boolean getDisabled() {
        Attr disableAttr = getAttributeNode("disabled");
        return disableAttr != null;
    }

    @Override
    public void setDisabled(boolean disabled) {
        Attr disableAttr = getAttributeNode("disabled");
        if(disabled && disableAttr == null)
            setAttribute("disabled", "disabled");
        else if(!disabled && disableAttr != null)
            removeAttribute("disabled");
    }

    @Override
    public String getName() {
        return getAttr("name", "");
    }

    @Override
    public void setName(String name) {
        setAttr("name", name);
    }

    @Override
    public int getTabIndex() {
        Attr tabAttr = getAttributeNode("tabindex");
        if(tabAttr == null)
            return -1;
        else return Integer.parseInt(tabAttr.getValue());
    }

    @Override
    public void setTabIndex(int tabIndex) {
        if(tabIndex < 0)
            throw new DOMException(DOMException.INDEX_SIZE_ERR, "INDEX_SIZE_ERR");
        Attr tabAttr = getAttributeNode("tabindex");
        if(tabAttr == null)
            setAttribute("tagindex", Integer.toString(tabIndex));
        else tabAttr.setValue(Integer.toString(tabIndex));
    }

    @Override
    public String getType() {
        return getAttr("type", DEFAULT_TYPE);
    }

    @Override
    public String getValue() {
        return getAttr("value", "");
    }

    @Override
    public void setValue(String value) {
       setAttr("value", value);
    }
    
}
