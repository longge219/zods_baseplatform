package com.zods.mysql.service.impl;
import com.zods.mysql.base.ObjectQuery;
import com.zods.mysql.base.PageResult;
import com.zods.mysql.dao.IBaseDao;
import com.zods.mysql.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类名称：BaseServiceImpl 类描述：服务实现基类（实现了CRUD基本方法）
 * 说明：所有service实现都必须继承此类，若觉得不满足需求，@Override即可
 */
public class BaseServiceImpl<T> implements IBaseService<T> {

    @Autowired
    private IBaseDao<T> baseDao;

    /**
     * 方法名：save
     *
     * @param entity 返回类型：
     * @return
     */
    @Override
    public Integer save(T entity) throws Exception {
        return baseDao.insert(entity);
    }

    /**
     * 方法名：update
     *
     * @param entity 返回类型：
     * @return
     */
    @Override
    public Integer update(T entity) throws Exception {
        return baseDao.update(entity);
    }

    /**
     * 方法名：deleteById
     *
     * @param id 返回类型：
     * @return
     */
    @Override
    public Integer deleteById(Serializable id) throws Exception {
        return baseDao.deleteById(id);
    }

    /**
     * 方法名：getById
     *
     * @param id 返回类型：
     * @return
     */
    @Override
    public T getById(Serializable id) throws Exception {
        return baseDao.getById(id);
    }

    /**
     * 方法名：getByWhere
     *
     * @param params 返回类型：
     * @return
     */
    @Override
    public List<T> getByWhere(HashMap<String, Object> params) throws Exception {
        return baseDao.getByWhere(params);
    }

    /**
     * 方法名：getAll
     *
     * @return 返回类型：
     */
    @Override
    public List<T> getAll() throws Exception {
        return baseDao.getAll();
    }

    /**
     * 方法名：getPageList
     *
     * @param queryObject 查询对象
     * @return 返回类型：PageSupport<T> 说明：分页查询\一般查询（当queryObject为null,则作页面查询，就是一般结果集）
     */
    @Override
    public final PageResult<T> findPage(ObjectQuery queryObject) throws Exception {
        PageResult<T> pageResult = new PageResult<T>();
        List<T> resultList = new ArrayList<>();
        Integer totalRecord = 0;
        if (queryObject != null && queryObject.getPageSize() != -1) {
            totalRecord = this.findCount(queryObject);
            if (totalRecord.intValue() > 0) {
                resultList = this.findQuery(queryObject);
            }
        } else {
            queryObject = new ObjectQuery();
            queryObject.setPageSize(-1);
            resultList = this.findQuery(queryObject);
            totalRecord = resultList.size();
        }
        pageResult.setCurrentPage(queryObject.getCurrentPage());
        pageResult.setPageSize(queryObject.getPageSize());
        pageResult.setTotalRecords(totalRecord);
        pageResult.setItems(resultList);
        return pageResult;
    }

    /**
     * 方法名：findQuery
     *
     * @param objectQuery
     * @return
     * @throws Exception 返回类型：List<T> 说明：查询结果集 （分页查询基本实现，若自定义，@Override即可）
     */
    public List<T> findQuery(ObjectQuery objectQuery) throws Exception {
        return baseDao.findQuery(objectQuery);
    }

    /**
     * 方法名：findCount
     *
     * @param objectQuery
     * @return
     * @throws Exception 返回类型：Integer 说明：查询总条数 （分页查询基本实现，若自定义，@Override即可）
     */
    @Override
    public Integer findCount(ObjectQuery objectQuery) throws Exception {
        return baseDao.findCount(objectQuery);
    }

}
