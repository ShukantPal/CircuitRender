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
public class HTMLBaseElement extends HTMLElement implements org.w3c.dom.html.HTMLBaseElement{
    
    public HTMLBaseElement(HTMLDocument ownerDocument){
        super("base", ownerDocument);
    }

    @Override
    public boolean isValidChild(String tagName){
        return false;
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
    public String getTarget() {
        return getAttr("target", null);
    }

    @Override
    public void setTarget(String target) {
        setAttr("target", target);
    }
    
}
