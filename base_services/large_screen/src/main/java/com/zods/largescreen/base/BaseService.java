package com.zods.largescreen.base;
import com.zods.largescreen.common.curd.entity.BaseEntity;
import com.zods.largescreen.common.curd.params.PageParam;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
/**
 * @author jianglong
 * @version 1.0
 * @Description 项目级的Service公共处理基类
 * @createDate 2022-06-20
 */
public interface BaseService<P extends PageParam, T extends BaseEntity> extends GaeaBaseService<P, T> {

}
