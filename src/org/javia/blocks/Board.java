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
	if (active) {
	    if (Math.abs(dx) > Math.abs(dy)) {
		dx = Math.max(dx, -64);
		dx = Math.min(dx, 64);
		BlockType type = active.type;
		Point[] border = dx < 0 ? type.left : type.right;
		for (Point p: border) {
		    auxPoint.set(p.x+dx, p.y);
		    
		}
	    } else {

	    }
	}
    }
}