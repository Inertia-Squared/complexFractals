package me.inertia.ComplexFractals;

import me.inertia.ComplexFractals.Iterations.IterativePointSet;
import me.inertia.ComplexFractals.UI.AxisObject;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.awt.*;

public class Main extends PApplet {
    IterativePointSet draggablePoint;
    public static PApplet applet;
    AxisObject primaryAxis;
    public static double scale = 0.003d;
    public static double offsetX = 0d;
    public static double offsetY = 0d;
    static boolean[] keys = new boolean[10000];
    public static boolean fastMode = true;

    @Override
    public void keyPressed(KeyEvent event) {
        super.keyPressed(event);
        keys[event.getKeyCode()]=true;
    }

    @Override
    public void keyReleased(KeyEvent event) {
        super.keyReleased(event);
        keys[event.getKeyCode()]=false;
    }

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
        frameRate(240);
        ellipseMode(CENTER);
        rectMode(CORNER);
        textAlign(CENTER);
        draggablePoint = new IterativePointSet(0.5f,0.5f,20,"P", Color.cyan);
        primaryAxis = new AxisObject(20f);

        background(56);
    }
    public void draw(){
        //System.out.println(scale);

        handleKeys();
        if(!fastMode) background(36);
        primaryAxis.generateAxis();

        translate(width/2f,height/2f);
        //do Non-Static UI rendering here, to make math easier for later
            draggablePoint.animateSinCos();
            draggablePoint.updateSelf();
            draggablePoint.iteratePoints(20, true);
            if(!fastMode)draggablePoint.renderPoints();
            if(draggablePoint.mag>2&&!mousePressed){
                saveFrame("sc####.png");
                exit();
            }
        translate(-width/2f,-height/2f);
            if(fastMode) {
                fill(0);
                rect(0, 0, 60, 35);
            }
        fill(255);
        text(frameRate,30,15);
        text(Math.round((draggablePoint.mag/2f)*1000f)/10f+"%",30,30);
    }
    double rad = 0;


    private static void handleKeys(){
        if(keys[87])offsetY-=0.01f/scale;
        if(keys[65])offsetX-=0.01f/scale;
        if(keys[83])offsetY+=0.01f/scale;
        if(keys[68])offsetX+=0.01f/scale;
    }


    public static void main(String[] args) {
        PApplet.main("me.inertia.ComplexFractals.Main");
    }
}
