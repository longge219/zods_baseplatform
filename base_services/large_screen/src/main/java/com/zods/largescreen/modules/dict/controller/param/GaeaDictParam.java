package com.zods.largescreen.modules.dict.controller.param;
import com.zods.largescreen.common.annotation.Query;
import com.zods.largescreen.common.constant.QueryEnum;
import com.zods.largescreen.common.curd.params.PageParam;
import lombok.Data;
import java.io.Serializable;
/**
 * @desc 数据字典 请求参数
 * @author jianglong
 * @date 2022-06-23
 **/
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
