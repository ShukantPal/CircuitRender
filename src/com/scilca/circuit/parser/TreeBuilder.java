/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.parser;

import com.scilca.dom.*;
import java.util.ArrayList;
import org.w3c.dom.Text;

/**
 * TreeBuilder is used by the HTMLParser to construct the DOM tree for a 
 * HTML document.
 * @author Sukant Pal
 * @param <T> - The Elements to add to the DOM tree
 * @param <E> - The DOM document to build.
 */
public final class TreeBuilder<T extends Element, E extends Document> {
    
    E treeDoc;
    ArrayList<T> stackOfOpenElements;
    
    public TreeBuilder(E treeDoc){
        this.treeDoc = treeDoc;
        this.stackOfOpenElements = new ArrayList<>();
    }
    
    public void addElement(T element, boolean autoClose){
        if(stackOfOpenElements.size() > 0)
            stackOfOpenElements.get(
                    stackOfOpenElements.size() - 1
            ).appendChild(element);
        else treeDoc.appendChild(element);
        
        if(!autoClose)
            stackOfOpenElements.add(element);
    }
    
    public void closeElementsTill(String tagName){
        if(stackOfOpenElements.size() > 0
                && stackOfOpenElements.stream().anyMatch(
                        (T e) -> {
                            if(e.getTagName().equals(tagName))
                                return true;
                            return false;
                        }
                ))
            for(int elementIndex = stackOfOpenElements.size() - 1; 
                    elementIndex >= 0; // Element Index needs no check due to if statement
                    // above
                    elementIndex--){
                if(stackOfOpenElements.get(elementIndex).getTagName().equals(tagName)){
                    stackOfOpenElements.remove(elementIndex);
                    break;
                }
                stackOfOpenElements.remove(elementIndex);
            }
    } 
    
    public void addComment(Comment DOMComment){
        if(stackOfOpenElements.isEmpty())
            treeDoc.appendChild(DOMComment);
        else stackOfOpenElements.get(
                stackOfOpenElements.size() - 1
        ).appendChild(DOMComment);
    }
    
    public void addText(Text DOMText){
        if(!stackOfOpenElements.isEmpty())
            stackOfOpenElements.get(
                stackOfOpenElements.size() - 1
            ).appendChild(DOMText);
    }
    
    public E getDocument(){
        return treeDoc;
    }
    
    public ArrayList<T> getStack(){
        return stackOfOpenElements;
    }
    
}
