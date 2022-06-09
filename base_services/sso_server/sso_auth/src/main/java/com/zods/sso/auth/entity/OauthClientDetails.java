package com.zods.sso.auth.entity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * @author jianglong
 * @version 1.0
 * @Description 用户
 * @createDate 2022-06-08
 */
@Data
@Table(name = "oauth_client_details")
public class OauthClientDetails implements Serializable {

    private static final long serialVersionUID = 6464816132738812339L;

    /**
     * 主键,唯一标识每一个客户端
     */
    @Id
    @Column(name = "client_id")
    private String clientId;

    /**
     * 客户端所能访问的资源id集合,多个资源时用逗号(,)分隔
     */
    @Column(name = "resource_ids")
    private String resourceIds;

    /**
     * 客户端(client)的访问密匙;
     */
    @Column(name = "client_secret")
    private String clientSecret;

    /**
     * 客户端申请的权限范围
     */
    @Column(name = "scope")
    private String scope;

    /**
     * 客户端支持的grant_type,
     * 可选值包括authorization_code,password,refresh_token,implicit,client_credentials,
     * 若支持多个grant_type用逗号(,)分隔
     */
    @Column(name = "authorized_grant_types")
    private String authorizedGrantTypes;

    /**
     * 客户端的重定向URI,可为空
     */
    @Column(name = "web_server_redirect_uri")
    private String webServerRedirectUri;

    /**
     * 客户端所拥有的Spring Security的权限值,可选, 若有多个权限值,用逗号(,)分隔
     */
    @Column(name = "authorities")
    private String authorities;

    /**
     * 客户端的access_token的有效时间值(单位:秒),可选
     */
    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;

    /**
     * 客户端的refresh_token的有效时间值(单位:秒),可选
     */
    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;

    /**
     * 预留的字段,在Oauth的流程中没有实际的使用,可选,但若设置值,必须是JSON格式的数据
     */
    @Column(name = "additional_information")
    private String additionalInformation;

    /**
     * 用户是否自动Approval操作, 默认值为 'false', 可选值包括 'true','false', 'read','write'.
     * 该字段只适用于grant_type="authorization_code"的情况,
     * 当用户登录成功后,若该值为'true'或支持的scope值,则会跳过用户Approve的页面, 直接授权.
     * 该字段与 trusted 有类似的功能, 是 spring-security-oauth2 的 2.0 版本后添加的新属性.
     */
    @Column(name = "autoapprove")
    private String autoapprove;

}
