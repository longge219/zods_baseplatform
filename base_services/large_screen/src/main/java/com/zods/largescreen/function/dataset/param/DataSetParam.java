package com.zods.largescreen.function.dataset.param;
import com.zods.plugins.zods.annotation.Query;
import com.zods.plugins.zods.constant.QueryEnum;
import com.zods.plugins.zods.curd.params.PageParam;
import lombok.Data;

import java.io.Serializable;


/**
* @desc DataSet 数据集查询输入类
* @author jianglong
* @date 2022-06-14
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
