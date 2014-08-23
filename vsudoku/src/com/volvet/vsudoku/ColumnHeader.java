package com.volvet.vsudoku;


public class ColumnHeader extends LinkNode {
	public   static int COL_HEADER  = 0;
	public   static int ROW_HEADER  = 1;
	private  int  mSize;
	private  int  mType;
    public ColumnHeader(int type, int idx) {
    	super(type == COL_HEADER ? idx : 0,  type == COL_HEADER ? 0 : idx);
    	mSize = 0;
    	mType = type;
    }
    
    public int size() {
    	return mSize;
    }
    
    public int type() {
    	return mType;
    }
    
    public void insertNode(LinkNode node) {
    	if( mType == COL_HEADER ){
    		insertColNode(node);
    	} else {
    		insertRowNode(node);
    	}
    }
    
    protected void insertColNode(LinkNode node){
    	node.down(down());
    	down(node);
    	
    	mSize ++;
    }
    
    protected void insertRowNode(LinkNode node) {
    	node.right(right());
    	right(node);
    	
    	mSize ++;
    }    
}
