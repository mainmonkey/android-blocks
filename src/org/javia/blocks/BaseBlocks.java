package org.javia.blocks;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.Menu;
import android.view.SubMenu;
import android.view.MenuItem;
import android.util.Log;

abstract class BaseBlocks extends Activity {
    static private final BlockType 
	SMALL_SQUARE   = new SmallSquareBlock(),
	BIG_SQUARE     = new BigSquareBlock(),
	HORIZONTAL_BAR = new HorizBarBlock(),
	VERTICAL_BAR   = new VertBarBlock(),
	TRIANGLE_UP    = new TriUpBlock(),
	TRIANGLE_DOWN  = new TriDownBlock();

    BoardView boardView;

    abstract Block[] getConfig(int config);
    abstract void setLevel(int level);
	    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	setContentView(boardView = new BoardView(this));
	setLevel(1);
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
