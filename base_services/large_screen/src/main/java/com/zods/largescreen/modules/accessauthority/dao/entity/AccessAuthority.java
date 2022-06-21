package com.zods.largescreen.modules.accessauthority.dao.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zods.largescreen.common.curd.entity.GaeaBaseEntity;
import lombok.Data;
/**
* @description 权限管理 entity
* @author 木子李·De <lide1202@hotmail.com>
* @date 2019-02-17 08:50:10.009
**/
@TableName(keepGlobalPrefix=true, value="access_authority")
@Data
public class AccessAuthority extends GaeaBaseEntity {
    /** 父菜单代码 */
    private String parentTarget;

    /** 菜单代码 */
    private String target;

    /** 菜单名称 */
    private String targetName;

    /** 按钮代码 */
    private String action;

    /** 按钮名称 */
    private String actionName;

    /**  0--未删除 1--已删除 DIC_NAME=DEL_FLAG */
    private Integer deleteFlag;

    /** 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG */
    private Integer enableFlag;

    private Integer sort;



}
