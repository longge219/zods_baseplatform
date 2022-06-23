package com.zods.largescreen.modules.report.controller.param;
import com.zods.largescreen.common.annotation.Query;
import com.zods.largescreen.common.constant.QueryEnum;
import com.zods.largescreen.common.curd.params.PageParam;
import lombok.Data;
import java.io.Serializable;
/**
 * @desc 报表查询请求参数对象
 * @author jianglong
 * @date 2022-06-23
 **/
@Data
public class ReportParam extends PageParam implements Serializable{

    /** 报表名称 */
    @Query(QueryEnum.LIKE)
    private String reportName;

    /** 报表作者 */
    @Query(QueryEnum.LIKE)
    private String reportAuthor;

    /** 报表编码 */
    @Query(QueryEnum.LIKE)
    private String reportCode;

    /** 报表类型 */
    @Query(QueryEnum.EQ)
    private String reportType;
}
