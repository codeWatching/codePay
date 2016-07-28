package org.codepay.gateway.model.response;

public class PageResponse {

    public final static int PAGE_SHOW_COUNT = 10;
    private int pageno = 1;
    private int pagesize = 0;
    private long total = 0;

    public PageResponse(int pageno, int pagesize, long total){
        super();
        this.pageno = pageno;
        this.pagesize = pagesize;
        this.total = total;
    }

    public int getPageno() {
        return pageno;
    }

    public void setPageno(int pageno) {
        this.pageno = pageno;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

}
