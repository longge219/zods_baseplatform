package com.zods.sso.auth.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
/**
 * @author jianglong
 * @version 1.0
 * @Description 用户角色
 * @createDate 2022-06-08
 */
@Data
@Table(name = "auth_role")
public class AuthRole implements Serializable {

    private static final long serialVersionUID = -4923104863933748398L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 备注
     */
    @Column(name = "description")
    private String description;

    /**
     * 创建时间
     */
    @Column(name = "created")
    private Date created;

    /**
     * 最后一次更新时间
     */
    @Column(name = "updated")
    private Date updated;

}
