package org.javia.blocks;

import android.graphics.Canvas;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Paint;
import android.view.MotionEvent;

class BoardView extends View {
    Board board;
    int width, height;
    int border;
    Paint paint = new Paint();
    float downX, downY;

    BoardView(Context context, Board board) {
	super(context);
	this.board = board;
	paint.setColor(0xff000000);
	paint.setStyle(Paint.Style.FILL);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	width  = w;
	height = h;
	border = (int)((height - 6 * 64) / 2);
    }

    public BoardView(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    public void onDraw(Canvas canvas) {
	canvas.translate(0, border);
	board.draw(canvas);
	canvas.translate(0, -border);
	canvas.drawRect(0, 0, width, border, paint);
	canvas.drawRect(0, height-border-1, width, height, paint);
	canvas.drawRect(width-127, height-border+4, width-1, height-border+12, BigSquareBlock.green);
    }

    public boolean onTouchEvent(MotionEvent event) {
	int action = event.getAction();
	float x = event.getX();
	float y = event.getY();

	switch (action) {
	case MotionEvent.ACTION_DOWN:
	    downX = x;
	    downY = y;
	    float effY = downY - border;
	    effY = Math.max(effY, 0);
	    effY = Math.min(effY, 6*64);
	    Block active = board.setActive(Math.round(downX), Math.round(effY));
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
	    }
	    break;
	}
	return true;
    }
}