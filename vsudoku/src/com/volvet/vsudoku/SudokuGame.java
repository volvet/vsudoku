package com.volvet.vsudoku;

import java.util.List;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SudokuGame {
	static final String TAG = "SudokuGame"; 
	static final int BTN_NUM = 10;
	private OnClickListener  mBtnClickListener = new  OnClickListener() {
		@Override
		public void onClick(View view) {
			int num = (Integer)view.getTag();
			
			Log.i(TAG, "Click " + Integer.toString(num));
		}
	};
 	
	public SudokuGame() {
		
	}
	
	public void createInputListener(List<Button> buttons) {
		if( buttons.size() != BTN_NUM){
			Log.e(TAG, "Create Input Listener failed");
			return;
		}
		
		int i;
		for( i=0;i<buttons.size();i++ ){
			Button btn = buttons.get(i);
			btn.setTag(i+1);
			btn.setOnClickListener(mBtnClickListener);
		}
	}
}
