package main.java.com.xalbec.quadtreedemo;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.ArrayList;

public class QuadTree {

    private boolean isDivided = false;
    private int capacity;
    private PApplet parent;
    private Boundary boundary;
    private ArrayList<PVector> points = new ArrayList<PVector>();

    private QuadTree NW;
    private QuadTree NE;
    private QuadTree SW;
    private QuadTree SE;


    //when there are 4 points in one rectangle, subdivide;

    public QuadTree(PApplet parent, Boundary boundary, int capacity) {

        this.parent = parent;
        this.boundary = boundary;
        this.capacity = capacity;

    }

    //gives a new point to our Quadtree to keep track of
    public void insert(PVector pnt) {

        //if the point is not in the bounds just skip this.
        if (!this.boundary.contains(pnt)) {
            return;
        }

        //if there is room add it, if not split it and add the point to the correct tree.
        //error occurred because when array was set to null, it first just needs to see if the array is null because there is no size function for a null reference.
        if (!isDivided && points.size() < 4) {
            points.add(pnt);
        } else {
            //if you get more than 4 points only sub divide 1 time
            if (!isDivided) {
                subdivide();
            }
            NW.insert(pnt);
            NE.insert(pnt);
            SW.insert(pnt);
            SE.insert(pnt);

        }

    }

    //Divides the quadtree into NW, NE, SW, SE using 4 new Boundaries.
    public void subdivide() {

        NW = new QuadTree(parent, new Boundary(boundary.x - boundary.width / 2, boundary.y - boundary.height / 2, boundary.width / 2, boundary.height / 2), 4);
        NE = new QuadTree(parent, new Boundary(boundary.x + boundary.width / 2, boundary.y - boundary.height / 2, boundary.width / 2, boundary.height / 2), 4);
        SW = new QuadTree(parent, new Boundary(boundary.x - boundary.width / 2, boundary.y + boundary.height / 2, boundary.width / 2, boundary.height / 2), 4);
        SE = new QuadTree(parent, new Boundary(boundary.x + boundary.width / 2, boundary.y + boundary.height / 2, boundary.width / 2, boundary.height / 2), 4);
        isDivided = true;

        //inserts the points into the leaves of the tree.
        for(PVector pnt: this.points){
            NW.insert(pnt);
            NE.insert(pnt);
            SW.insert(pnt);
            SE.insert(pnt);
        }

        //I have given the old points to the new quadtrees so i dont want to store each point twice.
        this.points = null;

    }

    public void displayEdge() {

        //draws a rectangle around the bounds of the quadtree
        parent.stroke(255);
        parent.fill(0);
        parent.strokeWeight(1);
        parent.rectMode(PConstants.CENTER);
        parent.rect(boundary.x, boundary.y, boundary.width*2-1, boundary.height*2-1);

        //if the tree has sub trees, those trees need to be displayed.
        if (isDivided) {
            NW.displayEdge();
            NE.displayEdge();
            SW.displayEdge();
            SE.displayEdge();
        }

    }

    public void displayPoints() {

        //draws a red point for each point in the entire quadtree
        parent.stroke(255, 0, 0);
        parent.strokeWeight(5);

        if (isDivided) {
            NW.displayPoints();
            NE.displayPoints();
            SW.displayPoints();
            SE.displayPoints();
        }else{
            //else it is a leaf and its points must be displayed.
            for (PVector pnt : points) {
                parent.point(pnt.x, pnt.y);
            }

        }

    }

    //will need to return points of the quadtree located inside the quadtree inside some bound.
    public void query(Boundary bound, ArrayList<PVector> foundPnts){

    

        if(this.boundary.intersects(bound)){

            if(isDivided){
                NE.query(bound, foundPnts);
                NW.query(bound, foundPnts);
                SE.query(bound, foundPnts);
                SW.query(bound, foundPnts);
            }else{

                for(PVector p : this.points){
                    if(bound.contains(p)){
                        foundPnts.add(p);
                    }
                }

            }

        }

    }

    public void setParent(PApplet parent) {
        this.parent = parent;
    }

}
