package org.codepay.common.orm.mybatis.paginator.domain;

import java.io.Serializable;

public class Pagination implements Serializable {
	private static final long serialVersionUID = -889157967741969292L;

	private int pageNo = 1;

	private int pageSize;
 
	private int totalCount = -1;
	
	public Pagination(int pageNo, int pageSize, int totalCount) {
        super();
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

 

	public Pagination(int pageSize) {
		if (pageSize > 0) {
			this.pageSize = pageSize;
		}
	}

	/**
	 * 每页的记录数量，无默认值.
	 */
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize > 0) {
			this.pageSize = pageSize;
		}
	}

	/**
	 * 当前页的页号,序号从1开始.
	 */
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 第一条记录在结果集中的位置,序号从0开始.
	 */
	public int getFirst() {
		if (pageNo < 1 || pageSize < 1)
			return 0;
		else
			return (pageNo - 1) * pageSize;
	}

	/**
	 * 总记录数.
	 */
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 计算总页数.
	 */
	public int getTotalPages() {
		if (totalCount == -1)
			return -1;

		int count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 返回下页的页号,序号从1开始.
	 */
	public int getNextPage() {
		if (isHasNext())
			return pageNo + 1;
		else
			return pageNo;
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 返回上页的页号,序号从1开始.
	 */
	public int getPrePage() {
		if (isHasPre())
			return pageNo - 1;
		else
			return pageNo;
	}

    @Override
    public String toString() {
        return "Pagination [pageNo=" + pageNo + ", pageSize=" + pageSize + ", totalCount=" + totalCount + "]";
    }
	
	
}
