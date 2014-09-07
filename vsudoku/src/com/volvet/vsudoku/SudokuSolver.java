package com.volvet.vsudoku;

public class SudokuSolver {
    private LinkMatrix  mLinkMatrix;
    private int         mColNum,  mRowNum;
    private int[]       mDataMatrix = null;
    
    public SudokuSolver() {
    	mLinkMatrix = new LinkMatrix();
    	mDataMatrix = new int [4 * Cell.SUDOKU_SIZE * Cell.SUDOKU_SIZE * Cell.SUDOKU_SIZE * Cell.SUDOKU_SIZE * Cell.SUDOKU_SIZE];
    }
    
    public void init(int[] sudokuMatrix) {
    	calcLinkMatrixSize(sudokuMatrix);
    	buildDataMatrix(sudokuMatrix);
    }
    
    public int solve(){
    	mLinkMatrix.init(mColNum, mRowNum, mDataMatrix);
    	return mLinkMatrix.search(0);
    }
    
    protected void calcLinkMatrixSize(int[] sudokuMatrix){
    	int i;
    	mColNum = 4 * Cell.SUDOKU_SIZE * Cell.SUDOKU_SIZE;
    	mRowNum = 0;
    	for( i=0;i<sudokuMatrix.length;i++ ){
    		if( sudokuMatrix[i] != 0 ){
    			mRowNum += Cell.SUDOKU_SIZE;
    		} else {
    			mRowNum += 1;
    		}
    	}
    }
    
    private void buildDataMatrix(int [] sudokuMatrix) {
    	int i;
    	int x, y;
    	int lineIdx = 0;
    	
    	for( i=0;i<sudokuMatrix.length;i++ ){
    		y = i/Cell.SUDOKU_SIZE;
    		x = i%Cell.SUDOKU_SIZE;
    		lineIdx += buildDataMatrixLines(sudokuMatrix[i], lineIdx, x, y);
    	}
    }
    
    private int buildDataMatrixLines(int value, int lineIdx, int x, int y){
    	if( value != 0 ){
    		buildDataMatrixLine(value, lineIdx, x, y);
    		return 1;
    	} else {
    		for( int i=1;i<Cell.SUDOKU_SIZE+1;i++ )
    		    buildDataMatrixLine(i, lineIdx + i, x, y);
    		
    		return Cell.SUDOKU_SIZE;
    	}
    }
    
    private void buildDataMatrixLine(int value, int lineIdx, int x, int y){
    	int pos = lineIdx * mColNum;
    	
    	zeroDataMatrixLine(pos);
    	
    	mDataMatrix[pos + y*Cell.SUDOKU_SIZE + x] = 1;
    	mDataMatrix[pos + Cell.SUDOKU_SIZE*Cell.SUDOKU_SIZE + y*Cell.SUDOKU_SIZE + value - 1] = 1;
    	mDataMatrix[pos + 2*Cell.SUDOKU_SIZE*Cell.SUDOKU_SIZE + x*Cell.SUDOKU_SIZE + value -1] = 1;
    	mDataMatrix[pos + 3*Cell.SUDOKU_SIZE*Cell.SUDOKU_SIZE + ((y/3)*3+x/3)*Cell.SUDOKU_SIZE + value - 1] = 1;
    }
    
    private void zeroDataMatrixLine(int pos){
    	for( int i=0;i<mColNum;i++ ){
    		mDataMatrix[pos + i] = 0; 
    	}
    }

}
