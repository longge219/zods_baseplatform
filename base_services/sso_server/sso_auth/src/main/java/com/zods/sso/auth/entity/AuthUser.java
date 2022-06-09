package com.zods.sso.auth.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
/**
 * @author jianglong
 * @version 1.0
 * @Description 用户
 * @createDate 2020/09/09 14:45
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auth_user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class AuthUser implements Serializable {


    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码，加密存储
     */
    @Column(name = "`password`")
    @JsonIgnore
    private String password;

    /**
     * 注册手机号
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 注册邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 是否启用
     */
    @Column(name = "is_enable")
    private Boolean isEnable;

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

    /**姓名*/
    private String fullName;

    /**账号类型：true:移动账号，false:平台账号*/
    private Boolean accountType;
    /**备注*/
    private String description;
}
