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
public class HTMLHRElement extends HTMLElement implements org.w3c.dom.html.HTMLHRElement{

    public static final String DEFAULT_ALIGN = "center";
    public static final String DEFAULT_SIZE = "200";
    public static final String DEFAULT_WIDTH = "5";
    public static final boolean DEFAULT_NO_SHADE = true;
    
    protected HTMLHRElement(HTMLDocument ownerDocument){
        super("hr", ownerDocument);
    }
    
    @Override
    public String getAlign() {
        Attr alignAttr = getAttributeNode("align");
        if(alignAttr == null)
            return DEFAULT_ALIGN;
        else return alignAttr.getValue();
    }

    @Override
    public void setAlign(String align) {
        Attr alignAttr = getAttributeNode("align");
        if(alignAttr == null)
            setAttribute("align", align);
        else alignAttr.setValue(align);
    }

    @Override
    public boolean getNoShade() {
        Attr noshadeAttr = getAttributeNode("noshade");
        if(noshadeAttr == null)
            return DEFAULT_NO_SHADE;
        else return Boolean.getBoolean(noshadeAttr.getValue());
    }

    @Override
    public void setNoShade(boolean noShade) {
        Attr noshadeAttr = getAttributeNode("noshade");
        if(noshadeAttr == null)
            setAttribute("noshade", Boolean.toString(noShade));
        else noshadeAttr.setValue(Boolean.toString(noShade));
    }

    @Override
    public String getSize() {
        Attr sizeAttr = getAttributeNode("size");
        if(sizeAttr == null)
            return DEFAULT_SIZE;
        else return sizeAttr.getValue();
    }

    @Override
    public void setSize(String size) {
        Attr sizeAttr = getAttributeNode("size");
        if(sizeAttr == null)
            setAttribute("size", size);
        else sizeAttr.setValue(size);
    }

    @Override
    public String getWidth() {
        Attr widthAttr = getAttributeNode("width");
        if(widthAttr == null)
            return DEFAULT_WIDTH;
        else return widthAttr.getValue();
    }

    @Override
    public void setWidth(String width) {
        Attr widthAttr = getAttributeNode("width");
        if(widthAttr == null)
            setAttribute("width", width);
        else widthAttr.setValue(width);
    }
    
}
