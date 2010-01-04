package org.javia.blocks;

public class LargeBlocks extends BaseBlocks {
    private static final int initPos[][] = {
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

    Block[] getConfig(int config) {
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

    void setLevel(int level) {
        boardView.setBoard(new Board(5, 6, 3, 4, getConfig(level)));
    }

}
