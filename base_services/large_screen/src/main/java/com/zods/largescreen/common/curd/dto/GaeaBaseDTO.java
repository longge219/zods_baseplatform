package com.zods.largescreen.common.curd.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zods.largescreen.common.annotation.Formatter;
import com.zods.largescreen.common.utils.GaeaUtils;
import java.util.Date;
/**
 * @description 公共基础DTO
 * @author jianglong
 * @date 2022-06-14
 **/
public class GaeaBaseDTO extends BaseDTO {
    private Long id;
    @Formatter(
            key = "gaea:user:nickname:${orgCode}",
            replace = {"orgCode"},
            targetField = "createByView"
    )
    private String createBy;

    private String createByView;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createTime;

    @Formatter(
            key = "gaea:user:nickname:${orgCode}",
            replace = {"orgCode"},
            targetField = "updateByView"
    )
    private String updateBy;

    private String updateByView;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date updateTime;

    private Integer version;

    public GaeaBaseDTO() {
    }

    public String getAccessKey() {
        return this.id == null ? null : GaeaUtils.getPassKey(this.id);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCreateByView() {
        return this.createByView;
    }

    public void setCreateByView(String createByView) {
        this.createByView = createByView;
    }

    public String getUpdateByView() {
        return this.updateByView;
    }

    public void setUpdateByView(String updateByView) {
        this.updateByView = updateByView;
    }
}
