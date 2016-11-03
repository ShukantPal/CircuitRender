/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.parser;

import java.nio.file.Path;
import java.util.ArrayList;
import com.scilca.circuit.io.CharacterReader;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author Sukant Pal
 */
public final class CircuitHTMLFactory {
    
    static ArrayList<String> createdParsers = new ArrayList<>();
    
    boolean isStateTaken(String stateStr){
        for(String cp : createdParsers)
            if(cp.equals(stateStr))
                return true;
        return false;
    }
    
    public CircuitHTML getCircuitHTML(Path filePath) throws java.io.FileNotFoundException, ParserConfigurationException{
        if(isStateTaken(filePath.toString()))
            throw new IllegalArgumentException();
        else
            return new CircuitHTML(filePath.toString());
    }
    
    public CircuitHTML getCiruitHTML(CharacterReader inputSource) throws ParserConfigurationException{
         return new CircuitHTML(inputSource);
    }
    
}
