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
public class HTMLDivElement extends HTMLElement implements org.w3c.dom.html.HTMLDivElement{

    public static final String DEFAULT_ALIGN = "left";
    
    protected HTMLDivElement(HTMLDocument ownerDocument){
        super("div", ownerDocument);
    }
    
    @Override
    public String getAlign() {
        return getAttr("align", DEFAULT_ALIGN);
    }

    @Override
    public void setAlign(String align) {
        setAttr("align", align);
    }
    
}
