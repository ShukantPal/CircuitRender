/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.io;

/**
 * A TokenIterator uses this class to provide a interface for HTML parsing 
 * using any input source. This can be implemented for any input source 
 * relatively quicker than a full TokenIterator.
 * @author Sukant Pal
 */
public interface CharacterReader {
    
    /**
     * The TokenIterator calls this method while trying to get the next character 
     * in a input source. This method should return the code point of the next 
     * character in the input source. If the input source's end has already been 
     * passed then -1 should be returned indicating that the input source cannot 
     * be read further.
     * @return - The code point of the next character in the input source or -1 if 
     * the input source's end has been reached.
     * @throws Exception - If the CharacterReader fails to read the next character.
     */
    public int read() throws Exception;
    
    public void reset() throws Exception;
    
    public void backupMode();
    
}
