
package com.zods.largescreen.modules.dashboard.controller.dto;
import com.zods.largescreen.common.curd.dto.GaeaBaseDTO;
import com.zods.largescreen.modules.dashboardwidget.controller.dto.ReportDashboardWidgetDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
*
* @description 大屏设计 dto
* @author Raod
* @date 2021-04-12 14:52:21.761
**/
@Data
public class ReportDashboardDto extends GaeaBaseDTO implements Serializable {
    /** 报表编码 */
    private String reportCode;

    /** 看板标题 */
    private String title;

    /** 宽度px */
    private Long width;

    /** 高度px */
    private Long height;

    /** 背景色 */
    private String backgroundColor;

    /** 背景图片 */
    private String backgroundImage;

    /** 工作台中的辅助线 */
    private String presetLine;

    /** 自动刷新间隔秒，数据字典REFRESH_TYPE */
    private Integer refreshSeconds;

    /** 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG */
    private Integer enableFlag;

    /**  0--未删除 1--已删除 DIC_NAME=DEL_FLAG */
    private Integer deleteFlag;

    /** 排序，降序 */
    private Integer sort;

    private List<ReportDashboardWidgetDto> widgets;

}
