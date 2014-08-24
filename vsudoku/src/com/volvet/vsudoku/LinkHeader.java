package com.volvet.vsudoku;


public class LinkHeader extends LinkNode {
	public   final static int COL_HEADER  = 0;
	public   final static int ROW_HEADER  = 1;
	public   final static int ROOT_HEADER = 2;
	private  int  mSize;
	private  int  mType;
    public LinkHeader(int type, int idx) {
    	super(type == COL_HEADER ? idx : -1,  type == COL_HEADER ? -1 : idx);
    	mSize = 0;
    	mType = type;
    	
    	linkSelf();
    }
    
    public LinkHeader() {
    	super(-1, -1);
    	mType = ROOT_HEADER;
    	mSize = 0;
    	
    	linkSelf();
    }
    
    public int size() {
    	return mSize;
    }
    
    public void size(int size) {
    	mSize = size;
    }
    
    public void decreaseSize() {
    	-- mSize; 
    }
    
    public void increaseSize() {
    	++ mSize;
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
    	down().up(node);
    	node.down(down());
    	node.up(this);
    	down(node);
    	
    	mSize ++;
    }
    
    protected void insertRowNode(LinkNode node) {
    	right().left(node);
    	node.right(right());
  	    node.left(this);
  	    right(node);
    	
    	mSize ++;
    }
    
    private void linkSelf() {
    	switch(mType)
    	{
    	case ROOT_HEADER:
    		right(this);
    		left(this);
    		break;
    	case COL_HEADER:
    	case ROW_HEADER:
    		right(this);
    		left(this);
    		up(this);
    		down(this);
    		break;
    	}
    }
}
