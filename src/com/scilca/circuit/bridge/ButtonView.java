/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.bridge;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Sukant Pal
 */
public class ButtonView extends View{
    
    protected ButtonView(){
        super((short) BUTTON_VIEW);
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, super.getWidth(), super.getHeight());
    }
    
}
