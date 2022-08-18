package com.zods.workflow.modules.model.service.impl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zods.workflow.common.ResponseBean;
import com.zods.workflow.modules.model.controller.param.ModelParam;
import com.zods.workflow.modules.model.service.ModelService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ModelQuery;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import lombok.extern.slf4j.Slf4j;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * @desc 流程图模型管理-服务接口实现
 * @author jianglong
 * @date 2022-08-09
 **/
@Service
@Slf4j
public class ModelServiceImpl implements ModelService {

    @Resource
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;
    /**
     * 打开在线编辑器时加载指定模型到页面
     * @param modelId
     * @return
     */
    public ObjectNode getEditorJson(String modelId){
        ObjectNode modelNode = null;
        Model model = repositoryService.getModel(modelId);
        if (model != null) {
            try {
                if (StringUtils.isNotEmpty(model.getMetaInfo())) {
                    modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
                } else {
                    modelNode = objectMapper.createObjectNode();
                    modelNode.put(ModelDataJsonConstants.MODEL_NAME, model.getName());
                }
                modelNode.put(ModelDataJsonConstants.MODEL_ID, model.getId());
                ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(
                        new String(repositoryService.getModelEditorSource(model.getId()), "utf-8"));
                modelNode.put("model", editorJsonNode);

            } catch (Exception e) {
                log.error("Error creating model JSON", e);
                throw new ActivitiException("Error creating model JSON", e);
            }
        }
        return modelNode;
    }


    /**
     * 保存流程图编辑器的信息
     * @param modelParam
     */
    public void  saveModel(ModelParam modelParam){
        try {
            Model model = repositoryService.getModel(modelParam.getModelId());
            ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
            modelJson.put(ModelDataJsonConstants.MODEL_NAME, modelParam.getName());
            modelJson.put(ModelDataJsonConstants.MODEL_DESCRIPTION, modelParam.getDescription());
            model.setMetaInfo(modelJson.toString());
            model.setName(modelParam.getName());
            model.setDeploymentId(null);
            Integer version = model.getVersion();
            version++;
            model.setVersion(version);
            repositoryService.saveModel(model);
            repositoryService.addModelEditorSource(model.getId(), modelParam.getJson_xml().getBytes("utf-8"));
            InputStream svgStream = new ByteArrayInputStream(modelParam.getSvg_xml().getBytes("utf-8"));
            TranscoderInput input = new TranscoderInput(svgStream);
            PNGTranscoder transcoder = new PNGTranscoder();
            // Setup output
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outStream);
            // Do the transformation
            transcoder.transcode(input, output);
            final byte[] result = outStream.toByteArray();
            repositoryService.addModelEditorSourceExtra(model.getId(), result);
            outStream.close();
        } catch (Exception e) {
            log.error("Error saving model", e);
            throw new ActivitiException("Error saving model", e);
        }
    }

    /**查询所有模型*/
    public ResponseBean modelLists(ModelParam modelParam){
        ModelQuery query = repositoryService.createModelQuery();
        if (StringUtils.isNotEmpty(modelParam.getKey())) {
            query.modelKey(modelParam.getKey());
        }
        if (StringUtils.isNotEmpty(modelParam.getName())) {
            query.modelName(modelParam.getName());
        }
        int start = (modelParam.getPageNumber() - 1) * modelParam.getPageSize();
        List<Model> page = query.orderByCreateTime().desc().listPage(start, modelParam.getPageSize());
        return ResponseBean.builder().data(page).build();
    }


    /**新增模型*/
    public ResponseBean addSave(ModelParam modelRequest)  throws JsonProcessingException {
        Model model = repositoryService.newModel();
        model.setCategory(modelRequest.getCategory());
        model.setKey(modelRequest.getKey());
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, modelRequest.getName());
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, modelRequest.getDescription());
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, modelRequest.getVersion());
        model.setMetaInfo(modelNode.toString());
        model.setName(modelRequest.getName());
        model.setVersion(modelRequest.getVersion());
        ModelQuery modelQuery = repositoryService.createModelQuery();
        List<Model> list = modelQuery.modelKey(modelRequest.getKey()).list();
        if (list.size() > 0) {
            return  ResponseBean.builder().data("模型标识不能重复").build();
        } else {
            // 保存模型到act_re_model表
            repositoryService.saveModel(model);
            HashMap<String, Object> content = new HashMap();
            content.put("resourceId", model.getId());
            HashMap<String, String> properties = new HashMap();
            properties.put("process_id", modelRequest.getKey());
            properties.put("name", modelRequest.getName());
            properties.put("category", modelRequest.getCategory());
            content.put("properties", properties);
            HashMap<String, String> stencilset = new HashMap();
            stencilset.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            content.put("stencilset", stencilset);
            // 保存模型文件到act_ge_bytearray表
            repositoryService.addModelEditorSource(model.getId(), objectMapper.writeValueAsBytes(content));
            return  ResponseBean.builder().data(model).build();
        }
    }

    public ResponseBean modelDeployment(String modelId){
        try {
            Model model = repositoryService.getModel(modelId);
            byte[] modelData = repositoryService.getModelEditorSource(modelId);
            JsonNode jsonNode = objectMapper.readTree(modelData);
            BpmnModel bpmnModel = (new BpmnJsonConverter()).convertToBpmnModel(jsonNode);
            Deployment deploy = repositoryService.createDeployment().category(model.getCategory())
                    .name(model.getName()).key(model.getKey())
                    .addBpmnModel(model.getKey() + ".bpmn20.xml", bpmnModel)
                    .deploy();
            model.setDeploymentId(deploy.getId());
            repositoryService.saveModel(model);
            return  ResponseBean.builder().data("SUCCESS").build();
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseBean.builder().data("流程图不合规范，请重新设计").build();
        }
    }

    /**删除模型*/
   public  ResponseBean removeModel(String modelId){
       repositoryService.deleteModel(modelId);
       return ResponseBean.builder().data("删除成功").build();
    }

    public void modelExport( String modelId, HttpServletResponse response) throws IOException {
        byte[] modelData = repositoryService.getModelEditorSource(modelId);
        JsonNode jsonNode = objectMapper.readTree(modelData);
        BpmnModel bpmnModel = (new BpmnJsonConverter()).convertToBpmnModel(jsonNode);
        byte[] xmlBytes = (new BpmnXMLConverter()).convertToXML(bpmnModel, "UTF-8");
        ByteArrayInputStream in = new ByteArrayInputStream(xmlBytes);
        IOUtils.copy(in, response.getOutputStream());
        String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
        response.setHeader("Content-Disposition","attachment;filename=" + filename);
        response.setHeader("content-Type", "application/xml");
        response.flushBuffer();
    }

}
