package com.zods.largescreen.modules.dataset.controller.param;
import com.zods.largescreen.modules.datasetparam.controller.dto.DataSetParamDto;
import com.zods.largescreen.modules.datasettransform.controller.dto.DataSetTransformDto;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
/**
 * @desc 数据集动态查询输入类 dto
 * @author jianglong
 * @date 2022-06-22
 **/
@Data
public class DataSetTestTransformParam implements Serializable{

    /** 数据源编码 */
    private String sourceCode;

    /** 动态查询sql或者接口中的请求体 */
    private String dynSentence;

    /** 数据集类型 */
    private String setType;

    /** 请求参数集合 */
    private List<DataSetParamDto> dataSetParamDtoList;

    /** 数据转换集合SQL HTTP */
    private List<DataSetTransformDto> dataSetTransformDtoList;

}
