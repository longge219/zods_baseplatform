package com.zods.largescreen.modules.layer.dao.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zods.largescreen.common.curd.entity.GaeaBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * @author jianglong
 * @version 1.0
 * @Description 底图图层信息表
 * @createDate 2022-06-24
 */
@TableName(keepGlobalPrefix=true, value = "large_scrren_layer")
@Data
public class Layer extends GaeaBaseEntity implements Serializable {

    @ApiModelProperty(value = "图层名称")
    private String layerName;

    @ApiModelProperty(value = "图层Url地址")
    private String layertUrl;

    /**
     * 图层设备布局，以json字符串存储；例：
     * [{"deviceid":"01","x":"100","y":"100"},
     * {"deviceid":"02","x":"200","y":"200"},
     * {"deviceid":"03","x":"300","y":"300"}]
     */
    @ApiModelProperty(value = "图层设备布局")
    private String deviceLayout;

    /**
     * '热区布局，以json字符串存储；例：
     * [{"layerId":"01","name":"弹药室热区","points":[{"x":0.3,"y":0.5},{"x":0.5,"y":0.5},{"x":0.5,"y":0.8},{"x":0.3,"y":0.8}]},
     * {"layerId":"02",“name”:"枪械室热区","points":[{"x":0.1,"y":0.2},{"x":0.2,"y":0.2},{"x":0.2,"y":0.3},{"x":0.1,"y":0.3}]}\r\n,
     * {"layerId":"03",“name”:“车场热区”,"points":[{"x":0.5,"y":0.7},{"x":0.7,"y":0.7},{"x":0.7,"y":1},{"x":0.5,"y":0.1}]}',
     * */
    @ApiModelProperty(value = "热区布局")
    private String hotzonLayout;

    @ApiModelProperty(value = "图层附件id")
    private Long attachId;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDelete;
}
