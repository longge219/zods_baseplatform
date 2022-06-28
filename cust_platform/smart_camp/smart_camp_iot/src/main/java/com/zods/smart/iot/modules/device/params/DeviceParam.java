package com.zods.smart.iot.modules.device.params;
import com.zods.plugins.db.annotation.Query;
import com.zods.plugins.db.constant.QueryEnum;
import com.zods.plugins.db.curd.params.PageParam;
import java.io.Serializable;
/**
 * @desc 设备-查询请求参数
 * @author jianglong
 * @date 2022-06-23
 **/
public class DeviceParam extends PageParam implements Serializable {
    /** 设备编号 */
    @Query(value = QueryEnum.EQ)
    private String fileType;
}
