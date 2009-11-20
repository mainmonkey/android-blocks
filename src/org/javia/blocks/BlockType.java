package org.javia.blocks;

import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Canvas;
import android.graphics.Paint;

abstract class BlockType {
    static final int size = Blocks.SIZE;

    abstract boolean contains(int x, int y);
    Path path;
    Point[][] frontier = new Point[4][];
    
    static protected Paint paintBorder = new Paint(), paintFill = new Paint();
    static {
	paintBorder.setColor(0xff000000);
	paintBorder.setStyle(Paint.Style.STROKE);
	paintFill.setColor(0xffd0b0c0);
	paintFill.setStyle(Paint.Style.FILL);
    }

    void draw(Canvas canvas) {	
	canvas.drawPath(path, paintFill);
	canvas.drawPath(path, paintBorder);
    }

    void setFrontier(Point[] left, Point[] down, Point[] right, Point[] up) {
	frontier[Board.LEFT]  = left;
	frontier[Board.DOWN]  = down;
	frontier[Board.RIGHT] = right;
	frontier[Board.UP]    = up;
    }
}

class SmallSquareBlock extends BlockType {
    SmallSquareBlock() {
	{
	    int s1 = size-2;
	    path = new Path();
	    path.moveTo(1, 1);
	    path.lineTo(1, s1);
	    path.lineTo(s1, s1);
	    path.lineTo(s1, 1);
	    path.close();
	}

	int s1 = size-1;
	Point 
	    p0 = new Point(0, 0), 
	    p1 = new Point(0, s1),
	    p2 = new Point(s1, s1),
	    p3 = new Point(s1, 0);

	setFrontier(new Point[]{p0, p1}, 
		    new Point[]{p1, p2},
		    new Point[]{p2, p3},
		    new Point[]{p3, p0});
    }

    boolean contains(int x, int y) {
	boolean ret = x >= 0 && x < size &&
	    y >= 0 && y < size;
	// Blocks.log("contains " + x + ' ' + y + ' ' + ret);
	return ret;
    }

    void draw(Canvas canvas) {
	super.draw(canvas);
	canvas.drawPoint(size-2, size-2, paintBorder);
    }
}

class BigSquareBlock extends BlockType {
    static Paint green = new Paint();

    static {
	green.setStyle(Paint.Style.FILL);
	green.setColor(0xffb070a0);
	green.setAntiAlias(true);
    }

    BigSquareBlock() {
	{
	int s2 = size+size-2;
	path = new Path();
	path.moveTo(1, 1);
	path.lineTo(1, s2);
	path.lineTo(s2, s2);
	path.lineTo(s2, 1);
	path.close();
	}

	int s1 = size-1;
	int s2 = size+size-1;
	Point 
	    p0 = new Point(0, 0),
	    p1 = new Point(0, s1),
	    p2 = new Point(0, s2),
	    p3 = new Point(s1, s2),
	    p4 = new Point(s2, s2),
	    p5 = new Point(s2, s1),
	    p6 = new Point(s2, 0),
	    p7 = new Point(s1, 0);
	
	setFrontier(new Point[]{p0, p1, p2},
		    new Point[]{p2, p3, p4},
		    new Point[]{p4, p5, p6},
		    new Point[]{p6, p7, p0});
    }

    boolean contains(int x, int y) {
	return x >= 0 && x < size+size &&
	    y >= 0 && y < size+size;
    }

    void draw(Canvas canvas) {
	super.draw(canvas);
	canvas.drawCircle(size, size, size/2, green);
	canvas.drawPoint(size+size-2, size+size-2, paintBorder);
      	// canvas.drawPath(path, green);
	// canvas.drawPath(path, paintBorder);	
    }
}

class HorizBarBlock extends BlockType {
    HorizBarBlock() {
	{
	    int s1 = size-2;
	    int s2 = size+size-2;
	    path = new Path();
	    path.moveTo(1, 1);
	    path.lineTo(1, s1);
	    path.lineTo(s2, s1);
	    path.lineTo(s2, 1);
	    path.close();
	}

	int s1 = size-1;
	int s2 = size+size-1;

	Point 
	    p0 = new Point(0, 0), 
	    p1 = new Point(0, s1),
	    p2 = new Point(s1, s1),
	    p3 = new Point(s2, s1),
	    p4 = new Point(s2, 0),
	    p5 = new Point(s1, 0);

	setFrontier(new Point[]{p0, p1},	
		    new Point[]{p1, p2, p3},
		    new Point[]{p3, p4},
		    new Point[]{p4, p5, p0});
    }

    boolean contains(int x, int y) {
	return x >= 0 && x < size+size &&
	    y >= 0 && y < size;
    }

    void draw(Canvas canvas) {
	super.draw(canvas);
	canvas.drawPoint(size+size-2, size-2, paintBorder);
    }
}

class VertBarBlock extends BlockType {
    VertBarBlock() {
	{
	    int s1 = size-2;
	    int s2 = size+size-2;
	    path = new Path();
	    path.moveTo(1, 1);
	    path.lineTo(1, s2);
	    path.lineTo(s1, s2);
	    path.lineTo(s1, 1);
	    path.close();
	}

	int s1 = size-1;
	int s2 = size+size-1;
	Point 
	    p0 = new Point(0, 0),
	    p1 = new Point(0, s1),
	    p2 = new Point(0, s2),
	    p3 = new Point(s1, s2),
	    p4 = new Point(s1, s1),
	    p5 = new Point(s1, 0);
	
	setFrontier(new Point[]{p0, p1, p2},
		    new Point[]{p2, p3},
		    new Point[]{p3, p4, p5},
		    new Point[]{p5, p0});
    }

    boolean contains(int x, int y) {
	return x >= 0 && x < size &&
	    y >= 0 && y < size+size;
    }

    void draw(Canvas canvas) {
	super.draw(canvas);
	canvas.drawPoint(size-2, size+size-2, paintBorder);
    }
}

class TriUpBlock extends BlockType {
    TriUpBlock() {
	{
	    int s1 = size - 2;
	    int s2 = size + size - 2;
	    path = new Path();
	    path.moveTo(1, 1);
	    path.lineTo(1, s2);
	    path.lineTo(s1, s2);
	    path.lineTo(s1, s1);
	    path.lineTo(s2, s1);
	    path.lineTo(s2, 1);
	    path.close();
	}

	int s1 = size - 1;
	int s2 = size + size - 1;
	Point 
	    p0 = new Point(0, 0), 
	    p1 = new Point(0, s1),
	    p2 = new Point(0, s2),
	    
	    p3 = new Point(s1, s2),
	    // p4 = new Point(s1, s1),
	    p5 = new Point(s2, s1),
	    p6 = new Point(s2, 0),
	    p7 = new Point(s1, 0);

	setFrontier(new Point[]{p0, p1, p2},
		    new Point[]{p2, p3, p5},
		    new Point[]{p3, p5, p6},
		    new Point[]{p6, p7, p0});
    }

    boolean contains(int x, int y) {
	return 
	    x>=0 
	    && y>=0 
	    && x < size + size
	    && y < size + size
	    && (x < size || y < size);
    }

    void draw(Canvas canvas) {
	super.draw(canvas);
	canvas.drawPoint(size-2, size+size-2, paintBorder);
	canvas.drawPoint(size+size-2, size-2, paintBorder);
    }
}

class TriDownBlock extends BlockType {
    TriDownBlock() {
	{
	    int s1 = size+1;
	    int s2 = size+size-2;
	    path = new Path();
	    path.moveTo(1, s1);
	    path.lineTo(1, s2);
	    path.lineTo(s2, s2);
	    path.lineTo(s2, 1);
	    path.lineTo(s1, 1);
	    path.lineTo(s1, s1);
	    path.close();
	}

	int s1 = size;
	int s2 = size+size-1;
	Point 
	    p0 = new Point(0, s1), 
	    p1 = new Point(0, s2),

	    p2 = new Point(s1, s2),
	    p3 = new Point(s2, s2),
	    p4 = new Point(s2, s1),
	    p5 = new Point(s2, 0),

	    p6 = new Point(s1, 0);

	setFrontier(new Point[]{p0, p1, p6},
		    new Point[]{p1, p2, p3},
		    new Point[]{p3, p4, p5},
		    new Point[]{p5, p6, p0});
    }

    boolean contains(int x, int y) {
	return 
	    x>=0 
	    && y>=0 
	    && x < size + size 
	    && y < size + size
	    && (x >= size || y >= size);
    }

    void draw(Canvas canvas) {
	super.draw(canvas);
	canvas.drawPoint(size+size-2, size+size-2, paintBorder);
    }
}
