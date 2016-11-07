/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.bridge;

import com.scilca.dom.html.HTMLButtonElement;
import javax.swing.JButton;

/**
 *
 * @author Sukant Pal
 */
public final class ComponentLoader {
    
    private ComponentLoader(){
    }
    
    public static JButton getButton(HTMLButtonElement loadedElement){
        ButtonView initButton = new ButtonView();
        initButton.setText(loadedElement.getButtonText());
        return initButton;
    }
    
}
