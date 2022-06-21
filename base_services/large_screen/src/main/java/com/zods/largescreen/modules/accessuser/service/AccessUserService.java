package com.zods.largescreen.modules.accessuser.service;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.accessuser.controller.dto.AccessUserDto;
import com.zods.largescreen.modules.accessuser.controller.dto.GaeaUserDto;
import com.zods.largescreen.modules.accessuser.controller.dto.UpdatePasswordDto;
import com.zods.largescreen.modules.accessuser.controller.param.AccessUserParam;
import com.zods.largescreen.modules.accessuser.dao.entity.AccessUser;

import java.util.Map;

/**
* @desc AccessUser 用户管理服务接口
* @author 木子李·De <lide1202@hotmail.com>
* @date 2019-02-17 08:50:11.902
**/
public interface AccessUserService extends GaeaBaseService<AccessUserParam, AccessUser> {

    /** 获取用户的角色树
     * @param loginName 被操作的对象
     * @param operator 当前登录者
     * @return
     */
    Map getRoleTree(String loginName, String operator);


    /** 保存用户的角色树
     * @param accessUserDto
     * @return
     */
    Boolean saveRoleTree(AccessUserDto accessUserDto);

    /** 重置密码
     * @param gaeaUserDto
     * @return
     */
    Boolean resetPassword(GaeaUserDto gaeaUserDto);

    /** 用户登录
     * @param gaeaUserDto
     * @return
     */
    GaeaUserDto login(GaeaUserDto gaeaUserDto);

    /**
     * 修改密码
     * @param dto
     * @return
     */
    Boolean updatePassword(UpdatePasswordDto dto);
}
