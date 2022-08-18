package com.zods.workflow.modules.history.controller;
import com.zods.workflow.common.ResponseBean;
import com.zods.workflow.modules.ActivitiTracingChart;
import com.zods.workflow.modules.history.controller.dto.FlowInfo;
import com.zods.workflow.modules.history.controller.params.FlowHistoryParams;
import com.zods.workflow.modules.history.controller.params.HistoryParams;
import com.zods.workflow.modules.history.service.FlowHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
/**
 * @desc 流程历史接口
 * @author jianglong
 * @date 2022-08-09
 **/
@Api(value = "流程监控接口")
@Controller
@RequestMapping("/flow/monitor")
public class FlowHistoryController {







    @Resource
    private RuntimeService runtimeService;

    @Resource
    ProcessEngineConfiguration configuration;

    @Resource
    private ActivitiTracingChart activitiTracingChart;



    @Resource
    private FlowHistoryService flowHistoryServiceImpl;


    private String prefix = "activiti/monitor";

    @GetMapping("/instance")
    public String processList() {
        return prefix + "/processInstance";
    }

    @GetMapping("/history")
    public String processHistory() {
        return prefix + "/processHistory";
    }

    @GetMapping("/execution")
    public String execution() {
        return prefix + "/execution";
    }

    @GetMapping("/historyDetail")
    public String historyDetail(String processInstanceId, ModelMap mmap) {
        mmap.put("processInstanceId", processInstanceId);
        return prefix + "/processHistoryDetail";
    }

    @GetMapping("/processVariablesDetail")
    public String processVariablesDetail(String processInstanceId, ModelMap mmap) {
        mmap.put("processInstanceId", processInstanceId);
        return prefix + "/processVariablesDetail";
    }

    @ApiOperation("查询所有正在运行的流程实例列表")
    @RequestMapping(value = "/listProcess", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean getListProcess(@RequestBody FlowHistoryParams flowHistoryParams) {
        return flowHistoryServiceImpl.getListProcess(flowHistoryParams);
    }

    @ApiOperation("查询所有流程实例列表-包含在运行和已结束")
    @RequestMapping(value = "/listHistoryProcess", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean listHistoryProcess(@RequestBody FlowHistoryParams flowHistoryParams) {
        return flowHistoryServiceImpl.getListHistProcess(flowHistoryParams);
    }

    @ApiOperation("查询一个流程的活动历史")
    @RequestMapping(value = "/history/{processInstanceId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean history(@RequestBody HistoryParams historyParams) {
        return flowHistoryServiceImpl.history(historyParams);
    }

    @ApiOperation("查询所有正在运行的执行实例列表")
    @RequestMapping(value = "/listExecutions", method = RequestMethod.POST)
    @ResponseBody
    public List<FlowInfo> listExecutions(@RequestParam(required = false) String name) {
       return  flowHistoryServiceImpl.listExecutions(name);
    }

    @ApiOperation("流程图进度追踪,已结束标红，运行中标绿")
    @RequestMapping(value = {"/traceProcess/{processInstanceId}"}, method = RequestMethod.GET)
    public void traceprocess(@PathVariable String processInstanceId, HttpServletResponse response) throws IOException {
        activitiTracingChart.generateFlowChart(processInstanceId, response.getOutputStream());
    }

    @ApiOperation("挂起一个流程实例")
    @RequestMapping(value = "/suspend/{processInstanceId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean suspend(@PathVariable String processInstanceId) {
        runtimeService.suspendProcessInstanceById(processInstanceId);
        return ResponseBean.builder().data("SUCCESS").build();
    }

    @ApiOperation("唤醒一个挂起的流程实例")
    @RequestMapping(value = "/run/{processInstanceId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean rerun(@PathVariable String processInstanceId) {
        runtimeService.activateProcessInstanceById(processInstanceId);
        return ResponseBean.builder().data("SUCCESS").build();
    }

    @ApiOperation("查询一个流程的变量")
    @RequestMapping(value = "/variables/{processInstanceId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean variables(@RequestBody HistoryParams historyParams) {
                return flowHistoryServiceImpl.variables(historyParams);
    }

}
