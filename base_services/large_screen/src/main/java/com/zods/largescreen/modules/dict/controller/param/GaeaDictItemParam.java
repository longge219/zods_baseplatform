package com.zods.largescreen.modules.dict.controller.param;
import com.zods.largescreen.common.annotation.Query;
import com.zods.largescreen.common.constant.QueryEnum;
import com.zods.largescreen.common.curd.params.PageParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据字典项(GaeaDictItem)param
 *
 * @author lirui
 * @since 2021-03-10 13:05:59
 */
@Data
public class GaeaDictItemParam extends PageParam implements Serializable {

    /**
     * 数据字典编码
     */
    private String dictCode;
    /**
     * 字典项名称
     */
    @Query(QueryEnum.LIKE)
    private String itemName;

    /**
     * 语言标识
     */
    private String locale;

    /**
     * 1：启用，0:禁用
     */
    private Integer enabled;
}
