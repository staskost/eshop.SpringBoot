package com.staskost.eshop.model;

import java.util.List;

public class RequestResult<T extends BaseEntity> {

	private int totalCount;

	private List<? extends BaseEntity> paginatedResults;

	public RequestResult() {
	}

	public RequestResult(int count, List<? extends BaseEntity> paginatedResults) {
		this.totalCount = count;
		this.paginatedResults = paginatedResults;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<? extends BaseEntity> getPaginatedResults() {
		return paginatedResults;
	}

	public void setPaginatedResults(List<? extends BaseEntity> paginatedResults) {
		this.paginatedResults = paginatedResults;
	}

	@Override
	public String toString() {
		return "Result [count=" + totalCount + ", results=" + paginatedResults + "]";
	}

}