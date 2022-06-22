package com.zods.largescreen.modules.datasetparam.service.impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zods.largescreen.code.ResponseCode;
import com.zods.largescreen.common.curd.mapper.GaeaBaseMapper;
import com.zods.largescreen.common.exception.BusinessExceptionBuilder;
import com.zods.largescreen.modules.datasetparam.controller.dto.DataSetParamDto;
import com.zods.largescreen.modules.datasetparam.dao.DataSetParamMapper;
import com.zods.largescreen.modules.datasetparam.dao.entity.DataSetParam;
import com.zods.largescreen.modules.datasetparam.service.DataSetParamService;
import com.zods.largescreen.util.ParamsResolverHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author jianglong
 * @version 1.0
 * @Description DataSetParam 数据集动态参数服务实现
 * @createDate 2022-06-22
 */
@Service
@Slf4j
public class DataSetParamServiceImpl implements DataSetParamService {

    //ScriptEngine校验引擎
    private ScriptEngine engine;
    {
        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("JavaScript");
    }

    @Autowired
    private DataSetParamMapper dataSetParamMapper;

    @Override
    public GaeaBaseMapper<DataSetParam> getMapper() {
      return dataSetParamMapper;
    }

    /**
     * 参数校验  js脚本
     * @param dataSetParamDto 数据集动态参数
     * @return boolean
     */
    @Override
    public Object verification(DataSetParamDto dataSetParamDto) {
        String validationRules = dataSetParamDto.getValidationRules();
        if (StringUtils.isNotBlank(validationRules)) {
            try {
                engine.eval(validationRules);
                if(engine instanceof Invocable){
                    Invocable invocable = (Invocable) engine;
                    Object exec = invocable.invokeFunction("verification", dataSetParamDto);
                    ObjectMapper objectMapper = new ObjectMapper();
                    if (exec instanceof Boolean) {
                        return objectMapper.convertValue(exec, Boolean.class);
                    }else {
                        return objectMapper.convertValue(exec, String.class);
                    }
                }
            } catch (Exception ex) {
                throw BusinessExceptionBuilder.build(ResponseCode.EXECUTE_JS_ERROR, ex.getMessage());
            }
        }
        return true;
    }

    /**
     * 参数校验  js脚本
     * @param dataSetParamDtoList 数据集动态参数集合
     * @param contextData 数据集动态参数Map
     * @return
     */
    @Override
    public boolean verification(List<DataSetParamDto> dataSetParamDtoList, Map<String, Object> contextData) {
        if (null == dataSetParamDtoList || dataSetParamDtoList.size() == 0) {
            return true;
        }
        for (DataSetParamDto dataSetParamDto : dataSetParamDtoList) {
            if (null != contextData) {
                String value = contextData.getOrDefault(dataSetParamDto.getParamName(), "").toString();
                dataSetParamDto.setSampleItem(value);
            }
            Object verification = verification(dataSetParamDto);
            if (verification instanceof Boolean) {
                if (!(Boolean) verification) {
                    return false;
                }
            }else {
                //将得到的值重新赋值给contextData
                if (null != contextData) {
                    contextData.put(dataSetParamDto.getParamName(), verification);
                }
                dataSetParamDto.setSampleItem(verification.toString());
            }
        }
        return true;
    }

    /**
     * 参数替换
     * @param contextData 动态参数
     * @param dynSentence 查询语句或接口
     * @return 组装参数后的动态查询语句或接口字符串
     */
    @Override
    public String transform(Map<String, Object> contextData, String dynSentence) {
        if (StringUtils.isBlank(dynSentence)) {
            return dynSentence;
        }
        if (dynSentence.contains("${")) {
            dynSentence = ParamsResolverHelper.resolveParams(contextData, dynSentence);
        }
        if (dynSentence.contains("${")) {
            throw BusinessExceptionBuilder.build(ResponseCode.INCOMPLETE_PARAMETER_REPLACEMENT_VALUES, dynSentence);
        }
        return dynSentence;
    }

    /**
     * 参数替换
     * @param dataSetParamDtoList 动态参数
     * @param dynSentence 查询语句或接口
     * @return 组装参数后的动态查询语句或接口字符串
     */
    @Override
    public String transform(List<DataSetParamDto> dataSetParamDtoList, String dynSentence) {
        Map<String, Object> contextData = new HashMap<>();
        if (null == dataSetParamDtoList || dataSetParamDtoList.size() <= 0) {
            return dynSentence;
        }
        dataSetParamDtoList.forEach(dataSetParamDto -> {
            contextData.put(dataSetParamDto.getParamName(), dataSetParamDto.getSampleItem());
        });
        return transform(contextData, dynSentence);
    }











}
