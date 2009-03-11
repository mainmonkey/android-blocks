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

    static final int SIZE = Blocks.SIZE;

    Block blocks[];
    Block active;
    Block big;
    int width, height;
    int sizeX, sizeY;
    int endX, endY;
    int MAX_X, MAX_Y;
    Paint paint = new Paint();
    boolean solved = false;

    Board(int sizeX, int sizeY, int endX, int endY, Block blocks[]) {
	this.blocks = blocks;
	this.big    = blocks[blocks.length-1];
	this.sizeX = sizeX;
	this.sizeY = sizeY;
	this.endX  = endX;
	this.endY  = endY;
	width  = sizeX * SIZE;
	height = sizeY * SIZE;
	MAX_X = width  - 1;
	MAX_Y = height - 1;
	paint.setColor(0xffffffff);
	paint.setStyle(Paint.Style.FILL);
    }

    void draw(Canvas canvas) {
	canvas.drawRect(0, 0, width, height, paint);
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
	// Blocks.log("active " + x + ' ' + y + ' ' + (x/64f) + ' ' + (y/64f));
	active = findBlockAt(x, y);
	return active;
    }

    int snap(int x) {
	int d = x % SIZE;
	if (d < 8) {
	    return x - d;
	}
	int dd = SIZE - d;
	if (dd < 8) {
	    return x + dd;
	}
	return x;
    }

    boolean moveTo(int x, int y) {
	boolean changed = false;
	if (active != null) {
	    //snap to grid
	    x = snap(x);
	    y = snap(y);
	    int dx = x - active.posX;
	    int dy = y - active.posY;
	    int space, dir;
	    for (int i = 0; i < 2; ++i) {
		if (dx != 0 && (space = spaceAvailable(active, (dir = (dx < 0 ? LEFT : RIGHT)))) > 0) {
		    // Blocks.log("move horiz " + dir + ' ' + space);
		    move(active, dir, Math.min(space, Math.abs(dx)));
		    dx = 0;
		    changed = true;
		} 
		if (dy != 0 && (space = spaceAvailable(active, (dir = (dy < 0 ? UP : DOWN)))) > 0) {
		    // Blocks.log("move vert " + dir + ' ' + space);
		    move(active, dir, Math.min(space, Math.abs(dy)));
		    dy = 0;
		    changed = true;
		}
	    }
	}
	return changed;
    }

    boolean solvedNow() {
	if (!solved && big.posX == endX * SIZE && big.posY == endY * SIZE) {
	    solved = true;
	    return true;
	}
	return false;
    }

    private void move(Block block, int dir, int amount) {
	// Blocks.log("move " + block + ' ' + dir + ' ' + amount);
	if (amount <= 0) {
	    return;
	}
	Point[] frontier = block.type.frontier[dir];
	final int amountX = dir == LEFT ? -amount : dir == RIGHT ? amount : 0;
	final int amountY = dir == UP   ? -amount : dir == DOWN ? amount : 0;	
	final int x = block.posX;
	final int y = block.posY;
	Block next;
	for (Point p: frontier) {
	    final int newX = x + p.x + amountX;
	    final int newY = y + p.y + amountY;
	    if ((next = findBlockAt(newX, newY)) != null) {
		if (next == block) {
		    Blocks.log("same " + p.x + ' ' + p.y + ' ' + amountX + ' ' + amountY);
		}
		final int aux = (dir == LEFT || dir == RIGHT) ? x-next.posX : y-next.posY;
		final int dist = Math.abs(aux) % SIZE;
		move(next, dir, amount - dist);
	    }
	}
	block.posX += amountX;
	block.posY += amountY;
    }

    int spaceAvailable(Block block, int dir) {
	for (Block b: blocks) {
	    b.available = -1;
	}
	return spaceAvRec(block, dir);
    }

    private int spaceAvRec(final Block block, final int dir) {
	int av;
	if ((av = block.getAvailable()) >= 0) {
	    return av;
	}
	int available = SIZE;
	Point[] frontier = block.type.frontier[dir];
	final int x = block.posX;
	final int y = block.posY;
	final int moveX = dir == LEFT ? -SIZE : dir == RIGHT ? SIZE : 0;
	final int moveY = dir == UP ? -SIZE : dir == DOWN ? SIZE : 0;
	Block next;
	for (Point p: frontier) {
	    final int newX = x + p.x + moveX;
	    final int newY = y + p.y + moveY;
	    int localAv = SIZE;
	    if (newX < 0 || newX > MAX_X || newY < 0 || newY > MAX_Y) {
		localAv = dir == LEFT ? (newX+SIZE) : dir == RIGHT ? (MAX_X + SIZE - newX) :
		    dir == UP ? (newY + SIZE) : (MAX_Y + SIZE - newY);
	    } else if ((next = findBlockAt(newX, newY)) != null) {
		final int aux = (dir == LEFT || dir == RIGHT) ? x-next.posX : y-next.posY;
		final int dist = Math.abs(aux) % SIZE;
		localAv = spaceAvRec(next, dir) + dist;
		// Blocks.log("av " + dir + ' ' + newX + ' ' + newY + ' ' + block + ' ' + next + ' ' + dist + ' ' + localAv);
	    }
	    available = Math.min(available, localAv);
	    if (available <= 0) {
		break;
	    }		
	}
	block.available = available;
	return available;
    }
}