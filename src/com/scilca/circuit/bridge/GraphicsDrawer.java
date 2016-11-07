/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.bridge;

import com.scilca.dom.html.HTMLButtonElement;
import com.scilca.dom.html.HTMLDocument;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
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
    JPanel GUITree;
    
    static void addToTree(JComponent GUITree, HTMLElement DOMElement){
        JComponent jComp = null;
        GUITree.setLayout(new FlowLayout());
        
        switch(DOMElement.getTagName()){
            case "button":
                JButton jButton = ComponentLoader.getButton((HTMLButtonElement) DOMElement);
                GUITree.add(jButton);
                jComp = jButton;
                break;
            case "div":
                JPanel section = new JPanel();
                section.setLayout(new GridLayout());
                GUITree.add(section);
                jComp = section;
                break;
            case "form":
                JPanel sectionForm = new JPanel();
                sectionForm.setLayout(new GridLayout());
                GUITree.add(sectionForm);
                jComp = sectionForm;
                break;
        }
        
        if(jComp != null)
            for(Node childNode 
                    : (com.scilca.dom.NodeList) DOMElement.getChildNodes()){
                if(childNode.getNodeType() == Node.ELEMENT_NODE){
                    addToTree(jComp, (HTMLElement) childNode);
                }
            }
    }
    
    static JPanel getGraphicsTree(HTMLDocument DOMTree){
        JPanel renderView = new JPanel();
        
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
