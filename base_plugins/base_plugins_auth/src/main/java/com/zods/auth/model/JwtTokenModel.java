package com.zods.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2020/10/9 15:00
 */
@Data
@Builder
@AllArgsConstructor
public class JwtTokenModel {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 账号名
     */
    private String user_name;

    /**
     * 账号名
     */
    private String roleName;

    /**
     * 权限
     */
    private List<String> authorities;

    /**
     * 数据权限:驿站编码
     */
    private List<String> purviewCodes;

    /**
     * 客户端id
     */
    private String client_id;

    /**
     * oauth2 app 鉴权范围
     */
    private List<String> scope;

    /**
     * jwt id
     */
    private String jti;

    /**
     * refresh token 超时时间
     */
    private Long iat;

    /**
     * access token 超时时间
     */
    private Long exp;

    /**
     * 当前token生成时间
     */
    private String time;

    public JwtTokenModel() {
    }

    public String getTokenKey() {
        return userId + ":" + time;
    }
}
