/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.io;

import java.util.*;

/**
 * HTMLTokeniser is a interface that is a implementation for reading 
 tokens from a input source. Different TokenIterators classes can read 
 * from different sources. This is because they use CharacterReaders for 
 * indirectly accessing the input source. 
 * @author Sukant Pal
 */
public final class HTMLTokeniser implements Tokensier{
    public static final String START_ELEMENT = "<";
    public static final String END_ELEMENT = ">";
    public static final String ATTR_SIGN = "=";
        
    String currentToken;
    String previousToken;
        
    List<String> tokens;
    int tokenCount;
    
    boolean readOnly = false;
    
    CharacterReader tokenReader;
    private int readChar = -1;
    
    boolean backupMode = false;
    
    private static boolean isValidifiedCharacter(char nextC){
        return Character.isAlphabetic(nextC) || Character.isDigit(nextC) || (nextC != ' ' && nextC != '='
                && nextC != '<' && nextC != '>' && nextC != '/' && nextC!='\"'
                && nextC != '.' && nextC != Character.LINE_SEPARATOR);
    }
        
    private static boolean isWhiteSpaceOnly(String s){
        for(char c : s.toCharArray())
            if(c != ' ')
                return false;
        return true;
    }
        
    public HTMLTokeniser(CharacterReader inputSource){
        this.tokenReader = inputSource;
        currentToken = null;
        previousToken = null;
        tokens = new ArrayList<>();
    }
        
    @Override
    public String nextToken() throws Exception{
        if(backupMode){
            backupMode = false;
            return currentToken;
        }
        
        String nextToken = new String();
        int nextInt = tokenReader.read();
        
        if(nextInt == -1 && readChar == -1)
            return null;

        char nextChar =  (char) (readChar != -1 ? readChar : nextInt);
        
        if(Character.LINE_SEPARATOR == nextChar || nextChar == ' ' || nextChar == 9
                || nextChar == 10 || nextChar == '\t'){
          readChar = -1;
          return nextToken();  
        } else if(nextChar == '<'){
            nextToken = START_ELEMENT;
            int nextI = readChar == nextChar ? nextInt : tokenReader.read();
            if(nextI == -1)
                return nextToken;
           
            char nextC = (char) nextI;
                switch (nextC) {
                    case '/':
                        nextToken = nextToken.concat("/");
                        readChar = -1;
                        break;
                    case '?':
                        nextToken = nextToken.concat("?");
                        readChar = -1;
                        break;
                    case '!':
                        nextToken = nextToken.concat("!");
                        readChar = -1;
                        break;
                    default:
                        readChar = nextC;
                        break;
                }
            } else if(nextChar == '>'){
                nextToken = END_ELEMENT;
                readChar = nextInt;
            } else if(nextChar == '?'){
                nextToken = "?";
                readChar  = nextInt;
        } else if(HTMLTokeniser.isValidifiedCharacter(nextChar)){
            nextToken += nextChar;
            if(readChar == nextChar && nextInt != '>')
                nextToken += (char) nextInt; // This precaution is for tags 
            // with one letter names like 'p'
            else if(readChar == nextChar && nextInt == '>')
                tokenReader.backupMode(); // If a one letter tag is found 
            // then the closing tag is read. So we need to backup here.
            
            while(true){
                int nextI = tokenReader.read();
                if(nextI == -1)
                    break;
                char nextC = (char) nextI;
                if(nextC != '\"' && nextC != '='
                        && nextC != '>' && nextC != ' '
                        && (nextC != '<' || !tokens.get(tokens.size() - 1).equals(">")))
                nextToken += nextC;
                else {
                    readChar = nextC;
                    break;
                }
            }
            nextToken = nextToken.trim();
        } else if(nextChar == '='){
            nextToken = ATTR_SIGN;
            readChar = nextInt;
        } else if(nextChar == '\"'){
            nextToken = "\"";
            readChar = nextInt;
        } else if(nextChar == '/'){
            nextToken = "/";
            int nextI = readChar == nextChar ? nextInt : tokenReader.read();
            if(nextI == -1)
               return nextToken;
                
            char nextC = (char) nextI;
            if(nextC == '>'){
                nextToken += ">";
                readChar = -1;
        }
        else readChar = nextI;
        } else {
            readChar = -1;
            return nextToken();
        }
            
        if(readChar == ' ' || readChar == '\t')
            readChar = -1;
            
        nextToken = nextToken.trim();
        if(nextToken.equals("") || isWhiteSpaceOnly(nextToken))
            return nextToken();
            
        previousToken = currentToken;
        currentToken = nextToken;
        tokens.add(currentToken);
        return nextToken;
    }
    
    /**
     * While parsing a HTML document a parser function may try to search for a 
     * ending point which is a starting point for the next function. If the 
     * first function needs to go back by one point it may use this function. This 
     * allows the second function to start correctly.
     */
    public void backupMode(){
        this.backupMode = true;
    }
        
    /**
     * The current token in the buffer of the HTMLTokeniser.
     * @return - The current token
     */
    public String currentToken(){
        return currentToken;
    }
        
    /**
     * The previous token in the buffer
     * @return 
     */
    public String previousToken(){
        return previousToken;
    }
        
    public String[] readTokens(){
        return tokens.toArray(new String[tokens.size()]);
    }
    
    public void reset() throws Exception{
        tokenReader.reset();
        currentToken = null;
        previousToken = null;
        tokens = new ArrayList<>();
    }
    
}
