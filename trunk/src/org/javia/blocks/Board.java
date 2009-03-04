package org.javia.blocks;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

class Board {
    static final int 
	LEFT  = 0,
	DOWN  = 1,
	RIGHT = 2,
	UP    = 3;

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

    Block setActive(int x, int y) {
	active = findBlockAt(x, y);
	return active;
    }

    boolean moveTo(int x, int y) {
	if (active != null) {
	    int dx = x - active.posX;
	    int dy = y - active.posY;
	    if (dx != 0 || dy != 0) {
		int space, dir; 
		if (dx != 0 && (space = spaceAvailable(active, (dir = dx < 0 ? LEFT : RIGHT))) > 0) {
		    move(active, dir, Math.min(space, Math.abs(dx)));
		    return true;
		} else if (dy != 0 && (space = spaceAvailable(active, (dir = dy < 0 ? UP : DOWN))) > 0) {
		    move(active, dir, Math.min(space, Math.abs(dy)));
		    return true;
		}
	    }
	}
	return false;
    }

    private void move(Block block, int dir, int amount) {
	Blocks.log("" + block + ' ' + dir + ' ' + amount);
	if (amount <= 0) {
	    return;
	}
	Point[] frontier = block.type.frontier[dir];
	int signedAmount = dir==LEFT || dir==UP ? -amount : amount;
	int x = block.posX;
	int y = block.posY;
	if (dir == LEFT || dir == RIGHT) {
	    for (Point p: frontier) {
		Block next = findBlockAt(p.x + x + signedAmount, p.y + y);
		if (next != null && next != block) {
		    int dist = Math.abs(next.posX - x) % 64;
		    move(next, dir, amount - dist);
		}		
	    }
	    block.posX += signedAmount;
	} else {
	    for (Point p: frontier) {
		Block next = findBlockAt(p.x + x, p.y + y + signedAmount);
		if (next != null && next != block) {
		    int dist = Math.abs(next.posY - y) % 64;
		    move(next, dir, amount - dist);
		}		
	    }
	    block.posY += signedAmount;
	}	
    }

    int spaceAvailable(Block block, int dir) {
	for (Block b: blocks) {
	    b.available = -1;
	}
	return spaceAvRec(block, dir);
    }

    private int spaceAvRec(Block block, int dir) {
	if (block.available >= 0) {
	    return block.available;
	}
	int available = 64;
	Point[] frontier = block.type.frontier[dir];
	int x = block.posX;
	int y = block.posY;
	int move = (dir == LEFT || dir == UP) ? -64 : +64;
	if (dir == LEFT || dir == RIGHT) {
	    for (Point p: frontier) {
		Block next = findBlockAt(p.x + x + move, p.y + y);
		if (next != null && next != block) {
		    int dist = Math.abs(next.posX - x) % 64;
		    int localAv = spaceAvRec(next, dir) + dist;
		    available = Math.min(available, localAv);
		}		
	    }
	} else {
	    for (Point p: frontier) {
		Block next = findBlockAt(p.x + x, p.y + y + move);
		if (next != null && next != block) {
		    int dist = Math.abs(next.posY - y) % 64;
		    int localAv = spaceAvRec(next, dir) + dist;
		    available = Math.min(available, localAv);
		}		
	    }
	}
	block.available = available;
	return available;
    }
}