package com.volvet.vsudoku;

import android.util.AttributeSet;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class sudokuBoardView extends View {
	static final int LINE_COLOR = Color.BLACK;
	static final int TEXT_COLOR = Color.BLACK;
	static final int BACKGROUND_COLOR = Color.WHITE;
	static final int BACKGROUND_SELECTED_COLOR = Color.YELLOW;
	static final String TAG = "sudokuBoardView";
	static final float TEXT_RATIO = 0.8f;
	float   mGridLength;
	int     mSectorLineWidth;
	Paint   mLinePaint;
	Paint   mSectorLinePaint;
	
	
	public sudokuBoardView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public sudokuBoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mLinePaint = new Paint();
		mLinePaint.setColor(LINE_COLOR);
		mSectorLinePaint = new Paint();
		mSectorLinePaint.setColor(LINE_COLOR);
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
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		int i;
		float x, y;
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		float boardLength = mGridLength * 9.0f;
		
		for( i=0;i<=9;i++ ){
			x = paddingLeft + mGridLength * i;
			y = paddingTop + mGridLength * i;
			
			canvas.drawLine(x, paddingTop, x, boardLength, i%3==0 ? mSectorLinePaint : mLinePaint);
		
			canvas.drawLine(paddingLeft, y, boardLength,  y, i%3==0 ? mSectorLinePaint :  mLinePaint);			
		}		
	}
	
	protected void calcSectorLineWidth(int boardLength) {
		float density = getContext().getResources().getDisplayMetrics().density;
		float sizeInDip = boardLength / density;
		float sectorLineWidth = sizeInDip > 150 ? 3.0f : 2.0f;
		
		mSectorLineWidth = (int)(sectorLineWidth * density);		
		mSectorLinePaint.setStrokeWidth(mSectorLineWidth);
	}
}
