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
 * @deprecated in HTML 4.01
 * @author Sukant Pal
 */
public class HTMLFontElement extends HTMLElement implements org.w3c.dom.html.HTMLFontElement{

    public static final String DEFAULT_COLOR = "black";
    public static final String DEFAULT_FACE = "Arial";
    public static final String DEFAULT_SIZE = "2";
    
    protected HTMLFontElement(HTMLDocument ownerDocument){
        super("font", ownerDocument);
    }
    
    @Override
    public String getColor() {
        return getAttr("color", DEFAULT_COLOR);
    }

    @Override
    public void setColor(String color) {
        setAttr("color", color);
    }

    @Override
    public String getFace() {
        return getAttr("face", DEFAULT_FACE);
    }

    @Override
    public void setFace(String face) {
        setAttr("face", face);
    }

    @Override
    public String getSize() {
        return getAttr("size", DEFAULT_SIZE);
    }

    @Override
    public void setSize(String size) {
        try{
            double setValid;
            setValid = Double.parseDouble(size);
        } catch(NumberFormatException e){
            throw new DOMException(DOMException.SYNTAX_ERR, "SYNTAX(NUMBER NEEDED)_ERR");
        }
        
        setAttr("size", size);
    }
    
}
