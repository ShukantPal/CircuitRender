/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.dom.html;

import org.w3c.dom.DOMException;

/**
 *
 * @author Sukant Pal
 */
public class HTMLBaseFontElement extends HTMLElement implements org.w3c.dom.html.HTMLBaseFontElement{
    
    public static final String DEFAULT_COLOR = "black";
    public static final String DEFAULT_FACE = "Arial";
    public static final String DEFAULT_SIZE = "2";
    
    public HTMLBaseFontElement(HTMLDocument ownerDocument){
        super("basefont", ownerDocument);
    }

    @Override
    public boolean isValidChild(String tagName){
        return false;
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
            double testValid;
            testValid = Double.parseDouble(size);
        } catch(NumberFormatException e){
            throw new DOMException(DOMException.SYNTAX_ERR, "SYNTAX(NUMBER_NEEDED)_ERR");
        }
        
        setAttr("size", size);
    }
    
}
