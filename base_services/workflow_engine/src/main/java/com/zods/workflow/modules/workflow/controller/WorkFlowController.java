package com.zods.workflow.modules.workflow.controller;
import com.zods.workflow.common.ResponseBean;
import com.zods.workflow.modules.workflow.controller.param.WorkFlowParam;
import com.zods.workflow.modules.workflow.service.WorkFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.activiti.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @desc 流程管理-web层
 * @author jianglong
 * @date 2022-08-09
 **/
@Controller
@RequestMapping("/flow/manage")
@Api(tags = "部署管理接口")
@Slf4j
public class WorkFlowController {

    @Autowired
    private WorkFlowService workFlowServiceImpl;

    @ApiOperation("上传一个工作流文件")
    @RequestMapping(value = "/workFlowFileUpload", method = RequestMethod.POST)
    @ResponseBody
    public void workFlowFileUpload(@RequestParam MultipartFile uploadfile){
        try {
             workFlowServiceImpl.fileupload(uploadfile.getOriginalFilename(),uploadfile.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
                log.error("部署失败");
            }
    }

    @ApiOperation("查询已部署工作流列表")
    @RequestMapping(value = "/getWorkFlowlist", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean getWorkFlowlist(@RequestBody WorkFlowParam workFlowParam) {
       return workFlowServiceImpl.getWorkFlowlist(workFlowParam);
    }

    @ApiOperation("删除一次部署的工作流")
    @RequestMapping(value = "/remove/{deploymentId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean removeWorkFlowByDeploymentId(@PathVariable String deploymentId) {
        workFlowServiceImpl.removeWorkFlowByDeploymentId(deploymentId);
        return ResponseBean.builder().build();
    }


    @ApiOperation("查看工作流图片")
    @RequestMapping(value = "/showresource", method = RequestMethod.GET)
    public void showresource(@RequestParam("pdid") String pdid, HttpServletResponse response) throws Exception {
        workFlowServiceImpl.showresource(pdid,response);
    }

    @ApiOperation("查看工作流定义")
    @RequestMapping(value = "/showProcessDefinition/{pdid}/{resource}", method = RequestMethod.GET)
    public void showProcessDefinition(@PathVariable("pdid") String pdid, @PathVariable(value="resource") String resource,
                                      HttpServletResponse response) throws Exception {
        workFlowServiceImpl.showProcessDefinition(pdid,resource,response);
    }

    @ApiOperation("将流程定义转为模型")
    @RequestMapping(value = "/exchangeProcessToModel/{pdid}", method = RequestMethod.GET)
    @ResponseBody
    public String exchangeProcessToModel(@PathVariable("pdid") String pdid) throws Exception {
             return workFlowServiceImpl.exchangeProcessToModel(pdid);
    }


    @ApiOperation("遍历流程信息")
    @GetMapping(value = "/info/{processInstanceId}")
    @ResponseBody
    public ResponseBean removeProcess(@PathVariable String processInstanceId) {
        return workFlowServiceImpl.removeProcess(processInstanceId);
    }

    @ApiOperation("撤销:强制结束一个流程")
    @GetMapping(value = "/forceEnd/{taskId}")
    @ResponseBody
    public ResponseBean forceEnd(@PathVariable String taskId) {
        return workFlowServiceImpl.forceEnd(taskId);
    }

    @ApiOperation("驳回或跳转到指定节点")
    @GetMapping(value = "/jump/{taskId}/{sid}")
    @ResponseBody
    public ResponseBean jump(@PathVariable String taskId, @PathVariable String sid) {
        return workFlowServiceImpl.jump(taskId,sid);
    }

    @ApiOperation("动态创建流程")
    @GetMapping(value = "/createProcess")
    @ResponseBody
    public ResponseBean createProcess() {
        return workFlowServiceImpl.createProcess();
    }


}
