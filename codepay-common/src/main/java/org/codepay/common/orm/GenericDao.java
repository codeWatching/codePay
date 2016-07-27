package org.codepay.common.orm;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.codepay.common.orm.mybatis.paginator.domain.PageBounds;
import org.codepay.common.orm.mybatis.paginator.domain.PageList;

/**
 * @Descriptions  系统公共基础DAO
 * @date 2015-2-9
 * @author codeWatching
 */
public interface GenericDao<T> {
	
	public long generateSequence(String sqlNameWithNameSpace);
	
	public int insertAndReturnAffectedCount(String sqlNameWithNameSpace, T obj);
	
	public int insertAndSetupId(String sqlNameWithNameSpace, T obj);
	
	public int insertBatchAndReturnAffectedCount(String sqlNameWithNameSpace,Collection<T> objs);

	public int insert(String sqlNameWithNameSpace, Map<String, Object> paramMap);
	
	public int update(String sqlNameWithNameSpace, Map<String, Object> param);

    public int updateBatchAndReturnAffectedCount(String sqlNameWithNameSpace, Collection<T> objs);
    
	public int updateByObj(String sqlNameWithNameSpace, Object param);
	
    public int delete(String sqlNameWithNameSpace);

    public int delete(String sqlNameWithNameSpace, Map<String, Object> param);
    
    public int delete(String sqlNameWithNameSpace,Object param);

	public T queryOne(String statement, long id);
	
	public T queryOne(String statement, String idStr);
	
	public T queryOne(String sqlNameWithNameSpace, Map<String, Object> map);
	
	public T queryOneByObject(String sqlNameWithNameSpace, String mapKey, Object mapValue);
	
	public int queryCount(String sqlNameWithNameSpace, Map<String, Object> map);
	
    public PageList<T> queryList(String sqlNameWithNameSpace, Map<String, Object> paramMap, PageBounds pageBounds);
	
	public <E> List<E> queryList(String sqlNameWithNameSpace, Map<String, Object> map);
	
	public List<T> queryList(String sqlNameWithNameSpace);
	
	public List<T> queryIdIn(String sqlNameWithNameSpace, long[] idList);
	
	public List<T> queryIdIn(String sqlNameWithNameSpace, String[] idList);
	
	public Map<String, Object> selectOneToMap(String sqlNameWithNameSpace, Map<String, Object> param);

}
