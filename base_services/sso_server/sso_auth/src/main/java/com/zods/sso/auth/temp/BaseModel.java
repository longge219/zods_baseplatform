package com.zods.sso.auth.temp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2021/3/18 16:48
 */
@Data
@Accessors(chain = true)
public class BaseModel implements Serializable {

    private static final long serialVersionUID = 8259128981350299997L;

    @NotBlank(message = "客户端ID不能为空")
    @ApiModelProperty(value = "客户端ID", hidden = true)
    private String client_id = "client";

    @NotBlank(message = "客户端密钥不能为空")
    @ApiModelProperty(value = "客户端密钥", hidden = true)
    private String client_secret = "secret";

}
