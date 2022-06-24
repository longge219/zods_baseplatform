package com.zods.largescreen.modules.layer.controller.param;
import com.zods.largescreen.common.annotation.Query;
import com.zods.largescreen.common.constant.QueryEnum;
import com.zods.largescreen.common.curd.params.PageParam;
import lombok.Data;
import java.io.Serializable;
/**
 * @author jianglong
 * @version 1.0
 * @Description 图层查询输入类
 * @createDate 2022-06-24
 */
@Data
public class LayerParam extends PageParam implements Serializable{

    /**图层名称*/
    @Query(QueryEnum.LIKE)
    private String layerName;

}
