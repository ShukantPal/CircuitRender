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
package com.scilca.circuit.tags;

/**
 * This ENUM is used to differentiate between different HTML DTDs and their 
 * allowed tags.
 * @author Sukant Pal
 */
public enum HTML_DTD {
    
    /**
     * HTML 4.01 Strict DTD
     */
    HTML_4_01_STRICT("-//W3C//DTD HTML 4.01//EN", "http://www.w3.org/TR/html4/strict.dtd", 4.01),
    
    /**
     * HTML 4.01 Transitional DTD
     */
    HTML_4_01_TRANSITIONAL("-//W3C//DTD HTML 4.01 Transitional//EN", "http://www.w3.org/TR/html4/loose.dtd", 4.01),
    
    /**
     * HTML 4.01 Frameset DTD
     */
    HTML_4_01_FRAMESET("-//W3C//DTD HTML 4.01 Frameset//EN", "http://www.w3.org/TR/html4/frameset.dtd", 4.01),
    
    /**
     * HTML 5.....!!!!
     */
    HTML_5(null,null, 5);
    
    public final String publicKey;
    public final String systemKey;
    
    public final double HTML_VERSION;
    
    HTML_DTD(String publicKey, String systemKey, double HTML_VERSION){
        this.HTML_VERSION = HTML_VERSION;
        
        this.publicKey = publicKey;
        this.systemKey = systemKey;
    }
    
    public static HTML_DTD getDTD(String publicKey, String systemKey){
        switch(publicKey){
            case "-//W3C//DTD HTML 4.01//EN":
                if(systemKey.equals("http://www.w3.org/TR/html4/strict.dtd"))
                    return HTML_4_01_STRICT;
            case "-//W3C//DTD HTML 4.01 Transitional//EN":
                if(systemKey.equals("http://www.w3.org/TR/html4/loose.dtd"))
                    return HTML_4_01_TRANSITIONAL;
            case "-//W3C//DTD HTML 4.01 Frameset//EN":
                if(systemKey.equals("http://www.w3.org/TR/html4/frameset.dtd"))
                    return HTML_4_01_FRAMESET;
            case "":
                return HTML_5;
        }
        return null;
    }
    
}
