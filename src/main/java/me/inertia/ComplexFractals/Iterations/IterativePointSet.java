package me.inertia.ComplexFractals.Iterations;

import com.vm.jcomplex.Complex;
import me.inertia.ComplexFractals.Main;
import me.inertia.ComplexFractals.UI.DraggablePoint;

import java.awt.*;
import java.io.Console;

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
        super.renderSelf();
        float textSize = a.g.textSize;
        a.textSize(12);
        Complex number = new Complex(x,y);
        a.text( Math.round(number.abs()*100f)/100f+"cis"+Math.round(number.getArgument()/a.PI*100f)/100f+"PI",x/(float) Main.scale,(y/(float)Main.scale)+radius/2+15);
        a.textSize(textSize);
   }
   int count = 0;
    double mag = 0.59;
   public void animateSinCos(){
        x = (float) ( Math.cos(Math.toRadians(count))*mag);
        y = (float) ( Math.sin(Math.toRadians(count))*mag);
        count++;
        if(dragging){
            count = (int) Math.toDegrees(new Complex(a.mouseX-a.width/2f,a.mouseY-a.height/2f).getArgument());
        }
        if(count>=360)count = 0;
   }

  // boolean toggle = true;
   public void iteratePoints(int depth, boolean mandel){
        this.mandel = mandel;
        System.out.println("NEW SET");

        Complex number = new Complex(x,y);
       //System.out.println(x+", "+number.getImaginary());
        mag = number.abs();
        float prevx = x;
        float prevy = y;
       for (int i = 0; i < depth; i++) {
           if(mandel){number = new Complex(number.pow(2).getReal(),number.pow(2).getImaginary()+y);}
           else{number = new Complex(number.pow(2).getReal(),number.pow(2).getImaginary());}
           float x = (float) number.getReal();
           float y = (float) number.getImaginary();
          // if(toggle) {
            //   System.out.println(x+", "+number.getImaginary());
           //}
           int prevFill = a.g.fillColor;
           a.fill(fill.darker().darker().getRGB());
           a.ellipse(x/(float) Main.scale,y/(float) Main.scale,radius,radius);
           a.line(x/(float) Main.scale,y/(float) Main.scale,prevx/(float) Main.scale,prevy/(float) Main.scale);
           a.fill(prevFill);
           prevx=x;
           prevy=y;
       }
       //toggle = false;
   }
}
