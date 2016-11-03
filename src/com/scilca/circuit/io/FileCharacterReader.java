/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.io;

import java.io.*;

/**
 * 
 * @author Sukant Pal
 */
public final class FileCharacterReader implements CharacterReader{
    FileReader fileReadAccess;
    
    public FileCharacterReader(File file) throws FileNotFoundException{
        fileReadAccess = new FileReader(file);
    }
    
    /**
     * This method accesses the file and returns the next character in the 
     * file. It actually calls the wrapped FileReader object's method and 
     * returns its results.
     * @return - The code point of the next character of the file or -1 if 
     * the file's EOF has passed
     * @throws IOException - If the access to the file causes a IOException 
     * by the FileReader then it is passed directly.
     */
    @Override
    public int read() throws IOException{
        if(backupMode){
            backupMode = false;
            return curChar;
        }
        
        curChar = fileReadAccess.read();
        return curChar;
    }
    
    @Override
    public void reset() throws IOException{
        fileReadAccess.reset();
    }
    
    boolean backupMode;
    int curChar;
    
    @Override
    public void backupMode(){
        backupMode = true;
    }
    
}
