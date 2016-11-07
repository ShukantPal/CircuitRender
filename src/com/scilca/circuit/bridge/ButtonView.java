/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.bridge;

import com.scilca.dom.html.HTMLButtonElement;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.AbstractButton;
import javax.swing.JButton;

/**
 *
 * @author Sukant Pal
 */
public class ButtonView extends JButton implements View{
    
    public static final int DEFAULT_WIDTH = 100;
    public static final int DEFAULT_HEIGHT = 200;
    public static final Font DEFAULT_FONT = Font.getFont("verdana");
    
    public static JButton getButtonView(HTMLButtonElement loadedElement){
        ButtonView initButton = new ButtonView();
        initButton.setText(loadedElement.getButtonText());
        return initButton;
    }
    
    public ButtonView(){
        super.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        super.setVisible(true);
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.setFont(DEFAULT_FONT);
        super.paintComponent(g);
    }
    
    @Override
    public short getViewType(){
        return BUTTON_VIEW;
    }
    
}
