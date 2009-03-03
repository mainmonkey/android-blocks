package org.javia.blocks;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

class Board {
    Block blocks[];
    Block active;

    Board(int sizeX, int sizeY, int size, Block blocks[]) {
	this.blocks = blocks;
    }

    void draw(Canvas canvas) {
	canvas.drawColor(0xffffffff);
	for (Block block: blocks) {
	    block.draw(canvas);
	}
    }

    private Block findBlockAt(int x, int y) {
	for (Block block: blocks) {
	    if (block.contains(x, y)) {
		return block;
	    }
	}
	return null;
    }

    void setActive(int x, int y) {
	active = findBlockAt(x, y);
    }

    Point auxPoint = new Point();
    void move(int dx, int dy) {
	if (active != null) {
	    int space = spaceAvailable(active, dx < 0 ? LEFT : RIGHT);
	    if (space > 0) {

	    } else {
		space = spaceAvailable(active, dy < 0 ? UP : DOWN);
	    }
	}
    }

    int spaceAvailable(Block block, int dir) {
	for (Block b: blocks) {
	    b.available = -1;
	}
	return spaceAvRec(block, dir);
    }

    static final int 
	RIGHT = 1,
	UP    = 2,
	LEFT  = 3,
	DOWN  = 4;

    int spaceAvRec(Block block, int dir) {
	if (block.available >= 0) {
	    return block.available;
	}
	int available = 64;
	Point[] frontier = block.type.frontier[dir];
	int move = (dir == LEFT || dir == UP) ? -64 : +64;
	if (dir == LEFT || dir == RIGHT) {
	    for (Point p: frontier) {
		Block next = findBlockAt(p.x + move, p.y);
		if (next != null) {
		    int dist = Math.abs(next.posX - block.posX) % 64;
		    int localAv = spaceAvRec(next, dir) + dist;
		    available = Math.min(available, localAv);
		}		
	    }
	} else {
	    for (Point p: frontier) {
		Block next = findBlockAt(p.x, p.y + move);
		if (next != null) {
		    int dist = Math.abs(next.posY - block.posY) % 64;
		    int localAv = spaceAvRec(next, dir) + dist;
		    available = Math.min(available, localAv);
		}		
	    }
	}
	block.available = available;
	return available;
    }
}