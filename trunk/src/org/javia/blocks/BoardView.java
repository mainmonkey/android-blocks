package org.javia.blocks;

import android.graphics.Canvas;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.widget.Toast;

class BoardView extends View {
    Context context;
    int width, height;

    float downX, downY;

    Board board;
    int borderX, borderY;

    BoardView(Context context) {
	super(context);
	this.context = context;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	width  = w;
	height = h;
	update();
    }

    void setBoard(Board board) {
	this.board = board;
	update();
	invalidate();
    }

    private void update() {
	if (board != null && width > 0) {
	    borderY = (height - board.height) / 2;
	    borderX = (width  - board.width)  / 2; 
	}
    }
    
    public void onDraw(Canvas canvas) {
	canvas.drawColor(0xff000000);
	canvas.translate(borderX, borderY);
	board.draw(canvas);
	canvas.translate(-borderX, -borderY);
	//canvas.drawRect(0, 0, width, border, paint);
	//canvas.drawRect(0, height-border-1, width, height, paint);
	// canvas.drawRect(width-127, height-border+4, width-1, height-border+12, BigSquareBlock.green);
    }

    public boolean onTouchEvent(MotionEvent event) {
	int action = event.getAction();
	float x = event.getX();
	float y = event.getY();

	switch (action) {
	case MotionEvent.ACTION_DOWN:
	    downX = x;
	    downY = y;
	    float effY = Math.min(Math.max(downY - borderY, 0), board.MAX_Y);
	    float effX = Math.min(Math.max(downX - borderX, 0), board.MAX_X);
	    Block active = board.setActive(Math.round(effX), Math.round(effY));
	    if (active != null) {
		downX = (downX - active.posX);
		downY = (downY - active.posY);
	    }
	    break;

	case MotionEvent.ACTION_MOVE:
	    float dx = x - downX;
	    float dy = y - downY;
	    int idx = Math.round(dx), idy = Math.round(dy);
	    if (board.moveTo(idx, idy)) {		
		invalidate();
		if (board.solvedNow()) {
		    Toast.makeText(context, "Solved!", Toast.LENGTH_LONG).show();
		}
	    }
	    break;
	}
	return true;
    }
}