package com.staskost.eshop.model;

import java.util.List;

public class RequestResult<T> {

	private int totalCount;

	private List<T> paginatedResults;

	public RequestResult() {
	}

	public RequestResult(int count, List<T> paginatedResults) {
		this.totalCount = count;
		this.paginatedResults = paginatedResults;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getPaginatedResults() {
		return paginatedResults;
	}

	public void setPaginatedResults(List<T> paginatedResults) {
		this.paginatedResults = paginatedResults;
	}

	@Override
	public String toString() {
		return "Result [count=" + totalCount + ", results=" + paginatedResults + "]";
	}

}