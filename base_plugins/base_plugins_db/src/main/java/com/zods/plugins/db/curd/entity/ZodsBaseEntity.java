package com.zods.plugins.db.curd.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.util.Date;
/**
 * @description 实体类公共属性
 * @author jianglong
 * @date 2022-06-14
 **/
@Data
public class ZodsBaseEntity extends BaseEntity {

    /**主键ID*/
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**创建人*/
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**创建时间*/
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**最后更新人*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**最后更新时间*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**更新版本号-递增+1*/
    @TableField(fill = FieldFill.INSERT_UPDATE, update = "%s+1")
    @Version
    private Integer version;

}
