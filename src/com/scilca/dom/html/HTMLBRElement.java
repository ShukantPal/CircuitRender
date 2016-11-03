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

/**
 *
 * @author Sukant Pal
 */
public class HTMLBRElement extends HTMLElement implements org.w3c.dom.html.HTMLBRElement{

    public static final String DEFAULT_CLEAR = "none";
    
    protected HTMLBRElement(HTMLDocument ownerDocument){
        super("br", ownerDocument);
    }
    
    @Override
    public String getClear() {
        Attr clearAttr = getAttributeNode("clear");
        if(clearAttr == null)
            return DEFAULT_CLEAR;
        else return clearAttr.getValue();
    }

    @Override
    public void setClear(String clear) {
        if(!clear.equals("none")
                && !clear.equals("left")
                && !clear.equals("right")
                && !clear.equals("all"))
            throw new DOMException(DOMException.SYNTAX_ERR, "SYNTAX_ERR");
        Attr clearAttr = getAttributeNode("clear");
        if(clearAttr == null)
            setAttribute("clear", clear);
        else clearAttr.setValue(clear);
    }
    
}
