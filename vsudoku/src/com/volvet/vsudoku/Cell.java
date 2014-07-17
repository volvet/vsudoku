package com.volvet.vsudoku;

public class Cell {
	private int  mValue;
	private boolean  mEditable;
	private boolean  mValidate;
	private int mX, mY;
	
	public Cell() {
		this(0, 0, 0, true);
	}
	
	public Cell(int value) {
		this(value, 0, 0, true);
	}
	
	public Cell(int value, int x, int y, boolean bEditable) {
		setValue(value);
		setX(x);
		setY(y);
		setEditable(bEditable);
		
		mValidate = true;
	}
	
	public void setEditable(boolean bEditable) {
		mEditable = bEditable;
	}
	
	public boolean getEditable() {
		return mEditable;
	}

	public void setX(int x) {
		mX = x;
	}
	
	public void setY(int y) {
		mY = y;
	}
	
	public int getX() {
		return mX;
	}
	
	public int getY() {
		return mY;
	}
	
	public int getValue() {
		return mValue;
	}
	
	public void setValue(int value) {
		mValue = value;
	}
	
	public boolean getValidate() {
		return mValidate;
	}
	
	public void setValidate(boolean bValidate) {
		mValidate = bValidate;
	}
}
