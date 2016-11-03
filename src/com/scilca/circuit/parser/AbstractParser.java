/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.parser;

import com.scilca.circuit.io.CharacterReader;
import com.scilca.circuit.io.HTMLTokeniser;
import com.scilca.circuit.io.Tokensier;
import com.scilca.circuit.sparks.ParserStateException;

import com.scilca.dom.html.*;

/**
 * The default circuit parsers that include the provided functionality defined 
 * in the <code>CircuitParser</code> interface are subclasses of <code>AbstractParser
 * </code>
 * @author Sukant Pal
 */
public abstract class AbstractParser implements CircuitParser{
    
    static final String MANUAL_STATE_MSG = "The state of the parser "
            + "is manual and the document cannot be constructed.";
    
    static final String AUTO_STATE_MSG = "The state of the parser "
            + "is not manual and the element cannot be read.";
    
    static String PARSER_CLOSED_MSG = "The parser has been closed "
            + "and it cannot read the input further.";
    
    boolean parserStarted;
    boolean onManualRun;
    
    boolean parserClosed;
    
    CharacterReader inputSource;
    
    Tokensier inputTokensier;
    
    public AbstractParser(CharacterReader inputSource){
        this.inputSource = inputSource;
        this.inputTokensier = new HTMLTokeniser(inputSource);
        this.onManualRun = false;
        this.parserStarted = false;
        this.parserClosed = false;
    }
    
    @Override
    public boolean isStarted(){
        return parserStarted;
    }
    
    @Override
    public boolean getManualRun(){
        return onManualRun;
    }
    
    public void setManualRun(boolean onManualRun) throws ParserStateException{
        if(isStarted())
            throw new ParserStateException("The parser cannot switch states. The parser has "
            + "already started.");
        this.onManualRun = onManualRun;
    }
    
    @Override
    public boolean getClosed(){
        return parserClosed;
    }
    
    @Override
    public HTMLDocument closeParser(){
        parserClosed = true;
        return null;
    }
    
    @Override
    public abstract HTMLDocument parseDocument() throws Exception;
    
    @Override
    public abstract HTMLElement constructElement() throws Exception;
    
    @Override
    public abstract String currentToken();
    
    @Override
    public String toString(){
        return "Circuit Parser (Input - " + inputSource + " )";
    }
    
}
