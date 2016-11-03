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
public class HTMLHeadingElement extends HTMLElement implements org.w3c.dom.html.HTMLHeadingElement{
    
    public static final String DEFAULT_ALIGN = "left";
    
    protected HTMLHeadingElement(int size, HTMLDocument ownerDocument){
        super("h" + Integer.toString(size), ownerDocument);
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
    
}
