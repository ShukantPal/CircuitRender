/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.bridge;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Sukant Pal
 */
public class StackLayout extends BoxLayout{
    
    int rowNumber;
    ArrayList<JPanel> horizontalLayouts;
    
    public final JPanel addStack(){
       JPanel jp = new JPanel();
       jp.setLayout(new BoxLayout(jp, X_AXIS));
       horizontalLayouts.add(jp);
       return jp;
    }
    
    public StackLayout(JComponent comp){
        super(comp, Y_AXIS);
        
        horizontalLayouts = new ArrayList<>();
        
        rowNumber = 0;
        addStack();
    }
    
    public void addLayoutComponent(Component comp){
        horizontalLayouts.get(rowNumber).add(comp);
    }
    
    @Override
    public void addLayoutComponent(Component comp, Object constraints){
        addLayoutComponent(comp);
    }
    
    
    
}
