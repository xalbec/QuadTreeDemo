package main.java.com.xalbec.quadtreedemo;

import processing.core.PVector;

//Class exclusively used by quadtree to know its bounds. Commonly called nodes.
public class Boundary {

    //x and y are the center of the boundary
    int x;
    int y;
    //width and height are length from the center to the edge
    int width;
    int height;

    //x,y are the center of the Bounds
    //width and height go from the center to the edge
    public Boundary(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean contains(PVector vect) {

        //is it between the left and right wall?
        //includes the left edge, not the right edge.
        if (vect.x >= x - width && vect.x < x + width) {
            //is it between top and bottom?
            //includes top edge not the bottom edge.
            if (vect.y >= y - height && vect.y < y + height) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    //checks if two boundaries intersect
    public boolean intersects(Boundary bound) {

        return !(
                bound.x - bound.width > this.x + this.width ||
                bound.x + bound.width < this.x - this.width ||
                bound.y - bound.height > this.y + this.height ||
                bound.y + bound.height < this.y - this.height
        );

    }

}