package me.inertia.ComplexFractals.Iterations;

import com.vm.jcomplex.Complex;
import me.inertia.ComplexFractals.Main;
import me.inertia.ComplexFractals.UI.DraggablePoint;

import java.awt.*;

public class IterativePointSet extends DraggablePoint {

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
        super.renderSelf();
        float textSize = a.g.textSize;
        a.textSize(10);
        Complex number = new Complex(x,y);
        a.text( Math.round(number.abs()*100f)/100f+"cis"+Math.round(number.getArgument()/a.PI*100f)/100f+"PI",x/(float) Main.scale,(y/(float)Main.scale)+radius/2+15);
        a.textSize(textSize);
   }
}
