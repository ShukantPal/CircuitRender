/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.sparks;

/**
 *
 * @author Sukant Pal
 */
public class ParserStateException extends IllegalStateException{
    
    public ParserStateException(){
        super();
    }
    
    public ParserStateException(String msg){
        super(msg);
    }
    
    public ParserStateException(String msg, Throwable cause){
        super(msg, cause);
    }
    
    public ParserStateException(Throwable cause){
        super(cause);
    }
    
}
