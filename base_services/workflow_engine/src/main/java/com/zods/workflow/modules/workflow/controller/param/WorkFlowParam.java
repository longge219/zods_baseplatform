package com.zods.workflow.modules.workflow.controller.param;
import com.zods.workflow.common.PageParam;
import lombok.Data;
import java.io.Serializable;
/**
 * @author jianglong
 * @version 1.0
 * @Description 工作流请求参数对象
 * @createDate 2022-08-10
 */
@Data
public class WorkFlowParam extends PageParam implements Serializable {

    private String key;

    private String name;

    private boolean latest;
}
