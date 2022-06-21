package com.zods.largescreen.modules.accessuser.controller;
import com.zods.largescreen.common.annotation.Permission;
import com.zods.largescreen.common.bean.ResponseBean;
import com.zods.largescreen.common.curd.controller.GaeaBaseController;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.common.holder.UserContentHolder;
import com.zods.largescreen.modules.accessuser.controller.dto.AccessUserDto;
import com.zods.largescreen.modules.accessuser.controller.dto.GaeaUserDto;
import com.zods.largescreen.modules.accessuser.controller.dto.UpdatePasswordDto;
import com.zods.largescreen.modules.accessuser.controller.param.AccessUserParam;
import com.zods.largescreen.modules.accessuser.dao.entity.AccessUser;
import com.zods.largescreen.modules.accessuser.service.AccessUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
/**
* @desc 用户管理 controller
* @author 木子李·De <lide1202@hotmail.com>
* @date 2019-02-17 08:50:11.902
**/
@RestController
@Api(tags = "用户管理管理")
@RequestMapping("/accessUser")
@Permission(code = "userManage", name = "用户管理")
public class AccessUserController extends GaeaBaseController<AccessUserParam, AccessUser, AccessUserDto> {

    @Autowired
    private AccessUserService accessUserService;

    @Override
    public GaeaBaseService<AccessUserParam, AccessUser> getService() {
        return accessUserService;
    }

    @Override
    public AccessUser getEntity() {
        return new AccessUser();
    }

    @Override
    public AccessUserDto getDTO() {
        return new AccessUserDto();
    }


    /**
     * 获取用户的角色树
     * @return
     */
    @Permission( code = "grantRole", name = "分配角色")
    @GetMapping("/roleTree/{loginName}")
    public ResponseBean getRoleTree(@PathVariable("loginName")String loginName){
        String operator = UserContentHolder.getContext().getUsername();
        Map map = accessUserService.getRoleTree(loginName, operator);
        return responseSuccessWithData(map);
    }

    /**
     * 保存用户的角色树
     * @return
     */
    @Permission( code = "grantRole", name = "分配角色")
    @PostMapping("/saveRoleTree")
    public ResponseBean saveRoleTree(@RequestBody AccessUserDto dto){
        Boolean data = accessUserService.saveRoleTree(dto);
        return responseSuccessWithData(data);
    }


    /**
     * 重置密码
     * @param dto
     * @return
     */
    @Permission( code = "resetPassword", name = "重置密码")
    @PostMapping({"/resetPassword"})
    public ResponseBean resetPassword(@RequestBody @Validated GaeaUserDto dto) {
        Boolean data = accessUserService.resetPassword(dto);
        return responseSuccessWithData(data);
    }

    /**
     * 简单实现登录
     * @param dto
     * @return
     */
    @PostMapping({"/login"})
    public ResponseBean login(@RequestBody @Validated GaeaUserDto dto) {
        return responseSuccessWithData(accessUserService.login(dto));
    }

    /**
     * 修改自己的密码
     * @param dto
     * @return
     */
    @PostMapping("/updatePassword")
    public ResponseBean updatePassword(@RequestBody UpdatePasswordDto dto) {
        return responseSuccessWithData(accessUserService.updatePassword(dto));
    }

}
