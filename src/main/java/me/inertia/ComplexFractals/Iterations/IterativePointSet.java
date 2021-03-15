package me.inertia.ComplexFractals.Iterations;

import com.vm.jcomplex.Complex;
import me.inertia.ComplexFractals.Main;
import me.inertia.ComplexFractals.UI.DraggablePoint;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

//TODO
// optimise the heck outta this
// add GPU compute for iterations, localise non-static variables to stop over-reads/writes (maybe create manager function [or class] to assign tasks?)

public class IterativePointSet extends DraggablePoint {
    private boolean mandel = false;


    public IterativePointSet(String label) {
        this(0,0,10,label, Color.lightGray);
    }

    public IterativePointSet(float x, float y,String label) {
        this(x,y,10,label, Color.lightGray);
    }

    public IterativePointSet(float x, float y, int radius, String label){
        this(x,y,10,label,Color.lightGray);
    }

    public IterativePointSet(float x, float y, String label, Color fill){
        this(x,y,5,"",fill);
    }

    public IterativePointSet(float x, float y, int radius, String label, Color fill){
        super(x,y,radius,label,fill);
    }

    @Override
    protected void renderSelf(){
        int fill = a.g.fillColor;
        a.fill(0,0);
        if(!mandel){a.ellipse(0,0,(float)(2d/Main.scale),(float)(2d/Main.scale));}
        else{a.ellipse(0,0,(float)(4d/Main.scale),(float)(4d/Main.scale));}
        a.fill(fill);
        if(!Main.fastMode)super.renderSelf();
        float textSize = a.g.textSize;
        a.textSize(12);
        Complex number = new Complex(x,y);
       if(!Main.fastMode) a.text( Math.round(number.abs()*100f)/100f+"cis"+Math.round(number.getArgument()/a.PI*100f)/100f+"PI",x/(float) Main.scale,(y/(float)Main.scale)+radius/2+15);
        a.textSize(textSize);
   }
   int count = 0;
   public double mag = 0.0001;
    double speedMult = 3d;//rotation step quality, higher value means higher quality but much slower
   public void animateSinCos(){
        x = (float) ( Math.cos(Math.toRadians(count)/(mag*speedMult))*mag);
        y = (float) ( Math.sin(Math.toRadians(count)/(mag*speedMult))*mag);
        count++;
        if(dragging){
            count = (int) Math.toDegrees(new Complex(a.mouseX-a.width/2f,a.mouseY-a.height/2f).getArgument());
        }
        if(count>=360*(mag*speedMult)){
            count = 0;
            mag+=0.0025d;//ring step value, smaller means higher quality but slower
        }
   }

  // boolean toggle = true;
    ArrayList<PVector> points = new ArrayList<>();
   double pow = 2d;
   public void iteratePoints(int depth, boolean mandel){
       int iterations = 1;
        this.mandel = mandel;
       // System.out.println("NEW SET");
        Complex number = new Complex(x,y);
       //System.out.println(x+", "+number.getImaginary());
        if(dragging)mag = number.abs();
        float prevx = x;
        float prevy = y;
        double dist = 0;
       int prevFill = a.g.fillColor;
       for (int i = 0; i < depth; i++) {
           if(mandel){
               //number = number.pow(2d);
               number = new Complex(number.pow(pow).getReal(),number.pow(pow).getImaginary());
               //number = new Complex(number.pow(number.getReal()).getReal(),number.pow(number.getImaginary()).getImaginary());
               number = number.add(new Complex(x,y));
           }
           else{number = new Complex(number.pow(2).getReal(),number.pow(2).getImaginary());}
           float x = (float) number.getReal();
           float y = (float) number.getImaginary();
          // if(toggle) {
            //   System.out.println(x+", "+number.getImaginary());
           //}
           iterations = i;
           dist = number.abs();
           if(dist>2d) break;
           if(!Main.fastMode) {
               a.fill(fill.darker().darker().getRGB());
               a.ellipse(x / (float) Main.scale, y / (float) Main.scale, radius, radius);
               a.line(x / (float) Main.scale, y / (float) Main.scale, prevx / (float) Main.scale, prevy / (float) Main.scale);
           }
           prevx=x;
           prevy=y;
       }

       //System.out.println(number.abs());

       if(!Main.fastMode) {
           if (dist <= 2d) {
               points.add(new PVector(x, y));
           }
       }else {
               if (dist <= 2d) {
                   a.point(x / (float) Main.scale, y / (float) Main.scale);
               } else {
                   a.stroke(56,56,(float) ((255d*iterations)/(depth))+56f);//redundant, need to check iterations internally, will slow down program
                   a.point(x / (float) Main.scale, y / (float) Main.scale);
                   a.stroke(0);
               }
       }
       a.fill(prevFill);
       //toggle = false;
   }

   public void renderPoints(){
       for (PVector p:points) {
           a.point(p.x/(float) Main.scale,p.y/(float) Main.scale);
       }
   }
}
