package com.volvet.vsudoku;

public class CellPool {
	private    Cell[][]   mCells;
	private    CellGroup[]  mRows;
	private    CellGroup[]  mColumns;
	private    CellGroup[]  mSectors;
	
	public static CellPool  buildDebugCellPool() {
		int[] values = new int[Cell.SUDOKU_SIZE*Cell.SUDOKU_SIZE];
		int i;
		
		for(i=0;i<Cell.SUDOKU_SIZE*Cell.SUDOKU_SIZE;i++ ){
			values[i] = (i%9) + 1;
		}
		
		return new CellPool(values);		
	}
		
	public CellPool() {
		allocResource();
		
		init();
	}
	
	public CellPool(int[] values) {		
		allocResource();
		
		init(values);
	}
	
	protected void allocResource() {
		mCells = new Cell[Cell.SUDOKU_SIZE][Cell.SUDOKU_SIZE];
		mRows = new CellGroup[Cell.SUDOKU_SIZE];
		mColumns = new CellGroup[Cell.SUDOKU_SIZE];
		mSectors = new CellGroup[Cell.SUDOKU_SIZE];
	}
	
	protected void init() {
		int i, j;
		for( j=0;j<Cell.SUDOKU_SIZE;j++ ){
			for( i=0;i<Cell.SUDOKU_SIZE;i++ ){
				mCells[j][i] = new Cell(0, i, j);
			}
		}
		
		buildCellGroup();
	}
	
	protected void init(int[] values) {
		int i;
		for( i=0;i<Cell.SUDOKU_SIZE*Cell.SUDOKU_SIZE;i++ ){
			if( i< values.length ){
				mCells[i/Cell.SUDOKU_SIZE][i%Cell.SUDOKU_SIZE] = new Cell(values[i], i/Cell.SUDOKU_SIZE, i%Cell.SUDOKU_SIZE);
			} else {
				mCells[i/Cell.SUDOKU_SIZE][i%Cell.SUDOKU_SIZE] = new Cell(0, i/Cell.SUDOKU_SIZE, i%Cell.SUDOKU_SIZE);
			}
		}
		
		buildCellGroup();
	}
	
	public Cell  getCell(int i, int j) {
		return mCells[j][i];
	}
	
	protected void buildCellGroup() {
		int i;
		for( i=0;i<Cell.SUDOKU_SIZE;i++ ){
			mRows[i] = CellGroup.BuildCellGroup(mCells, i, CellGroup.CELL_GROUP_ROW);
			mColumns[i] = CellGroup.BuildCellGroup(mCells, i, CellGroup.CELL_GROUP_COLUMN);
			mSectors[i] = CellGroup.BuildCellGroup(mCells, i, CellGroup.CELL_GROUP_SECTOR);
		}
	}

}
