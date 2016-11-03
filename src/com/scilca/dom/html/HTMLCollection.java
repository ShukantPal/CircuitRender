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

import org.w3c.dom.Node;
import java.util.HashMap;

/**
 *
 * @author Sukant Pal
 */
public class HTMLCollection implements org.w3c.dom.html.HTMLCollection{

    HashMap<String, HTMLElement> elementCollection;
    
    protected HTMLCollection(){
        elementCollection = new HashMap<>();
    }
    
    @Override
    public int getLength() {
        return elementCollection.size();
    }

    @Override
    public Node item(int index) {
        return elementCollection.get((String) elementCollection.keySet().toArray()[index]);
    }
    
    public HTMLElement elementAt(int index){
        return elementCollection.get((String) elementCollection.keySet().toArray()[index]);
    }

    @Override
    public Node namedItem(String name) {
        return elementCollection.get(name);
    }
    
    public HTMLElement namedElement(String name){
        return elementCollection.get(name);
    }
    
    protected void add(HTMLElement htmlElement){
        elementCollection.put(htmlElement.getNodeName(), htmlElement);
    }
    
    protected HTMLElement remove(String elementName){
        return elementCollection.remove(elementName);
    }
    
    protected void addAll(HTMLCollection subSet){
        for(int eleIndex = 0; eleIndex < subSet.getLength(); eleIndex++){
            add((HTMLElement) subSet.item(eleIndex));
        }
    }
    
}
