package com.zods.largescreen.modules.dataset.service;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.dataset.controller.dto.DataSetDto;
import com.zods.largescreen.modules.dataset.controller.dto.OriginalDataDto;
import com.zods.largescreen.modules.dataset.controller.param.DataSetParam;
import com.zods.largescreen.modules.dataset.dao.entity.DataSet;
import java.util.List;
/**
 * @desc 数据集 数据集服务接口
 * @author jianglong
 * @date 2022-06-22
 **/
public interface DataSetService extends GaeaBaseService<DataSetParam, DataSet> {

    /**单条详情*/
    DataSetDto detailSet(Long id);

    /**单条详情*/
    DataSetDto detailSet(String setCode);


    /**新增数据集、添加查询参数、数据转换*/
    DataSetDto insertSet(DataSetDto dto);

    /**更新数据集、添加查询参数、数据转换*/
    void updateSet(DataSetDto dto);

    /**删除数据集、添加查询参数、数据转换*/
    void deleteSet(Long id);

    /**测试数据转换*/
    OriginalDataDto testTransform(DataSetDto dto);

    /**获取数据*/
    OriginalDataDto getData(DataSetDto dto);

    /**获取所有数据集*/
    List<DataSet> queryAllDataSet();
}
