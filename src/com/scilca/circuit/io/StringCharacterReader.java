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
package com.scilca.circuit.io;

/**
 *
 * @author Sukant Pal
 */
public class StringCharacterReader {
    int pos = 0;
    String strObj;
    
    public StringCharacterReader(String strObj){
        this.strObj = new String();
        for(char sc : strObj.toCharArray())
            this.strObj += sc;
    }
    
    public char read(){
        return strObj.charAt(pos++);
    }
    
    public char read(int mark){
        return strObj.charAt(mark);
    }
    
    public void reset(){
        pos = 0;
    }
    
}
