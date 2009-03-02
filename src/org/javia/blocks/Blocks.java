package org.javia.blocks;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.Menu;
import android.view.SubMenu;
import android.view.MenuItem;

public class Blocks extends Activity
{
    static private final int SIZE = 64;
    static private final int initPos[][] = {
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

    Board board;
    static private final BlockType 
	smallSq  = new SmallSquareBlock(SIZE),
	bigSq    = new BigSquareBlock(SIZE),
	horizBar = new HorizBarBlock(SIZE),
	vertBar  = new VertBarBlock(SIZE),
	triUp    = new TriUpBlock(SIZE),
	triDown  = new TriDownBlock(SIZE);

    private Block[] getConfig(int config) {
	Block blocks[] = new Block[14];
	int positions[] = initPos[config];
	for (int i = 0; i < 14; ++i) {
	    BlockType type = 
		i < 6  ? smallSq : 
		i < 9  ? vertBar :
		i < 11 ? triUp :
		i < 13 ? triDown : 
		bigSq;
	    int pos = positions[i];
	    int y = pos % 6;
	    int x = pos / 6;
	    blocks[i] = new Block(type, x*SIZE, y*SIZE);
	}
	return blocks;
    }
	    
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
	board = new Board(5, 6, 64, getConfig(0));	
        setContentView(new BoardView(this, board));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
	return true;	
    }

    public boolean onOptionsItemSelected(MenuItem item) {
	return false;
    }
}
