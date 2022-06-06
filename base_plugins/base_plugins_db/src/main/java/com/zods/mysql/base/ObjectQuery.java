package com.zods.mysql.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 分页查询条件基类，有其他查询参数继承此类即可
 */
@Data
public class ObjectQuery implements Serializable {

    @ApiModelProperty(name = "fuzzyKey", value = "模糊查询条件", dataType = "String")
    private String fuzzyKey;

    /**每页显示记录数*/
    @ApiModelProperty(name = "pageSize", value = "每页大小（大于等于1）", dataType = "int")
    private int pageSize = 10;

    /**当前页页码*/
    @ApiModelProperty(name = "currentPage", value = "第几页（大于等于1）", dataType = "int")
    private int currentPage = 1;

    @ApiModelProperty(value="排序字段，默认使用ID来排序")
    private String sortField = "id";

    @ApiModelProperty(value = "排序方式，默认降序【desc/asc】")
    private String sortOrder = "desc";

    @ApiModelProperty(hidden = true)
    private final String DESC = "desc";

    @ApiModelProperty(hidden = true)
    private final String ASC = "asc";

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    public boolean isAsc(){
        return ASC.equals(getSortOrder());
    }

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    public int getPageOffset() {
        return (currentPage - 1) * pageSize;
    }

}
