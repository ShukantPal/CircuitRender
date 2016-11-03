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
public class HTMLAppletElement extends HTMLElement implements org.w3c.dom.html.HTMLAppletElement{
    
    public String DEFAULT_ALIGN = "center";
    
    public HTMLAppletElement(HTMLDocument ownerDocument){
        super("applet", ownerDocument);
    }

    @Override
    public String getAlign() {
        return getAttr("size", DEFAULT_ALIGN);
    }

    @Override
    public void setAlign(String align) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAlt() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAlt(String alt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getArchive() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setArchive(String archive) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getCode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCode(String code) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getCodeBase() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCodeBase(String codeBase) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getHeight() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHeight(String height) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getHspace() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHspace(String hspace) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setName(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getObject() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setObject(String object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getVspace() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setVspace(String vspace) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getWidth() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWidth(String width) {
        throw new UnsupportedOperationException();
    }
    
}
