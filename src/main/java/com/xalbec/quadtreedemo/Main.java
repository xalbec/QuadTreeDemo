package main.java.com.xalbec.quadtreedemo;

import processing.core.PApplet;
import processing.core.PVector;

import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends PApplet {

    public static void main(String[] args){

        PApplet.main(new String[] {Main.class.getName()});

    }

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private Boundary boundary = new Boundary(WIDTH/2, HEIGHT/2, WIDTH/2, HEIGHT/2);
    private QuadTree qt = new QuadTree(this, boundary, 4);

    Boundary bound = new Boundary(150, 150, 75, 75);
    ArrayList<PVector> foundPnts = new ArrayList<PVector>();

    public void settings(){
        size(WIDTH, HEIGHT);
    }

    public void setup(){

        for(int i = 0; i < 100; i++){
            qt.insert(new PVector(random(WIDTH), random(HEIGHT)));
        }

        qt.query(bound,foundPnts);
        System.out.println(foundPnts.size());

    }

    public void draw(){

        background(0);

        qt.displayEdge();
        qt.displayPoints();

        fill(0,0);
        stroke(0,255,0);
        strokeWeight(1);
        rect(150, 150, 150, 150);

    }

    public void mousePressed(){

        //if you wanna click to add points uncomment
        PVector p = new PVector(mouseX, mouseY);
        qt.insert(p);

        if(bound.contains(p)){
            foundPnts.add(p);
        }
        System.out.println(foundPnts.size());

    }

}
