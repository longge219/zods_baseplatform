package com.zods.sso.auth.controller;
import com.zods.sso.auth.model.LoginModel;
import com.zods.sso.auth.model.ResponseModel;
import com.zods.sso.auth.service.OauthService;
import com.zods.sso.auth.temp.BaseModel;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-08
 */
@RestController
@AllArgsConstructor
public class OauthController implements OauthApi {

    private final OauthService oauthService;

    @Override
    public ResponseModel login(@RequestBody LoginModel loginModel, HttpServletRequest request) {
        return oauthService.login(loginModel.setGrant_type("password"), request);
    }

    @Override
    public ResponseModel refreshToken(@RequestHeader("Authorization") String refreshToken) {
        return oauthService.refreshToken(new LoginModel().setGrant_type("refresh_token").setRefresh_token(refreshToken));
    }

    @Override
    public ResponseModel logout(@RequestHeader("Authorization") String access_token) {
        return oauthService.logout(access_token);
    }

    @Override
    public ResponseModel smsSend(@RequestParam("mobile") String phone, @RequestBody BaseModel baseModel) {
        return oauthService.smsSend(phone, baseModel);
    }

}
