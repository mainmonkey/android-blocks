package org.javia.blocks;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.Menu;
import android.view.SubMenu;
import android.view.MenuItem;
import android.util.Log;

public class Blocks extends Activity
{
    static final int SIZE = 64;
    static private final int initPos1[][] = {
	{0, 8, 0, 9, 0, 10, 0, 11, 0, 12, 0, 15, 0, 16, 0, 19, 1, 0, 1, 3, 2, 13},
	{0, 12, 0, 15, 0, 16, 0, 19, 1, 0, 1, 3, 2, 8, 2, 10, 2, 13},
	{0, 13, 0, 14, 0, 16, 0, 19, 1, 0, 1, 3, 1, 8, 1, 11, 2, 9}
    };

    static private final int initPos2[][] = {
	{2, 3, 8, 9, 14, 15, 4, 10, 16, 18, 21, 19, 22, 0}, 
	{2, 3, 4, 8, 9, 10, 12, 14, 16, 18, 21, 19, 22, 0}, 
	{2, 8, 14, 20, 26, 27, 12, 18, 24, 3, 15, 4, 16, 0}, 
	{5, 11, 17, 16, 22, 28, 14, 20, 26, 2, 12, 3, 18, 0}, 
	{4, 5, 10, 11, 16, 17, 2, 8, 14, 18, 21, 19, 22, 0}, 
	{2, 3, 4, 8, 9, 10, 12, 18, 24, 14, 16, 20, 22, 0}, 
	{2, 8, 14, 15, 20, 26, 12, 18, 24, 3, 21, 4, 22, 0}, 
	{12, 13, 18, 19, 24, 25, 14, 20, 26, 3, 16, 4, 22, 0}, 
	{4, 5, 10, 11, 16, 17, 12, 18, 24, 2, 20, 8, 21, 0}, 
	{2, 6, 7, 8, 12, 13, 18, 24, 26, 3, 16, 4, 22, 14}, 
	{14, 15, 21, 27, 10, 5, 12, 22, 23, 18, 3, 19, 10, 0}, 
	{12, 13, 18, 19, 24, 25, 26, 16, 11, 1, 4, 2, 22, 14}
    };

    static private final BlockType 
	SMALL_SQUARE   = new SmallSquareBlock(),
	BIG_SQUARE     = new BigSquareBlock(),
	HORIZONTAL_BAR = new HorizBarBlock(),
	VERTICAL_BAR   = new VertBarBlock(),
	TRIANGLE_UP    = new TriUpBlock(),
	TRIANGLE_DOWN  = new TriDownBlock();

    BoardView boardView;

    private Block[] getConfig1(int config) {
	int positions[] = initPos1[config];
	int size = positions.length / 2;
	Block[] blocks = new Block[size+1];
	for (int i = 0; i < size; ++i) {
	    int id = positions[i+i];
	    int pos = positions[i+i+1];
	    BlockType type = 
		id == 0 ? SMALL_SQUARE :
		id == 1 ? VERTICAL_BAR :
		HORIZONTAL_BAR;
	    int x = pos % 4;
	    int y = pos / 4;
	    blocks[i] = new Block(type, x, y);
	}
	blocks[size] = new Block(BIG_SQUARE, 1, 0);
	return blocks;
    }

    private Block[] getConfig2(int config) {
	Block blocks[] = new Block[14];
	int positions[] = initPos2[config];
	for (int i = 0; i < 14; ++i) {
	    BlockType type = 
		i < 6  ? SMALL_SQUARE :
		i == 6 ? VERTICAL_BAR :
		i < 9  ? (config < 10 ? VERTICAL_BAR:HORIZONTAL_BAR) :
		i < 11 ? TRIANGLE_UP :
		i < 13 ? TRIANGLE_DOWN : 
		BIG_SQUARE;
	    int pos = positions[i];
	    int y = pos % 6;
	    int x = pos / 6;
	    blocks[i] = new Block(type, x, y);
	}
	return blocks;
    }
	    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	setContentView(boardView = new BoardView(this));
	setLevel(1);
    }

    void setLevel(int level) {
	Board board;
	if (level <= 3) {
	    board = new Board(4, 5, 1, 3, getConfig1(level - 1));
	} else {
	    board = new Board(5, 6, 3, 4, getConfig2(level - 4));
	}
        boardView.setBoard(board);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
	Menu sub;
	sub = menu.addSubMenu(0, 0, 0, "Level Green");
	for (int i = 1; i <= 3; ++i) {
	    sub.add(0, i, 0, "Level " + i);
	}

	sub = menu.addSubMenu(0, 0, 0, "Level Blue");
	for (int i = 4; i <= 9; ++i) {
	    sub.add(0, i, 0, "Level " + i);
	}

	sub = menu.addSubMenu(0, 0, 0, "Level Magenta");
	for (int i = 10; i <= 15; ++i) {
	    sub.add(0, i, 0, "Level " + i);
	}
	return true;	
    }

    public boolean onOptionsItemSelected(MenuItem item) {
	int id = item.getItemId();
	if (id > 0) {
	    setLevel(id);
	    return true;
	}
	return false;
    }

    static void log(String msg) {
	Log.d("Blocks", msg);
    }
}
