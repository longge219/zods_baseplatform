package com.zods.largescreen.modules.reportexcel.controller.dto;
import com.zods.largescreen.common.curd.dto.GaeaBaseDTO;
import lombok.Data;
import java.io.Serializable;
/**
 * @desc Excel报表Dto
 * @author jianglong
 * @date 2022-06-23
 **/
@Data
public class ReportExcelDto extends GaeaBaseDTO implements Serializable {

    /**报表名称*/
    private String reportName;

    /**报表编码*/
    private String reportCode;

    /**数据集编码，以|分割*/
    private String setCodes;

    /**分组*/
    private String reportGroup;

    /**数据集查询参数*/
    private String setParam;

    /**报表json字符串*/
    private String jsonStr;

    /**报表类型*/
    private String reportType;

    /**数据总计*/
    private long total;

    /**导出类型*/
    private String exportType;

}
