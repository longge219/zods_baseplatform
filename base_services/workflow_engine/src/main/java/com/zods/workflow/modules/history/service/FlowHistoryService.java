package com.zods.workflow.modules.history.service;
import com.zods.workflow.common.ResponseBean;
import com.zods.workflow.modules.history.controller.dto.FlowInfo;
import com.zods.workflow.modules.history.controller.params.FlowHistoryParams;
import com.zods.workflow.modules.history.controller.params.HistoryParams;
import java.util.List;
/**
 * @desc 流程历史服务接口
 * @author jianglong
 * @date 2022-08-09
 **/
public interface FlowHistoryService {

    /**查询所有正在运行的流程实例列表*/
    ResponseBean getListProcess( FlowHistoryParams flowHistoryParams);

    /**查询所有流程实例列表-包含在运行和已结束*/
    ResponseBean getListHistProcess( FlowHistoryParams flowHistoryParams);

    /**查询一个流程的活动历史*/
    ResponseBean history(HistoryParams historyParams);

    /**查询所有正在运行的执行实例列表*/
    List<FlowInfo> listExecutions(String name);

    /**查询一个流程的变量*/
    ResponseBean variables(HistoryParams historyParams);

}
