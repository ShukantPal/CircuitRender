/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.bridge;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.LayoutManager;
import javax.swing.JPanel;

/**
 *
 * @author Sukant Pal
 */
public final class ContainerView extends JPanel implements View{
   
    LayoutManager currentManager;
    
    public ContainerView() {
        super();
        currentManager = new FlowLayout();
    }

    @Override
    public short getViewType() {
        return CONTAINER_VIEW;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    
    public LayoutManager getLayoutManager(){
        return currentManager;
    }
    
}
