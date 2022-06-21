package com.zods.largescreen.modules.dict.controller.param;

import com.zods.largescreen.common.annotation.Query;
import com.zods.largescreen.common.constant.QueryEnum;
import com.zods.largescreen.common.curd.params.PageParam;
import lombok.Data;

import java.io.Serializable;

/**
 * (GaeaDict)param
 *
 * @author lr
 * @since 2021-02-23 10:01:02
 */
@Data
public class GaeaDictParam extends PageParam implements Serializable {
    /**
     * 字典名称
     */
    @Query(QueryEnum.LIKE)
    private String dictName;
    /**
     * 字典编号
     */
    @Query(QueryEnum.LIKE)
    private String dictCode;
}
