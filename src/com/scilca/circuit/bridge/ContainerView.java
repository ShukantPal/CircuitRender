/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.bridge;

import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Sukant Pal
 */
public final class ContainerView extends View{

    public FlowLayout internalLayout;
    JPanel internalPane;
    
    public ContainerView() {
        super((short) CONTAINER_VIEW);
        this.internalLayout = new FlowLayout();
        this.internalPane = new JPanel();
       
        this.internalPane.setLayout(internalLayout);
    }

    @Override
    public void paintComponent(Graphics g) {
        this.internalPane.paintComponents(g);
    }
    
    
    
}
