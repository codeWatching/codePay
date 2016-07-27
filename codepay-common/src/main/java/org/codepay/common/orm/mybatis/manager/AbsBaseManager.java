package org.codepay.common.orm.mybatis.manager;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codepay.common.orm.domain.BaseDomain;
import org.codepay.common.orm.mybatis.dao.BaseDao;
import org.codepay.common.orm.mybatis.paginator.domain.PageBounds;
import org.codepay.common.orm.mybatis.paginator.domain.PageList;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Descriptions 抽象Manager,实现BaseManager定义.
 * @author codeWatching
 */
public abstract class AbsBaseManager<M extends BaseDomain> implements BaseManager<M> {

    protected BaseDao<M> dao;

    public void setDao(BaseDao<M> dao) {
        this.dao = dao;
    }

    @Override
    public int saveBatch(Collection<M> model) {
        return dao.saveBatch(model);
    }

    @Override
    public int updateBatch(Collection<M> model) {
        return dao.updateBatch(model);
    }

    @Override
    public int saveBatch(List<M> list, Integer step) {
        for (int i = 0;; i += step) {
            if (list.size() - i <= step) {
                dao.saveBatch(list.subList(i, list.size()));
                break;
            }
            List<M> subList = list.subList(i, i + step);
            dao.saveBatch(subList);
        }
        return step;

    }

    @Override
    @Transactional
    public M save(M model) {
        return dao.save(model);
    }

    @Override
    public M update(M model) {
        model.setLast_modify_time(new Date());
        return dao.update(model);
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }

    @Override
    public M find(Long id) {
        return dao.get(id);
    }

    @Override
    public List<M> findAll() {
        return this.dao.findAll();
    }

    @Override
    public PageList<M> findByPage(Map<String, Object> params, PageBounds pageBounds) {
        return dao.findPageByArgs(params, pageBounds);
    }

    public BaseDao<M> getDao() {
        return dao;
    }
}
