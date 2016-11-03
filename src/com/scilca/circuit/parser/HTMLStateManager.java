/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.parser;

import com.scilca.circuit.states.HTMLStates;

/**
 *
 * @author Sukant Pal
 */
public final class HTMLStateManager {
   
    HTMLStates currentState;
    
    HTMLStateManager(HTMLStates initState){
        currentState = initState;
    }
    
    public static HTMLStateManager getManager(){
        return new HTMLStateManager(HTMLStates.INITIAL);
    }
    
    public void switchOnAddElement(String tagName){
        switch(currentState){
            case INITIAL:
            case BEFORE_HTML:
                if(tagName.equals("html"))
                    currentState = HTMLStates.BEFORE_HEAD;
                break;
            case BEFORE_HEAD:
                if(tagName.equals("head"))
                    currentState = HTMLStates.IN_HEAD;
                break;
            case IN_HEAD:
                if(tagName.equals("noscript"))
                    currentState = HTMLStates.IN_HEAD_NOSCRIPT;
                break;
            case AFTER_HEAD:
                if(tagName.equals("body"))
                    currentState = HTMLStates.IN_BODY;
                break;
        }
    }
    
    public void closeElement(String tagName){
        switch(currentState){
            case IN_HEAD:
                if(tagName.equals("head"))
                    currentState = HTMLStates.AFTER_HEAD;
                break;
            case IN_HEAD_NOSCRIPT:
                if(tagName.equals("noscript"))
                    currentState = HTMLStates.IN_HEAD;
                break;
            case IN_BODY:
                if(tagName.equals("body"))
                    currentState = HTMLStates.AFTER_BODY;
                break;
        }
    }
    
    public HTMLStates getState(){
        return currentState;
    }
    
}
