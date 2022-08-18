package com.zods.workflow.modules.history.controller.params;
import com.zods.workflow.common.PageParam;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jianglong
 * @version 1.0
 * @Description 流程历史请求参数对象
 * @createDate 2022-08-11
 */
@Data
public class HistoryParams extends PageParam implements Serializable {

    private String processInstanceId;

}
