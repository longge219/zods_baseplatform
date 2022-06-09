package com.zods.sso.auth.service;
import com.zods.sso.auth.model.LoginModel;
import com.zods.sso.auth.model.ResponseModel;
import com.zods.sso.auth.temp.BaseModel;
import javax.servlet.http.HttpServletRequest;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-08
 */
public interface OauthService {

    /**
     * 登录/刷新
     *
     * @param loginModel
     * @param request
     * @return
     */
    ResponseModel login(LoginModel loginModel, HttpServletRequest request);

    /**
     * 刷新token
     *
     * @param loginModel
     * @return
     */
    ResponseModel refreshToken(LoginModel loginModel);

    /**
     * 登出
     *
     * @return
     */
    ResponseModel logout(String access_token);

    /**
     * 用户获取手机验证码
     *
     * @param phone
     * @param baseModel
     * @return
     */
    ResponseModel smsSend(String phone, BaseModel baseModel);
}
