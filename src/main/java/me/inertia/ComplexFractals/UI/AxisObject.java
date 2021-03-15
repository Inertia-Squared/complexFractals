package me.inertia.ComplexFractals.UI;

import me.inertia.ComplexFractals.Main;
import processing.core.PApplet;

import java.awt.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static me.inertia.ComplexFractals.Main.*;

public class AxisObject {
    float targetIntervals=10f;
    PApplet a;

    public AxisObject(float targetIntervals){
        this.targetIntervals = targetIntervals;
        a = Main.applet;
    }


    public void generateAxis(){
        float strokeWeight = a.g.strokeWeight;
        int strokeColor = a.g.strokeColor;
        a.strokeWeight(1);
        a.stroke(125);
        a.line(0+offsetX,a.height/2f+offsetY,a.width+offsetX,a.height/2f+offsetY);
        a.line(a.width/2f+offsetX,0+offsetY,a.width/2f+offsetX,a.height+offsetY);
        generateBlips();
        a.strokeWeight(strokeWeight);
        a.stroke(strokeColor);
    }

    public static String setSignificanDigits(double value, int significantDigits) {
        if (significantDigits < 0) throw new IllegalArgumentException();

        // this is more precise than simply doing "new BigDecimal(value);"
        BigDecimal bd = new BigDecimal(value, MathContext.DECIMAL64);
        bd = bd.round(new MathContext(significantDigits, RoundingMode.HALF_UP));
        final int precision = bd.precision();
        if (precision < significantDigits)
            bd = bd.setScale(bd.scale() + (significantDigits-precision));
        return bd.toPlainString();
    }

    private void generateBlips(){
        a.strokeWeight(1);
        boolean toggle = true;
        for(int i = (int) (-targetIntervals); i<0; i++){
            double x = i*(Math.round(a.width/200d)*100d)/(((double)targetIntervals))+a.width/2d;
            double y = a.height/2d;
            if(i!=0) {
                if (toggle) {
                    toggle = false;
                    a.line((float) (x)+offsetX, -10 + (float) y+offsetY, (float) (x)+offsetX, (float) (10 + y)+offsetY);
                    a.text(setSignificanDigits(((x - a.width / 2d)) * Main.scale, 2), (float) (x)+offsetX, (float) (y + 25)+offsetY);
                } else {
                    toggle = true;
                    a.line((float) (x)+offsetX, (float) (-5 + y)+offsetY, (float) (x)+offsetX, (float) (5 + y)+offsetY);
                }
            }
        }
        toggle = false;
        for(int i = (0); i<targetIntervals; i++){
            double x = i*(Math.round(a.width/200d)*100d)/(((double)targetIntervals))+a.width/2d;
            double y = a.height/2d;
            if(i!=0) {
                if (toggle) {
                    toggle = false;
                    a.line((float) x+offsetX, -10 + (float) y+offsetY, (float) x+offsetX, (float) (10 + y)+offsetY);
                    a.text(setSignificanDigits(((x - a.width / 2d)) * Main.scale, 2), (float) x+offsetX, (float) (y + 25)+offsetY);
                } else {
                    toggle = true;
                    a.line((float) x+offsetX, (float) (-5 + y)+offsetY, (float) x+offsetX, (float) (5 + y)+offsetY);
                }
            }
        }
        toggle = true;
        for(int i = (int) (-targetIntervals); i<0; i++) {
            if(i!=0) {
            double x = a.width / 2d;
            double y = i * (Math.round(a.width / 200d) * 100d) / (((double) targetIntervals)) + a.height / 2d;
            if (toggle) {
                toggle = false;
                a.line((float) (x - 10)+offsetX, (float) y+offsetY, (float) x + 10+offsetX, (float) (y)+offsetY);
                a.text(setSignificanDigits((-(y - a.height / 2d)) * Main.scale, 2), (float) (x - 25d)+offsetX, (float) (y)+offsetY);
            } else {
                toggle = true;
                a.line((float) x - 5+offsetX, (float) (y)+offsetY, (float) x + 5+offsetX, (float) (y)+offsetY);
            }
        }
        }
        toggle = false;
        for(int i = (0); i<targetIntervals; i++){
            if(i!=0) {
                double x = a.width / 2d;
                double y = i * (Math.round(a.width / 200d) * 100d) / (((double) targetIntervals)) + a.height / 2d;
                if (toggle) {
                    toggle = false;
                    a.line((float) (x - 10)+offsetX, (float) y+offsetY, (float) x + 10+offsetX, (float) (y)+offsetY);
                    a.text(setSignificanDigits((-(y - a.height / 2d)) * Main.scale, 2), (float) (x - 25d)+offsetX, (float) (y)+offsetY);
                } else {
                    toggle = true;
                    a.line((float) x - 5+offsetX, (float) (y)+offsetY, (float) x + 5+offsetX, (float) (y)+offsetY);
                }
            }
        }
    }
}
