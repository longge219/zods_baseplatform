package com.zods.plugins.zods.curd.entity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import java.util.Date;
/**
 * @description 实体类公共属性
 * @author jianglong
 * @date 2022-06-14
 **/
public class GaeaBaseEntity extends BaseEntity {

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

    public GaeaBaseEntity() {
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
