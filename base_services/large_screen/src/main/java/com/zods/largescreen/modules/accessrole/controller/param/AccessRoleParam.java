package com.zods.largescreen.modules.accessrole.controller.param;
import com.zods.largescreen.common.annotation.Query;
import com.zods.largescreen.common.constant.QueryEnum;
import com.zods.largescreen.common.curd.params.PageParam;
import lombok.Data;

import java.io.Serializable;

/**
* @desc AccessRole 角色管理查询输入类
* @author 木子李·De <lide1202@hotmail.com>
* @date 2019-02-17 08:50:14.136
**/
@Data
public class AccessRoleParam extends PageParam implements Serializable{

    /** 角色编码 */
    @Query(value = QueryEnum.LIKE)
    private String roleCode;

    // 角色名称
    @Query(value = QueryEnum.LIKE)
    private String roleName;

    // 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG
    @Query
    private Integer enableFlag;

}