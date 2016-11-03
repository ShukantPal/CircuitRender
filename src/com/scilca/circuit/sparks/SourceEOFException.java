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
public class SourceEOFException extends RuntimeException{
    
    public SourceEOFException(){
        super();
    }
    
    public SourceEOFException(String msg){
        super(msg);
    }
    
}
