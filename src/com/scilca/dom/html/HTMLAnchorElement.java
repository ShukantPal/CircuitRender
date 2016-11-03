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
public class HTMLAnchorElement extends HTMLElement implements org.w3c.dom.html.HTMLAnchorElement{
    
    protected HTMLAnchorElement(HTMLDocument ownerDocument){
        super("a", ownerDocument);
    }

    @Override
    public String getAccessKey() {
        return getAttr("accesskey", null);
    }

    @Override
    public void setAccessKey(String accessKey) {
        setAttr("accesskey", accessKey);
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
    public String getCoords() {
        return getAttr("coords", null);
    }

    @Override
    public void setCoords(String coords) {
        setAttr("coords", coords);
    }

    @Override
    public String getHref() {
        return getAttr("href", getOwnerDocument().getDocumentURI());
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
    public String getName() {
        return getAttr("name", "");
    }

    @Override
    public void setName(String name) {
        setAttr("name", name);
    }

    @Override
    public String getRel() {
        return getAttr("rel", "");
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
    public String getShape() {
        return getAttr("shape", "");
    }

    @Override
    public void setShape(String shape) {
        setAttr("shape", shape);
    }

    @Override
    public int getTabIndex() {
        Attr tabindexAttr = getAttributeNode("tabindex");
        if(tabindexAttr == null)
            return -1;
        else return Integer.parseInt(tabindexAttr.getValue());
    }

    @Override
    public void setTabIndex(int tabIndex) {
        setAttr("tabindex", Integer.toString(tabIndex));
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
        return getAttr("type", "text/html");
    }

    @Override
    public void setType(String type) {
        setAttr("type", type);
    }

    @Override
    public void blur() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void focus() {
        throw new UnsupportedOperationException();
    }
    
}
