package org.codepay.common.orm.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * @Descriptions  系统基础实体 包含:id,version,create_time,last_modify_time 基本属性
 * @date 2015-2-9
 * @author codeWatching
 */
public abstract class BaseDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;


    @JSONField (format="yyyyMMddHHmmss")
    private Date create_time;

    @JSONField (format="yyyyMMddHHmmss")
    private Date last_modify_time;


    public Date getLast_modify_time() {
        return last_modify_time;
    }

    public void setLast_modify_time(Date last_modify_time) {
        this.last_modify_time = last_modify_time;
    }
    
    public BaseDomain(){
        super();
        this.create_time = new Date();

        this.last_modify_time = this.create_time;

    }
    /**
     * 查询对象时,如果没有查询到结果,则new一个空对象返回
     * 避免直接返回null
     * @return
     */
    public Boolean isEmptyModel(){
		return id == null ? Boolean.TRUE : Boolean.FALSE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Date getCreate_time() {
        return create_time;
    }
    
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
