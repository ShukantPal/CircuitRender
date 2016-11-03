/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scilca.circuit.bridge;

import java.awt.Color;
import java.awt.Insets;
import javax.swing.border.AbstractBorder;

/**
 *
 * @author Sukant Pal
 */
public class ViewBorder extends AbstractBorder{
    
    double borderRadius;
    Insets borderThickness;
    Color borderColor;
    BorderStyles borderStyle;
    
    public ViewBorder(){
        borderRadius = 0;
        borderThickness = new Insets(2,2,2,2);
        borderColor = Color.BLACK;
        borderStyle = BorderStyles.SOLID;
    }
    
    public ViewBorder(double borderRadius){
        this();
        if(borderRadius > 0)
            this.borderRadius = borderRadius;
    }
    
    public ViewBorder(double borderRadius, Color borderColor){
        this(borderRadius);
        this.borderColor = borderColor;
    }
    
    public ViewBorder(double borderRadius, Insets borderThickness, Color borderColor, BorderStyles borderStyle){
        this.borderRadius = borderRadius;
        this.borderThickness = borderThickness;
        this.borderColor = borderColor;
        this.borderStyle = borderStyle;
    }
    
    public double getBorderRadius(){
        return borderRadius;
    }
    
    public void setBorderRadius(double borderRadius){
        if(borderRadius >= 0)
            this.borderRadius = borderRadius;
    }
    
    public Insets getBorderThickness(){
        return borderThickness;
    }
    
    public void setBorderThickness(Insets borderThickness){
        if(borderThickness != null)
            this.borderThickness = borderThickness;
        else this.borderThickness = new Insets(2,2,2,2);
    }
    
    public Color getColor(){
        return borderColor;
    }
    
    public void setColor(Color borderColor){
        if(borderColor != null)
            this.borderColor = borderColor;
        else this.borderColor = Color.BLACK;
    }
    
    public BorderStyles getBorderStyle(){
        return borderStyle;
    }
    
    public void setBorderStyle(BorderStyles borderStyle){
        if(borderStyle != null)
            this.borderStyle = borderStyle;
        else this.borderStyle = BorderStyles.SOLID;
    }
    
}
