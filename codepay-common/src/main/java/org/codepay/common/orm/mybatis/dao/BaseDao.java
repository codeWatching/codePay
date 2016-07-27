package org.codepay.common.orm.mybatis.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.codepay.common.orm.mybatis.GenericMybatisDao;
import org.codepay.common.orm.mybatis.paginator.domain.PageBounds;
import org.codepay.common.orm.mybatis.paginator.domain.PageList;

/**
 * Description: 系统基础Dao<br>
 * @author codeWatching
 */
public class BaseDao<T> extends GenericMybatisDao<T> {

    public static final String SQLNAME_SEPARATOR = ".";

    // 基本CRUD save
    public static final String SQL_SAVE = "save";

    // 基本CRUD 批量 save
    public static final String SQL_SAVE_BATCH = "saveBatch";

    // 基本CRUD 批量 update
    public static final String SQL_UPDATE_BATCH = "updateBatch";

    // 基本CRUD update
    public static final String SQL_UPDATE = "update";

    // 基本CRUD get 通过id进行单条记录查询
    public static final String SQL_GETBYID = "get";

    // 基本CRUD delete 通过id进行单条删除
    public static final String SQL_DELETEBYID = "delete";

    // 基本CRUD delete 通过id集合进行批量删除
    public static final String SQL_DELETEBYIDS = "deleteByIds";

    // 基本CRUD find 单条查询 查询参数(必须)
    public static final String SQL_FINDUNIQUE = "findUnique";

    // 基本CRUD find 分页查询 分页参数(必须)
    public static final String SQL_FINDPAGEBY = "findPageBy";

    // 基本CRUD find 分页查询 分页参数(必须)
    public static final String SQL_FINDALL = "findAll";

    // 基本CRUD find 集合查询 查询参数(非必须)
    public static final String SQL_FINDLISTBY = "findListBy";

    // 基本CRUD count 集合统计 查询参数(非必须)
    public static final String SQL_GETCOUNTBY = "getCountBy";

    private String defaultNameSpace;

    /**
     * 不能用于SQL中的非法字符（主要用于排序字段名）
     */
    public static final String[] ILLEGAL_CHARS_FOR_SQL = {",", ";", " ", "\"", "%"};

    /**
     * 获取默认SqlMapping命名空间。 使用泛型参数中业务实体类型的全限定名作为默认的命名空间。
     * 如果实际应用中需要特殊的命名空间，可由子类重写该方法实现自己的命名空间规则。
     *
     * @return 返回命名空间字符串
     */
    @SuppressWarnings("unchecked")
    private String getDefaultSqlNamespace() {
        if (StringUtils.isBlank(defaultNameSpace)) {
            Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0];
            defaultNameSpace = clazz.getName();
        }
        return defaultNameSpace;
    }

    /**
     * 将SqlMapping命名空间与给定的SqlMapping名组合在一起。
     *
     * @param sqlName SqlMapping名
     * @return 组合了SqlMapping命名空间后的完整SqlMapping名
     */
    protected String getSqlName(String sqlName) {
        return sqlNamespace + SQLNAME_SEPARATOR + sqlName;
    }
    /**
     * SqlMapping命名空间
     */
    private String sqlNamespace = getDefaultSqlNamespace();

    /**
     * 获取SqlMapping命名空间
     *
     * @return SqlMapping命名空间
     */
    public String getSqlNamespace() {
        return sqlNamespace;
    }

    /**
     * 设置SqlMapping命名空间。 此方法只用于注入SqlMapping命名空间，以改变默认的SqlMapping命名空间，
     * 不能滥用此方法随意改变SqlMapping命名空间。
     *
     * @param sqlNamespace SqlMapping命名空间
     */
    public void setSqlNamespace(String sqlNamespace) {
        this.sqlNamespace = sqlNamespace;
    }

    /**
     * 批量新增
     *
     * @param
     * @return
     */
    public int saveBatch(Collection<T> objs) {
        return super.insertBatchAndReturnAffectedCount(getSqlName(SQL_SAVE_BATCH), objs);
    }

    /**
     * (系统默认)保存对象 例如:save(U_account u_account) 则调用namespace
     * com.huicai.tarro.core.model.user.U_account.save进行对象保存,并返回带有id的对象
     *
     * @param ob
     * @return
     */
    public T save(T ob) {
        super.insertAndSetupId(getSqlName(SQL_SAVE), ob);
        return ob;
    }

    /**
     * (系统默认)更新对象 参数:id(必须,防止批量更新) 例如:update(U_account u_account) 则调用namespace
     * com.huicai.tarro.core.model.user.U_account.update进行对象更新,并返回更新后的对象
     *
     * @param ob
     * @return
     */
    public T update(T ob) {
        super.updateByObj(getSqlName(SQL_UPDATE), ob);
        return ob;
    }

    /**
     * 批量更新
     *
     * @param objs
     * @return
     */
    public int updateBatch(Collection<T> objs) {
        return super.updateBatchAndReturnAffectedCount(getSqlName(SQL_UPDATE_BATCH), objs);
    }

    /**
     * 批量更新
     *
     * @param objs
     * @return
     */
    public int updateBatch(String sqlName, Collection<T> objs) {
        return super.updateBatchAndReturnAffectedCount(getSqlName(sqlName), objs);
    }

    /**
     * (系统默认)查询 参数:id(必须) 例如:get(Lond u_account_id) 则调用namespace
     * com.huicai.tarro.core.model.user.U_account.get进行按照id查询
     *
     * @param id
     * @return
     */
    public T get(Long id) {
        return super.queryOne(getSqlName(SQL_GETBYID), id);
    }

    /**
     * (系统默认)删除 参数:id(必须) 例如:delete(Lond u_account_id) 则调用namespace
     * com.huicai.tarro.core.model.user.U_account.delete进行按照id查询
     *
     * @param id
     * @return
     */
    public void delete(@Param("id") Long id) {
        super.delete(getSqlName(SQL_DELETEBYID), id);
    }

    /**
     * (系统默认)批量删除 参数:ids(必须) 例如:delete(Long[] u_account_id) 则调用namespace
     * com.huicai.tarro.core.model.user.U_account.deleteByIds进行按照id查询
     *
     * @param ids
     * @return
     */
    public void deleteByIds(@Param("ids") Long[] ids) {
        super.delete(getSqlName(SQL_DELETEBYIDS), ids);
    }

    /**
     * (系统默认)查询全部数据 例如:findALl() 则调用namespace
     * com.huicai.tarro.core.model.user.U_account.findAll进行查询
     *
     * @return
     */
    public List<T> findAll() {
        return super.queryList(getSqlName(SQL_FINDALL));
    }

    @Override
    public <E> List<E> queryList(String sqlName, Map<String, Object> map) {
        return super.queryList(getSqlName(sqlName), map);
    }

    /**
     * 此方法不返回null
     */
    public <E> List<E> findList(String sqlName, Object obj) {
        return super.getSqlSessionTemplate().selectList(getSqlName(sqlName), obj);
    }

    @SuppressWarnings("rawtypes")
    public T findUnique(Map map) {
        return super.queryOne(getSqlName(SQL_FINDUNIQUE), map);
    }

    @Override
    public T queryOne(String sqlName, String idStr) {
        return super.queryOne(getSqlName(sqlName), idStr);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public T queryOne(String sqlName, Map map) {
        return super.queryOne(getSqlName(sqlName), map);
    }

    public <E> E queryOne(String sqlName) {
        return this.queryOne(sqlName, new Object());
    }

    public <E> E queryOne(String sqlName, Object obj) {
        return super.getSqlSessionTemplate().selectOne(getSqlName(sqlName), obj);
    }

    /**
     * (系统默认)查询全部数据 例如:findALl() 则调用namespace
     * com.huicai.tarro.core.model.user.U_account.findAll进行查询
     *
     * @param pageBounds
     * @return
     */
    public PageList<T> findPageByArgs(Map<String, Object> paramMap, PageBounds pageBounds) {
        return super.queryList(getSqlName(SQL_FINDPAGEBY), paramMap, pageBounds);
    }
}
