/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.parser;

import com.scilca.circuit.sparks.ParserStateException;
import com.scilca.dom.html.HTMLDocument;
import com.scilca.dom.html.HTMLElement;

/**
 * All parsers in the circuit browser implement this interface
 * @author Sukant Pal
 */
public interface CircuitParser {
    
    public boolean getClosed();
    
    public HTMLDocument parseDocument() throws Exception;
    
    public HTMLElement constructElement() throws Exception;
    
    public String currentToken();
    
    public default boolean getManualRun(){
        return false;
    }
    
    public void setManualRun(boolean onManualRun) throws ParserStateException;
    
    public boolean isStarted();
    
    public HTMLDocument closeParser();
    
}
