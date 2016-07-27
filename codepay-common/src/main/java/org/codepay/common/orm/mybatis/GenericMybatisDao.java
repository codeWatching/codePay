package org.codepay.common.orm.mybatis;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codepay.common.orm.GenericDao;
import org.codepay.common.orm.mybatis.paginator.domain.PageBounds;
import org.codepay.common.orm.mybatis.paginator.domain.PageList;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("rawtypes")
public class GenericMybatisDao<T> implements GenericDao<T> {

     private SqlSessionTemplate sqlSessionTemplate;
    
     @Autowired
     public void setsqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate)
     {
     this.sqlSessionTemplate = sqlSessionTemplate;
     }
    
     public SqlSessionTemplate getSqlSessionTemplate() {
     return sqlSessionTemplate;
     }

    @Override
    public long generateSequence(String sqlNameWithNameSpace) {
        return (Long) sqlSessionTemplate.selectOne(sqlNameWithNameSpace);
    }

    /**
     * 插入数据，返回affectedCount，没有改变id
     * 
     * @param sqlNameWithNameSpace
     * @param obj
     * @return
     */
    @Override
    public int insertAndReturnAffectedCount(String sqlNameWithNameSpace, T obj) {
        return sqlSessionTemplate.insert(sqlNameWithNameSpace, obj);
    }

    /**
     * 插入数据，sql里将id设置进obj，返回affectedCount
     * 
     * @param sqlNameWithNameSpace
     * @param obj
     * @return 因为返回值是int型，不是long型，所以不是id，而是affectedCount
     */
    @Override
    public int insertAndSetupId(String sqlNameWithNameSpace, T obj) {
        return sqlSessionTemplate.insert(sqlNameWithNameSpace, obj);
    }

    @Override
    public int insertBatchAndReturnAffectedCount(String sqlNameWithNameSpace, Collection<T> objs) {
        return sqlSessionTemplate.insert(sqlNameWithNameSpace, objs);
    }

    @Override
    public int insert(String sqlNameWithNameSpace, Map<String, Object> paramMap) {
        return sqlSessionTemplate.insert(sqlNameWithNameSpace, paramMap);
    }

    @Override
    public int delete(String sqlNameWithNameSpace) {
        return sqlSessionTemplate.delete(sqlNameWithNameSpace);
    }

    @Override
    public int delete(String sqlNameWithNameSpace, Map<String, Object> param) {
        return sqlSessionTemplate.delete(sqlNameWithNameSpace, param);
    }

    @Override
    public int delete(String sqlNameWithNameSpace, Object param) {
        return sqlSessionTemplate.delete(sqlNameWithNameSpace, param);
    }

    @Override
    public int update(String sqlNameWithNameSpace, Map<String, Object> param) {
        return sqlSessionTemplate.update(sqlNameWithNameSpace, param);
    }

    @Override
    public int updateBatchAndReturnAffectedCount(String sqlNameWithNameSpace, Collection<T> objs) {
        return sqlSessionTemplate.update(sqlNameWithNameSpace, objs);
    }

    @Override
    public int updateByObj(String sqlNameWithNameSpace, Object param) {
        return sqlSessionTemplate.update(sqlNameWithNameSpace, param);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T queryOne(String statement, long id) {
        return (T) sqlSessionTemplate.selectOne(statement, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T queryOne(String statement, String idStr) {
        return (T) sqlSessionTemplate.selectOne(statement, idStr);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T queryOne(String sqlNameWithNameSpace, Map map) {
        return (T) sqlSessionTemplate.selectOne(sqlNameWithNameSpace, map);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T queryOneByObject(String sqlNameWithNameSpace, String mapKey, Object mapValue) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(mapKey, mapValue);
        return (T) sqlSessionTemplate.selectOne(sqlNameWithNameSpace, paramMap);
    }

    /**
     * select count(*) where xxx
     * 
     * @param sqlNameWithNameSpace
     * @param map
     * @return
     */
    @Override
    public int queryCount(String sqlNameWithNameSpace, Map map) {
        return (Integer) sqlSessionTemplate.selectOne(sqlNameWithNameSpace, map);
    }

    /**
     * select * where 分页
     * 
     * @param sqlNameWithNameSpace
     * @param map
     * @param page
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public PageList<T> queryList(String sqlNameWithNameSpace, Map<String, Object> paramMap, PageBounds pageBounds) {
        return (PageList<T>) sqlSessionTemplate.selectList(sqlNameWithNameSpace, paramMap, pageBounds);
    }

    /**
     * select * where
     * 
     * @param sqlNameWithNameSpace
     * @param map
     * @return
     */
    @Override
    public <E> List<E> queryList(String sqlNameWithNameSpace, Map<String, Object> map) {
        return sqlSessionTemplate.selectList(sqlNameWithNameSpace, map);
    }

    /**
     * 慎用。不能用没有where条件的sql
     * 
     * @param sqlNameWithNameSpace
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> queryList(String sqlNameWithNameSpace) {
        return (List<T>) sqlSessionTemplate.selectList(sqlNameWithNameSpace);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> queryIdIn(String sqlNameWithNameSpace, long[] idList) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("idList", idList);
        return (List<T>) sqlSessionTemplate.selectList(sqlNameWithNameSpace, paramMap);
    }

    /**
     * 需要预防idList拼接后sql超长超出数据库sql长度的情况
     * 
     * @param sqlNameWithNameSpace
     * @param idList
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> queryIdIn(String sqlNameWithNameSpace, String[] idList) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("idList", idList);
        return (List<T>) sqlSessionTemplate.selectList(sqlNameWithNameSpace, paramMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> selectOneToMap(String sqlNameWithNameSpace, Map param) {
        return (Map<String, Object>) sqlSessionTemplate.selectOne(sqlNameWithNameSpace, param);
    }

    /**
     * count 查询
     * 
     * @param statement
     * @param parameter
     * @return
     */
    public Object selectOne(String statement, Object parameter) {
        return (Object) sqlSessionTemplate.selectOne(statement, parameter);
    }

}
