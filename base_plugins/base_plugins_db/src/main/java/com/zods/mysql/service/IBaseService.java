package com.zods.mysql.service;

import com.zods.mysql.base.ObjectQuery;
import com.zods.mysql.base.PageResult;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 类名称：IBaseService 类描述：Service基类（声明CRUD常用接口方法） 备注：所有service层接口都必须继承此类
 */
public interface IBaseService<T> {
    /**
     * 保存
     *
     * @param entity
     * @return
     * @throws Exception
     */
    Integer save(T entity) throws Exception;

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
     * 查询(分页 查询)
     *
     * @param queryObject
     * @return
     * @throws Exception
     */
    PageResult<T> findPage(ObjectQuery queryObject) throws Exception;

    /**
     * 查询总条数
     *
     * @param objectQuery
     * @return
     * @throws Exception
     */
    Integer findCount(ObjectQuery objectQuery) throws Exception;
}
