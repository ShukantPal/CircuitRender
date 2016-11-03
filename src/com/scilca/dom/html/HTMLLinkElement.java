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

/**
 *
 * @author Sukant Pal
 */
public class HTMLLinkElement extends HTMLElement implements org.w3c.dom.html.HTMLLinkElement{
    
    protected HTMLLinkElement(HTMLDocument ownerDocument){
        super("link", ownerDocument);
    }
        
    @Override
    public boolean isValidChild(String tagName){
        return false;
    }
    
    @Override
    public boolean getDisabled() {
        Attr disabledAttr = getAttributeNode("disabled");
        return disabledAttr != null;
    }

    @Override
    public void setDisabled(boolean disabled) {
        if(disabled)
            setAttribute("disabled", "disabled");
    }

    @Override
    public String getCharset() {
        return getAttr("charset", "");
    }

    @Override
    public void setCharset(String charset) {
        setAttr("charset", charset);
    }

    @Override
    public String getHref() {
        return getAttr("href", null);
    }

    @Override
    public void setHref(String href) {
        setAttr("href", href);
    }

    @Override
    public String getHreflang() {
        return getAttr("hreflang", "en");
    }

    @Override
    public void setHreflang(String hreflang) {
        setAttr("hreflang", hreflang);
    }

    @Override
    public String getMedia() {
        return getAttr("media", "screen");
    }

    @Override
    public void setMedia(String media) {
        setAttr("media", media);
    }

    @Override
    public String getRel() {
        return getAttr("rel", null);
    }

    @Override
    public void setRel(String rel) {
        setAttr("rel", rel);
    }

    @Override
    public String getRev() {
        return getAttr("rev", "");
    }

    @Override
    public void setRev(String rev) {
        setAttr("rev", rev);
    }

    @Override
    public String getTarget() {
        return getAttr("target", null);
    }

    @Override
    public void setTarget(String target) {
        setAttr("target", target);
    }

    @Override
    public String getType() {
        return getAttr("type", null);
    }

    @Override
    public void setType(String type) {
        setAttr("type", type);
    }
    
}
