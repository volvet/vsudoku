package com.volvet.vsudoku;

import android.util.Log;
import java.util.List;
import java.util.ArrayList;


public class LinkMatrix {
	private static final String TAG = "LinkMatrix";
	
	private static final int  COL_OFFSET   = 1;
	
	public static final int  STATUS_RESOLVED  = -1;
	public static final int  STATUS_NO_ANSWER = -2;
	
	private   LinkHeader[]   mColLinks = null;
	private   LinkHeader[]   mRowLinks = null;
	private   int            mRowNum,  mColNum;
	private   List<Integer>  mResultList = new ArrayList<Integer>();
	
	public LinkMatrix() {
		mRowNum = 0;
		mColNum = 0;		
	}
	
	public void init(int colNum, int rowNum,  int[] dataMatrix) {
	    mRowNum = rowNum;
	    mColNum = colNum;
	    
	    initColLinks();
	    initRowLinks();
	    initNodes(dataMatrix);
	    
	    mResultList.clear();
	}
	
	public int search(int k) {
		int col = selectCol();
		
		if( col < 0 ) return col;
		
		cover(col);
		LinkNode colNode = mColLinks[col + COL_OFFSET];
		
		while( null != colNode ){
			mResultList.add(colNode.row());		
			LinkNode rowNode = mRowLinks[colNode.row()];
			while( null != rowNode ){
				cover(rowNode.col());
				rowNode = rowNode.right();
			}
			
			int res = search(k+1);
			if( res < 0 ){
				return res;
			}
			rowNode = mRowLinks[colNode.row()];
			while( null != rowNode ){
				uncover(rowNode.col());
				rowNode = rowNode.right();
			}
			
			mResultList.remove(mResultList.indexOf(colNode.row()));
			colNode = colNode.down();
		}
		uncover(col);
		return k;
	}
	
	public List<Integer> GetResult() {
		return mResultList;
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
			mRowLinks[i] = new LinkHeader(LinkHeader.ROW_HEADER, i);	
			mRowLinks[i-1].down(mRowLinks[i]);
			mRowLinks[i].up(mRowLinks[i-1]);
		}
	}
	
	private void initNodes(int [] dataMatrix){
		int i, j;
		for( j=0;j<mRowNum;j++ ){
			for( i=0;i<mColNum;i++ ){
				if( dataMatrix[j*mColNum+i] != 0 ){
				    LinkNode node = new LinkNode(i, j);
				    mRowLinks[j].insertNode(node);
				    mColLinks[i+COL_OFFSET].insertNode(node);
				}
			}
		}
	}
	
	private void cover(int colIdx) {
		LinkHeader  colHeader = mColLinks[colIdx + COL_OFFSET];
		LinkNode   colNode = colHeader.down();
		
		colHeader.left().right(colHeader.right());
		colHeader.right().left(colHeader.left());
		
		while( null != colNode ){
			LinkHeader rowHeader = mRowLinks[colNode.row()];
			LinkNode   rowNode = rowHeader;
			
			while( null != rowNode ){				
				rowNode.up().down(rowNode.down());
				rowNode.down().up(rowNode.up());
				mColLinks[rowNode.col()].decreaseSize();
				rowNode = rowNode.right();
			}
			
			colNode = colNode.down();
		}
		
	}
	
	private void uncover(int colIdx) {
		LinkHeader  colHeader = mColLinks[colIdx + COL_OFFSET];
		LinkNode   colNode = colHeader.down();
		
		while( null != colNode ) {
			LinkHeader rowHeader = mRowLinks[colNode.row()];
			LinkNode   rowNode = rowHeader;
			
			while( null != rowNode ){
				mColLinks[rowNode.col()].increaseSize();
				rowNode.down().up(rowNode);
				rowNode.up().down(rowNode);
				rowNode = rowNode.right();
			}
			
			colNode = colNode.down();
		}
		colHeader.right().left(colHeader);
		colHeader.left().right(colHeader);
	}
	
	private int selectCol() {	
		int  minSize = mRowNum + 1;
		int  col = -1;
		LinkHeader  root = mColLinks[0];
		
		if( null == root.right() ){
			return STATUS_RESOLVED;
		}
		
		LinkHeader colHeader = (LinkHeader)root.right();
		
		while( null != colHeader ){
			if( colHeader.size() < minSize ){
				col = colHeader.col();
				minSize = colHeader.size();
			}
			
			if( minSize == 0 ){
				return STATUS_NO_ANSWER;
			}
			
			if( minSize == 1) {
				return col;
			}
		}
		
		return col;
	}
	
	public static boolean Test() {
		int []  dataMatrix = {
				0, 1, 1, 1,
				1, 0, 0, 1,
				1, 1, 1, 0, 
				0, 1, 0, 1,
				1, 0, 0, 0, 
		};
		
		LinkMatrix  linkMatrix = new LinkMatrix();
		linkMatrix.init(4, 5, dataMatrix);
		int i;
		for( i=0;i<linkMatrix.mColLinks.length;i++ ){
			Log.i(TAG, "col " + Integer.toString(i) + " size = " + Integer.toString(linkMatrix.mColLinks[i].size()));
			LinkNode node = linkMatrix.mColLinks[i];
			while( node != null ){
				Log.i(TAG, "pos = " + Integer.toString(node.col()) + ", " + Integer.toString(node.row()));
				node = node.down();
			}
		}
		for( i=0;i<linkMatrix.mRowLinks.length;i++ ){
			Log.i(TAG, "row " + Integer.toString(i) + " size = " + Integer.toString(linkMatrix.mRowLinks[i].size()));
			LinkNode node = linkMatrix.mRowLinks[i];
			while( node != null ){
				Log.i(TAG, "pos = " + Integer.toString(node.col()) + ", " + Integer.toString(node.row()));
				node = node.right();
			}
		}
		
		
		
		return false;
	}
}
