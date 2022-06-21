package com.zods.largescreen.modules.accessauthority.controller;
import com.zods.largescreen.common.annotation.Permission;
import com.zods.largescreen.common.bean.ResponseBean;
import com.zods.largescreen.common.bean.TreeNode;
import com.zods.largescreen.common.curd.controller.GaeaBaseController;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.common.holder.UserContentHolder;
import com.zods.largescreen.modules.accessauthority.controller.dto.AccessAuthorityDto;
import com.zods.largescreen.modules.accessauthority.controller.param.AccessAuthorityParam;
import com.zods.largescreen.modules.accessauthority.dao.entity.AccessAuthority;
import com.zods.largescreen.modules.accessauthority.service.AccessAuthorityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
/**
 * @author jianglong
 * @version 1.0
 * @Description 权限管理
 * @createDate 2022-06-20
 */
@RestController
@Api(tags = "权限管理管理")
@RequestMapping("/accessAuthority")
@Permission(code = "authorityManage", name = "权限管理")
public class AccessAuthorityController extends GaeaBaseController<AccessAuthorityParam, AccessAuthority, AccessAuthorityDto> {

    @Autowired
    private AccessAuthorityService accessAuthorityService;

    @Override
    public GaeaBaseService<AccessAuthorityParam, AccessAuthority> getService() {
        return accessAuthorityService;
    }

    @Override
    public AccessAuthority getEntity() {
        return new AccessAuthority();
    }

    @Override
    public AccessAuthorityDto getDTO() {
        return new AccessAuthorityDto();
    }

    /**
     * 获取一二级菜单
     * @return
     */
    @Permission( code = "query", name = "查询")
    @GetMapping("/menuTree")
    public ResponseBean menuTree(){
        String username = UserContentHolder.getContext().getUsername();
        List<TreeNode> parentTreeList = accessAuthorityService.getAuthorityTree(username, false);
        return responseSuccessWithData(parentTreeList);
    }
}