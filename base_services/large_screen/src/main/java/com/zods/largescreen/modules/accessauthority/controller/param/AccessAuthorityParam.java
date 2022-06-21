package com.zods.largescreen.modules.accessauthority.controller.param;
import com.zods.largescreen.common.annotation.Query;
import com.zods.largescreen.common.constant.QueryEnum;
import com.zods.largescreen.common.curd.params.PageParam;
import lombok.Data;
import java.io.Serializable;

/**
 * @author jianglong
 * @version 1.0
 * @Description 权限管理查询输入类
 * @createDate 2022-06-20
 */
@Data
public class AccessAuthorityParam extends PageParam implements Serializable{

    /** 父菜单代码 */
    @Query(value = QueryEnum.LIKE)
    private String parentTarget;

    /** 菜单代码 */
    @Query(value = QueryEnum.LIKE)
    private String target;

    /** 菜单名称 */
    @Query(value = QueryEnum.LIKE)
    private String targetName;

    /** 按钮代码 */
    @Query(value = QueryEnum.LIKE)
    private String action;

    /** 按钮名称 */
    @Query(value = QueryEnum.LIKE)
    private String actionName;

    // 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG
    @Query
    private Integer enableFlag;
}