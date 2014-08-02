package com.volvet.vsudoku;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class SimpleSudokuBuiler implements SudokuBuilder {
	
	private final static int[] baseSudoku = {
		4, 7, 1, 9, 3, 6, 5, 8, 2, 
		2, 6, 3, 5, 8, 1, 4, 7, 9, 
		9, 5, 8, 7, 4, 2, 1, 6, 3, 
		8, 2, 5, 6, 7, 9, 3, 1, 4, 
		3, 4, 6, 1, 5, 8, 2, 9, 7, 
		1, 9, 7, 4, 2, 3, 8, 5, 6, 
		5, 8, 9, 2, 6, 4, 7, 3, 1, 
		7, 1, 4, 3, 9, 5, 6, 2, 8,
		6, 3, 2, 8, 1, 7, 9, 4, 5,
	};
	
	private  int[]  mRandomArrayForNum = new int[Cell.SUDOKU_SIZE];
	//private  int[]  mRandonArrayForRow = new int[Cell.SUDOKU_SIZE];
	//private  int[]  mRandonArrayForColumn = new int[Cell.SUDOKU_SIZE];
	
	public SimpleSudokuBuiler() {
		
	}

	@Override
	public int[] buildSudokuProtoType() {
		// TODO Auto-generated method stub
		int[] sudokuArray = new int[Cell.SUDOKU_SIZE*Cell.SUDOKU_SIZE];
		init();
		System.arraycopy(baseSudoku, 0, sudokuArray, 0, baseSudoku.length);	
		processSudokuArray(sudokuArray);
		return sudokuArray;
	}
	
	private void init() {
		Calendar  calendar = Calendar.getInstance();
		Random  random = new Random();
		List<Integer> randomList = new ArrayList<Integer>();
		int i;
		
		random.setSeed(calendar.getTimeInMillis());
		
		for( i=0;i<Cell.SUDOKU_SIZE;i++ ){
			int randomNum;
			do {
				randomNum = random.nextInt(Cell.SUDOKU_SIZE) + 1;
				if( !randomList.contains(randomNum) ){
					randomList.add(randomNum);
					break;
				}
			} while(true);
			
			mRandomArrayForNum[i] = randomNum;
		}
	}
	
    private void processSudokuArray(int[] sudokuArray) {
    	int i;
    	for( i=0;i<sudokuArray.length;i++ ){
    		sudokuArray[i] = mRandomArrayForNum[sudokuArray[i]-1];
    	}
    }

}
