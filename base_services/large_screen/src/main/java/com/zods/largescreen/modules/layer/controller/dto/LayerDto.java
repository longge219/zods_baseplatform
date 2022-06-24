package com.zods.largescreen.modules.layer.controller.dto;
import com.zods.largescreen.common.curd.dto.GaeaBaseDTO;
import lombok.Data;
import java.io.Serializable;
/**
 * @author jianglong
 * @version 1.0
 * @Description 图层 dto
 * @createDate 2022-06-24
 */
@Data
public class LayerDto extends GaeaBaseDTO implements Serializable {

    /**图层名称*/
    private String layerName;

    /**图层Url地址*/
    private String layertUrl;

    /**图层设备布局*/
    private String deviceLayout;

    /**热区布局，以json字符串存储*/
    private String hotzonLayout;

    /**图层附件id*/
    private Long attachId;

    /**是否删除*/
    private Boolean isDelete;
}
