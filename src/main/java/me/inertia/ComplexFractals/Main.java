package me.inertia.ComplexFractals;

import me.inertia.ComplexFractals.Iterations.IterativePointSet;
import me.inertia.ComplexFractals.UI.AxisObject;
import org.lwjgl.opengl.GL;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class Main extends PApplet {
    IterativePointSet draggablePoint;
    public static PApplet applet;
    AxisObject primaryAxis;
    public static double scale = 0.0021d;
    public static float offsetX = 0f;
    public static float offsetY = 0f;
    static boolean[] keys = new boolean[10000];
    public static boolean fastMode = false; //kind of redundant after bulk-tasking
    public static boolean renderPointer = false;
    public static int testRate = 2000;
    private static int minimumRate = 100;
    public static int targetFrameRate = 6;
    int depth = 250;
    public static int depthMag;

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

    @Override
    public void stop(){
        //do unload here
       // glfwTerminate();
        super.stop();
    }

    public void settings(){
        fullScreen(FX2D);

    }
    public void setup(){
        applet = this; //must go first
        frameRate(targetFrameRate);
        ellipseMode(CENTER);
        rectMode(CORNER);
        textAlign(CENTER);
        depthMag = round((float) Math.log10(depth));
        draggablePoint = new IterativePointSet(0.5f,0.5f,20,"P", Color.cyan);
        primaryAxis = new AxisObject(20f);
        //GL.createCapabilities();
        background(56);
    }


    public void draw(){
        //System.out.println(scale);
        optimiseSpeed();
        handleKeys();
        if(!fastMode) background(36);
        primaryAxis.generateAxis();
        if(fastMode) {
            fill(0);
            rect(0, 0, 60, 35);
        }
        fill(255);
        text(frameRate,30,15);
        text(Math.round((draggablePoint.mag/2f)*1000f)/10f+"%",30,30);
        text(testRate*round(frameRate)*depth,30,45);
        translate((width/2f)+offsetX,(height/2f)+offsetY);
        //do Non-Static UI rendering here, to make math easier for later
            draggablePoint.animateSinCos();
            draggablePoint.updateSelf();
            draggablePoint.iteratePoints(depth, true);
            if(!fastMode)draggablePoint.renderPoints();
            if(draggablePoint.mag>2&&!mousePressed){
                saveFrame("sc####.png");
                exit();
            }
       // translate((-width/2f)-offsetX,(-height/2f)+offsetY);


    }
    double rad = 0;

    int framesLagged =1;
    int framesFast =1;
    private void optimiseSpeed(){
        if(frameRate-targetFrameRate<-((targetFrameRate/10f)+1f)){testRate-=framesLagged;framesLagged++;
        if(testRate<minimumRate) testRate = minimumRate;
        }
        else{framesLagged=0;}
        if(frameRate-targetFrameRate>-0.2f){testRate+=framesFast;framesFast++;}else{
            framesFast=0;
        }
    }


    private static void handleKeys(){
        if(keys[87])offsetY+=5f;
        if(keys[65])offsetX+=5f;
        if(keys[83])offsetY-=5f;
        if(keys[68])offsetX-=5f;
        if(keys[67]){
            offsetX=0;
            offsetY=0;
        }
    }


    public static void main(String[] args) {
        PApplet.main("me.inertia.ComplexFractals.Main");
    }
}
