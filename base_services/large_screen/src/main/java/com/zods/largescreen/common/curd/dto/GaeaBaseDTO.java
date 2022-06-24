package com.zods.largescreen.common.curd.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zods.largescreen.common.annotation.Formatter;
import lombok.Data;
import java.util.Date;
/**
 * @description 公共基础DTO
 * @author jianglong
 * @date 2022-06-14
 **/
@Data
public class GaeaBaseDTO extends BaseDTO {
    private Long id;
    @Formatter(
            key = "gaea:user:nickname:${orgCode}",
            replace = {"orgCode"},
            targetField = "createByView"
    )
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Formatter(
            key = "gaea:user:nickname:${orgCode}",
            replace = {"orgCode"},
            targetField = "updateByView"
    )
    private String updateBy;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private Integer version;

    public GaeaBaseDTO() {
    }
}
