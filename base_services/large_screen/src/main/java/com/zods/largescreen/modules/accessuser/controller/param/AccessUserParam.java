package com.zods.largescreen.modules.accessuser.controller.param;
import com.zods.largescreen.common.annotation.Query;
import com.zods.largescreen.common.constant.QueryEnum;
import com.zods.largescreen.common.curd.params.PageParam;
import lombok.Data;

import java.io.Serializable;

/**
* @desc AccessUser 用户管理查询输入类
* @author 木子李·De <lide1202@hotmail.com>
* @date 2019-02-17 08:50:11.902
**/
@Data
public class AccessUserParam extends PageParam implements Serializable{

    //  登录名
    @Query(value = QueryEnum.LIKE)
    private String loginName;

    // 真实用户
    @Query(value = QueryEnum.LIKE)
    private String realName;

    // 手机号码
    @Query(value = QueryEnum.LIKE)
    private String phone;

    // 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG
    @Query
    private Integer enableFlag;

}