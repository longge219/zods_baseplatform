package com.zods.mysql.dao;
import com.zods.mysql.base.ObjectQuery;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 类名称：IBaseDao 类描述：Dao基类（声明CRUD常用接口方法）说明：所有DAO层都必须继承此类
 */

public interface IBaseDao<T> {
    /**
     * 保存
     *
     * @param entity
     * @return
     * @throws Exception
     */
    Integer insert(T entity) throws Exception;

    /**
     * 修改
     *
     * @param entity
     * @return
     * @throws Exception
     */
    Integer update(T entity) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    Integer deleteById(Serializable id) throws Exception;

    /**
     * 查询（ID）
     *
     * @param id
     * @return
     * @throws Exception
     */
    T getById(Serializable id) throws Exception;

    /**
     * 查询（where）
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<T> getByWhere(HashMap<String, Object> params) throws Exception;

    /**
     * 查询（All）
     *
     * @return
     * @throws Exception
     */
    List<T> getAll() throws Exception;

    /**
     * 查询（分页查询_结果集）
     *
     * @param objectQuery
     * @return
     * @throws Exception
     */
    List<T> findQuery(ObjectQuery objectQuery) throws Exception;

    /**
     * 查询（分页查询_数量）
     *
     * @param objectQuery
     * @return
     * @throws Exception
     */
    Integer findCount(ObjectQuery objectQuery) throws Exception;
}
