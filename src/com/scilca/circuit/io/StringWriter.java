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
public final class StringWriter {
    private CharacterWriter cw;
    
    public StringWriter(CharacterWriter cw){
        this.cw = cw;
    }
    
    public boolean write(char c){
        return cw.write(c);
    }
    
    public void write(String buffer){
        for(char c : buffer.toCharArray())
            write(c);
    }
    
    public void write(char[] charBuffer){
        for(char c : charBuffer)
            write(c);
    }
    
    public void write(Object o){
        write(o.toString());
    }
    
    public void newLine(){
        write((char) Character.LINE_SEPARATOR);
    }
    
}
