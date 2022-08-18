package com.zods.workflow.modules.model.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zods.workflow.common.ResponseBean;
import com.zods.workflow.modules.model.controller.param.ModelParam;
import com.zods.workflow.modules.model.service.ModelService;
import io.swagger.annotations.ApiOperation;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * @desc 流程图模型管理-web层
 * @author jianglong
 * @date 2022-08-09
 **/
@RestController
@RequestMapping("/activiti-explorer")
public class ModelController {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ModelController.class);

    @Autowired
    private ModelService modelServiceImpl;

    /**
     * 打开在线编辑器时加载指定模型到页面
     * @param modelId
     * @return
     */
    @RequestMapping(value="/model/{modelId}/json", method = RequestMethod.GET, produces = "application/json")
    public ObjectNode getEditorJson(@PathVariable String modelId) {
        return modelServiceImpl.getEditorJson(modelId);
    }

    /**
     * 保存流程图编辑器的信息
     * @param modelParam
     */
    @RequestMapping(value = "/model/{modelId}/save", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void saveModel(@RequestBody ModelParam modelParam) {
        modelServiceImpl.saveModel(modelParam);
    }

    /**
     * 获取流程图编辑器的汉化文件
     * @return: 汉化文件字符串
     */
    @RequestMapping(value = "/editor/stencilset", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getStencilset() {
        InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");
        try {
            return IOUtils.toString(stencilsetStream, "utf-8");
        } catch (Exception e) {
            throw new ActivitiException("Error while loading stencil set", e);
        }
    }


    @ApiOperation("查询所有模型")
    @RequestMapping(value = "/modelLists", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean modelLists(@RequestBody ModelParam modelParam) {
               return modelServiceImpl.modelLists(modelParam);
    }


    /**
     * 新增模型
     */
    @PostMapping("/add")
    @ResponseBody
    public ResponseBean addSave(ModelParam modelRequest) {
        try{
            return modelServiceImpl.addSave(modelRequest);
        }catch(Exception e){
             e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("/deploy/{modelId}")
    @ResponseBody
    public ResponseBean modelDeployment(@PathVariable String modelId) {
           return modelServiceImpl.modelDeployment(modelId);
    }

    @PostMapping("/remove/{modelId}")
    @ResponseBody
    public ResponseBean removeModel(@PathVariable String modelId) {
          return modelServiceImpl.removeModel(modelId);
    }

    @GetMapping("/export/{modelId}")
    public void modelExport(@PathVariable String modelId, HttpServletResponse response) throws IOException {
         modelServiceImpl.modelExport(modelId,response);
    }
}
