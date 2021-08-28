package com.lch.board.domain;

public class PageDomain {
	private int page;
	private int pageNum;
	private int totalNum;


	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public PageDomain() {
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPage() {
		return page;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageNum() {
		return this.pageNum;
	}

}
