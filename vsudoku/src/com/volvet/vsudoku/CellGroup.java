package com.volvet.vsudoku;

import java.util.Map;
import java.util.HashMap;

public class CellGroup {
	public static final int SUDOKU_SIZE = 9;
	
	private Cell[] mCells;
	public CellGroup() {
		mCells = new Cell[SUDOKU_SIZE];
	}

	public void addCell(Cell cell, int pos){
		mCells[pos] = cell;
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
