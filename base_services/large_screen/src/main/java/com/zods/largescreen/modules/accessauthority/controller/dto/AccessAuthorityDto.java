package com.zods.largescreen.modules.accessauthority.controller.dto;
import com.zods.largescreen.common.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
/**
 * @author jianglong
 * @version 1.0
 * @Description 权限管理 dto
 * @createDate 2022-06-20
 */
@Data
public class AccessAuthorityDto extends GaeaBaseDTO implements Serializable {
    /** 父菜单代码 */
    @ApiModelProperty(value = "父菜单代码")
    private String parentTarget;

    /** 目标菜单 */
    @ApiModelProperty(value = "菜单代码")
    @NotEmpty(message = "6002")
    private String target;

    /** 目标菜单名称 */
    @ApiModelProperty(value = "菜单名称")
    @NotEmpty(message = "6002")
    private String targetName;

    /** 目标按钮 */
    @ApiModelProperty(value = "按钮代码")
    @NotEmpty(message = "6002")
    private String action;

    /** 目标按钮名称 */
    @ApiModelProperty(value = "按钮名称")
    @NotEmpty(message = "6002")
    private String actionName;

    /**  0--未删除 1--已删除 DIC_NAME=DEL_FLAG */
    @ApiModelProperty(value = " 0--未删除 1--已删除 DIC_NAME=DEL_FLAG")
    private Integer deleteFlag;

    /** 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG */
    @ApiModelProperty(value = "0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG")
    @NotNull(message = "6002")
    private Integer enableFlag;

    @ApiModelProperty(value = "sort")
    private Integer sort;
}