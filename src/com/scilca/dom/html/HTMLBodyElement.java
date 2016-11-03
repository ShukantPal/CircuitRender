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
public class HTMLBodyElement extends HTMLElement implements org.w3c.dom.html.HTMLBodyElement{

    public static final String DEFAULT_BACKGROUND = "white";
    public static final String DEFAULT_LINK = "blue";
    public static final String DEFAULT_ALINK = "purple";
    public static final String DEFAULT_VLINK = "purple";
    public static final String DEFAULT_TEXT = "black";
    
    protected HTMLBodyElement(HTMLDocument ownerDocument) {
        super("body", ownerDocument);
    }

    @Override
    public String getALink() {
        Attr alinkAttr = getAttributeNode("alink");
        if(alinkAttr != null)
            return alinkAttr.getValue();
        else return DEFAULT_ALINK;
    }

    @Override
    public void setALink(String aLink) {
        Attr alinkAttr = getAttributeNode("alink");
        if(alinkAttr == null)
            setAttribute("alink", aLink);
        else alinkAttr.setValue(aLink);
    }

    @Override
    public String getBackground() {
        Attr backgroundAttr = getAttributeNode("background");
        if(backgroundAttr == null)
            return null;
        else return backgroundAttr.getValue();
    }

    @Override
    public void setBackground(String background) {
        Attr backgroundAttr = getAttributeNode("background");
        if(backgroundAttr == null)
            setAttribute("background", background);
        else backgroundAttr.setValue(background);
    }

    @Override
    public String getBgColor() {
        Attr backgroundAttr = getAttributeNode("bgcolor");
        if(backgroundAttr == null)
            return DEFAULT_BACKGROUND;
        else return backgroundAttr.getValue();
    }

    @Override
    public void setBgColor(String bgColor) {
        Attr backgroundAttr = getAttributeNode("bgcolor");
        if(backgroundAttr == null)
            setAttribute("background", bgColor);
        else backgroundAttr.setValue(bgColor);
    }

    @Override
    public String getLink() {
        Attr linkAttr = getAttributeNode("link");
        if(linkAttr != null)
            return linkAttr.getValue();
        else return DEFAULT_LINK;
    }

    @Override
    public void setLink(String link) {
        Attr linkAttr = getAttributeNode("link");
        if(linkAttr == null)
            setAttribute("link", link);
        else linkAttr.setValue(link);
    }

    @Override
    public String getText() {
        Attr textAttr = getAttributeNode("text");
        if(textAttr != null)
            return textAttr.getValue();
        else return DEFAULT_TEXT;
    }

    @Override
    public void setText(String text) {
        Attr textAttr = getAttributeNode("text");
        if(textAttr == null)
            setAttribute("text", text);
        else textAttr.setValue(text);
    }

    @Override
    public String getVLink() {
        Attr vlinkAttr = getAttributeNode("vlink");
        if(vlinkAttr != null)
            return vlinkAttr.getValue();
        else return DEFAULT_VLINK;
    }

    @Override
    public void setVLink(String vLink) {
        Attr vlinkAttr = getAttributeNode("vlink");
        if(vlinkAttr == null)
            setAttribute("vlink", vLink);
        else vlinkAttr.setValue(vLink);
    }
    
    @Override
    public String toString(){
        return "HTMLBodyElement";
    }
    
}
