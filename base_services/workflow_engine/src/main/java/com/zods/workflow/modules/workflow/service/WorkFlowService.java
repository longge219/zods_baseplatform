package com.zods.workflow.modules.workflow.service;
import com.zods.workflow.common.ResponseBean;
import com.zods.workflow.modules.workflow.controller.param.WorkFlowParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
/**
 * @desc 流程管理服务接口
 * @author jianglong
 * @date 2022-08-22
 **/
public interface WorkFlowService {

     /**上传一个工作流文件*/
     void fileupload(String filename, InputStream is) ;

     /**查询已部署工作流列表*/
     ResponseBean getWorkFlowlist(WorkFlowParam workFlowParam);

     /**删除一次部署的工作流*/
     void removeWorkFlowByDeploymentId(String deploymentId);

     /**查看工作流图片*/
     void showresource(String pdid, HttpServletResponse response);

     /**查看工作流定义*/
     void showProcessDefinition(String pdid, String resource, HttpServletResponse response);

     /**将流程定义转为模型*/
     String exchangeProcessToModel( String pdid);

     /**遍历流程信息*/
     ResponseBean removeProcess(String processInstanceId);

     /**撤销:强制结束一个流程*/
     ResponseBean forceEnd(@PathVariable String taskId);

     /**驳回或跳转到指定节点*/
     ResponseBean jump( String taskId,  String sid);

     /**动态创建流程*/
     ResponseBean createProcess();
}
