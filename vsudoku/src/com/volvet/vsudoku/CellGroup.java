package com.volvet.vsudoku;

import java.util.Map;
import java.util.HashMap;

public class CellGroup {	
	public final static int CELL_GROUP_ROW = 0;
	public final static int CELL_GROUP_COLUMN = 1;
	public final static int CELL_GROUP_SECTOR = 2;
	
	static CellGroup BuildCellGroup(Cell[][] cells, int idx, int groupType) {
		CellGroup  cellGroup = new CellGroup();
		
		cellGroup.addCells(cells, idx, groupType);
		
		return cellGroup;
	}
	
	
	private Cell[] mCells;
	public CellGroup() {
		mCells = new Cell[Cell.SUDOKU_SIZE];
	}
	
	protected void addCells(Cell[][] cells, int idx, int groupType) {
		int i;
		switch(groupType) {
		case CELL_GROUP_ROW:
			for( i=0;i<Cell.SUDOKU_SIZE;i++ ){
				mCells[i] = cells[idx][i];
			}
			break;
		case CELL_GROUP_COLUMN:
			for( i=0;i<Cell.SUDOKU_SIZE;i++ ) {
				mCells[i] = cells[i][idx];
			}
			break;
		case CELL_GROUP_SECTOR:
			for( i=0;i<Cell.SUDOKU_SIZE;i++ ){
				mCells[i] = cells[idx/3*3 + i/3][idx%3*3 + i%3];
			}
			break;
		}
	}
	
	public void   cleanValidateFlag() {
		for( int i=0;i<mCells.length;i++ ){
			mCells[i].setValidate(true);
		}
	}
	
	public boolean validate() {
		boolean bValidate = true;	
		
		Map<Integer, Cell>  hashCells = new HashMap<Integer, Cell>();  
		
		for( int i=0;i<mCells.length;i++ ){			
			if( mCells[i].getValue() != 0 ){
				int value = mCells[i].getValue();
				if( hashCells.get(value) != null ){
					mCells[i].setValidate(false);
					hashCells.get(value).setValidate(false);
					bValidate = false;
				} else {
					hashCells.put(value, mCells[i]);
				}
			}
		}
		
		return bValidate;
	}
}
