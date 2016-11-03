/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.dom.html;

/**
 *
 * @author Sukant Pal
 */
public class HTMLFormattingElement extends HTMLElement{
    
    public static enum FORMATTING_TYPE{
        BOLD, ITALICS, UNDERLINE, SMALL, BIG,
    }
    
    FORMATTING_TYPE ft;
    
    public HTMLFormattingElement(FORMATTING_TYPE ft, HTMLDocument ownerDocument){
        super(ft.toString(), ownerDocument);
        this.ft = ft;
    }
    
    public FORMATTING_TYPE getFormattingType(){
        return ft;
    }
    
}
