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
}