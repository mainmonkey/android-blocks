package org.javia.blocks;

import android.graphics.Path;
import android.graphics.Point;

abstract class BlockType {
    abstract boolean contains(int x, int y);
    Path path;
    Point[] up, down, left, right;
    protected int size;

    BlockType(int size) {
	this.size = size;
    }
}

class SmallSquareBlock extends BlockType {
    SmallSquareBlock(int size) {
	super(size);
	path = new Path();
	path.moveTo(0, 0);
	path.lineTo(size, 0);
	path.lineTo(size, size);
	path.lineTo(0, size);
	path.close();

	Point 
	    p1 = new Point(0, 0), 
	    p2 = new Point(size, 0),
	    p3 = new Point(0, size),
	    p4 = new Point(size, size);
	
	up    = new Point[]{p1, p2};
	down  = new Point[]{p3, p4};
	left  = new Point[]{p1, p3};
	right = new Point[]{p1, p4};	
    }

    boolean contains(int x, int y) {
	return x > 0 && x < size &&
	    y > 0 && y < size;
    }
}

class TriUpBlock extends BlockType {
    TriUpBlock(int s1) {
	super(s1);
	int s2 = s1 + s1;
	path = new Path();
	path.moveTo(0, 0);
	path.lineTo(0, s2);
	path.lineTo(s1, s2);
	path.lineTo(s1, s1);
	path.lineTo(s2, s1);
	path.lineTo(s2, 0);
	path.close();

	Point 
	    p0 = new Point(0, 0), 
	    p1 = new Point(0, s1),
	    p2 = new Point(0, s2),
	    
	    p3 = new Point(s1, s2),
	    p4 = new Point(s1, s1),
	    p5 = new Point(s2, s1),
	    p6 = new Point(s2, 0),
	    p7 = new Point(s1, 0);
	
	up    = new Point[]{p0, p6, p7};
	down  = new Point[]{p2, p3, p4, p5};
	left  = new Point[]{p0, p1, p2};
	right = new Point[]{p3, p4, p5, p6};	
    }

    boolean contains(int x, int y) {
	return 
	    x>0 && y>0 &&
	    ((x<size && y<size+size) ||
	     (y<size && x<size+size));
    }
}

class TriDownBlock extends BlockType {
    TriDownBlock(int s1) {
	super(s1);
	int s2 = s1 + s1;
	path = new Path();
	path.moveTo(0, s1);
	path.lineTo(0, s2);
	path.lineTo(s2, s2);
	path.lineTo(s2, 0);
	path.lineTo(s1, 0);
	path.lineTo(s1, s1);
	path.close();

	Point 
	    p0 = new Point(0, 0), 
	    p1 = new Point(0, s1),
	    p2 = new Point(0, s2),
	    
	    p3 = new Point(s1, s2),
	    p4 = new Point(s1, s1),
	    p5 = new Point(s2, s1),
	    p6 = new Point(s2, 0),
	    p7 = new Point(s1, 0);
	
	up    = new Point[]{p0, p6, p7};
	down  = new Point[]{p2, p3, p4, p5};
	left  = new Point[]{p0, p1, p2};
	right = new Point[]{p3, p4, p5, p6};	
    }

    boolean contains(int x, int y) {
	return 
	    x>0 && y>0 &&
	    ((x<size && y<size+size) ||
	     (y<size && x<size+size));
    }
}

