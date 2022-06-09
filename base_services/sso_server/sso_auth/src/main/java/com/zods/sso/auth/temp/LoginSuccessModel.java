package com.zods.sso.auth.temp;

import lombok.Data;

/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2020/12/26 15:43
 */
@Data
public class LoginSuccessModel {

    public LoginSuccessModel(String accessToken, String refreshToken, int expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    private String accessToken;

    private String refreshToken;

    private int expiresIn;

}
