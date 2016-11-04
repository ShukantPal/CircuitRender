/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.parser;

import com.scilca.circuit.states.HTMLStates;
import com.scilca.dom.html.HTMLBodyElement;
import com.scilca.dom.html.HTMLDocument;
import com.scilca.dom.html.HTMLElement;

/**
 *
 * @author Sukant Pal
 */
public final class HTMLStateManager {
   
    HTMLStates currentState;
    TreeBuilder DOMBuilder;
    
    HTMLStateManager(HTMLStates initState, TreeBuilder DOMBuilder){
        currentState = initState;
        this.DOMBuilder = DOMBuilder;
    }
    
    public static HTMLStateManager getManager(TreeBuilder DOMBuilder){
        return new HTMLStateManager(HTMLStates.INITIAL, DOMBuilder);
    }
    
    public void switchOnAddElement(HTMLElement foundElement){
        switch(currentState){
            case INITIAL:
            case BEFORE_HTML:
                if(foundElement.getTagName().equals("html"))
                    currentState = HTMLStates.BEFORE_HEAD;
                break;
            case BEFORE_HEAD:
                if(foundElement.getTagName().equals("head")){
                    currentState = HTMLStates.IN_HEAD;
                    ((HTMLDocument) DOMBuilder.getDocument())
                            .setHead(foundElement);
                }
                break;
            case IN_HEAD:
                if(foundElement.getTagName().equals("noscript"))
                    currentState = HTMLStates.IN_HEAD_NOSCRIPT;
                break;
            case AFTER_HEAD:
                if(foundElement.getTagName().equals("body")){
                    currentState = HTMLStates.IN_BODY;
                    ((HTMLDocument) DOMBuilder.getDocument())
                            .setBody((HTMLBodyElement) foundElement);
                    System.out.println("found " + foundElement);
                }
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
