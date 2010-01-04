package org.javia.blocks;

public class SmallBlocks extends BaseBlocks {
    private static final int initPos[][] = {
	{0, 8, 0, 9, 0, 10, 0, 11, 0, 12, 0, 15, 0, 16, 0, 19, 1, 0, 1, 3, 2, 13},
	{0, 12, 0, 15, 0, 16, 0, 19, 1, 0, 1, 3, 2, 8, 2, 10, 2, 13},
	{0, 13, 0, 14, 0, 16, 0, 19, 1, 0, 1, 3, 1, 8, 1, 11, 2, 9}
    };
    
    Block[] getConfig(int config) {
	int positions[] = initPos1[config];
	int size = positions.length / 2;
	Block[] blocks = new Block[size+1];
	for (int i=size+size-2; i >= 0; i-=2) {
	    int id = positions[i];
	    int pos = positions[i+1];
	    BlockType type = 
		id == 0 ? SMALL_SQUARE :
		id == 1 ? VERTICAL_BAR :
		HORIZONTAL_BAR;
	    int x = pos % 4;
	    int y = pos / 4;
	    blocks[i>>1] = new Block(type, x, y);
	}
	blocks[size] = new Block(BIG_SQUARE, 1, 0);
	return blocks;
    }
    
    void setLevel(int level) {
        boardView.setBoard(new Board(4, 5, 1, 3, getConfig(level)));
    }

}
