/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.io;

/**
 *
 * @author Sukant Pal
 */
public interface Tokensier {
    
    public String nextToken() throws Exception;
    
    public String currentToken();
    
    public String previousToken();
    
    public String[] readTokens();
    
    public void reset() throws Exception;
    
    public void backupMode();
    
}
