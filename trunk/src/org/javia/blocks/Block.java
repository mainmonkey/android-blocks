package org.javia.blocks;

import android.graphics.Canvas;
import android.graphics.Paint;

class Block {
    BlockType type;
    int posX, posY;
    int available;

    Block(BlockType type, int x, int y) {
	this.type = type;
	posX = x;
	posY = y;
    }
    
    boolean contains(int x, int y) {
	return type.contains(x-posX, y-posY);
    }
    
    void draw(Canvas canvas) {
	canvas.translate(posX, posY);
	type.draw(canvas);
	canvas.translate(-posX, -posY);
    }

    public String toString() {
	return "block " + type + " (" + posX + ", " + posY + ") ";
    }

    int getAvailable() {
	return available;
    }

    /*
    int distanceTo(Block block, int dir) {
	switch (dir) {
	case Board.LEFT:
	    return Math.abs(posX - block.posX) & 0x3f;
	case Board.RIGHT:
	    return 64 - Math.abs(posX - block.posX) & 0x3f;
	case Board.UP:
	    return Math.abs(posY - block.posY) & 0x3f;
	case Board.DOWN:
	    return 64 - Math.abs(posY - block.posY) & 0x3f;
	}
	return 0;
    }
    */
}
