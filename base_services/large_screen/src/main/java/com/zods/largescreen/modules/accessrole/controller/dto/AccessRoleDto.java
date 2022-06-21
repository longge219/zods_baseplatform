
package com.zods.largescreen.modules.accessrole.controller.dto;
import com.zods.largescreen.common.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
*
* @description 角色管理 dto
* @author 木子李·De <lide1202@hotmail.com>
* @date 2019-02-17 08:50:14.136
**/
@Data
public class AccessRoleDto extends GaeaBaseDTO implements Serializable {

    /** 角色编码 */
    @ApiModelProperty(value = "角色编码")
    @NotEmpty(message = "6002")
    private String roleCode;

    /** 角色名称 */
    @ApiModelProperty(value = "角色名称")
    @NotEmpty(message = "6002")
    private String roleName;

    /**  0--未删除 1--已删除 DIC_NAME=DEL_FLAG */
    @ApiModelProperty(value = " 0--未删除 1--已删除 DIC_NAME=DEL_FLAG")
    private Integer deleteFlag;

    /** 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG */
    @ApiModelProperty(value = "0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG")
    @NotNull(message = "6002")
    private Integer enableFlag;

    /** 角色保存的权限 */
    private List<String> authorityList;
}