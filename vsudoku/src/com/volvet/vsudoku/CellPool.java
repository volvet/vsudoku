package com.volvet.vsudoku;

public class CellPool {
	private    Cell[][]   mCells;
	private    CellGroup[]  mRows;
	private    CellGroup[]  mColumns;
	private    CellGroup[]  mSectors;
		
	public CellPool() {
		mCells = new Cell[Cell.SUDOKU_SIZE][Cell.SUDOKU_SIZE];
		mRows = new CellGroup[Cell.SUDOKU_SIZE];
		mColumns = new CellGroup[Cell.SUDOKU_SIZE];
		mSectors = new CellGroup[Cell.SUDOKU_SIZE];
		
		init();
	}
	
	protected void init() {
		int i, j;
		for( j=0;j<Cell.SUDOKU_SIZE;j++ ){
			for( i=0;i<Cell.SUDOKU_SIZE;i++ ){
				mCells[j][i] = new Cell(0, i, j);
			}
		}
		
		for( i=0;i<Cell.SUDOKU_SIZE;i++ ){
			mRows[i] = CellGroup.BuildCellGroup(mCells, i, CellGroup.CELL_GROUP_ROW);
			mColumns[i] = CellGroup.BuildCellGroup(mCells, i, CellGroup.CELL_GROUP_COLUMN);
			mSectors[i] = CellGroup.BuildCellGroup(mCells, i, CellGroup.CELL_GROUP_SECTOR);
		}
	}
	
	public Cell  getCell(int i, int j) {
		return mCells[j][i];
	}

}
