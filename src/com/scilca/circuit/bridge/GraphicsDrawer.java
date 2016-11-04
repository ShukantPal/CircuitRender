/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.bridge;

import com.scilca.dom.html.HTMLButtonElement;
import com.scilca.dom.html.HTMLDocument;
import java.awt.GridLayout;
import org.w3c.dom.html.HTMLElement;
import javax.swing.JPanel;
import org.w3c.dom.Node;

/**
 *
 * @author Sukant Pal
 */
public final class GraphicsDrawer {
    
    JPanel renderScreen;
    HTMLDocument DOMTree;
    ContainerView GUITree;
    
    static void addToTree(ContainerView GUITree, HTMLElement DOMElement){
        switch(DOMElement.getTagName()){
            case "button":
                ButtonView buttonView = new ButtonView();
                GUITree.add(ButtonView.getButtonView((HTMLButtonElement) DOMElement));
                break;
            case "div":
                ContainerView divView = new ContainerView();
                GUITree.add(divView);
                break;
        }
        
        for(Node childNode 
                : (com.scilca.dom.NodeList) DOMElement.getChildNodes()){
            if(childNode.getNodeType() == Node.ELEMENT_NODE){
                addToTree(GUITree, (HTMLElement) childNode);
            }
        }
    }
    
    static ContainerView getGraphicsTree(HTMLDocument DOMTree){
        ContainerView renderView = new ContainerView();
        System.out.println(DOMTree.getBody());
        
        HTMLElement bodyElement = DOMTree.getBody();
        for(Node childNode 
                : (com.scilca.dom.NodeList) bodyElement.getChildNodes()){
            if(childNode.getNodeType() == Node.ELEMENT_NODE){
                addToTree(renderView, (HTMLElement) childNode);
            }
        }
        
        return renderView;
    }
    
    public GraphicsDrawer(JPanel renderScreen, HTMLDocument DOMTree){
        this.renderScreen = renderScreen;
        this.DOMTree = DOMTree;
        this.GUITree = getGraphicsTree(DOMTree);
        
        GridLayout singleLayout = new GridLayout();
        renderScreen.setLayout(singleLayout);
        renderScreen.add(GUITree);
    }
    
    public void paintTree(){
        GUITree.setVisible(true);
        GUITree.repaint();
    }
    
}
