package com.volvet.vsudoku;

import java.util.List;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SudokuGame {
	static final String TAG = "SudokuGame"; 
	static final int BTN_NUM = 10;
	
	private CellPool   mCellPool;
	private SudokuGameListener  mListener = null;
	private OnClickListener  mBtnClickListener = new  OnClickListener() {
		@Override
		public void onClick(View view) {
			int num = (Integer)view.getTag();
			
			if( mCellPool == null ){
				return;
			}
			Cell cell = mCellPool.getSelectedCell();			
			if( cell == null ) {
				return;
			}
			
			if( cell.getEditable() ){
				cell.setValue(num);
				
				if( mListener != null ){
					mListener.onCellValueChanged();
				}
			}
		}
	};
 	
	public SudokuGame() {
		mCellPool = null;
	}
	
	public void setCellPool(CellPool  cellPool) {
		mCellPool = cellPool;
	}
	
	public void setListener(SudokuGameListener listener) {
		mListener = listener;
	}
	
	public void createInputListener(List<Button> buttons) {
		if( buttons.size() != BTN_NUM){
			Log.e(TAG, "Create Input Listener failed");
			return;
		}
		
		int i;
		for( i=0;i<buttons.size();i++ ){
			Button btn = buttons.get(i);
			btn.setTag(i);
			btn.setOnClickListener(mBtnClickListener);
		}
	}
	
	public interface SudokuGameListener {
		void onCellValueChanged();
	}
}
