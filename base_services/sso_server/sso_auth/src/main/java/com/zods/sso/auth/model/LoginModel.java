package com.zods.sso.auth.model;
import com.zods.sso.auth.temp.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;
/**
 * @author jianglong
 * @version 1.0
 * @Description 用户登陆对象
 * @createDate
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "用户登录对象")
public class LoginModel extends BaseModel {

    @NotBlank(message = "鉴权类型不能为空")
    @ApiModelProperty(value = "鉴权类型", hidden = true)
    private String grant_type;

    @ApiModelProperty("登录名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty(value = "刷新token", hidden = true)
    private String refresh_token;

    @ApiModelProperty(value = "验证码", hidden = true)
    private String code;

    @ApiModelProperty(value = "手机号", hidden = true)
    private String phone;

}
