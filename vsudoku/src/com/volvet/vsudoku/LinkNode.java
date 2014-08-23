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
	
	public LinkNode(int col, int row) {
		this(col, row, null, null, null, null);
	}
	
	public LinkNode(int col, int row, LinkNode up, LinkNode down, LinkNode left, LinkNode right) {
		col(col);
		row(row);
		up(up);
		down(down);
		left(left);
		right(right);
	}
	
	public LinkNode  up() {
		return mUp;
	}
	
	public void up(LinkNode node) {
		mUp = node;
	}
	
	public LinkNode down() {
		return mDown;
	}
	
	public void down(LinkNode node) {
		mDown = node;
	}
	
	public LinkNode left() {
		return mLeft;
	}
	
	public void left(LinkNode node){
		mLeft = node;
	}
	
	public LinkNode right() {
		return mRight;
	}

	public void right(LinkNode node) {
		mRight = node;
	}
	
	public int col() {
		return mCol;
	}
	
	public void col(int col) {
		mCol = col;
	}
	
	public int row() {
		return mRow;
	}
	
	public void row(int row) {
		mRow = row;
	}
 }
