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

/**
 *
 * @author Sukant Pal
 */
public abstract class CharacterData extends Node implements org.w3c.dom.CharacterData{

    static String deleteRange(String DataHandle, int pos1, int pos2){
        String newData = new String();
        for(int i=0; i<pos1; i++)
            newData+=DataHandle.charAt(i);
        for(int i=pos2+1; i<DataHandle.length(); i++)
            newData+=DataHandle.charAt(i);
        return newData;
    }
    
    static String insertRange(String DataHandle, String range, int pos){
        String newData = new String();
        for(int i=0; i<pos && i<DataHandle.length(); i++) 
            newData+=DataHandle.charAt(i);
        newData+=range;
        for(int i=pos; i<DataHandle.length(); i++)
            newData+=DataHandle.charAt(i);
        return newData;
    }
    
    protected static String getConvertedString(String characterData){
        if(!characterData.contains("&"))
            return characterData;
        
        for(int charIndex = 0; charIndex < characterData.length(); charIndex++){
            char charAt = characterData.charAt(charIndex);
            if(charAt == '&'){
                String shortForm = new String();
                int oldPos = charIndex;
                for(int shortIndex = charIndex; shortIndex < characterData.length(); shortIndex++){
                    char shortChar= characterData.charAt(shortIndex);
                    shortForm += shortChar;
                    if(shortChar == ';'){
                        charIndex = shortIndex + 1;
                        break;
                    }
                }

                switch(shortForm){
                    case "&lt;":
                        shortForm = "<";
                        break;
                    case "&gt;":        
                        shortForm = ">";
                        break;
                    case "&amp;":
                        shortForm = "&";
                        break;
                    case "&apos;":
                        shortForm = "\'";
                        break;
                    case "&quot;":
                        shortForm = "\"";
                        break;
                }
                
                // Put the changes in the string.
                characterData = deleteRange(characterData, oldPos, charIndex - 1);
                characterData = insertRange(characterData, shortForm, oldPos);
            }
        }

        return characterData;
    }
    
    public CharacterData(String nodeName, String nodeValue, short nodeType, Document ownerDocument){
        super(nodeName, nodeType, ownerDocument);
        this.nodeValue = nodeValue;
    }
    
    @Override
    public String getData() throws DOMException {
        return nodeValue;
    }

    @Override
    public void setData(String data) throws DOMException {
        nodeValue = data;
    }

    @Override
    public int getLength() {
        return getData().length();
    }

    @Override
    public String substringData(int offset, int count) throws DOMException {
        return getData().substring(offset, offset + count);
    }

    @Override
    public void appendData(String arg) throws DOMException {
        nodeValue += arg;
    }

    void checkDOMString(String DOMString) throws DOMException{
        if(DOMString.contains("<")
                || DOMString.contains(">"))
            throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "INVALID_CHARACTER_DATA");
    }
    
    void checkIndex(int offset, int count) throws DOMException{
        if(offset < 0 || offset + count >= getNodeValue().length())
            throw new DOMException(DOMException.INDEX_SIZE_ERR, "INDEX_SIZE_ERR");
    }
    
    @Override
    public void insertData(int offset, String arg) throws DOMException {
        checkDOMString(arg);
        checkIndex(offset, 0);
        nodeValue = insertRange(nodeValue, arg, offset);
    }

    @Override
    public void deleteData(int offset, int count) throws DOMException {
        checkIndex(offset, count);
        nodeValue = deleteRange(nodeValue, offset, offset + count);
    }

    @Override
    public void replaceData(int offset, int count, String arg) throws DOMException {
        if(count != arg.length())
            throw new DOMException(DOMException.DOMSTRING_SIZE_ERR, "DOMSTRING_SIZE_ERR");
        checkDOMString(arg);
        checkIndex(offset, count);
        
        deleteData(offset, count);
        insertData(offset, arg);
    }
    
}
