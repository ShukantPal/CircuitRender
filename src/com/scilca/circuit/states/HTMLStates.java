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
package com.scilca.circuit.states;

/**
 *
 * @author Sukant Pal
 */
public enum HTMLStates {
    
    /**
    * The INITIAL State defines the state of the HTML Parser in which it has 
    * not started the TokenIterator. After starting the TokenIterator this 
    * state turns over to the BEFORE_HTML State.
    */
    INITIAL,
    
    /**
     * The BEFORE_HTML state defines the state of the HTML Parser in which it 
     * has started the TokenIterator but has not reached a HTML element. Speaking 
     * of a general HTML or XHTML document, this state means that the DOCTYPE or
     * XML declaration are being read.
     */
    BEFORE_HTML, 
    
    /**
     * The BEFORE_HEAD state implies that the parser is waiting for a HEAD tag to 
     * come and has already found the HTML tag.
     */
    BEFORE_HEAD, 
    
    /**
     * The IN_HEAD state implies that the parse is inside the head element and is 
     * only adding elements that can be the children of the HEAD element.
     */
    IN_HEAD, 
    
    /**
     * The IN_HEAD_NOSCRIPT state means that the parser in a NOSCRIPT element which 
     * is in the HEAD element of the HTML document.
     */
    IN_HEAD_NOSCRIPT, 
    
    /**
     * The AFTER_HEAD state implies that the parser is waiting for a BODY element to 
     * come across. If a FRAMSET element comes, then it will turnover to IN_FRAMESET 
     * and if it sees a BODY element it will come into IN_BODY state.
     */
    AFTER_HEAD, IN_BODY,
    TEST, IN_TABLE, IN_TABLE_TEXT, IN_CAPTION, IN_COLUMN_GROUP, IN_ROW, IN_CELL, IN_SELECT,
    IN_SELECT_IN_TABLE, AFTER_BODY, IN_FRAMESET, AFTER_FRAMESET, AFTER_AFTER_BODY, AFTER_AFTER_FRAMESET
}
