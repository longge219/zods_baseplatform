package com.zods.sso.auth.temp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Wangchao
 * @version 1.0
 * @Description 用户权限
 * @createDate 2022/02/16 14:45
 */
@Data
@Accessors(chain = true)
@Table(name = "auth_role_permission")
public class AuthRolePermission implements Serializable {

    private static final long serialVersionUID = 526431219412293160L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父权限
     */
    @Column(name = "parent_id")
    private Long roleId;
    
    /**
     * 父权限
     */
    @Column(name = "permission_id")
    private Long permissionId;


}
