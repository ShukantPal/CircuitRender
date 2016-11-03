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

/**
 *
 * @author Sukant Pal
 */
public class HTMLHtmlElement extends HTMLElement implements org.w3c.dom.html.HTMLHtmlElement{

    protected HTMLHtmlElement(HTMLDocument ownerDocument) {
        super("html", ownerDocument);
    }
    
    @Override
    public boolean isValidChild(String tagName){
        if(tagName.equals("head")
                || tagName.equals("body"))
            return true;
        return false;
    }
    
    public HTMLHtmlElement getHtmlElement(HTMLDocument ownerDocument){
        if(ownerDocument == null || ownerDocument.htmlElement != null)
            throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "NO_MODIFICATION_ALLOWED_ERR");
        
        HTMLHtmlElement hhe = new HTMLHtmlElement(ownerDocument);
        return hhe;
    }

    @Override
    public String getVersion() {
        return Double.toString(ownerHTMLDocument.HtmlVersion);
    }

    @Override
    public void setVersion(String version) {
        ownerHTMLDocument.HtmlVersion = Double.parseDouble(version);
    }
    
}
