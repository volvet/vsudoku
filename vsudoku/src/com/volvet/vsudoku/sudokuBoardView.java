package com.volvet.vsudoku;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class SudokuBoardView extends View {
	static final int LINE_COLOR = Color.BLACK;
	static final int TEXT_COLOR = Color.BLACK;
	static final int BACKGROUND_COLOR = Color.WHITE;
	static final int BACKGROUND_SELECTED_COLOR = Color.YELLOW;
	static final int BACKGROUND_READONLY_COLOR = Color.rgb(50,50,50);
	static final int BACKGROUND_INVALIDATE_COLOR = Color.RED;
	static final String TAG = "sudokuBoardView";
	static final float TEXT_RATIO = 0.8f;
	private float   mGridLength;
	private int     mCellValueLeft;
	private int     mCellValueTop;
	private Paint   mLinePaint;
	private Paint   mSectorLinePaint;	
	private Paint   mCellValuePaint;
	private Paint   mSelectedCellBackgroundPaint;
	private Paint   mReadOnlyBackgroundPaint;
	private Paint   mInvalidateBackgroundPaint;
	
	private CellPool   mCellPool;
	
	public SudokuBoardView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public SudokuBoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mLinePaint = new Paint();
		mLinePaint.setColor(LINE_COLOR);
		mSectorLinePaint = new Paint();
		mSectorLinePaint.setColor(LINE_COLOR);
		mCellValuePaint = new Paint();
		mCellValuePaint.setColor(TEXT_COLOR);
		mSelectedCellBackgroundPaint = new Paint();
		mSelectedCellBackgroundPaint.setColor(BACKGROUND_SELECTED_COLOR);
		mSelectedCellBackgroundPaint.setAlpha(100);
		mReadOnlyBackgroundPaint = new Paint();
		mReadOnlyBackgroundPaint.setColor(BACKGROUND_READONLY_COLOR);
		mReadOnlyBackgroundPaint.setAlpha(100);
		mInvalidateBackgroundPaint = new Paint();
		mInvalidateBackgroundPaint.setColor(BACKGROUND_INVALIDATE_COLOR);
		mInvalidateBackgroundPaint.setAlpha(100);
		
		mCellPool = CellPool.buildDebugCellPool();
		
	}
	
	@Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		
		if( (widthMode != MeasureSpec.UNSPECIFIED) && (heightMode != MeasureSpec.UNSPECIFIED) ){
			int boardLength = Math.min(width - getPaddingLeft() - getPaddingRight(), height - getPaddingTop() - getPaddingBottom());
			mGridLength = boardLength / 9f;
			calcSectorLineWidth(boardLength);
		} else {
			Log.w(TAG, "sudokuBoardView:onMeasure:  width and height mode is UNSPECIFIED");
		}		
		
		float textSize = mGridLength * 0.8f;
		mCellValuePaint.setTextSize(textSize);
		
		mCellValueTop = (int)(mGridLength - mCellValuePaint.getTextSize())/2;
		mCellValueLeft = (int)(mGridLength - mCellValuePaint.measureText("9"))/2;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		int i;
		float x, y;
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		float boardLength = mGridLength * 9.0f;
		
		for( i=0;i<=Cell.SUDOKU_SIZE;i++ ){
			x = paddingLeft + mGridLength * i;
			y = paddingTop + mGridLength * i;
			
			canvas.drawLine(x, paddingTop, x, boardLength, i%3==0 ? mSectorLinePaint : mLinePaint);
		
			canvas.drawLine(paddingLeft, y, boardLength,  y, i%3==0 ? mSectorLinePaint :  mLinePaint);			
		}		
		
		onDrawCells(canvas, paddingLeft, paddingTop);
	}
	
	protected void onDrawCells(Canvas canvas, int paddingLeft, int paddingTop) {
		int i, j;
		float ascent = mCellValuePaint.ascent();
		
		for( j=0;j<Cell.SUDOKU_SIZE;j++ ){
			for( i=0;i<Cell.SUDOKU_SIZE;i++ ){
				Cell cell = mCellPool.getCell(i, j);
				int cellLeft = Math.round(mGridLength * i + paddingLeft);
				int cellTop  = Math.round(mGridLength * j + paddingTop);
				if( cell.getValue() != 0 ){
					canvas.drawText(Integer.toString(cell.getValue()), cellLeft + mCellValueLeft, 
							cellTop + mCellValueTop - ascent, mCellValuePaint);					
				}
				if( cell.getEditable() == false ){
					canvas.drawRect(cellLeft, cellTop, cellLeft + mGridLength, cellTop + mGridLength, mReadOnlyBackgroundPaint);
				}
				if( cell.getValidate() == false ){
					canvas.drawRect(cellLeft, cellTop, cellLeft + mGridLength, cellTop + mGridLength, mInvalidateBackgroundPaint);
				}
			}
		}
		
		if( mCellPool.getSelectedCell() != null ){
			int left = paddingLeft +  Math.round(mCellPool.getSelectedCell().getX()*mGridLength);
			int top = paddingTop +  Math.round(mCellPool.getSelectedCell().getY()*mGridLength);
			canvas.drawRect(left, top, left + mGridLength, top + mGridLength, mSelectedCellBackgroundPaint);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int)event.getX();
		int y = (int)event.getY();
		
		switch(event.getAction()){
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			setSelectedCell(x, y);
			break;
		case MotionEvent.ACTION_CANCEL:
            setSelectedCell(-1, -1);
			break;
		default:
			break;			
		}			
		
		postInvalidate();
		return true;
	}
	
	protected void calcSectorLineWidth(int boardLength) {
		float density = getContext().getResources().getDisplayMetrics().density;
		float sizeInDip = boardLength / density;
		float sectorLineWidth = sizeInDip > 150 ? 3.0f : 2.0f;
		mSectorLinePaint.setStrokeWidth((int)(sectorLineWidth * density));
	}
	
	protected void setSelectedCell(int x, int y){
		int posX = (int)((x - getPaddingLeft())/mGridLength);
		int posY = (int)((y - getPaddingTop())/mGridLength);
		
		if( (posX < Cell.SUDOKU_SIZE) && (posY < Cell.SUDOKU_SIZE) ){
			mCellPool.setSelectedCell(posX, posY);
		}
	}
	
	public CellPool getCellPool() {
	    return mCellPool;
	}
	
	public void  newGame() {
		SudokuBuilder builder = new SimpleSudokuBuiler();
		mCellPool = new CellPool(builder.buildSudokuProtoType());
		postInvalidate();
	}
}
