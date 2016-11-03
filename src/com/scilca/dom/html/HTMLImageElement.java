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

/**
 *
 * @author Sukant Pal
 */
public class HTMLImageElement extends HTMLElement implements org.w3c.dom.html.HTMLImageElement{

    protected HTMLImageElement(HTMLDocument ownerDocument){
        super("img", ownerDocument);
    }
    
    @Override
    public String getLowSrc() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLowSrc(String lowSrc) {
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
    public String getAlign() {
        throw new UnsupportedOperationException();
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
    public String getBorder() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBorder(String border) {
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
    public boolean getIsMap() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setIsMap(boolean isMap) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getLongDesc() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLongDesc(String longDesc) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSrc() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSrc(String src) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getUseMap() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setUseMap(String useMap) {
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
