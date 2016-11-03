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

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

import java.util.HashMap;

/**
 *
 * @author Sukant Pal
 */
public final class EntityMap implements org.w3c.dom.NamedNodeMap{

    private final HashMap<String, org.w3c.dom.Entity> entityMap;
    
    public EntityMap(){
        entityMap = new HashMap<>();
    }
    
    public EntityMap(HashMap<String, org.w3c.dom.Entity> entityMap){
        this.entityMap = entityMap;
    }
    
    @Override
    public Node getNamedItem(String name) {
        return entityMap.get(name);
    }

    @Override
    public Node setNamedItem(Node arg) throws DOMException {
        if(arg.getNodeType() != Node.ENTITY_NODE)
            throw new DOMException(DOMException.TYPE_MISMATCH_ERR, "TYPE_MISMATCH_ERR");
        Node oldValue = getNamedItem(arg.getNodeName());
        entityMap.put(arg.getNodeName(), (org.w3c.dom.Entity) arg);
        return oldValue;
    }

    @Override
    public Node removeNamedItem(String name) throws DOMException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node item(int index) {
        return entityMap.get((String) entityMap.keySet().toArray()[index]);
    }

    @Override
    public int getLength() {
        return entityMap.size();
    }

    @Override
    public Node getNamedItemNS(String namespaceURI, String localName) throws DOMException {
        throw new DOMException(DOMException.NOT_FOUND_ERR, "NOT_FOUND_ERR");
    }

    @Override
    public Node setNamedItemNS(Node arg) throws DOMException {
        throw new DOMException(DOMException.NOT_FOUND_ERR, "NOT_FOUND_ERR");
    }

    @Override
    public Node removeNamedItemNS(String namespaceURI, String localName) throws DOMException {
        throw new DOMException(DOMException.NOT_FOUND_ERR, "NOT_FOUND_ERR");
    }
    
}
