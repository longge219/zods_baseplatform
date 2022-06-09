package com.zods.sso.auth.controller;
import com.zods.sso.auth.model.LoginModel;
import com.zods.sso.auth.model.ResponseModel;
import com.zods.sso.auth.temp.BaseModel;
import com.zods.sso.auth.temp.UriConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-08
 */
@Api(tags = "认证中心API")
public interface OauthApi {

    /**
     * 根据账户密码登录获取token
     * @param loginModel
     * @param request
     * @return
     */
    @PostMapping(UriConstant.AUTH_LOGIN)
    @ApiOperation(value = "登录")
    ResponseModel login(LoginModel loginModel, HttpServletRequest request);

    /**
     * 刷新token
     * @param refreshToken
     * @return
     */
    @PostMapping(UriConstant.AUTH_REFRESH_TOKEN)
    @ApiOperation(value = "刷新token")
    @ApiImplicitParam(name = "Authorization", paramType = "header", value = "依据refresh_token刷新令牌", dataType = "string")
    ResponseModel refreshToken(@RequestHeader("Authorization") String refreshToken);

    /**
     * 登出清理
     *
     * @param access_token
     * @return
     */
    @DeleteMapping(UriConstant.AUTH_LOGOUT)
    @ApiOperation(value = "登出")
    @ApiImplicitParam(name = "Authorization", dataType = "String", paramType = "header", value = "根据令牌access_token退出登录")
    ResponseModel logout(@RequestHeader("Authorization") String access_token);

    /**
     * 获取手机验证码
     *
     * @param phone
     * @param baseModel
     * @return
     */
    @PostMapping(UriConstant.AUTH_SMS_SEND)
    @ApiOperation(value = "获取手机验证码", notes = "获取手机验证码")
    ResponseModel smsSend(@RequestParam("mobile") String phone, @RequestBody BaseModel baseModel);
}
