package com.volvet.vsudoku;

public class CellPool {
	private    Cell[][]   mCells;
	private    CellGroup[]  mRows;
	private    CellGroup[]  mColumns;
	private    CellGroup[]  mSectors;
	private    Cell         mSelectedCell;
	
	private final static int sampleSudoku[] = {
		0, 5, 2, 0, 0, 6, 0, 0, 0, 
		1, 6, 0, 9, 0, 0, 0, 0, 4, 
		0, 4, 9, 8, 0, 3, 6, 2, 0, 
		4, 0, 0, 0, 0, 0, 8, 0, 0,  
		0, 8, 3, 2, 0, 1, 5, 9, 0, 
		0, 0, 1, 0, 0, 0, 0, 0, 2, 
		0, 9, 7, 3, 0, 5, 2, 4, 0, 
		2, 0, 0, 0, 0, 9, 0, 5, 6, 
		0, 0, 0, 1, 0, 0, 9, 7, 0
	};
	
	public static CellPool  buildDebugCellPool() {		
		return new CellPool(sampleSudoku);		
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
		
		mSelectedCell = null;
	}
	
	protected void init(int[] values) {
		int i;
		for( i=0;i<Cell.SUDOKU_SIZE*Cell.SUDOKU_SIZE;i++ ){		
			if( i< values.length ){
				boolean bEditable = values[i] == 0 ? true : false;
				mCells[i/Cell.SUDOKU_SIZE][i%Cell.SUDOKU_SIZE] = new Cell(values[i], i%Cell.SUDOKU_SIZE, i/Cell.SUDOKU_SIZE, bEditable);
			} else {
				mCells[i/Cell.SUDOKU_SIZE][i%Cell.SUDOKU_SIZE] = new Cell(0, i%Cell.SUDOKU_SIZE, i/Cell.SUDOKU_SIZE);
			}
		}
		
		buildCellGroup();
		
		mSelectedCell = null;
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
	
	public Cell getSelectedCell() {
		return mSelectedCell;
	}
	
	public void setSelectedCell(int x, int y){
		if( (x>=0) && (x<Cell.SUDOKU_SIZE) && 
			(y>=0) && (y<Cell.SUDOKU_SIZE) ){
			mSelectedCell = mCells[y][x];
		} else {
			mSelectedCell = null;
		}
	}
	
	public boolean validate() {
		boolean bValidate = true;
		int i;
		for( i=0;i<Cell.SUDOKU_SIZE;i++ ){
			bValidate &= mRows[i].validate();
			bValidate &= mColumns[i].validate();
			bValidate &= mSectors[i].validate();
		}
		
		return bValidate;
	}
	
	public boolean validate(int x, int y) {
		boolean bValidate = true;
		if( (x>=0) && (x<Cell.SUDOKU_SIZE) && 
			(y>=0) && (y<Cell.SUDOKU_SIZE) ){
			bValidate &= mRows[y].validate();
			bValidate &= mColumns[x].validate();
			bValidate &= mSectors[y/3*3 + x/3].validate();
		} 
		return bValidate;
	}

}
