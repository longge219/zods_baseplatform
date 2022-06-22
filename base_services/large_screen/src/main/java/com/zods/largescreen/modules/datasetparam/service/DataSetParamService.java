package com.zods.largescreen.modules.datasetparam.service;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.datasetparam.controller.dto.DataSetParamDto;
import com.zods.largescreen.modules.datasetparam.controller.param.DataSetParamParam;
import com.zods.largescreen.modules.datasetparam.dao.entity.DataSetParam;
import java.util.List;
import java.util.Map;
/**
 * @author jianglong
 * @version 1.0
 * @Description DataSetParam 数据集动态参数服务接口
 * @createDate 2022-06-22
 */
public interface DataSetParamService extends GaeaBaseService<DataSetParamParam, DataSetParam> {

    /**
     * 参数校验  js脚本
     * @param dataSetParamDto 数据集动态参数
     * @return boolean
     */
    Object verification(DataSetParamDto dataSetParamDto);

    /**
     * 参数校验  js脚本
     * @param dataSetParamDtoList 数据集动态参数集合
     * @param contextData 数据集动态参数Map
     * @return
     */
    boolean verification(List<DataSetParamDto> dataSetParamDtoList, Map<String, Object> contextData);

    /**
     * 参数替换
     * @param contextData 动态参数
     * @param dynSentence 查询语句或接口
     * @return 组装参数后的动态查询语句或接口字符串
     */
    String transform(Map<String, Object> contextData, String dynSentence);

    /**
     * 参数替换
     * @param dataSetParamDtoList 动态参数
     * @param dynSentence 查询语句或接口
     * @return 组装参数后的动态查询语句或接口字符串
     */
    String transform(List<DataSetParamDto> dataSetParamDtoList, String dynSentence);

}
