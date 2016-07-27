package org.codepay.common.orm.mybatis.manager;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.codepay.common.orm.domain.BaseDomain;
import org.codepay.common.orm.mybatis.paginator.domain.PageBounds;
import org.codepay.common.orm.mybatis.paginator.domain.PageList;


/**
 * @Descriptions 底层基础Manager,实现常用方法.
 * @date 2015年3月24日
 * @author codeWatching
 */
public interface BaseManager<M extends BaseDomain> {

    /**
     * 增加
     * 
     * @param
     * @return
     */
    public M save(M model);

    /**
     * 更新model.
     * 
     * @param model
     * @return
     */
    public M update(M model);

    /**
     * 删除model.
     * 
     * @param id
     * @return
     */
    public void delete(Long id);

    /**
     * 根据id查询.
     * 
     * @param id
     * @return
     */
    public M find(Long id);

    /**
     * 查询所有.
     * 
     * @return
     */
    public List<M> findAll();

    /**
     * 分页查询.
     * 
     * @param params
     * @param pageBounds
     * @return
     */
    public PageList<M> findByPage(Map<String, Object> params, PageBounds pageBounds);
	
    /**
     * 批量增加
     * 
     * @param
     * @return
     */
	int saveBatch(Collection<M> model);
	/**
	 * 批量添加
	 * @param list 只支持list集合
	 * @param step 步长,例如每一万条做一次batch
	 * @return
	 */
	int saveBatch(List<M> list,Integer step);
    /**
     * 批量更新
     * 
     * @param
     * @return
     */
    int updateBatch(Collection<M> model);
}
