package com.volvet.vsudoku;

import com.volvet.vsudoku.R;

import java.util.List;
import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;

public class MainActivity extends Activity implements SudokuGame.SudokuGameListener {
	private SudokuGame  mGame = new SudokuGame();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		createInputListener();
		mGame.setCellPool(((SudokuBoardView)findViewById(R.id.vsudoku_board)).getCellPool());
		mGame.setListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	protected void createInputListener() {
		List<Button> btnList = new ArrayList<Button>();	
		
		btnList.add((Button)findViewById(R.id.button_c));
		btnList.add((Button)findViewById(R.id.button_1));
		btnList.add((Button)findViewById(R.id.button_2));
		btnList.add((Button)findViewById(R.id.button_3));
		btnList.add((Button)findViewById(R.id.button_4));
		btnList.add((Button)findViewById(R.id.button_5));
		btnList.add((Button)findViewById(R.id.button_6));
		btnList.add((Button)findViewById(R.id.button_7));
		btnList.add((Button)findViewById(R.id.button_8));
		btnList.add((Button)findViewById(R.id.button_9));		
		
		mGame.createInputListener(btnList);
	}

	@Override
	public void onCellValueChanged() {
		// TODO Auto-generated method stub
		findViewById(R.id.vsudoku_board).postInvalidate();
	}

}
