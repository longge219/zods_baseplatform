package com.zods.workflow.modules.model.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zods.workflow.common.ResponseBean;
import com.zods.workflow.modules.model.controller.param.ModelParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @desc 流程图模型管理-服务接口
 * @author jianglong
 * @date 2022-08-09
 **/
public interface ModelService {

    /**
     * 打开在线编辑器时加载指定模型到页面
     * @param modelId
     * @return
     */
     ObjectNode getEditorJson(String modelId);


    /**
     * 保存流程图编辑器的信息
     * @param modelParam
     */
     void saveModel(ModelParam modelParam);



     /**查询所有模型*/
    ResponseBean modelLists( ModelParam modelParam);


    /**新增模型*/
    ResponseBean addSave(ModelParam modelRequest)  throws JsonProcessingException;


    ResponseBean modelDeployment(String modelId);


    /**删除模型*/
    ResponseBean removeModel(String modelId);


    void modelExport( String modelId, HttpServletResponse response) throws IOException;

}
