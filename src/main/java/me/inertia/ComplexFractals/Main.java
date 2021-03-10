package me.inertia.ComplexFractals;

import me.inertia.ComplexFractals.Iterations.IterativePointSet;
import me.inertia.ComplexFractals.UI.AxisObject;
import me.inertia.ComplexFractals.UI.DraggablePoint;
import processing.core.PApplet;
import com.vm.jcomplex.Complex;
import processing.event.MouseEvent;

import java.awt.*;

public class Main extends PApplet {
    IterativePointSet draggablePoint;
    public static PApplet applet;
    AxisObject primaryAxis;
    public static double scale = 0.002d;

    @Override
    public void mouseWheel(MouseEvent event) {
        super.mouseWheel(event);
        if(scale>=0.0000001d) {
            scale += (double) event.getCount() * scale * 0.1d;
        }else{
            scale=0.0000001d;
        }
    }

    public void settings(){
        fullScreen(FX2D);
    }
    public void setup(){
        applet = this; //must go first

        ellipseMode(CENTER);
        textAlign(CENTER);
        draggablePoint = new IterativePointSet(0.5f,0.5f,20,"P", Color.cyan);
        primaryAxis = new AxisObject(20f);
    }
    public void draw(){
       // System.out.println(scale);
        background(36);
        primaryAxis.generateAxis();

        translate(width/2f,height/2f);
        //do Non-Static UI rendering here, to make math easier for later
        draggablePoint.updateSelf();
        translate(-width/2f,-height/2f);
    }


    public static void main(String[] args) {
        PApplet.main("me.inertia.ComplexFractals.Main");
    }
}
