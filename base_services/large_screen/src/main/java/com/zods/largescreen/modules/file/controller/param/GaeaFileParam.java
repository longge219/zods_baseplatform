package com.zods.largescreen.modules.file.controller.param;
import com.zods.largescreen.common.annotation.Query;
import com.zods.largescreen.common.constant.QueryEnum;
import com.zods.largescreen.common.curd.params.PageParam;
import java.io.Serializable;
/**
 * @desc 文件-查询请求参数
 * @author jianglong
 * @date 2022-06-23
 **/
public class GaeaFileParam extends PageParam implements Serializable {

    /** 模糊查询 */
    @Query(value = QueryEnum.LIKE)
    private String filePath;

    /** 模糊查询 */
    @Query(value = QueryEnum.EQ)
    private String fileType;
}
