/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.dom.html;

import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 *
 * @author Sukant Pal
 */
public class HTMLTitleElement extends HTMLElement implements org.w3c.dom.html.HTMLTitleElement{

    public HTMLTitleElement(HTMLDocument ownerDocument) {
        super("title", ownerDocument);
    }

    @Override
    public String getText() {
        String text = new String();
        for(Node childNode : getChildNodes()){
            if(childNode.getNodeType() == Node.TEXT_NODE)
                text += ((Text) childNode).getData();
        }
        
        return text;
    }

    @Override
    public void setText(String text) {
        for(int childIndex = 0; 
                childIndex < getChildNodes().getLength();
                childIndex++)
            if(getChildNodes().item(childIndex).getNodeType() == Node.TEXT_NODE)
                getChildNodes().remove(childIndex);
        
        getChildNodes().add(
            getOwnerDocument().createTextNode(text)
        );
    }
    
}
