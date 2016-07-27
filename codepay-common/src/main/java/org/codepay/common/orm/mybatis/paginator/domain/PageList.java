package org.codepay.common.orm.mybatis.paginator.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


/**
 * @Descriptions  包含“分页”信息的List
 * @date 2015-2-9
 * @author codeWatching
 */
public class PageList<E> extends ArrayList<E> implements Serializable {

    private static final long serialVersionUID = 1412759446332294208L;
    
    private Pagination pagination;

    public PageList() {}
    
	public PageList(Collection<? extends E> c) {
		super(c);
	}

	
	public PageList(Collection<? extends E> c,Pagination p) {
        super(c);
        this.pagination = p;
    }

    public PageList(Pagination p) {
        this.pagination = p;
    }


	/**
	 * 得到分页器，通过Paginator可以得到总页数等值
	 * @return
	 */
	public Pagination getPagination() {
		return pagination;
	}

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

	
}
