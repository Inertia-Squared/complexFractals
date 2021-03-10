package me.inertia.ComplexFractals.UI;

import me.inertia.ComplexFractals.Main;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

public class DraggablePoint {
    protected float x,y;
    protected int radius = 10;
    public String label;
    protected Color fill;
    protected PApplet a;
    private boolean dragging = false;

    public DraggablePoint(String label) {
        this(0,0,10,label, Color.lightGray);
    }
    public DraggablePoint(int radius,String label) {
        this(0,0,radius,label, Color.lightGray);
    }

    public DraggablePoint(float x, float y, String label) {
        this(x,y,10,label, Color.lightGray);
    }

    public DraggablePoint(float x, float y, int radius, String label){
        this(x,y,radius,label,Color.lightGray);
    }

    public DraggablePoint(float x, float y, String label, Color fill){
        this(x,y,10,"",fill);
    }

    public DraggablePoint(float x, float y, int radius, String label, Color fill){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.label = label;
        this.fill = fill;
        a = Main.applet;
        regulateVariables();
    }

    //public functions
    public void updateSelf(){
        if (a.mousePressed&&dragging){setCoordinatesFromVector(getDragLocation());}
        else{dragging = isPressedOn();}
        renderSelf();
    }

    public void setCoordinatesFromVector(PVector pVector){
        x = (float) (pVector.x*Main.scale);
        y = (float) (pVector.y*Main.scale);
    }

    public PVector getValues(){
        return new PVector(x,-y);
    }

    public void setValues(PVector pVector){
        x = pVector.x;
        y=-pVector.y;
    }

    //private or protected functions
    protected void renderSelf(){
        int prevFill = a.g.fillColor;
        a.fill(fill.getRGB());
        a.ellipse(x/(float) Main.scale,y/(float) Main.scale,radius,radius);
        a.fill(fill.darker().darker().darker().darker().getRGB()); //darker, yet darker
        a.text(label,x/(float) Main.scale,(y/(float)Main.scale)+radius/4);
        a.fill(prevFill);
    }

    private void regulateVariables(){
        if(radius<5)radius=5;
        if(label.length()>1)label=label.substring(0,0);
    }

    private PVector getDragLocation(){
        return new PVector((a.mouseX-a.width/2f), (a.mouseY-a.height/2f));
        //return new PVector(0,0);
    }

    private boolean isPressedOn(){
        //given ellipse render mode is CENTER
        //System.out.println(x/Main.scale);
        //System.out.println(a.mouseX*Main.scale);
        return (a.mousePressed&&PApplet.dist((float) (a.mouseX-a.width/2f), (float) (a.mouseY-a.height/2f), (float) (x/Main.scale), (float) (y/Main.scale))<=radius);
    }
}
