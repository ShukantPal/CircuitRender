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
package com.scilca.dom;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Sukant Pal
 */
public class NodeList implements org.w3c.dom.NodeList, Iterable<org.w3c.dom.Node>{

    ArrayList<org.w3c.dom.Node> nodeArray;
    
    public NodeList(){
        nodeArray = new ArrayList<>();
    }
    
    public NodeList(ArrayList<org.w3c.dom.Node> nodeArray){
        this.nodeArray = nodeArray;
    }
    
    @Override
    public org.w3c.dom.Node item(int index) {
        return nodeArray.get(index);
    }

    @Override
    public int getLength() {
        return nodeArray.size();
    }

    @Override
    public Iterator<org.w3c.dom.Node> iterator() {
        return nodeArray.iterator();
    }
    
    public void add(org.w3c.dom.Node node){
        nodeArray.add(node);
    }
    
    public boolean add(org.w3c.dom.Node node, int index){
        return nodeArray.add(node);
    }
    
    public boolean remove(org.w3c.dom.Node node){
        return nodeArray.remove(node);
    }
    
    public org.w3c.dom.Node remove(int index){
        return nodeArray.remove(index);
    }
    
    public void set(org.w3c.dom.Node node, int index){
        nodeArray.set(index, node);
    }
    
    public NodeList cloneList(){
        NodeList clone = new NodeList();
        clone.nodeArray = (ArrayList) this.nodeArray.clone();
        return clone;
    }
    
    @Override
    public String toString(){
        return nodeArray.toString();
    }
    
}
