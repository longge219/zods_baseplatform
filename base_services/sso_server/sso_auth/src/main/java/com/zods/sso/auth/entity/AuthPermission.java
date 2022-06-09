package com.zods.sso.auth.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
/**
 * @author jianglong
 * @version 1.0
 * @Description 用户权限
 * @createDate 2022-06-08
 */
@Data
@Accessors(chain = true)
@Table(name = "auth_permission")
public class AuthPermission implements Serializable {

    private static final long serialVersionUID = 6049296145324293547L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父权限
     */
    @Column(name = "parent_id")
    private Long parentId = 0L;

    /**
     * 权限名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 权限英文名称
     */
    @Column(name = "`enname`")
    private String enname;

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
