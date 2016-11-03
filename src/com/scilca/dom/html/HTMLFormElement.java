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

import com.scilca.dom.NodeList;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.html.HTMLCollection;

/**
 *
 * @author Sukant Pal
 */
public class HTMLFormElement extends HTMLElement implements org.w3c.dom.html.HTMLFormElement{

    public static final String DEFAULT_ENCTYPE = "application/x-www-form-urlencoded";
    public static final String DEFAULT_ACCEPT_CHARSET = "UNKNOWN";
    
    protected HTMLFormElement(HTMLDocument ownerDocument){
        super("form", ownerDocument);
    }
    
    @Override
    public HTMLCollection getElements() {
        NodeList elements = getChildNodes();
        com.scilca.dom.html.HTMLCollection hc = new com.scilca.dom.html.HTMLCollection();
        for(Node childNode : elements){
            if(childNode.getNodeType() != Node.ELEMENT_NODE)
                continue;
            else {
                HTMLElement childElement;
                try{
                    childElement = (HTMLElement) childNode;
                } catch(ClassCastException e){
                    continue;
                }
                hc.add(childElement);
            }
        }
        
        return hc;
    }

    @Override
    public int getLength() {
        return getChildNodes().getLength();
    }

    @Override
    public String getName() {
        Attr nameAttr = getAttributeNode("name");
        if(nameAttr == null)
            return "";
        else return nameAttr.getValue();
    }

    @Override
    public void setName(String name) {
        Attr nameAttr = getAttributeNode("name");
        if(nameAttr == null)
            setAttribute("name", name);
        else nameAttr.setValue(name);
    }

    @Override
    public String getAcceptCharset() {
        Attr acceptCharset = getAttributeNode("accept-charset");
        if(acceptCharset == null)
            return DEFAULT_ACCEPT_CHARSET;
        else return acceptCharset.getValue();
    }

    @Override
    public void setAcceptCharset(String acceptCharset) {
        Attr acceptCharsetAttr = getAttributeNode("accept-charset");
        if(acceptCharsetAttr == null)
            setAttribute("accept-charset", acceptCharset);
        else acceptCharsetAttr.setValue(acceptCharset);
    }

    @Override
    public String getAction() {
        Attr actionAttr = getAttributeNode("action");
        if(actionAttr == null)
            return "";
        else return actionAttr.getValue();
    }

    @Override
    public void setAction(String action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getEnctype() {
        Attr enctypeAttr = getAttributeNode("enctype");
        if(enctypeAttr == null)
            return DEFAULT_ENCTYPE;
        else return enctypeAttr.getValue();
    }

    @Override
    public void setEnctype(String enctype) {
        Attr enctypeAttr = getAttributeNode("enctype");
        if(enctypeAttr == null)
            setAttribute("enctype", enctype);
        else enctypeAttr.setValue(enctype);
    }

    @Override
    public String getMethod() {
        Attr methodAttr = getAttributeNode("method");
        if(methodAttr == null)
            return "get";
        else return methodAttr.getValue();
    }

    @Override
    public void setMethod(String method) {
        if(!method.equals("get") && !method.equals("post"))
            throw new DOMException(DOMException.SYNTAX_ERR, "SYNTAX_ERR");
        Attr methodAttr = getAttributeNode("method");
        if(methodAttr == null)
            setAttribute("method", method);
        else methodAttr.setValue(method);
    }

    @Override
    public String getTarget() {
        Attr targetAttr = getAttributeNode("target");
        if(targetAttr == null)
            return "";
        else return targetAttr.getValue();
    }

    @Override
    public void setTarget(String target) {
        Attr targetAttr = getAttributeNode("target");
        if(targetAttr == null)
            setAttribute("target", target);
        else targetAttr.setValue(target);
    }

    @Override
    public void submit() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException();
    }
    
}
