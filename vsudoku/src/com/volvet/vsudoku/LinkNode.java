package com.volvet.vsudoku;

public class LinkNode {
	private LinkNode  mUp = null;
	private LinkNode  mDown = null;
	private LinkNode  mLeft = null;
	private LinkNode  mRight = null;
	private int       mCol; 
	private int       mRow;
	
	public LinkNode() {
		this(0, 0, null, null, null, null);
	}
	
	public LinkNode(int col, int row, LinkNode up, LinkNode down, LinkNode left, LinkNode right) {
		mCol = col;
		mRow = row;
		Up(up);
		Down(down);
		Left(left);
		Right(right);
	}
	
	public LinkNode  Up() {
		return mUp;
	}
	
	public void Up(LinkNode node) {
		mUp = node;
	}
	
	public LinkNode Down() {
		return mDown;
	}
	
	public void Down(LinkNode node) {
		mDown = node;
	}
	
	public LinkNode Left() {
		return mLeft;
	}
	
	public void Left(LinkNode node){
		mLeft = node;
	}
	
	public LinkNode Right() {
		return mRight;
	}

	public void Right(LinkNode node) {
		mRight = node;
	}
	
	public int Col() {
		return mCol;
	}
	
	public void Col(int col) {
		mCol = col;
	}
	
	public int Row() {
		return mRow;
	}
	
	public void Row(int row) {
		mRow = row;
	}
 }
