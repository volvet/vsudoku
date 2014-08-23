package com.volvet.vsudoku;

public class LinkMatrix {
	private   LinkHeader[]   mColLinks = null;
	private   LinkHeader[]   mRowLinks = null;
	private   int            mRowNum,  mColNum;
	
	public LinkMatrix() {
		mRowNum = 0;
		mColNum = 0;		
	}
	
	public void init(int rowNum, int colNum,  int[] dataMatrix) {
	    mRowNum = rowNum;
	    mColNum = colNum;
	    
	    initColLinks();
	    initRowLinks();
	    initNodes(dataMatrix);
	}

	private void initColLinks() {
		int i;
		mColLinks = new LinkHeader[mColNum + 1];
		mColLinks[0] = new LinkHeader(LinkHeader.COL_HEADER, -1);
		for( i=0;i<mColNum;i++ ){
			mColLinks[i+1] = new LinkHeader(LinkHeader.COL_HEADER, i);	
			mColLinks[i].right(mColLinks[i+1]);
			mColLinks[i+1].left(mColLinks[i]);
		}		
	}
	
	private void initRowLinks() {
		int i;
		mRowLinks = new LinkHeader[mRowNum];
		mRowLinks[0] = new LinkHeader(LinkHeader.ROW_HEADER, 0);
		for( i=1;i<mRowNum;i++ ){
			mRowLinks[i+1] = new LinkHeader(LinkHeader.ROW_HEADER, i);	
			mRowLinks[i].down(mRowLinks[i+1]);
			mRowLinks[i+1].up(mRowLinks[i]);
		}
	}
	
	private void initNodes(int [] dataMatrix){
		int i, j;
		for( j=0;j<mRowNum;j++ ){
			for( i=0;i<mColNum;i++ ){
				if( dataMatrix[j*mColNum+i] != 0 ){
				    LinkNode node = new LinkNode(i, j);
				    mRowLinks[i].insertNode(node);
				    mColLinks[j].insertNode(node);
				}
			}
		}
	}
	
	public static boolean Test() {
		return false;
	}
}
