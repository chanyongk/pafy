package com.common.util;

public class PageVo {
	private int totalPage;
	private int nowPage;
	private int startPage;
	private int endPage;
	private int startNo;
	private int endNo;
	private int prevNo;
	private int nextNo;

	public int getTotalPage() {
		return totalPage;
	}
	public int getNowPage() {
		return nowPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public int getStartNo() {
		return startNo;
	}
	public int getEndNo() {
		return endNo;
	}
	public int getPrevNo() {
		return prevNo;
	}
	public int getNextNo() {
		return nextNo;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}
	public void setEndNo(int endNo) {
		this.endNo = endNo;
	}
	public void setPrevNo(int prevNo) {
		this.prevNo = prevNo;
	}
	public void setNextNo(int nextNo) {
		this.nextNo = nextNo;
	}
}
