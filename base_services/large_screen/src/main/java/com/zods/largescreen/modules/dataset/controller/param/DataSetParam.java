package com.zods.largescreen.modules.dataset.controller.param;
import com.zods.largescreen.common.annotation.Query;
import com.zods.largescreen.common.constant.QueryEnum;
import com.zods.largescreen.common.curd.params.PageParam;
import lombok.Data;

import java.io.Serializable;


/**
* @desc DataSet 数据集查询输入类
* @author Raod
* @date 2021-03-18 12:11:31.150755900
**/
@Data
public class DataSetParam extends PageParam implements Serializable{
    /** 数据集编码 */
    @Query(QueryEnum.LIKE)
    private String setCode;

    /** 数据集名称 */
    @Query(QueryEnum.LIKE)
    private String setName;

    /** 数据源编码 */
    @Query(QueryEnum.EQ)
    private String sourceCode;

    /** 数据集类型 */
    @Query(QueryEnum.EQ)
    private String setType;
}
