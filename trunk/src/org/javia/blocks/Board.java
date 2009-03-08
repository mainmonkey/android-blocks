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
	// Blocks.log("active " + x + ' ' + y + ' ' + (x/64f) + ' ' + (y/64f));
	active = findBlockAt(x, y);
	return active;
    }

    boolean moveTo(int x, int y) {
	if (active != null) {
	    final int dx = x - active.posX;
	    final int dy = y - active.posY;
	    int space, dir; 
	    Blocks.log("dx " + dx + ' ' + dy);
	    if (dx != 0 && (space = spaceAvailable(active, (dir = (dx < 0 ? LEFT : RIGHT)))) > 0) {
		Blocks.log("move horiz " + dir + ' ' + space);
		move(active, dir, Math.min(space, Math.abs(dx)));
		return true;
	    } 
	    if (dy != 0 && (space = spaceAvailable(active, (dir = (dy < 0 ? UP : DOWN)))) > 0) {
		Blocks.log("move vert " + dir + ' ' + space);
		move(active, dir, Math.min(space, Math.abs(dy)));
		return true;
	    }
	}
	return false;
    }

    private void move(Block block, int dir, int amount) {
	Blocks.log("move " + block + ' ' + dir + ' ' + amount);
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
		final int dist = Math.abs(aux) % 64;
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

    private static final int MAX_X = 5*64-1;
    private static final int MAX_Y = 6*64-1;

    private int spaceAvRec(final Block block, final int dir) {
	int av;
	if ((av = block.getAvailable()) >= 0) {
	    return av;
	}
	int available = 64;
	Point[] frontier = block.type.frontier[dir];
	final int x = block.posX;
	final int y = block.posY;
	final int moveX = dir == LEFT ? -64 : dir == RIGHT ? 64 : 0;
	final int moveY = dir == UP ? -64 : dir == DOWN ? 64 : 0;
	Block next;
	for (Point p: frontier) {
	    final int newX = x + p.x + moveX;
	    final int newY = y + p.y + moveY;
	    int localAv = 64;
	    if (newX < 0 || newX > MAX_X || newY < 0 || newY > MAX_Y) {
		localAv = dir == LEFT ? (newX+64) : dir == RIGHT ? (MAX_X + 64 - newX) :
		    dir == UP ? (newY + 64) : (MAX_Y + 64 - newY);
	    } else if ((next = findBlockAt(newX, newY)) != null) {
		final int aux = (dir == LEFT || dir == RIGHT) ? x-next.posX : y-next.posY;
		final int dist = Math.abs(aux) % 64;
		localAv = spaceAvRec(next, dir) + dist;
		Blocks.log("av " + dir + ' ' + newX + ' ' + newY + ' ' + block + ' ' + next + ' ' + dist + ' ' + localAv);
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