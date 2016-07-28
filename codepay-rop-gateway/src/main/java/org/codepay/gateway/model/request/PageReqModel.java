package org.codepay.gateway.model.request;

import com.rop.annotation.IgnoreSign;

/**
 * 分页请求信息.
 *
 * @author codeWatching
 * @date 2016-03-02
 * @since JDK1.6
 */
public abstract class PageReqModel extends ReqModel{

    @IgnoreSign
    private int pageno;
    
    @IgnoreSign
    private int pagesize;

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
    public PageReqModel(){}
    public PageReqModel(int pageno, int pagesize){
        super();
        this.pageno = pageno;
        this.pagesize = pagesize;
    }

}

